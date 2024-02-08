package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.SalesReport;
import com.loomi.ecommerce.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public List<ShoppingCartRepository> findAll(){
        return shoppingCartRepository.findAll();
    }
}
