package com.pokemon.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Ability {

	@Id
	String name;
	String Description;
	boolean isMega;

	@Override
	public String toString() {
		return "Ability{" +
				"name='" + name + '\'' +
				", Description='" + Description + '\'' +
				", isMega=" + isMega +
				", candy=" + candy +
				'}';
	}

	int candy;
	public Ability() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Ability(String name, boolean isMega, int candy) {
		super();
		this.name = name;
		this.isMega = isMega;
		this.candy = candy;

	}
	public Ability(String string) {
		this(string,false,0);
	}


}
