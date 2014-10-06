package jk.web.html.select;

public class ContentDiv {

	private String title;
	private String content;
	private String h = "h1";

	private static StringBuilder stringBuilder = new StringBuilder();

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getH() {
		return h;
	}
	public void setH(int h) {
		this.h = "h"+h;
	}
	@Override
	public String toString() {

		String string;

		synchronized (stringBuilder) {
			stringBuilder.setLength(0);
			stringBuilder.append("<div>");
			if(title!=null){
				stringBuilder.append('<');
				stringBuilder.append(h);
				stringBuilder.append('>');
				stringBuilder.append(title);
				stringBuilder.append("</");
				stringBuilder.append(h);
				stringBuilder.append('>');
			}
			if(content!=null){
				stringBuilder.append("<p>");
				stringBuilder.append(content);
				stringBuilder.append("</p>");
			}
			stringBuilder.append("</div>");
			string = stringBuilder.toString();
		}

		return string;
	}
}
