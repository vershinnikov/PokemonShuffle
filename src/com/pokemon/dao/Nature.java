package com.pokemon.dao;

import javax.persistence.*;

@Entity
public class Nature {

	@Id
	String nature;

	public Nature() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Nature{" +
				"nature='" + nature + '\'' +
				'}';
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public Nature(String nature) {
		super();
		this.nature = nature;
	}

	@ManyToOne(optional = false)
	private NvsN nvsNs;

	public NvsN getNvsNs() {
		return nvsNs;
	}

	public void setNvsNs(NvsN nvsNs) {
		this.nvsNs = nvsNs;
	}
}
