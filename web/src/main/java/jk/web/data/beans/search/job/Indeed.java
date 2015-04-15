package jk.web.data.beans.search.job;

import java.util.List;

// https://ads.indeed.com/jobroll/xmlfeed
// affiliate@fashionprofinder.com 
//F_pro_F2014
public class Indeed {

	private String query = "java";
	private String location;
	private int totalresults;
	private int start = 1;
	private int end = 10;
	private int radius = 25;
	private int pageNumber = 0;
	private boolean dupefilter;
	private boolean highlight;
	private List<IndeedResult> results;
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getTotalresults() {
		return totalresults;
	}
	public void setTotalresults(int totalresults) {
		this.totalresults = totalresults;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public boolean isDupefilter() {
		return dupefilter;
	}
	public void setDupefilter(boolean dupefilter) {
		this.dupefilter = dupefilter;
	}
	public boolean isHighlight() {
		return highlight;
	}
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
	}
	public List<IndeedResult> getResults() {
		return results;
	}
	public void setResults(List<IndeedResult> results) {
		this.results = results;
	}
}
