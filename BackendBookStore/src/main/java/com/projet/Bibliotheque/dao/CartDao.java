package com.projet.Bibliotheque.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet.Bibliotheque.entity.Cart;
import com.projet.Bibliotheque.entity.User;

@Repository
public interface CartDao extends CrudRepository<Cart, Integer>{
	
	public List<Cart> findByUser(User user);

}
