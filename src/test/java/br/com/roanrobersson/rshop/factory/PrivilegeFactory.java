package br.com.roanrobersson.rshop.factory;

import java.time.Instant;
import java.util.UUID;

import br.com.roanrobersson.rshop.domain.model.Privilege;

public class PrivilegeFactory {

	private static Instant instant = Instant.parse("2020-10-20T03:00:00Z");
	private static UUID id = UUID.fromString("b7705487-51a1-4092-8b62-91dccd76a41a");
	
	public static Privilege createPrivilege() {
		return new Privilege(id, "EDIT_CATEGORIES", "Allow edit categories", instant, instant);
	}
}
