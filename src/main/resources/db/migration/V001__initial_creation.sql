create table address (
  id char(36) not null,
  address_line varchar(75) not null,
  city varchar(75) not null,
  complement varchar(75),
  created_at datetime(6),
  main bit not null,
  neighborhood varchar(30) not null,
  nick varchar(20) not null,
  number varchar(6) not null,
  postal_code char(8) not null,
  reference_point varchar(75) not null,
  state varchar(75) not null,
  telephone varchar(11) not null,
  uf char(2) not null,
  updated_at datetime(6),
  user_id char(36) not null,
  primary key (id)
) engine = InnoDB;

create table category (
  id char(36) not null,
  created_at datetime(6),
  name varchar(127) not null,
  updated_at datetime(6),
  primary key (id)
) engine = InnoDB;

create table image (
  id char(36) not null,
  content_type varchar(50) not null,
  created_at datetime(6),
  file_name varchar(255) not null,
  file_size integer not null,
  original_file_name varchar(255) not null,
  updated_at datetime(6),
  primary key (id)
) engine = InnoDB;

create table privilege (
  id char(36) not null,
  created_at datetime(6),
  description varchar(100) not null,
  name varchar(30) not null,
  updated_at datetime(6),
  primary key (id)
) engine = InnoDB;

create table product (
  id char(36) not null,
  created_at datetime(6),
  description text,
  img_url varchar(255) not null,
  name varchar(127) not null,
  price decimal(10, 2) not null,
  sku varchar(12) not null,
  updated_at datetime(6),
  primary key (id)
) engine = InnoDB;

create table product_category (
  product_id char(36) not null,
  category_id char(36) not null,
  primary key (product_id, category_id)
) engine = InnoDB;

create table role (
  id char(36) not null,
  created_at datetime(6),
  name varchar(30) not null,
  updated_at datetime(6),
  primary key (id)
) engine = InnoDB;

create table role_privilege (
  role_id char(36) not null,
  privilege_id char(36) not null,
  primary key (role_id, privilege_id)
) engine = InnoDB;

create table user (
  id char(36) not null,
  birth_date date not null,
  cpf char(11) not null,
  created_at datetime(6),
  email varchar(50) not null,
  first_name varchar(50) not null,
  last_login_at datetime(6),
  name varchar(100) not null,
  password varchar(255) not null,
  primary_telephone varchar(11) not null,
  rg varchar(14) not null,
  secondary_telephone varchar(11) not null,
  updated_at datetime(6),
  verified_at datetime(6),
  primary key (id)
) engine = InnoDB;

create table user_role (
  user_id char(36) not null,
  role_id char(36) not null,
  primary key (user_id, role_id)
) engine = InnoDB;

create table value_type (
  id char(36) not null,
  created_at datetime(6),
  name varchar(15) not null,
  updated_at datetime(6),
  primary key (id)
) engine = InnoDB;

alter table
  address
add
  constraint UKb6y1uijh0upn1m97pfe915goq unique (nick, user_id);

alter table
  category
add
  constraint UK_46ccwnsi9409t36lurvtyljak unique (name);

alter table
  privilege
add
  constraint UK_h7iwbdg4ev8mgvmij76881tx8 unique (name);

alter table
  product
add
  constraint UK_jmivyxk9rmgysrmsqw15lqr5b unique (name);

alter table
  product
add
  constraint UK_q1mafxn973ldq80m1irp3mpvq unique (sku);

alter table
  role
add
  constraint UK_8sewwnpamngi6b1dwaa88askk unique (name);

alter table
  user
add
  constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);

alter table
  value_type
add
  constraint UK_j4ym354feo05hpluaccbm14um unique (name);

alter table
  address
add
  constraint FKda8tuywtf0gb6sedwk7la1pgi foreign key (user_id) references user (id);

alter table
  product_category
add
  constraint FKkud35ls1d40wpjb5htpp14q4e foreign key (category_id) references category (id);

alter table
  product_category
add
  constraint FK2k3smhbruedlcrvu6clued06x foreign key (product_id) references product (id);

alter table
  role_privilege
add
  constraint FKdkwbrwb5r8h74m1v7dqmhp99c foreign key (privilege_id) references privilege (id);

alter table
  role_privilege
add
  constraint FKsykrtrdngu5iexmbti7lu9xa foreign key (role_id) references role (id);

alter table
  user_role
add
  constraint FKa68196081fvovjhkek5m97n3y foreign key (role_id) references role (id);

alter table
  user_role
add
  constraint FK859n2jvi8ivhui0rl0esws6o foreign key (user_id) references user (id);