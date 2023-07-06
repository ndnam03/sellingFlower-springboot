package com.vnstart.library.service;

import com.vnstart.library.model.Customer;
import com.vnstart.library.model.Product;
import com.vnstart.library.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart addItemToCart(Product product, int quantity, Customer customer);
    ShoppingCart updateItemInCart(Product product, int quantity, Customer customer);
    ShoppingCart deleteItemFromCart(Product product, Customer customer);
}
