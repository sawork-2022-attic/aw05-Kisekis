
package com.micropos.carts.rest;

import com.micropos.carts.api.CartsApi;
import com.micropos.carts.dto.CartDto;
import com.micropos.carts.dto.InlineObjectDto;
import com.micropos.carts.mapper.CartMapper;
import com.micropos.carts.model.Cart;
import com.micropos.carts.service.CartService;
//import com.micropos.products.dto.ProductDto;
//import com.micropos.products.mapper.ProductMapper;
//import com.micropos.products.service.ProductService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CartController implements CartsApi {

    private final CartMapper CartMapper;

    private final CartService CartService;

    @Autowired
    private Cart cart;


    public CartController(CartService cartService, CartMapper cartMapper) {
        this.CartService = cartService;
        this.CartMapper = cartMapper;
    }

    @Override
    public ResponseEntity<CartDto> listItems(){
        CartDto cartDto = CartMapper.toCartDto(this.cart);
        if (cartDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<CartDto> deleteItemById(String id){
        CartService.delete(cart,id);
        return new ResponseEntity<>(CartMapper.toCartDto(this.cart), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CartDto> cartsCancelDelete() {
        CartService.cancel(cart);
        return new ResponseEntity<>(CartMapper.toCartDto(this.cart), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<CartDto> cartsCheckoutGet() {
        CartService.checkout(cart);
        return new ResponseEntity<>(CartMapper.toCartDto(this.cart), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<CartDto> cartsPost(@RequestBody InlineObjectDto json) {
        CartService.add(cart,json.getId(), json.getQuantity());
        return new ResponseEntity<>(CartMapper.toCartDto(this.cart), HttpStatus.OK);
    }

}


