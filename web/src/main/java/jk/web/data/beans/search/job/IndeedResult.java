package jk.web.data.beans.search.job;

import java.math.BigDecimal;
import java.util.Date;

public class IndeedResult {

	private String jobtitle;				//Java Developer
	private String company;					//XYZ Corp.
	private String city;					//Austin
	private String state;					//TX
	private String country;					//US
	private String formattedLocation;		//Austin, TX
	private String formattedLocationFull;	//Austin, TX
	private String source;					//Dice
	private String snippet;					//looking for an object-oriented Java Developer... Java Servlets, HTML, JavaScript, AJAX, Struts, Struts2, JSF) desirable. Familiarity with Tomcat and the Java..
	private String url;						//http://www.indeed.com/viewjob?jk=12345&indpubnum=8343699265155203
	private BigDecimal latitude;			//30.27127
	private BigDecimal longitude;			//-97.74103
	private int jobkey;						//12345
	private Date date;						//Mon, 02 Aug 2010 16:21:00 GMT
	private boolean sponsored;				//true
	private boolean expired;				//false
	private String formattedRelativeTime;	//11 hours ago
	private String onmousedown;				//indeed_clk(this,'0000');
	public String getJobtitle() {
		return jobtitle;
	}
	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getFormattedLocation() {
		return formattedLocation;
	}
	public void setFormattedLocation(String formattedLocation) {
		this.formattedLocation = formattedLocation;
	}
	public String getFormattedLocationFull() {
		return formattedLocationFull;
	}
	public void setFormattedLocationFull(String formattedLocationFull) {
		this.formattedLocationFull = formattedLocationFull;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public int getJobkey() {
		return jobkey;
	}
	public void setJobkey(int jobkey) {
		this.jobkey = jobkey;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public boolean isSponsored() {
		return sponsored;
	}
	public void setSponsored(boolean sponsored) {
		this.sponsored = sponsored;
	}
	public boolean isExpired() {
		return expired;
	}
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	public String getFormattedRelativeTime() {
		return formattedRelativeTime;
	}
	public void setFormattedRelativeTime(String formattedRelativeTime) {
		this.formattedRelativeTime = formattedRelativeTime;
	}
	public String getOnmousedown() {
		return onmousedown;
	}
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}
}
