package org.example.splitwiseaugmorning.strategies;

import org.example.splitwiseaugmorning.models.Expense;
import org.example.splitwiseaugmorning.models.Transaction;

import java.util.List;

public interface SettleUpStrategy {
    List<Transaction> settleUp(List<Expense> expenses);
}
