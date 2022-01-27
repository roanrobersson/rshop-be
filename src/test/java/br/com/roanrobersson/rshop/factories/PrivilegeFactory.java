package br.com.roanrobersson.rshop.factories;

import java.time.Instant;

import br.com.roanrobersson.rshop.domain.Privilege;

public class PrivilegeFactory {

	public static Privilege createPrivilege() {
		return new Privilege(1L, null, "EDIT_CATEGORIES", "Allow edit categories", Instant.now(), Instant.now());
	}
}
