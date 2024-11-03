package com.example.tea_loufu.service;

import com.example.tea_loufu.entity.Product;
import com.example.tea_loufu.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    public void deleteProduct(int productId) {
        // Kiểm tra xem sản phẩm có tồn tại không trước khi xóa
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product not found with id: " + productId);
        }
        // Xóa sản phẩm
        productRepository.deleteById(productId);
    }

    public Product findById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null); // Trả về null nếu không tìm thấy sản phẩm
    }

    public Product updateProduct(int productID, Product updatedProduct) {
        Optional<Product> existingProductOptional = productRepository.findById(productID);

        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setStock(updatedProduct.getStock());
            existingProduct.setImageUrl(updatedProduct.getImageUrl());

            return productRepository.save(existingProduct);
        } else {
            throw new EntityNotFoundException("Product with ID " + productID + " not found.");
        }
    }

}
