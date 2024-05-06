package com.oxiane.onepay.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.oxiane.onepay.Repository.TransactionRepository;
import com.oxiane.onepay.model.StatusTransaction;
import com.oxiane.onepay.model.Transaction;
import com.oxiane.onepay.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{
	  
    @Autowired
    private TransactionRepository transactionRepository;
    
    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) {
        if (transaction.getStatus() != StatusTransaction.NEW) {
            throw new IllegalArgumentException("La transaction doit être dans l'état 'NEW' pour être créée.");
        }
        return transactionRepository.save(transaction);
    }

    public Transaction authorizeTransaction(Long id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            if (transaction.getStatus() == StatusTransaction.NEW) {
                transaction.setStatus(StatusTransaction.AUTHORIZED);
                return transactionRepository.save(transaction);
            } else {
                throw new IllegalArgumentException("La transaction ne peut être autorisée que si elle est dans l'état 'NEW'.");
            }
        } else {
            throw new IllegalArgumentException("Transaction non trouvée avec l'ID: " + id);
        }
    }

    public Transaction captureTransaction(Long id) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction transaction = optionalTransaction.get();
            if ( StatusTransaction.AUTHORIZED.equals(transaction.getStatus())) {
                transaction.setStatus(StatusTransaction.CAPTURED);
                return transactionRepository.save(transaction);
            } else {
                throw new IllegalArgumentException("La transaction ne peut être capturée que si elle est dans l'état 'AUTHORIZED'.");
            }
        } else {
            throw new IllegalArgumentException("Transaction non trouvée avec l'ID: " + id);
        }
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

}
