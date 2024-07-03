package com.alibaba.easytools.spring.oss;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.alibaba.easytools.base.constant.SymbolConstant;
import com.alibaba.easytools.base.excption.BusinessException;
import com.alibaba.easytools.base.excption.CommonErrorEnum;
import com.alibaba.easytools.base.excption.SystemException;
import com.alibaba.easytools.common.util.EasyEnumUtils;
import com.alibaba.easytools.common.util.EasyOptionalUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSErrorCode;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.HttpUtil;
import com.aliyun.oss.internal.Mimetypes;
import com.aliyun.oss.internal.OSSUtils;
import com.aliyun.oss.internal.RequestParameters;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.imm.model.v20170906.CreateOfficeConversionTaskRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.aliyun.oss.internal.OSSConstants.DEFAULT_CHARSET_NAME;

/**
 * 自定义OSS的客户端
 *
 * @author 是仪
 * @deprecated  使用 EasyOssClient
 **/
@Slf4j
@Data
@Deprecated
public class OssClient {
    /**
     * https前缀
     */
    private static final String HTTPS_PREFIX = "https:";

    /**
     * 如果不传kind 全部放到这里
     */
    private static final String DEFAULT_KIND = "default";

    /**
     * 预览文件的后缀
     */
    public static final String PREVIEW_SRC_SUFFIX = "_preview";
    /**
     * oss枚举的类
     */
    private Class<? extends OssKindEnum> ossKindEnumClass;

    /**
     * 读文件的oss
     */
    private OSSClient readOss;
    /**
     * 写文件的oss
     * 可能会使用 internalEndpoint
     */
    private OSSClient writeOss;

    private String projectName;
    private String bucketName;
    private String endpoint;
    private String baseUrl;

    /**
     * bucketName+endpoint
     */
    private String ossDomain;

    /**
     * 绑定的域名 类似于 oss.alibaba.com  不要带任何前缀
     * 可以为空
     * 如果为空 则会生成 bucketName+endpoint 的连接
     * 如果不为空 则会生成 domain 的连接
     */
    private String domain;
    /**
     * 处理预览的时候有用
     */
    private IAcsClient iAcsClient;

    /**
     * 多媒体项目
     */
    private String immProject;

    /**
     * 是否需要替换域名
     * 生成私有的连接的时候 返回的是 bucketName+endpoint
     * 这个时候 我们要把替换成  domain ,当然只有domain!=bucketName+endpoint 的情况
     */
    private Boolean needReplaceDomain;

    public OssClient(OSSClient oss, String projectName, String bucketName, String endpoint,
        Class<? extends OssKindEnum> ossKindEnumClass) {
        this(projectName, bucketName, endpoint, ossKindEnumClass, null, null, null);
        this.readOss = oss;
        this.writeOss = oss;
    }

    /**
     * 构建oss
     *
     * @param projectName      项目名 多个项目公用一个oss 可以传值
     * @param bucketName
     * @param endpoint
     * @param ossKindEnumClass
     * @param iAcsClient
     * @param immProject
     * @param domain
     */
    public OssClient(String projectName, String bucketName, String endpoint,
        Class<? extends OssKindEnum> ossKindEnumClass, IAcsClient iAcsClient, String immProject, String domain) {
        this.projectName = projectName;
        this.iAcsClient = iAcsClient;
        this.bucketName = bucketName;
        this.endpoint = endpoint;
        if (ossKindEnumClass != null) {
            this.ossKindEnumClass = ossKindEnumClass;
        } else {
            this.ossKindEnumClass = DefaultOssKindEnum.class;
        }
        this.immProject = immProject;
        this.ossDomain = bucketName + "." + endpoint;
        this.domain = domain;
        this.needReplaceDomain = this.domain != null;
        // 为空 则拼接domain
        if (this.domain == null) {
            this.domain = ossDomain;
        }
        this.baseUrl = "https://" + this.domain + "/";
    }

    /**
     * 上传文件
     *
     * @param file 文件 不能为空
     * @return 返回文件的唯一key 可以获取文件url
     */
    public String put(File file) {
        return put(file, null, null, null, null, null);
    }

    /**
     * 上传文件
     *
     * @param file 文件 不能为空
     * @param kind 文件种类 可以为空 在每个项目创建OSSKindConstants 尽量不用重复 为空默认放在default下面
     * @return 返回文件的唯一key 可以获取文件url
     */
    public String put(File file, String kind) {
        return put(file, kind, null, null, null, null);
    }

    /**
     * 上传文件
     *
     * @param file        文件 不能为空
     * @param ossKindEnum 文件种类
     * @return 返回文件的唯一key 可以获取文件url
     */
    public String put(File file, OssKindEnum ossKindEnum) {
        return put(file, ossKindEnum.getCode(), null, null, null, ossKindEnum.getObjectAcl());
    }

    /**
     * 上传文件
     *
     * @param file     文件 不能为空
     * @param kind     文件种类 可以为空 在每个项目创建OSSKindConstants 尽量不用重复 为空默认放在default下面
     * @param fileName 用户下载时候的文件名 可以为空 为空则根据file的名字下载 如果还为空 则自动生成uuid
     * @return 返回文件的唯一key 可以获取文件url
     */
    public String put(File file, String kind, String fileName) {
        return put(file, kind, fileName, null, null, null);
    }

    /**
     * 上传文件
     *
     * @param file        文件 不能为空
     * @param kind        文件种类 可以为空 在每个项目创建OSSKindConstants 尽量不用重复 为空默认放在default下面
     * @param fileName    用户下载时候的文件名 可以为空 为空则根据file的名字下载 如果还为空 则自动生成uuid
     * @param contentType 文件类型 可以为空 用户打开时候浏览器的contentType 为空优先根据传入的fileName的后缀区分 如果fileName没有传入则
     *                    为空则根据file的名字的后缀区分 如果还为空 则默认流application/octet-stream
     * @return 返回文件的唯一key 可以获取文件url
     */
    public String put(File file, String kind, String fileName, String contentType) {
        return put(file, kind, fileName, contentType, null, null);
    }

    /**
     * 上传文件
     *
     * @param file        文件 不能为空
     * @param fileName    用户下载时候的文件名 可以为空 为空则根据file的名字下载 如果还为空 则自动生成uuid
     * @param contentType 文件类型 可以为空 用户打开时候浏览器的contentType 为空优先根据传入的fileName的后缀区分 如果fileName没有传入则
     *                    为空则根据file的名字的后缀区分 如果还为空 则默认流application/octet-stream
     * @return 返回文件的唯一key 可以获取文件url
     */
    public String put(String key, File file, String fileName, String contentType) {
        return put(file, null, fileName, contentType, key, null);
    }

    /**
     * 上传文件
     *
     * @param file        文件 不能为空
     * @param kind        文件种类 可以为空 在每个项目创建OSSKindConstants 尽量不用重复 为空默认放在default下面
     * @param fileName    用户下载时候的文件名 可以为空 为空则根据file的名字下载 如果还为空 则自动生成uuid
     * @param contentType 文件类型 可以为空 用户打开时候浏览器的contentType 为空优先根据传入的fileName的后缀区分 如果fileName没有传入则
     *                    为空则根据file的名字的后缀区分 如果还为空 则默认流application/octet-stream
     * @param key         服务器的唯一key
     * @param objectAcl   对象权限控制 默认私有的
     * @return 返回文件的唯一key 可以获取文件url
     * @see CannedAccessControlList
     */
    public String put(File file, String kind, String fileName, String contentType, String key,
        CannedAccessControlList objectAcl) {
        if (file == null) {
            throw new BusinessException("文件不能为空！");
        }
        if (!file.isFile()) {
            throw new BusinessException("不能上传目录!");
        }
        // 构建用来存储在服务器是上面的唯一目录值
        if (StringUtils.isBlank(key)) {
            key = buildKey(kind, fileName);
        }
        // 构建用户下载的时候显示的文件名
        String fileNameDownload = buildFileName(file, fileName);
        writeOss.putObject(bucketName, key, file, buildObjectMetadata(fileNameDownload, contentType, objectAcl));
        return key;
    }

    /**
     * 根据文件流上传文件
     *
     * @param inputStream 文件流 不能为空
     * @return 返回文件的唯一key 可以获取文件url
     */
    public String put(InputStream inputStream) {
        return put(inputStream, null, null, null, null, null);
    }

    /**
     * 根据文件流上传文件
     *
     * @param inputStream 文件流 不能为空
     * @param kind        文件种类 可以为空 在每个项目创建OSSKindConstants 尽量不用重复 为空默认放在default下面
     * @return 返回文件的唯一key 可以获取文件url
     */
    public String put(InputStream inputStream, String kind) {
        return put(inputStream, kind, null, null, null, null);
    }

    /**
     * 根据文件流上传文件
     *
     * @param inputStream 文件流 不能为空
     * @param kind        文件种类 可以为空 在每个项目创建OSSKindConstants 尽量不用重复 为空默认放在default下面
     * @param fileName    用户下载时候的文件名 可以为空 为空则自动生成uuid
     * @return 返回文件的唯一key 可以获取文件url
     */
    public String put(InputStream inputStream, String kind, String fileName) {
        return put(inputStream, kind, fileName, null, null, null);
    }

    /**
     * 根据文件流上传文件
     *
     * @param inputStream 文件流 不能为空
     * @param kind        文件种类 可以为空 在每个项目创建OSSKindConstants 尽量不用重复 为空默认放在default下面
     * @param fileName    用户下载时候的文件名 可以为空 为空则自动生成uuid
     * @param contentType 文件类型 可以为空 用户打开时候浏览器的contentType 为空优先根据传入的fileName的后缀区分 如果fileName没有传入则
     *                    默认流application/octet-stream
     * @return 返回文件的唯一key 可以获取文件url
     */
    public String put(InputStream inputStream, String kind, String fileName, String contentType) {
        return put(inputStream, kind, fileName, contentType, null, null);
    }

    /**
     * 根据文件流上传文件
     *
     * @param inputStream 文件流 不能为空
     * @param fileName    用户下载时候的文件名 可以为空 为空则自动生成uuid
     * @param contentType 文件类型 可以为空 用户打开时候浏览器的contentType 为空优先根据传入的fileName的后缀区分 如果fileName没有传入则
     *                    默认流application/octet-stream
     * @return 返回文件的唯一key 可以获取文件url
     */
    public String put(String key, InputStream inputStream, String fileName, String contentType) {
        return put(inputStream, null, fileName, contentType, key, null);
    }

    /**
     * 根据文件流上传文件
     *
     * @param inputStream 文件流 不能为空
     * @param kind        文件种类 可以为空 在每个项目创建OSSKindConstants 尽量不用重复 为空默认放在default下面
     * @param fileName    用户下载时候的文件名 可以为空 为空则自动生成uuid
     * @param contentType 文件类型 可以为空 用户打开时候浏览器的contentType 为空优先根据传入的fileName的后缀区分 如果fileName没有传入则
     *                    默认流application/octet-stream
     * @param key         生成服务器文件存储的key （空的场景，需要生成）
     * @param objectAcl   对象权限控制  默认私有的
     * @return 返回文件的唯一key 可以获取文件url
     * @see CannedAccessControlList
     */
    public String put(InputStream inputStream, String kind, String fileName, String contentType, String key,
        CannedAccessControlList objectAcl) {

        if (inputStream == null) {
            throw new BusinessException("文件流不能为空！");
        }
        // 构建用来存储在服务器是上面的唯一目录值
        if (StringUtils.isBlank(key)) {
            key = buildKey(kind, fileName);
        }
        // 构建用户下载的时候显示的文件名
        String fileNameDownload = buildFileName(fileName);
        // 上传文件
        writeOss.putObject(bucketName, key, inputStream, buildObjectMetadata(fileNameDownload, contentType, objectAcl));
        return key;
    }

    /**
     * 根据 key获取文件流
     *
     * @param key 文件key 在上传的时候获得
     * @return 返回文件流 如果不存在 则会抛出RuntimeException
     */
    public InputStream get(String key) {
        return get(key, null);
    }

    /**
     * 根据 key获取文件流
     *
     * @param key     文件key 在上传的时候获得
     * @param process 格式转换
     * @return 返回文件流 如果不存在 则会抛出RuntimeException
     */
    public InputStream get(String key, String process) {
        return getOssObject(key, process).getObjectContent();
    }

    /**
     * 根据 key获取OSS文件 如果需要获取文件名之类的请用此方法
     *
     * @param key 文件key 在上传的时候获得
     * @return 返回文件流 如果不存在 则会抛出RuntimeException
     */
    public OssObject getOssObject(String key) {
        return getOssObject(key, null);
    }

    /**
     * 根据 key获取OSS文件 如果需要获取文件名之类的请用此方法
     *
     * @param key     文件key 在上传的时候获得
     * @param process 格式转换
     * @return 返回文件流 如果不存在 则会抛出RuntimeException
     */
    public OssObject getOssObject(String key, String process) {

        if (StringUtils.isBlank(key)) {
            throw new BusinessException("获取文件key 不能为空");
        }
        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
            if (StringUtils.isNotBlank(process)) {
                getObjectRequest.setProcess(process);
            }
            return OssObject.buildWithAliyunOssObject(readOss.getObject(getObjectRequest));
        } catch (OSSException e) {
            String errorCode = e.getErrorCode();
            if (OSSErrorCode.NO_SUCH_KEY.equals(errorCode)) {
                throw new BusinessException("找不到指定文件");
            }
            throw new BusinessException(e.getMessage(), e);
        }
    }

    /**
     * 根据Key 获取url
     *
     * @param key 文件key 在上传的时候获得
     * @return 一个可以访问的url
     */
    public String getUrl(String key) {
        return getUrl(key, null, null);
    }

    /**
     * 根据Key 获取url
     *
     * @param key     文件key 在上传的时候获得
     * @param process 格式转换
     * @return 一个可以访问的url
     */
    public String getUrl(String key, String process) {
        return getUrl(key, null, null, process);
    }

    /**
     * 根据Key 获取url
     *
     * @param key        文件key 在上传的时候获得
     * @param expiration 超时时间 可以为空 共有读的不管传不传 都是永久的 私有的不传默认1小时
     * @param timeUnit   超时时间单位 和超时时间一起使用
     * @return 一个可以访问的url
     */
    public String getUrl(String key, Long expiration, TimeUnit timeUnit) {
        return getUrl(key, expiration, timeUnit, null);
    }

    /**
     * 根据Key 获取url
     *
     * @param key        文件key 在上传的时候获得
     * @param expiration 超时时间 可以为空 共有读的不管传不传 都是永久的 私有的不传默认1小时
     * @param timeUnit   超时时间单位 和超时时间一起使用
     * @param process    格式转换
     * @return 一个可以访问的url
     */
    public String getUrl(String key, Long expiration, TimeUnit timeUnit, String process) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        String kind = StringUtils.split(key, SymbolConstant.SLASH)[0];
        OssKindEnum kindEnum = EasyEnumUtils.getEnum(ossKindEnumClass, kind);

        // 使用默认的样式处理
        if (process == null) {
            process = kindEnum.getProcess();
        }

        // 代表是公有读 直接拼接连接即可
        if (kindEnum != null && kindEnum.getObjectAcl() == CannedAccessControlList.PublicRead) {
            Map<String, String> params = new LinkedHashMap<>();
            if (StringUtils.isNotBlank(process)) {
                params.put(RequestParameters.SUBRESOURCE_PROCESS, process);
            }
            if (MapUtils.isEmpty(params)) {
                return baseUrl + OSSUtils.determineResourcePath(bucketName, key,
                    readOss.getClientConfiguration().isSLDEnabled());
            } else {
                String queryString = HttpUtil.paramToQueryString(params, DEFAULT_CHARSET_NAME);
                return baseUrl + OSSUtils.determineResourcePath(bucketName, key,
                    readOss.getClientConfiguration().isSLDEnabled()) + "?" + queryString;
            }
        }

        // 设置默认超时时间
        if (expiration == null) {
            expiration = 5L;
            timeUnit = TimeUnit.MINUTES;
        } else {
            if (timeUnit == null) {
                throw new BusinessException("超时时间和超时时间单位必须同时为空或者同时不为空");
            }
        }

        Date expirationDate = new Date(System.currentTimeMillis() + timeUnit.toMillis(expiration));
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, key);
        generatePresignedUrlRequest.setExpiration(expirationDate);
        if (StringUtils.isNotBlank(process)) {
            generatePresignedUrlRequest.setProcess(process);
        }
        URL url = readOss.generatePresignedUrl(generatePresignedUrlRequest);
        String urlString = url.toString();
        if (needReplaceDomain) {
            urlString = urlString.replaceFirst(ossDomain, domain);
        }
        return urlString;
    }

    /**
     * 创建一个转换office 的任务。过一段时间会自动完成
     *
     * @param key
     */
    public void createOfficeConversionTask(String key) {
        String srcUri = "oss://" + bucketName + "/" + key;
        CreateOfficeConversionTaskRequest createOfficeConversionTaskRequest = new CreateOfficeConversionTaskRequest();
        createOfficeConversionTaskRequest.setProject(immProject);
        createOfficeConversionTaskRequest.setSrcUri(srcUri);
        createOfficeConversionTaskRequest.setTgtType("vector");
        createOfficeConversionTaskRequest.setTgtUri(srcUri + PREVIEW_SRC_SUFFIX);
        try {
            iAcsClient.getAcsResponse(createOfficeConversionTaskRequest);
        } catch (ClientException e) {
            throw new SystemException("创建转换office任务失败.", e);
        }
    }

    /**
     * 根据url生成key
     *
     * @param url 一个可以访问的url
     * @return 唯一key
     */
    public String urlToKey(String url) {
        if (StringUtils.isBlank(url)) {
            throw new BusinessException("url转key url不能为空");
        }
        try {
            URL urlObject = new URL(url);
            String path = urlObject.getPath();
            return path.substring(1);
        } catch (Exception e) {
            throw new BusinessException("请传入正确的url");
        }
    }

    /**
     * 根据key 移除文件
     *
     * @param key
     */
    public void delete(String key) {
        writeOss.deleteObject(bucketName, key);
    }

    /**
     * 构建 请求参数
     *
     * @param fileNameDownload
     * @param contentType
     * @return
     */
    private ObjectMetadata buildObjectMetadata(String fileNameDownload, String contentType,
        CannedAccessControlList objectAcl) {
        if (objectAcl == null) {
            objectAcl = CannedAccessControlList.Private;
        }
        if (objectAcl != CannedAccessControlList.Private && objectAcl != CannedAccessControlList.PublicRead) {
            throw new SystemException("目前仅仅支持私有和共有读类型。");
        }
        ObjectMetadata objectMetadata = new ObjectMetadata();
        if (StringUtils.isBlank(contentType)) {
            contentType = Mimetypes.getInstance().getMimetype(fileNameDownload);
        }
        objectMetadata.setContentType(contentType);
        try {
            // 防止中文乱码
            fileNameDownload = URLEncoder.encode(fileNameDownload, StandardCharsets.UTF_8.name()).replaceAll("\\+",
                "%20");
        } catch (UnsupportedEncodingException e) {
            throw new SystemException(CommonErrorEnum.COMMON_SYSTEM_ERROR, "不支持的字符编码", e);
        }
        objectMetadata.setContentDisposition("filename*=utf-8''" + fileNameDownload);
        // 默认私有
        objectMetadata.setObjectAcl(objectAcl);
        return objectMetadata;
    }

    /**
     * 用来 下载时 显示给用户的名称
     *
     * @param fileName
     * @return
     */
    private String buildFileName(String fileName) {
        if (StringUtils.isNotBlank(fileName)) {
            return fileName;
        }
        return UUID.randomUUID().toString();
    }

    /**
     * 用来 下载时 显示给用户的名称
     *
     * @param file
     * @param fileName
     * @return
     */
    private String buildFileName(File file, String fileName) {
        if (StringUtils.isNotBlank(fileName)) {
            return fileName;
        }
        fileName = file.getName();
        if (StringUtils.isNotBlank(fileName)) {
            return fileName;
        }
        return UUID.randomUUID().toString();
    }

    /**
     * 生成服务器文件存储的key 统一看不到名字 看不到后缀
     * 有些场景需要提前生成唯一存储key
     *
     * @param kind
     * @param fileName
     * @return
     */
    public String buildKey(String kind, String fileName) {
        if (StringUtils.isBlank(kind)) {
            kind = DEFAULT_KIND;
        }
        StringBuilder key = new StringBuilder();
        if (StringUtils.isNotBlank(projectName)) {
            key.append(projectName);
            key.append("/");
        }
        key.append(kind);
        key.append("/");
        Calendar calendar = Calendar.getInstance();
        key.append(calendar.get(Calendar.YEAR));
        key.append("/");
        key.append(StringUtils.leftPad(Integer.toString(calendar.get(Calendar.MONTH) + 1), 2, "0"));
        key.append("/");
        key.append(UUID.randomUUID());

        Optional.ofNullable(fileName).map(fileNameData -> {
            int lastDotIndex = fileNameData.lastIndexOf(SymbolConstant.DOT);
            if (lastDotIndex >= 0) {
                return fileNameData.substring(lastDotIndex);
            }
            return null;
        }).ifPresent(key::append);
        return key.toString();
    }

    /**
     * 将一个html中需要授权的资源替换成授权完成
     *
     * @param html        html文本
     * @param ossKindEnum 需要处理的对象的oss 枚举
     * @return 授权完成的html
     */
    public String htmlSourceAuthorization(String html, OssKindEnum ossKindEnum) {
        if (StringUtils.isBlank(html)) {
            return html;
        }
        Document doc = Jsoup.parse(html);
        // 查找所有image 标签
        Elements imgs = doc.getElementsByTag("img");
        for (Element img : imgs) {
            img.attr("src",
                getUrlFromUrl(img.attr("src"), EasyOptionalUtils.mapTo(ossKindEnum, OssKindEnum::getProcess)));
        }
        doc.outputSettings().prettyPrint(false);
        return doc.body().html();
    }

    /**
     * 将一个json中需要授权的资源替换成授权完成
     *
     * 这里注意 这个json 文本很奇怪。类似于：["root",{},["p",{},["span",{"data-type":"text"},["span",{"data-type":"leaf"},"测试"],
     * ["span",{"italic":true,"data-type":"leaf"},"文本"]]]]
     *
     * @param json        json文本
     * @param ossKindEnum 需要处理的对象的oss 枚举
     * @return 授权完成的html
     */
    public String jsonSourceAuthorization(String json, OssKindEnum ossKindEnum) {
        if (StringUtils.isBlank(json)) {
            return json;
        }
        JSONArray jsonArray = JSON.parseArray(json);

        // 给资源授权
        jsonSourceAuthorization(jsonArray, ossKindEnum);
        return jsonArray.toString();
    }

    private void jsonSourceAuthorization(JSONArray jsonArray, OssKindEnum ossKindEnum) {
        // 如果小于2级的标签 需要忽略
        if (jsonArray.size() < 2) {
            return;
        }
        // 递归 第三个及其以后的节点
        for (int i = 2; i < jsonArray.size(); i++) {
            Object data = jsonArray.get(i);
            // 可能是 字符串 也是可能是数组
            // 这里只需要考虑是数组的情况
            if (data instanceof JSONArray) {
                jsonSourceAuthorization((JSONArray)data, ossKindEnum);
            }
        }

        // 处理本节点的数据
        // 第0个数组代表标签名字
        String tagName = jsonArray.getString(0);
        //代表不是图片
        if (!"img".equals(tagName)) {
            return;
        }
        // 第1个数组代表 样式
        JSONObject style = jsonArray.getJSONObject(1);
        if (style == null) {
            return;
        }
        style.put("src",
            getUrlFromUrl(style.getString("src"), EasyOptionalUtils.mapTo(ossKindEnum, OssKindEnum::getProcess)));
    }

    /**
     * 给一个路径资源授权
     *
     * @param url     一个url
     * @param process process处理
     * @return
     */
    public String getUrlFromUrl(String url, String process) {
        if (StringUtils.isBlank(url)) {
            return url;
        }
        try {
            // 有空可能没有http 协议 默认https
            if (url.startsWith("//")) {
                url = HTTPS_PREFIX + url;
            }
            URL sourceUrl = new URL(url);
            // 代表不是当前 环境的资源
            if (!domain.equals(sourceUrl.getHost()) && !ossDomain.equals(sourceUrl.getHost())) {
                return url;
            }
            return getUrl(sourceUrl.getPath().substring(1), process);
        } catch (Exception e) {
            log.error("错误的路径:{}", url, e);
            return url;
        }
    }

}

