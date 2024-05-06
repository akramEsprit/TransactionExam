package com.oxiane.onepay.service;

import java.util.List;
import com.oxiane.onepay.model.Transaction;

public interface TransactionService {

    public Transaction createTransaction(Transaction transaction);

    public Transaction authorizeTransaction(Long id);

    public Transaction captureTransaction(Long id);

    public List<Transaction> getAllTransactions() ;

}
