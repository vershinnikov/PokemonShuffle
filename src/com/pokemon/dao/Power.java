package com.pokemon.dao;

import javax.persistence.*;

@Entity
public class Power {
	@Id 
	int id;
	int BasePower;
	int maxPower;
	int Lolli;
	public Power(int basePower, int lolli) {
		super();
		BasePower = basePower;
		Lolli = lolli;
		id=BasePower*1000+lolli;
		maxPower= BasePower+20+lolli*2;
	}
	public Power() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Power{" +
				"id=" + id +
				", BasePower=" + BasePower +
				", maxPower=" + maxPower +
				", Lolli=" + Lolli +
				'}';
	}
}
