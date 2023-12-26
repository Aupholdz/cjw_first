package CjwTset.pojo;

public class Result {
	String flag;
	Page page;
	Object data;

	public Result() {

	}

	public Result(String flag, Page page, Object data) {
		this.flag = flag;
		this.page = page;
		this.data = data;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
