package jk.web.data.beans;

import java.util.List;

public class Activity {

	private String title;
	private String activityType;
	private List<String> paragraphs;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public List<String> getParagraphs() {
		return paragraphs;
	}
	public void setParagraphs(List<String> paragraphs) {
		this.paragraphs = paragraphs;
	}
	@Override
	public String toString() {
		return "ActivityBean [title=" + title + ", activityType=" + activityType + ", paragraphs=" + paragraphs + "]";
	}
}
