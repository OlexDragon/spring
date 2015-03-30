package jk.web.beans.view.management;

import org.apache.logging.log4j.Level;

public class SearchCategoryView {

	private Long id;
	private String name;
	private boolean show = true;
	private String msg;
	private Level msgStatus;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name!=null ? name.trim() : null;
	}
	public boolean isShow() {
		return show;
	}
	public void setShow(boolean show) {
		this.show = show;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Level getMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(Level msgStatus) {
		this.msgStatus = msgStatus;
	}
	@Override
	public String toString() {
		return "\n\tSearchCategoryView [\n\t\tid=" + id + ",\n\t\tname=" + name + ",\n\t\tshow=" + show + "]";
	}
}
