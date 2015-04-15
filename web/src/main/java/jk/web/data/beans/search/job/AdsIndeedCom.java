package jk.web.data.beans.search.job;

import java.util.List;

import jk.web.data.beans.search.job.interfaces.SearchDetails;

// https://ads.indeed.com/jobroll/xmlfeed
// affiliate@fashionprofinder.com 
//F_pro_F2014
public class AdsIndeedCom implements SearchDetails{

	private int v;//Version. Which version of the API you wish to use. All publishers should be using version 2. Currently available versions are 1 and 2. This parameter is required. 
	private String query = "java";
	private String location;
	private int totalresults;
	private int start = 1;
	private int end = 10;
	private int radius = 25;
	private int pageNumber = 0;
	private int limit;
	private boolean dupefilter;
	private int highlight;//Setting this value to 1 will bold terms in the snippet that are also present in q. Default is 0. 
	private List<AdsIndeedComResult> results;

	public String getKeywords() {
		return query;
	}
	@Override
	public void setKeywords(String query) {
		this.query = query;
	}
	public String getLocation() {
		return location;
	}
	@Override
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
	public int getPage() {
		return pageNumber;
	}
	@Override
	public void setPage(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public boolean isDupefilter() {
		return dupefilter;
	}
	public void setDupefilter(boolean dupefilter) {
		this.dupefilter = dupefilter;
	}
	public int isHighlight() {
		return highlight;
	}
	public void setHighlight(int highlight) {
		this.highlight = highlight;
	}
	public List<AdsIndeedComResult> getResults() {
		return results;
	}
	public void setResults(List<AdsIndeedComResult> results) {
		this.results = results;
	}
	@Override
	public void setPagesize(Integer pagesize) {
		limit = pagesize;
	}
}
