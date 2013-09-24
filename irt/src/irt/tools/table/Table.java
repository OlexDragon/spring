package irt.tools.table;

import java.util.List;

public class Table {
	private String className;	//use with CSS(HTML)
	private HTMLHeader title;
	private List<Row> rows;
	private String id;


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public HTMLHeader getTitle() {
		return title;
	}


	public void setTitle(HTMLHeader title) {
		this.title = title;
	}


	public List<Row> getRows() {
		return rows;
	}


	public void setRows(List<Row> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {

		String returnStr = title!=null ? title.toString() : "";
		returnStr += "<table"+(id==null ? "" : " id=\""+id+"\" ")+(className==null ? "" : " class=\""+className+"\" " )+">";
		
		for(Row row:rows)
			returnStr += row;
		
		return returnStr+"</table>";
	}


	public void setId(String id) {
		this.id = id;
	}
}
