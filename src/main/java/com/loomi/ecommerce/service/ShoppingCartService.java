package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.ShoppingCart;
import com.loomi.ecommerce.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public List<ShoppingCart> findAll(){
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart save(ShoppingCart shoppingCart){
        shoppingCart.setId(null);
        return shoppingCartRepository.save(shoppingCart);
    }

    public ShoppingCart findById(Long id) {
        Optional<ShoppingCart> optionalShoppingCart =  shoppingCartRepository.findById(id);
        return optionalShoppingCart.orElse(null);
    }

    public ShoppingCart update(ShoppingCart shoppingCart) {
        ShoppingCart shoppingCartFound = findById(shoppingCart.getId());
        if (shoppingCartFound != null) {
            return shoppingCartRepository.save(shoppingCart);
        }else{
            return shoppingCart;
        }
    }

    public void deleteById(Long id) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setId(id);
        shoppingCartRepository.delete(shoppingCart);

    }
}
