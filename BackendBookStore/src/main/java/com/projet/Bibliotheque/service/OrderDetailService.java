package com.projet.Bibliotheque.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projet.Bibliotheque.configuration.JwtRequestFilter;
import com.projet.Bibliotheque.dao.CartDao;
import com.projet.Bibliotheque.dao.OrderDetailDao;
import com.projet.Bibliotheque.dao.ProductDao;
import com.projet.Bibliotheque.dao.UserDao;
import com.projet.Bibliotheque.entity.Cart;
import com.projet.Bibliotheque.entity.OrderDetail;
import com.projet.Bibliotheque.entity.OrderInput;
import com.projet.Bibliotheque.entity.OrderProductQuantity;
import com.projet.Bibliotheque.entity.Product;
import com.projet.Bibliotheque.entity.User;

@Service
public class OrderDetailService {
	
	private static final String ORDER_PLACED = "Placed";  
	
	@Autowired
	private OrderDetailDao orderDetailDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CartDao cartDao;
	
	public List<OrderDetail> getAllOrderDetails(){
		List<OrderDetail> orderDetails = new ArrayList<>();
		orderDetailDao.findAll().forEach(e -> orderDetails.add(e));
		
		return orderDetails;
	}
	
	public List<OrderDetail> getOrderDetails() {
		String currentUser = JwtRequestFilter.CURRENT_USER;
		User user = userDao.findById(currentUser).get();
		
		return orderDetailDao.findByUser(user);
	}
	
	public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
		List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();
		
		for(OrderProductQuantity o: productQuantityList) {
			Product product = productDao.findById(o.getProductId()).get();
			
			String currentUser = JwtRequestFilter.CURRENT_USER;
			User user= userDao.findById(currentUser).get();
			
			OrderDetail orderDetail = new OrderDetail(
					orderInput.getFullName(),
					orderInput.getFullAddress(),
					orderInput.getContactNumber(),
					orderInput.getAlternateContactNumber(),
					ORDER_PLACED,
					product.getProductDiscountedPrice()*o.getQuantity(),
					product,
					user);
			
			if(!isSingleProductCheckout) {
				List<Cart> carts= cartDao.findByUser(user);
				carts.stream().forEach(x -> cartDao.deleteById(x.getCartId()));			
				
			}
			orderDetailDao.save(orderDetail);
		}
	}
	
	

}
