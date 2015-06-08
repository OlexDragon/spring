package jk.geonames.objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="adminCode1")
public class AdminCode1 {

	private Integer adminCode1;

	@XmlAttribute(name="ISO3166-2")
	private String ISO3166_2;

	public String getISO3166_2() {
		return ISO3166_2;
	}
	public void setISO3166_2(String iSO3166_2) {
		ISO3166_2 = iSO3166_2;
	}
	@Override
	public String toString() {
		return "\n\tAdminCode1 [AdminCode1=" + adminCode1 + ", ISO3166_2="
				+ ISO3166_2 + "]";
	}
}
