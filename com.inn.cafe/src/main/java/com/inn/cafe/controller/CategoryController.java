package com.inn.cafe.controller;

import com.inn.cafe.model.Category;
import com.inn.cafe.dao.CategoryDao;
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
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @Operation(summary = "Create a new category", description = "Creates a new category and saves it in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category created successfully",
                    content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(
            @RequestBody(description = "Details of the new category", required = true) Category category) {
        Category savedCategory = categoryDao.save(category);
        return ResponseEntity.ok(savedCategory);
    }

    @Operation(summary = "Get category by ID", description = "Returns the category matching the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found",
                    content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(
            @Parameter(description = "ID of the category", required = true) @PathVariable Integer id) {
        Optional<Category> category = categoryDao.findById(id);
        return category.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all categories", description = "Returns a list of all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryDao.findAll();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Update an existing category", description = "Updates the category with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory(
            @Parameter(description = "ID of the category", required = true) @RequestParam Integer id,
            @RequestBody(description = "Updated category object", required = true) Category category) {
        if (!categoryDao.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        category.setId(id);
        Category updatedCategory = categoryDao.save(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @Operation(summary = "Delete a category", description = "Deletes the category with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Category not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID of the category", required = true) @RequestParam Integer id) {
        if (!categoryDao.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoryDao.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
