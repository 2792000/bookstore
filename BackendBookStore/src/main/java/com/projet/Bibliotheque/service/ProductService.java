package com.projet.Bibliotheque.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.projet.Bibliotheque.configuration.JwtRequestFilter;
import com.projet.Bibliotheque.dao.CartDao;
import com.projet.Bibliotheque.dao.ProductDao;
import com.projet.Bibliotheque.dao.UserDao;
import com.projet.Bibliotheque.entity.Cart;
import com.projet.Bibliotheque.entity.Product;
import com.projet.Bibliotheque.entity.User;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private CartDao cartDao;
	
	public Product addNewProduct(Product product) {
		return productDao.save(product);		
	}
	
	public List<Product> getAllProducts(int pageNumber, String searchKey){
		Pageable pageable = PageRequest.of(pageNumber, 8);
		
		if(searchKey.equals("")) {
			return (List<Product>) productDao.findAll(pageable);
		}else {
			return (List<Product>)productDao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey, searchKey, pageable);
		}
		
	}
	
	public void deleteProductDetails(Integer productId) {
		productDao.deleteById(productId);
	}

	public Product getProductDetailsById(Integer productId) {
		
		return productDao.findById(productId).get();
	}
	
	public List<Product> getProductDetails(boolean isSingeProductCheckout, Integer productId) {
	
		if(isSingeProductCheckout && productId != 0) {
			List<Product> list= new ArrayList<>();
			Product product = productDao.findById(productId).get();
			list.add(product);
			return list;
		}else {
		
			String username = JwtRequestFilter.CURRENT_USER;
			User user = userDao.findById(username).get();
			List<Cart>  carts= cartDao.findByUser(user);
			
			return carts.stream().map(x -> x.getProduct()).collect(Collectors.toList());
			
		}
		
	
	}
	
	
	

}
