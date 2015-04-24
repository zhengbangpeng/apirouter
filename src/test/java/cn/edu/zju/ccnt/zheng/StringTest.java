package cn.edu.zju.ccnt.zheng;

import java.util.HashMap;
import java.util.Map;

public class StringTest {
	public static void main(String[] args) {
		String str="{mts:'1585078',province:'江苏',ca}Name:'中国移动',telString:'15850781443',areaVid:'30511',ispVid:'3236139',carrier:'江苏移动'}";
		String json=str;
		json=json.substring(json.indexOf("{")+1,json.indexOf("}"));
		System.out.println(json);
		str=str.replace("'", "");
		Map<String,String>map=new HashMap<String,String>();
		String[] strs=str.split(",");
		for(String s:strs){
			String[]ss=s.split(":");
			System.out.println(ss[0]+":"+ss[1]);
			map.put(ss[0], ss[1]);
		}
		
		System.out.println(map.get("province"));
	}
}
