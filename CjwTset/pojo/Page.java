package CjwTset.pojo;

public class Page {
	int total;
	int startPage;
	int pageSize;

	public Page() {
		
	}

	public Page(int total, int startPage, int pageSize) {
		this.total = total;
		this.startPage = startPage;
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
