package com.pokemon.dao;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pokemon {
	public com.pokemon.dao.Nature getNature() {
		return Nature;
	}

	public void setNature(com.pokemon.dao.Nature nature) {
		Nature = nature;
	}

	public com.pokemon.dao.Power getPower() {
		return Power;
	}

	public void setPower(com.pokemon.dao.Power power) {
		Power = power;
	}

	public Ability getAbil() {
		return abil;
	}

	public void setAbil(Ability abil) {
		this.abil = abil;
	}

	public Pokemon getMega() {
		return mega;
	}

	public void setMega(Pokemon mega) {
		this.mega = mega;
	}

	public Pokemon(int pok_num, String pok_img, String pok_Name, String pok_Nature, int pok_Candy, int pok_Lolli,
				   String pok_LocStr, List<String> pok_Abil_List, String pok_Abil, String pok_Capture, boolean pok_Mega,
				   String pok_Power) {
		this.num= pok_num;
		this.img = pok_img;
		this.Name =  pok_Name;
		this.Nature = pok_Nature.equals("")?null:new Nature(pok_Nature);
		this.locStr = pok_LocStr;
		this.abil = pok_Abil.equals("")?null:new Ability(pok_Abil,pok_Mega,pok_Candy);
		this.captureRate = pok_Capture;
		this.Power = pok_Power.equals("")?null:new Power(Integer.parseInt(pok_Power),pok_Lolli);
		this.mega=null;
		this.list_abil = new ArrayList<>();
		for(int i=0;i<pok_Abil_List.size();i++){
			Ability a = new Ability(pok_Abil_List.get(i));
		}
	}

	@Override
	public String toString() {
		return "Pokemon{" +
				"id=" + id +
				", num=" + num +
				", Name='" + Name + '\'' +
				", img='" + img + '\'' +
				", Nature=" + Nature +
				", Power=" + Power +
				", abil=" + abil +
				", mega=" + mega +
				", locStr='" + locStr + '\'' +
				", captureRate='" + captureRate + '\'' +
				'}';
	}

	public Pokemon() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Id
	@GeneratedValue
	int id;
	int num;
	String Name;
	String img;
	@ManyToOne
	Nature Nature;
	@ManyToOne
	Power Power;
	@ManyToOne
	Ability abil;
	@ManyToMany
	List<Ability> list_abil;
	@OneToOne
	Pokemon mega;
	//@OneToOne
	//Location loc;
	String locStr;
	String captureRate;
}
