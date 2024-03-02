package com.ramzi.project.elasticsearch.controller;

import com.ramzi.project.elasticsearch.domain.Product;
import com.ramzi.project.elasticsearch.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/prodycts")
public class ProductController {
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<String> createOrUpdateDocument(@RequestParam Product product) throws IOException {
        String response = productRepository.createOrUpdateDocument(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/bulk")
    public ResponseEntity<String> bulk(@RequestBody List<Product> products) throws IOException {
        String response = productRepository.bulkSave(products);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/${productId}")
    public ResponseEntity<Product> getDocumentById(@PathVariable String productId) throws IOException {
        Product product = productRepository.findDocById(productId);
        log.info("Product document has been successfully retrieved");
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/${productId}")
    public ResponseEntity<String> deleteDocumentById(@PathVariable String productId) throws IOException {
        String message = productRepository.deleteById(productId);
        log.info("Product document has been successfully deleted");
        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() throws IOException{
        List<Product> products = productRepository.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
