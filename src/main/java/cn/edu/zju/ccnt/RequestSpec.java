package cn.edu.zju.ccnt;

public class RequestSpec {
	private String url;
	private RestRequestPramsGenerator paramsGenerator;
	private ResultStandardizer resultStandardizer;
	
	//请求是否有参数   1:有参数  0：无参数
	private int hasParams;
	
	public RequestSpec(String url, RestRequestPramsGenerator paramsGenerator,
			ResultStandardizer resultStandardizer,int hasParams) {
		this.url = url;
		this.paramsGenerator = paramsGenerator;
		this.resultStandardizer = resultStandardizer;
		this.hasParams=hasParams;
	}

	public String getUrl() {
		return url;
	}

	public RestRequestPramsGenerator getParamsGenerator() {
		return paramsGenerator;
	}

	public ResultStandardizer getResultStandardizer() {
		return resultStandardizer;
	}

	public int getHasParams() {
		return hasParams;
	}
	
	
	
}
