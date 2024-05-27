package com.inn.cafe.controller;

import com.inn.cafe.model.Bill;
import com.inn.cafe.dao.BillDao;
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
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillDao billDao;

    @Operation(summary = "Create a new bill", description = "Creates a new bill and saves it in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill created successfully",
                    content = @Content(schema = @Schema(implementation = Bill.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<Bill> createBill(
            @RequestBody(description = "Details of the new bill", required = true) Bill bill) {
        Bill savedBill = billDao.save(bill);
        return ResponseEntity.ok(savedBill);
    }

    @Operation(summary = "Get bill by ID", description = "Returns the bill matching the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill found",
                    content = @Content(schema = @Schema(implementation = Bill.class))),
            @ApiResponse(responseCode = "404", description = "Bill not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Bill> getBillById(
            @Parameter(description = "ID of the bill", required = true) @PathVariable Integer id) {
        Optional<Bill> bill = billDao.findById(id);
        return bill.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all bills", description = "Returns a list of all bills")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bills retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Bill.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/all")
    public ResponseEntity<List<Bill>> getAllBills() {
        List<Bill> bills = billDao.getAllBills();
        return ResponseEntity.ok(bills);
    }

    @Operation(summary = "Get bills by username", description = "Returns a list of bills created by the given username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bills retrieved successfully",
                    content = @Content(schema = @Schema(implementation = Bill.class))),
            @ApiResponse(responseCode = "404", description = "Bills not found for the user"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/byUser")
    public ResponseEntity<List<Bill>> getBillsByUserName(
            @Parameter(description = "Username of the creator", required = true) @RequestParam String username) {
        List<Bill> bills = billDao.getBillByUserName(username);
        return ResponseEntity.ok(bills);
    }

    @Operation(summary = "Update a bill", description = "Updates the bill with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Bill not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/update")
    public ResponseEntity<Bill> updateBill(
            @Parameter(description = "ID of the bill", required = true) @RequestParam Integer id,
            @RequestBody(description = "Updated bill object", required = true) Bill bill) {
        if (!billDao.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bill.setId(id);
        Bill updatedBill = billDao.save(bill);
        return ResponseEntity.ok(updatedBill);
    }

    @Operation(summary = "Delete a bill", description = "Deletes the bill with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bill deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Bill not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteBill(
            @Parameter(description = "ID of the bill", required = true) @RequestParam Integer id) {
        if (!billDao.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        billDao.deleteById(id);
        return ResponseEntity.ok().build();
    }

    // Additional Operations

    @Operation(summary = "Update bill's total amount", description = "Updates the total amount of the bill with the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total amount updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Bill not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PatchMapping("/updateTotal")
    public ResponseEntity<Void> updateTotal(
            @Parameter(description = "ID of the bill", required = true) @RequestParam Integer id,
            @Parameter(description = "New total amount", required = true) @RequestParam Integer total) {
        Optional<Bill> billOptional = billDao.findById(id);
        if (billOptional.isPresent()) {
            Bill bill = billOptional.get();
            bill.setTotal(total);
            billDao.save(bill);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
