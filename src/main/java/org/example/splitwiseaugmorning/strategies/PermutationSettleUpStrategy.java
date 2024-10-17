package org.example.splitwiseaugmorning.strategies;

import org.example.splitwiseaugmorning.models.Expense;
import org.example.splitwiseaugmorning.models.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier("PermutationSettleUpStrategy")
public class PermutationSettleUpStrategy implements SettleUpStrategy{
    @Override
    public List<Transaction> settleUp(List<Expense> expenses) {
        // uses Recursion and backtracking to get the answer
        return List.of();
    }
}
