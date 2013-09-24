package irt.tools.table;

public class OrderBy {
	private static final String DESC = "DESC";
	private static final String ORDER_BY = "ORDER BY`";

	private String orderBy;
	private boolean desc;

	public void setOrderBy(String orderBy) { 

		if(this.orderBy!=null && this.orderBy.equals(orderBy))
			desc= !desc;

		this.orderBy = orderBy;
	}

	public boolean isDesc() {
		return desc;
	}

	public void setDesc(boolean desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return (orderBy==null) 
				? ""
				: ORDER_BY+orderBy +"`"
						+ ((isDesc()) 
								? DESC
								: "");
	}

	@Override
	public boolean equals(Object obj) {
		return obj!=null ? obj.hashCode()==hashCode() : false;
	}

	@Override
	public int hashCode() {
		return orderBy!=null ? orderBy.hashCode() : super.hashCode();
	}
}
