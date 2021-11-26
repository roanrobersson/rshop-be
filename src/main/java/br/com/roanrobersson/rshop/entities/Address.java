package br.com.roanrobersson.rshop.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"user_id", "nick"}))	 
@Getter @Setter @NoArgsConstructor 
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@ManyToOne
	private User user;
	
	@Column(nullable=false, length=20)
	@EqualsAndHashCode.Include
	private String nick;
	
	@Column(nullable=false, length=75)
	private String address;
	
	@Column(nullable=false, length=6)
	private String number;
	
	@Column(nullable=false, length=30)
	private String neighborhood;
	
	@Column(nullable=false, length=75)
	private String city;
	
	@Column(nullable=false, length=75)
	private String state;
	
	@Column(columnDefinition = "CHAR(2) NOT NULL")
	private String uf;
	
	@Column(columnDefinition = "CHAR(8) NOT NULL")
	private String postalCode;
	
	@Column(length=75)
	private String complement;
	
	@Column(nullable=false, length=75)
	private String referencePoint;
	
	@Column(columnDefinition = "CHAR(11) NOT NULL")
	private String phone;
	
	public Address(Long id, String nick, String address, String number, 
			String neighborhood, String city, String state, String uf, 
			String postalCode, String complement, String referencePoint) {
		this.id = id;
		this.nick = nick;
		this.address = address;
		this.number = number;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
		this.uf = uf;
		this.postalCode = postalCode;
		this.referencePoint = referencePoint;
	}
}
