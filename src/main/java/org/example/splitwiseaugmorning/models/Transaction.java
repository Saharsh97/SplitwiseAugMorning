package org.example.splitwiseaugmorning.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.example.splitwiseaugmorning.models.enums.TransactionType;

@Getter
@Setter

// I will not store this in my tables,
// but using this class to represent actual transaction
public class Transaction {
    private User paidBy;
    private User receivedBy;
    private Integer amount;
    private TransactionType transactionType;

    public Transaction(User paidBy, User receivedBy, Integer amount, TransactionType transactionType) {
        this.paidBy = paidBy;
        this.receivedBy = receivedBy;
        this.amount = amount;
        this.transactionType = transactionType;
    }
}

// B -> D : 1000 Done
// A -> C : 500 Pending


// Models -> 100% help you with the core logic.
//        -> may or may not be tables.