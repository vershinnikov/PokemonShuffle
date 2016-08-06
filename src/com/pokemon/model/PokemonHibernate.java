package com.pokemon.model;

import com.pokemon.dao.Ability;
import com.pokemon.dao.Nature;
import com.pokemon.dao.Pokemon;
import com.pokemon.dao.Power;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Repository
public class PokemonHibernate implements PokemonManipulation{
	@PersistenceContext
	EntityManager em;	
	@Override
	@Transactional 
	public boolean addPokemon(Pokemon p) {
		Ability ab=p.getAbil();
		Nature na=p.getNature();
		Power po=p.getPower();
		if(ab!=null&&em.find(Ability.class,ab.getName())!=null){
			p.setAbil(em.find(Ability.class,ab.getName()));
		}else{
			em.persist(ab);
		}
		if(na!=null&&em.find(Nature.class,na.getNature())!=null){
			p.setNature(em.find(Nature.class,na.getNature()));
		}else{
			em.persist(na);
		}
		if(po!=null&&em.find(Power.class,po.getId())!=null){
			p.setPower(em.find(Power.class,po.getId()));
		}else{
			em.persist(po);
		}
		em.persist(p);
		return true;
	}

	@Override
	public Iterable anyRequest(String s) {
		return em.createQuery(s).getResultList();
	}

}

