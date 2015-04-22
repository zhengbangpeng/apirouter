package cn.edu.zju.ccnt.ip.telize;

import java.util.Map;

import cn.edu.zju.ccnt.RestRequestPramsGenerator;

/**
 * 
 * @author zheng
 * 2015-4-22 обнГ3:21:22
 */
public class RestRequestParmsGeneratorImpl extends RestRequestPramsGenerator{

	@Override
	public String generateParamString(Map<String, String> params)
			throws Exception {
		return RestRequestPramsGenerator.mapToParamString(params);
	}
	
}
