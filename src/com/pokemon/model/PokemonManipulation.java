package com.pokemon.model;

import com.pokemon.dao.Pokemon;

public interface PokemonManipulation {
	boolean addPokemon(Pokemon p);
	Iterable anyRequest(String s);
}
