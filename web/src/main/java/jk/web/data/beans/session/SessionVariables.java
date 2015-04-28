package jk.web.data.beans.session;

import jk.web.entities.statistic.IpAddressEntity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class SessionVariables {

	private IpAddressEntity ipAddressEntity;

	public IpAddressEntity getIpAddressEntity() {
		return ipAddressEntity;
	}

	public void setIpAddressEntity(IpAddressEntity ipAddressEntity) {
		this.ipAddressEntity = ipAddressEntity;
	}
}
