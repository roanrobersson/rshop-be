package br.com.roanrobersson.rshop.factory;

import java.time.Instant;

import br.com.roanrobersson.rshop.domain.model.Privilege;

public class PrivilegeFactory {

	public static Privilege createPrivilege() {
		return new Privilege(1L, "EDIT_CATEGORIES", "Allow edit categories", Instant.now(), Instant.now());
	}
}
