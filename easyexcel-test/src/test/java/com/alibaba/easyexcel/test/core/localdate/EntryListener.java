package com.alibaba.easyexcel.test.core.localdate;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSONObject;

/**
 * 读的监听器
 * 
 * @author ywzou
 *
 */
public class EntryListener implements ReadListener<DataEntry> {

	private static final Logger log = LoggerFactory.getLogger(EntryListener.class);

	private List<DataEntry> entries = new ArrayList<>();

	// 这个每一条数据解析都会来调用
	@Override
	public void invoke(DataEntry data, AnalysisContext context) {
		log.info("解析到一条数据:{}", JSONObject.toJSONString(data));
		entries.add(data);
	}

	// 所有数据解析完成了 都会来调用
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {

	}

	public List<DataEntry> getEntries() {
		return entries;
	}
}
