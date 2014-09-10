package org.jboss.soa.qa.resteasy;

import org.switchyard.component.bean.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(AuthorizedService.class)
public class AuthorizedServiceBean implements AuthorizedService {

	@Override
	public boolean invoke() {
		log.info("Authorized Service invoked.");
		return true;
	}
}
