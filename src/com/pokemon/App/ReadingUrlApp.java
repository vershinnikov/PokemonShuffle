package com.pokemon.App;

import com.pokemon.model.ReadingUrl;

public class ReadingUrlApp {

	//@Autowired
	//public PokemonManipulation poks;

	public static void main(String[] args) throws Exception {

		ReadingUrl rup = new ReadingUrl();
		rup.readConvertApp("online");
		System.out.println("end");
	}

}
