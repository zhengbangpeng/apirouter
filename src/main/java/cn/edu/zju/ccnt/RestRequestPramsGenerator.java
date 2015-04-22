package cn.edu.zju.ccnt;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.Message;
import org.mule.transformer.AbstractMessageTransformer;

public abstract class RestRequestPramsGenerator extends AbstractMessageTransformer{
	private static final Logger LOGGER = Logger.getLogger(RestRequestPramsGenerator.class);
	
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
		try {
			@SuppressWarnings("unchecked")
			Map<String, String> dataMap = (Map<String, String>)message.getInboundProperty("http.query.params");
			message.setInvocationProperty("paramString", generateParamString(dataMap));
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
			Message msg = CoreMessages.transformFailedFrom(ResultStandardizer.class);
			throw new TransformerException(msg);
		}
		
	}
	
	//实现将标准的请求参数转换为不同API的参数
	abstract public String generateParamString(Map<String, String> params) throws Exception;
	
	//根据map 返回类似a=1&b=2的String
	protected static String mapToParamString(Map<String, String> params){
		StringBuilder builder = null;
		
		for(Entry<String, String> entry : params.entrySet()){
			if(builder == null) builder = new StringBuilder();
			else builder.append("&");
			
			builder.append(entry.getKey() + "=" + entry.getValue());
		}

		return builder.toString();
	}

}
