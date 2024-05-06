package com.oxiane.onepay.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.oxiane.onepay.model.StatusTransaction;
import com.oxiane.onepay.model.Transaction;
import com.oxiane.onepay.service.TransactionService;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    public void testCreateTransaction() throws Exception {
        Transaction savedTransaction = new Transaction();
        savedTransaction.setId(1L);
        
        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(savedTransaction);

        mockMvc.perform(MockMvcRequestBuilders.post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\":100, \"moyenPaiementType\":\"CREDIT_CARD\", \"status\":\"NEW\", \"commands\":[{\"productName\":\"Product 1\", \"quantity\":2, \"price\":10}, {\"productName\":\"Product 2\", \"quantity\":1, \"price\":20}]}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));
    }

    @Test
    public void testAuthorizeTransaction() throws Exception {
        Transaction authorizedTransaction = new Transaction();
        authorizedTransaction.setId(1L);
        authorizedTransaction.setStatus(StatusTransaction.AUTHORIZED);
        
        when(transactionService.authorizeTransaction(1L)).thenReturn(authorizedTransaction);

        mockMvc.perform(MockMvcRequestBuilders.put("/transactions/1/authorize"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("AUTHORIZED"));
    }
    
    @Test
    public void testCaptureTransaction() throws Exception {
        Transaction capturedTransaction = new Transaction();
        capturedTransaction.setId(1L);
        capturedTransaction.setStatus(StatusTransaction.CAPTURED);
        
        when(transactionService.captureTransaction(1L)).thenReturn(capturedTransaction);

        mockMvc.perform(MockMvcRequestBuilders.put("/transactions/1/capture"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("CAPTURED"));
    }

    @Test
    public void testGetAllTransactions() throws Exception {
        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction();
        transaction1.setId(1L);
        transaction1.setStatus(StatusTransaction.NEW);
        transactions.add(transaction1);
        Transaction transaction2 = new Transaction();
        transaction2.setId(2L);
        transaction2.setStatus(StatusTransaction.AUTHORIZED);
        transactions.add(transaction2);

        when(transactionService.getAllTransactions()).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/transactions"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status").value("NEW"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].status").value("AUTHORIZED"));
    }
   
}
