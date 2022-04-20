package com.micropos.carts.service;

import com.micropos.carts.model.Cart;
import com.micropos.carts.model.Item;
import com.micropos.carts.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;


import java.io.Serializable;


@RequiredArgsConstructor
@Component
public class CartServiceImp implements CartService, Serializable {

    @Autowired
    private CircuitBreakerFactory factory;

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    @Override
    public void checkout(Cart cart) {
        cart.emptyList();
    }

    @Override
    public void cancel(Cart cart) {
        cart.emptyList();
    }

    @Override
    public Cart add(Cart cart, Product product, int amount) {
        return add(cart, product.getId(), amount);
    }

    @Override
    public Cart add(Cart cart, String productId, int amount) {
        CircuitBreaker cB = factory.create("circuitbreaker");
        return cB.run(()->{
            ResponseEntity<Product> productResponseEntity = restTemplate.
                    getForEntity("http://product-service/api/products/" + productId, Product.class);
            Product product = productResponseEntity.getBody();
            if (product == null) return cart;
            cart.addItem(new Item(product, amount));
            return cart;
                },throwable-> cart
        );
    }

    @Override
    public Cart delete(Cart cart, String productId) {
        add(cart,productId,-cart.getItem(productId).getQuantity());
        return cart;
    }

}
