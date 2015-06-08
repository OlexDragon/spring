package jk.service.adapters;

import java.math.BigDecimal;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlBigDecimalAdapter  extends XmlAdapter<String, BigDecimal>{

	@Override
	public BigDecimal unmarshal(String value) throws Exception {
		return new BigDecimal(value);
	}

	@Override
	public String marshal(BigDecimal value) throws Exception {
		return value.toString();
	}

}
