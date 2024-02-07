package com.loomi.ecommerce.service;

import com.loomi.ecommerce.entity.OrderItem;
import com.loomi.ecommerce.entity.Product;
import com.loomi.ecommerce.exeception.InsufficientStockException;
import com.loomi.ecommerce.exeception.ProductNotFoundException;
import com.loomi.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product save(Product product){

        product.setId(null);
        return productRepository.save(product);
    }

    public Product findById(Long id) {
        Optional<Product> optionalProduct=  productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    public List<OrderItem> findOrderItemByProjectId(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.get().getOrderItems();
    }

    public List<Product> findByProductName(String productName) {
        return  productRepository.findByProductNameContainingIgnoreCase(productName);
    }

    public List<Product> findByCategory(String category) {
        return  productRepository.findByCategoryContainingIgnoreCase(category);
    }

    public List<Product> findByDescription(String description) {
        return  productRepository.findByDescriptionContainingIgnoreCase(description);
    }

    public List<Product> findByPrice(double price) {
        return  productRepository.findByPrice(price);
    }

    public List<Product> findByQuantityInStock(int quantityInStock) {
        return  productRepository.findByQuantityInStock(quantityInStock);
    }
    public Product removeProductByQuantity(int quantity, Product product)
            throws InsufficientStockException, ProductNotFoundException {
        Product productFound = findById(product.getId());
        if (productFound != null) {
            if (product.getQuantityInStock() >= quantity) {
                product.setQuantityInStock(product.getQuantityInStock() - quantity);
                return productRepository.save(product);
            } else {
                throw new InsufficientStockException("Not enough stock available");
            }
        } else {
            throw new ProductNotFoundException("Product not found");
        }
    }

    public List<Product> availableProducts() {
        return productRepository.findByQuantityInStockGreaterThan(1);
    }

    public Product update(Product product) {
        Product productFound = findById(product.getId());
        if (productFound != null) {
            return productRepository.save(product);
        }else{
            return product;
        }
    }

    public void deleteById(Long id) {
        Product product = new Product();
        product.setId(id);
        productRepository.delete(product);

    }

}

