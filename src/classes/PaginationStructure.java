package classes;

import org.json.simple.JSONObject;

public class PaginationStructure {
	int limit;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public PaginationStructure(JSONObject data) throws Exception{
		super();
		BasicReflect basicReflect = new BasicReflect();
		basicReflect.setMethod(this, data);
	}
	
}
