package com.lambdaschool.shoppingcart.controllers;

import com.lambdaschool.shoppingcart.models.CartItem;
import com.lambdaschool.shoppingcart.models.User;
import com.lambdaschool.shoppingcart.services.CartItemService;
import com.lambdaschool.shoppingcart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController
{
    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "",
        produces = {"application/json"})
    public ResponseEntity<?> listCartForCurrentUser()
    {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User u = userService.findByName(username);
        return new ResponseEntity<>(u,
            HttpStatus.OK);
    }

    @PutMapping(value = "/add/product/{productid}",
        produces = {"application/json"})
    public ResponseEntity<?> addToCart(@PathVariable long productid)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = userService.findByName(username);

        CartItem addCartItem = cartItemService.addToCart(u.getUserid(),
            productid, "");
        return new ResponseEntity<>(addCartItem,
            HttpStatus.OK);
    }

    @DeleteMapping(value = "/remove/product/{productid}",
        produces = {"application/json"})
    public ResponseEntity<?> removeFromCart(@PathVariable long productid)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = userService.findByName(username);

        CartItem removeCartItem = cartItemService.removeFromCart(u.getUserid(),
            productid,
            "");
        return new ResponseEntity<>(removeCartItem,
            HttpStatus.OK);
    }
}
