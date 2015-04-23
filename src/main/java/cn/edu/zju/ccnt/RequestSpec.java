package cn.edu.zju.ccnt;

public class RequestSpec {
	private String url;
	private RestRequestPramsGenerator paramsGenerator;
	private ResultStandardizer resultStandardizer;
	
	//�����Ƿ��в���   1:�в���  0���޲���
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
