package br.com.roanrobersson.rshop.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.roanrobersson.rshop.domain.model.Address;
import br.com.roanrobersson.rshop.domain.model.Category;
import br.com.roanrobersson.rshop.domain.model.Privilege;
import br.com.roanrobersson.rshop.domain.model.Product;
import br.com.roanrobersson.rshop.domain.model.Role;
import br.com.roanrobersson.rshop.domain.model.User;
import br.com.roanrobersson.rshop.domain.repository.AddressRepository;
import br.com.roanrobersson.rshop.domain.repository.CategoryRepository;
import br.com.roanrobersson.rshop.domain.repository.PrivilegeRepository;
import br.com.roanrobersson.rshop.domain.repository.ProductRepository;
import br.com.roanrobersson.rshop.domain.repository.RoleRepository;
import br.com.roanrobersson.rshop.domain.repository.UserRepository;

@Component
public class DatabaseSeeder implements ApplicationRunner {

	String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaec cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
	String url = "https://raw.githubusercontent.com/devsuperior/dscalog-resources/master/backend/img/1-big.jpg";
	Instant instant = Instant.now();
	
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository cetegoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@SuppressWarnings("unused")
	public void run(ApplicationArguments args) {

		// Privilege
		Privilege pv1 = privilegeRepository.save(new Privilege(uuid("b7705487-51a1-4092-8b62-91dccd76a41a"), "CONSULT_CATEGORIES", "Allow consult categories", instant, instant));
		Privilege pv2 = privilegeRepository.save(new Privilege(uuid("91f550d9-548f-4d09-ac9c-1a95219033f7"), "EDIT_CATEGORIES", "Allow edit categories", instant, instant));
		Privilege pv3 = privilegeRepository.save(new Privilege(uuid("ab7fab73-0464-4f7c-bc18-069ff63a3dc9"), "CONSULT_USERS", "Allow consult users", instant, instant));
		Privilege pv4 = privilegeRepository.save(new Privilege(uuid("bafcfedf-8f1c-4f16-b474-351e347b13de"), "EDIT_USERS", "Allow edit users", instant, instant));
		Privilege pv5 = privilegeRepository.save(new Privilege(uuid("b7e8b3c9-d426-42f0-8594-5c46cd112aae"), "CONSULT_ADDRESSES", "Allow consult addresses", instant, instant));
		Privilege pv6 = privilegeRepository.save(new Privilege(uuid("a24dfd96-2b06-45d1-a4bb-f3a2ed9e3539"), "EDIT_ADDRESSES", "Allow edit addresses", instant, instant));
		Privilege pv7 = privilegeRepository.save(new Privilege(uuid("37f3ddf2-d8f1-4179-ac7d-7937f4501a0a"), "EDIT_FILE", "Allow edit file", instant, instant));
		Privilege pv8 = privilegeRepository.save(new Privilege(uuid("1b7e1912-d4f4-40d2-afa5-2dc214afefc2"), "CONSULT_PRODUCTS", "Allow consult products", instant, instant));
		Privilege pv9 = privilegeRepository.save(new Privilege(uuid("eb3c936f-e2a9-445c-866c-1df26bec56e3"), "EDIT_PRODUCTS", "Allow edit products", instant, instant));
		Privilege pv10 = privilegeRepository.save(new Privilege(uuid("c5ef538-d9fe8-459ba-9a81-cbb71f1ce30"), "CONSULT_ROLES", "Allow consult roles", instant, instant));
		Privilege pv11 = privilegeRepository.save(new Privilege(uuid("516ad67-c6f76-44aeb-326d-9875d6f7536"), "EDIT_ROLES", "Allow edit roles", instant, instant));
		Privilege pv12 = privilegeRepository.save(new Privilege(uuid("f9b5be9-a56d1-47adb-677b-88164922401"), "CONSULT_USER_ROLES", "Allow consult user roles", instant, instant));
		Privilege pv13 = privilegeRepository.save(new Privilege(uuid("585a242-f1058-476c8-656e-afe1fed5812"), "EDIT_USER_ROLES", "Allow edit user roles", instant, instant));		

		// Role
		Role r1 = roleRepository.save(new Role(uuid("18aace1e-f36a-4d71-b4d1-124387d9b63a"), Set.of(pv1), "CLIENT", instant, instant));
		Role r2 = roleRepository.save(new Role(uuid("eb1ffb79-5dfb-4b13-b615-eae094a06207"), Set.of(pv1, pv2, pv3, pv4, pv5, pv6, pv7, pv8, pv9, pv10, pv11, pv12, pv13), "OPERATOR", instant, instant));
		Role r3 = roleRepository.save(new Role(uuid("5e0b121c-9f12-4fd3-a7e6-179b5007149a"), Set.of(pv1, pv2, pv3, pv4, pv5, pv6, pv7, pv8, pv9, pv10, pv11, pv12, pv13), "ADMIN", instant, instant));
		Role r4 = roleRepository.save(new Role(uuid("650861b5-a749-4ed1-964b-c72a1d4c5f0e"), Set.of(pv1), "TEST", instant, instant));

		// User
		User u1 = userRepository.save(new User(uuid("821e3c67-7f22-46af-978c-b6269cb15387"), Set.of(r2), List.of(), "Alex", "Alex Brown", instant, "86213939059", "222182428", "alex@gmail.com", "$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq", "54991200038", "54991200038", instant, instant, null));
		User u2 = userRepository.save(new User(uuid("d16c83fe-3a2e-42b6-97b4-503b203647f6"), Set.of(r3), List.of(), "Maria", "Maria Green", instant, "67709960065", "355144724", "maria@gmail.com", "$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq", "54991200038", "54991200038", instant, instant, null));
		User u3 = userRepository.save(new User(uuid("3a2bf7b8-92fa-4a51-bee3-ae64a3c2286b"), Set.of(r1), List.of(), "Cliente", "Cliente Sobrenome", instant, "61406233080", "365829171", "cliente@gmail.com", "$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq", "54991200038", "54991200038", instant, instant, null));
		User u4 = userRepository.save(new User(uuid("8903af19-36e2-44d9-b649-c3319f33be20"), Set.of(r2), List.of(), "Operador", "Operador Sobrenome", instant, "56335885093", "360774246", "operador@gmail.com", "$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq", "54991200038", "54991200038", instant, instant, null));
		User u5 = userRepository.save(new User(uuid("5b79649c-f311-465f-b2d9-07355b56d08a"), Set.of(r3), List.of(), "Administrador", "Administrador Sobrenome", instant, "36825973010", "266166878", "administrador@gmail.com", "$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq", "54991200038", "54991200038", instant, instant, null));
		User u6 = userRepository.save(new User(uuid("32de2852-e42f-4b14-97be-c60f2e9098f4"), Set.of(r1), List.of(), "NãoVerificado", "NãoVerificado Sobrenome", instant, "65623542000", "497619301", "naoverificado@gmail.com", "$2a$10$IsIgcBqRUf46ovyutI0wIuCZshtYAtrhO3JFutBkVKTIcTRNqY7kq", "54991200038", "54991200038", instant, null, null));

		//Address
		Address a1 = addressRepository.save(new Address(uuid("37783d1e-f631-408f-8796-e4da82c275e0"), u1, "Casa", true, "Rua Frederico Malvenzi", "65", "São Pelegrino", "Erechim", "Rio Grande do Sul", "RS", "99700000", "AP 2", "Próximo ao mercado Nova Era", "54998204476", instant, null));
		Address a2 = addressRepository.save(new Address(uuid("7e88c136-b11c-4879-920f-1193db6fef0f"), u2, "Casa", true, "Av João Pessoa", "44", "São Pelegrino", "Natalândia", "Minas Gerais", "MG", "38658000", null, "Ao lado do Detran", "54991204478", instant, null));
		Address a3 = addressRepository.save(new Address(uuid("6353293a-d2b6-400f-997d-d6935032a52f"), u3, "Casa", true, "Rua Jacob Grommelmaier", "5545", "Centro", "Zumbi", "Rio de Janeiro", "RJ", "20000000", null, "Em frente ao La Bodega", "54991204433", instant, null));
		Address a4 = addressRepository.save(new Address(uuid("e9881540-bc9f-49b9-a827-eb4d5ff39887"), u3, "Casa mãe", false, "Av Pedro Cabral", "1111", "Nova Vila", "Erechim", "Rio Grande do Sul", "RS", "99700000", null, "Ao lado da Bella Casa", "54991204433", instant, null));
		Address a5 = addressRepository.save(new Address(uuid("bc9d79c6-9d51-4dbb-926c-00024e4cc456"), u3, "Meu trabalho", false, "Av Rubia Gonçalves", "59", "Centro", "Erechim", "Rio Grande do Sul", "RS", "99700000", "Sala 18", "Edifício Blumenau", "54999204433", instant, null));
		Address a6 = addressRepository.save(new Address(uuid("8c415152-a729-47a3-a50a-254e342a98a7"), u4, "Casa", true, "Rua Jacob Grommelmaier", "112", "Monte Negro", "Zumbi", "Minas Gerais", "MG", "20000000", null, "Próx bar do Bilu", "54991209433", instant, null));
		Address a7 = addressRepository.save(new Address(uuid("ce698b0f-6e11-4eff-9794-411b4067bb48"), u5, "Casa", true, "Av Rubia Gonçalves", "59", "Centro", "Erechim", "Rio Grande do Sul", "RS", "99700000", "Sala 15", "Edifício Blumenau", "54991604433", instant, null));
		Address a8 = addressRepository.save(new Address(uuid("0d467479-a939-4b04-a6d2-889a2f98d25f"), u6, "Trabalho", true, "Rua Francisco Chavier", "555", "Centro", "Passo Fundo", "Rio Grande do Sul", "RS", "99700000", "Sala 1", "Edifício David", "54991208433", instant, null));

		// Category
		Category c1 = cetegoryRepository.save(new Category(uuid("753dad79-2a1f-4f5c-bbd1-317a53587518"), "Livros", instant, instant));
		Category c2 = cetegoryRepository.save(new Category(uuid("431d856e-caf2-4367-823a-924ce46b2e02"), "Eletrônicos", instant, instant));
		Category c3 = cetegoryRepository.save(new Category(uuid("5c2b2b98-7b72-42dd-8add-9e97a2967e11"), "Computadores", instant, instant));
		Category c4 = cetegoryRepository.save(new Category(uuid("5227c10f-c81a-4885-b460-dbfee6dcc019"), "Somente para testes", instant, instant));

		// Product
		Product p1 = productRepository.save(new Product(uuid("7c4125cc-8116-4f11-8fc3-f40a0775aec7"), Set.of(c1), "O Senhor dos Anéis", loremIpsum, money(190.0), url, instant, null));
		Product p2 = productRepository.save(new Product(uuid("ff9d39d5-717f-4714-9688-9e75797c0ec0"), Set.of(c1, c3), "Smart TV", loremIpsum, money(2190.0), url, instant, null));
		Product p3 = productRepository.save(new Product(uuid("c8a0c055-030a-4e47-8aca-cf4634b98be5"), Set.of(c3), "Macbook Pro", loremIpsum, money(1250.0), url, instant, null));
		Product p4 = productRepository.save(new Product(uuid("f758d7cf-6005-4012-93fc-23afa45bf1ed"), Set.of(c3), "PC Gamer", loremIpsum, money(1200.0), url, instant, null));
		Product p5 = productRepository.save(new Product(uuid("e7310910-eb10-4694-854a-c95fcc7255eb"), Set.of(c3, c4), "Rails for Dummies", loremIpsum, money(100.99), url, instant, null));
		Product p6 = productRepository.save(new Product(uuid("c4c6609e-b45e-4821-af0c-a3f7bfd6f3a4"), Set.of(c3), "PC Gamer Ex", loremIpsum, money(1350.0), url, instant, null));
		Product p7 = productRepository.save(new Product(uuid("759c2a96-ea0f-4287-addb-cacd5e73b0bf"), Set.of(c3), "PC Gamer X", loremIpsum, money(1350.0),url, instant, null));
		Product p8 = productRepository.save(new Product(uuid("7d453673-4fb7-44d1-a2ce-2857a93f9018"), Set.of(c3), "PC Gamer Alfa", loremIpsum, money(1850.0), url, instant, null));
		Product p9 = productRepository.save(new Product(uuid("0187e60f-451a-4fe7-8e0a-d9649e9a8751"), Set.of(c3), "PC Gamer Tera", loremIpsum, money(1950.0), url, instant, null));
		Product p10 = productRepository.save(new Product(uuid("f778e30f-c329-4eae-aae1-bb98f52ffbc6"), Set.of(c3), "PC Gamer Y", loremIpsum, money(1700.0), url, instant, null));
		Product p11 = productRepository.save(new Product(uuid("8e9e066d-46e5-480a-ab8c-f970436e2547"), Set.of(c3), "PC Gamer Nitro", loremIpsum, money(1450.0), url, instant, null));
		Product p12 = productRepository.save(new Product(uuid("231182ad-3108-464e-8e39-a45cc39585a9"), Set.of(c3), "PC Gamer Card", loremIpsum, money(1850.0), url, instant, null));
		Product p13 = productRepository.save(new Product(uuid("c82bd447-2a95-4cc6-84a3-a253871d8db0"), Set.of(c3), "PC Gamer Plus", loremIpsum, money(1350.0), url, instant, null));
		Product p14 = productRepository.save(new Product(uuid("dba88c27-9c04-41a8-be7c-804001fa58ec"), Set.of(c3), "PC Gamer Hera", loremIpsum, money(2250.0), url, instant, null));
		Product p15 = productRepository.save(new Product(uuid("49c9067a-05a3-426e-82a7-f9b569f8638b"), Set.of(c3), "PC Gamer Weed", loremIpsum, money(2200.0), url, instant, null));
		Product p16 = productRepository.save(new Product(uuid("66191403-8fb5-4878-bd16-2613ae8d9b03"), Set.of(c3), "PC Gamer Max", loremIpsum, money(2340.0), url, instant, null));
		Product p17 = productRepository.save(new Product(uuid("68c92865-ec05-4ad2-8ba2-38fd44ae21c0"), Set.of(c3), "PC Gamer Turbo", loremIpsum, money(1280.0), url, instant, null));
		Product p18 = productRepository.save(new Product(uuid("40ec2be8-adc3-4d51-8477-25d85a7f02e6"), Set.of(c3), "PC Gamer Hot", loremIpsum, money(1450.0), url, instant, null));
		Product p19 = productRepository.save(new Product(uuid("87fdcec4-f750-427e-854f-9ff077d77843"), Set.of(c3), "PC Gamer Ez", loremIpsum, money(1750.0), url, instant, null));
		Product p20 = productRepository.save(new Product(uuid("9b8b9726-bda6-40d8-8ae8-f5f554c751ad"), Set.of(c3), "PC Gamer Tr", loremIpsum, money(1650.0), url, instant, null));
		Product p21 = productRepository.save(new Product(uuid("761e1442-00b6-4d15-bf7d-a71dd17fca51"), Set.of(c3), "PC Gamer Tx", loremIpsum, money(1680.0), url, instant, null));
		Product p22 = productRepository.save(new Product(uuid("568ce298-1b4c-4c21-bdd0-2caa9a009eed"), Set.of(c3), "PC Gamer Er", loremIpsum, money(1850.0),  url, instant, null));
		Product p23 = productRepository.save(new Product(uuid("f933c036-53c3-4b1e-b2ff-2e9f36470d36"), Set.of(c3), "PC Gamer Min", loremIpsum, money(2250.0), url, instant, null));
		Product p24 = productRepository.save(new Product(uuid("3fd5175e-b3c7-4e88-8684-8892e5d49145"), Set.of(c3), "PC Gamer Boo", loremIpsum, money(2350.0), url, instant, null));
		Product p25 = productRepository.save(new Product(uuid("638a3d4c-095f-48dc-ae7f-16ca3d3b4751"), Set.of(c3), "PC Gamer Foo", loremIpsum, money(4170.0), url, instant, null));
	}

	private UUID uuid(String uuid) {
		return UUID.fromString(uuid);
	}

	private BigDecimal money(Double value) {
		return BigDecimal.valueOf(value);
	}
}
