package com.alibaba.excel.metadata.typeconvertor;

import com.alibaba.fastjson.JSONObject;

/**
 * \* Author: xueyunlong@didiglobal.com
 * \* Date: 2018-12-19
 * \* Time: 16:48
 * \* Description:
 * \
 */
public class JsonTypeConvertor implements TypeConvertor<JSONObject>{

  @Override
  public JSONObject serialize(String s) {
      return JSONObject.parseObject(s);
  }

  @Override
  public String deserialize(JSONObject s) {
    return s.toJSONString();
  }

}
