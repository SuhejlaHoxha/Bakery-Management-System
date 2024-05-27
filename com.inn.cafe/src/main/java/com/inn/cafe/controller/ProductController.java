package com.inn.cafe.controller;

import com.inn.cafe.dao.ProductDao;
import com.inn.cafe.model.Product;
import com.inn.cafe.wrapper.ProductWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @Operation(summary = "Create a new product", description = "Creates a new product and saves it in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(
            @RequestBody(description = "Details of the new product", required = true) Product product) {
        Product savedProduct = productDao.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    @Operation(summary = "Find product by ID", description = "Returns the product matching the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/findById")
    public ResponseEntity<ProductWrapper> findById(
            @Parameter(description = "ID of the product", required = true) @RequestParam Integer id) {
        Optional<ProductWrapper> product = productDao.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all products", description = "Returns a list of all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ProductWrapper.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public ResponseEntity<List<ProductWrapper>> getAllProducts() {
        List<ProductWrapper> products = productDao.getAllProduct();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Update product status", description = "Updates the status of the product with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/updateStatus")
    public ResponseEntity<Void> updateStatus(
            @Parameter(description = "ID of the product", required = true) @RequestParam Integer id,
            @Parameter(description = "New status", required = true) @RequestParam String status) {
        productDao.updateProductStatus(status, id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get products by category", description = "Returns a list of products by category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ProductWrapper.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/byCategory")
    public ResponseEntity<List<ProductWrapper>> getProductsByCategory(
            @Parameter(description = "ID of the category", required = true) @RequestParam Integer categoryId) {
        List<ProductWrapper> products = productDao.getProductByCategory(categoryId);
        return ResponseEntity.ok(products);
    }
}
