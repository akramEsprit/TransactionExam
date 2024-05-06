package com.oxiane.onepay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oxiane.onepay.model.Transaction;
import com.oxiane.onepay.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/authorize")
    public ResponseEntity<Transaction> authorizeTransaction(@PathVariable Long id) {
        Transaction authorizedTransaction = transactionService.authorizeTransaction(id);
        return new ResponseEntity<>(authorizedTransaction, HttpStatus.OK);
    }

    @PutMapping("/{id}/capture")
    public ResponseEntity<Transaction> captureTransaction(@PathVariable Long id) {
        Transaction capturedTransaction = transactionService.captureTransaction(id);
        return new ResponseEntity<>(capturedTransaction, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

}
