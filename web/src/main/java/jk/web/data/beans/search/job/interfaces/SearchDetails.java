package jk.web.data.beans.search.job.interfaces;

public interface SearchDetails {

	public enum Format{
		XML("xml"),
		JSON("json");

		private String format;
		private Format(String format){
			this.format = format;
		}
		@Override
		public String toString(){
			return format;
		}
	}
	public enum JobType{
		FULLTIME("fulltime"),
		PARTTIME("parttime"),
		CONTRACT("contract"),
		INTERNSHIP("internship"),
		TEMPORARY("temporary");

		private String jobType;
		private JobType(String jobType){
			this.jobType = jobType;
		}
		@Override
		public String toString(){
			return jobType;
		}
	}
	void setKeywords(String keywords);
	void setLocation(String location);
	void setPage(Integer page);
	void setPagesize(Integer pagesize);
}
