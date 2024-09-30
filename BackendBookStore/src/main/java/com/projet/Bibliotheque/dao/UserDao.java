package com.projet.Bibliotheque.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projet.Bibliotheque.entity.User;

@Repository
public interface UserDao extends CrudRepository<User, String> {
}
