package com.projet.Bibliotheque.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet.Bibliotheque.entity.OrderDetail;
import com.projet.Bibliotheque.entity.User;

@Repository
public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer>{
	
	public List<OrderDetail> findByUser(User user);

}
