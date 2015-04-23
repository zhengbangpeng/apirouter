package cn.edu.zju.ccnt;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.config.i18n.CoreMessages;
import org.mule.config.i18n.Message;
import org.mule.transformer.AbstractMessageTransformer;

import edu.emory.mathcs.backport.java.util.LinkedList;


public class RequestPreprocessor extends AbstractMessageTransformer {
	private static final Logger LOGGER = Logger.getLogger(RequestPreprocessor.class);
	private static final Pattern P_WEATHER = Pattern.compile("/weather/q\\?.*");
	private static final Pattern P_FLIGHT = Pattern.compile("/flight/q\\?.*");
	private static final Pattern P_TRAIN = Pattern.compile("/train/q\\?.*");
	private static final Pattern P_HOTEL = Pattern.compile("/hotel/q\\?.*");
	private static final Pattern P_RESTAURANT = Pattern.compile("/restaurant/q\\?.*");
	private static final Pattern P_TAXI = Pattern.compile("/taxi/q\\?.*");
	private static final Pattern P_IP = Pattern.compile("/ip/q\\?.*");
	
	private static final List<RequestSpec> REQUEST_SPECS = new ArrayList<RequestSpec>();
	private static final List<RequestSpec> IP_SPECS = new ArrayList<RequestSpec>();
	static{
		REQUEST_SPECS.add(new RequestSpec(
				"weather.51wnl.com/weatherinfo/GetMoreWeather", 
				new cn.edu.zju.ccnt.weather._51wnl.RestRequestPramsGeneratorImpl(), 
				new cn.edu.zju.ccnt.weather._51wnl.StandardizerImpl(),1));
		
		REQUEST_SPECS.add(new RequestSpec(
				"api.rocliu.net/api/weatherweek", 
				new cn.edu.zju.ccnt.weather.okapi.RestRequestPramsGeneratorImpl(), 
				new cn.edu.zju.ccnt.weather.okapi.StandardizerImpl(),1));
		
		REQUEST_SPECS.add(new RequestSpec(
				"webservice.webxml.com.cn/WebServices/WeatherWebService.asmx/getWeatherbyCityName", 
				new cn.edu.zju.ccnt.weather.weatherws.RestRequestPramsGeneratorImpl(), 
				new cn.edu.zju.ccnt.weather.weatherws.StandardizerImpl(),1));
	}
	static{
		IP_SPECS.add(new RequestSpec(
				"apistore.baidu.com/microservice/iplookup",     //url直接加上/ip地址查询
				new cn.edu.zju.ccnt.ip.baidu.RestRequestPramsGeneratorImpl(), 
				new cn.edu.zju.ccnt.ip.baidu.StandardizerImpl(),1));
		IP_SPECS.add(new RequestSpec(
				"www.telize.com/geoip",     //url直接加上/ip地址查询
				new cn.edu.zju.ccnt.ip.telize.RestRequestPramsGeneratorImpl(), 
				new cn.edu.zju.ccnt.ip.telize.StandardizerImpl(),0));
	}

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
		String reqPath = (String)message.getInboundProperty("http.request");
		message.setInvocationProperty("systime", System.currentTimeMillis());
		message.setInvocationProperty("requstParams", message.getInboundProperty("http.query.params"));
		
		if(P_WEATHER.matcher(reqPath).matches()){
			message.setInvocationProperty("requestSpecs", new LinkedList(REQUEST_SPECS));
			/**
			 *  hasCache  是否有缓存  
			 * 	0：无缓存  
			 *  1：有缓存  
			 */
			message.setInvocationProperty("hasCache",1); 
			
			message.setInvocationProperty("timeoutMillis", 3600000L);   //数据更新周期
			message.setInvocationProperty("mongoCollectionName", "weatherToday");  //缓存表名
		}else if(P_IP.matcher(reqPath).matches()){
			message.setInvocationProperty("requestSpecs", new LinkedList(IP_SPECS));
			
			message.setInvocationProperty("hasCache",0);  //是否有缓存
		} else {
			LOGGER.error("request path not found.");
			Message msg = CoreMessages.transformFailedFrom(ResultStandardizer.class);
			throw new TransformerException(msg);
		}
		
		return message;
	}
	
}
