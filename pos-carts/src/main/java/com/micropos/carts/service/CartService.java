package com.micropos.carts.service;

import com.micropos.carts.model.*;


import java.util.List;

public interface CartService {

    public void checkout(Cart cart);

    public void cancel(Cart cart);

    public Cart add(Cart cart, Product product, int amount);

    public Cart add(Cart cart, String productId, int amount);

    public Cart delete(Cart cart,String id);

}
