package com.oxiane.onepay.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.oxiane.onepay.Repository.TransactionRepository;
import com.oxiane.onepay.model.StatusTransaction;
import com.oxiane.onepay.model.Transaction;
import com.oxiane.onepay.service.impl.TransactionServiceImpl;

@SpringBootTest
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    private TransactionService transactionService;

    @BeforeEach
    public void setup() {
    	transactionService = new TransactionServiceImpl(transactionRepository);
    }
    
    @Test
    public void testCreateTransaction() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setStatus(StatusTransaction.NEW);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction createdTransaction = transactionService.createTransaction(transaction);

        assertNotNull(createdTransaction);
        assertEquals(StatusTransaction.NEW, createdTransaction.getStatus());
    }

    @Test
    public void testAuthorizeTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setStatus(StatusTransaction.NEW);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);        
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        Transaction authorizedTransaction = transactionService.authorizeTransaction(1L);

        assertNotNull(authorizedTransaction);
        assertEquals(StatusTransaction.AUTHORIZED, authorizedTransaction.getStatus());
    }

    @Test
    public void testCaptureTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setStatus(StatusTransaction.AUTHORIZED);

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);        
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        Transaction capturedTransaction = transactionService.captureTransaction(1L);

        assertNotNull(capturedTransaction);
        assertEquals(StatusTransaction.CAPTURED, capturedTransaction.getStatus());
    }

    @Test
    public void testGetAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setAmount(BigDecimal.valueOf(100));
        transaction1.setStatus(StatusTransaction.NEW);
        transactions.add(transaction1);
        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setAmount(BigDecimal.valueOf(200));
        transaction2.setStatus(StatusTransaction.AUTHORIZED);
        transactions.add(transaction2);

        when(transactionRepository.findAll()).thenReturn(transactions);

        List<Transaction> retrievedTransactions = transactionService.getAllTransactions();

        assertEquals(2, retrievedTransactions.size());
    }

}
