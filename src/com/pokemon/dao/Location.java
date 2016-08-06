package com.pokemon.dao;

import javax.persistence.*;
@Entity
public class Location {

	@Id
	@GeneratedValue
	int id;
	String location;
	
}
