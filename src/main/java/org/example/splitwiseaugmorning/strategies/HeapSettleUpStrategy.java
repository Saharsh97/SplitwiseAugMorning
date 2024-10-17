package org.example.splitwiseaugmorning.strategies;

import org.example.splitwiseaugmorning.models.*;
import org.example.splitwiseaugmorning.models.enums.ExpenseType;
import org.example.splitwiseaugmorning.models.enums.TransactionType;
import org.example.splitwiseaugmorning.models.enums.UserExpenseType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Qualifier("HeapSettleUpStrategy")
public class HeapSettleUpStrategy implements SettleUpStrategy {
    @Override
    public List<Transaction> settleUp(List<Expense> expenses) {
        // use Priority Queues to get the answer;

        // 1. get the overall balance for these guys.
        Map<User, Integer> finalBalanceMap = new HashMap<>();
        for(Expense expense : expenses) {
            for(UserExpense userExpense : expense.getUserExpenses()) {
                User currentUser = userExpense.getUser();
                Integer existingBalance = finalBalanceMap.getOrDefault(currentUser, 0);
                if(userExpense.getUserExpenseType() == UserExpenseType.PAID_BY){
                    existingBalance += userExpense.getAmount();
                } else if (userExpense.getUserExpenseType() == UserExpenseType.HAD_TO_PAY) {
                    existingBalance -= userExpense.getAmount();
                }
                finalBalanceMap.put(currentUser, existingBalance);
            }
        }
        // now we have the overall final balance on each person

        // use heaps to get the Transactions!
        PriorityQueue<UserBalance> getterQueue = new PriorityQueue<>((ub1, ub2) -> ub2.getBalance() - ub1.getBalance()); // max heap of positive values
        PriorityQueue<UserBalance> payerQueue = new PriorityQueue<>(); // minHeap of negative values

        int totalPay = 0;
        int totalGet = 0;
        for(Map.Entry<User, Integer> entry : finalBalanceMap.entrySet()) {
            UserBalance userBalance = new UserBalance(entry.getKey(), entry.getValue());
            if(userBalance.getBalance() > 0){
                totalGet += userBalance.getBalance();
                getterQueue.add(userBalance);
            } else {
                totalPay += userBalance.getBalance();
                payerQueue.add(userBalance);
            }
        }

        if(Math.abs(totalPay) != Math.abs(totalGet)){
            throw new RuntimeException("Transactions cannot be generated");
        }

        List<Transaction> transactions = new ArrayList<>();
        while(!getterQueue.isEmpty() && !payerQueue.isEmpty()){
            UserBalance X = getterQueue.poll();
            UserBalance Y = getterQueue.poll();
            Integer payAmount = Math.min(Math.abs(X.getBalance()), Math.abs(Y.getBalance()));
            // Y -> X : payAmount
            Transaction transaction = new Transaction(Y.getUser(), X.getUser(), payAmount, TransactionType.PENDING);
            transactions.add(transaction);

            // update the balance for both.
            X.setBalance(X.getBalance() - payAmount);   // X = D, 0
            Y.setBalance(Y.getBalance() + payAmount);   // Y = B, -200

            if(X.getBalance() > 0){
                getterQueue.add(X);
            } if(Y.getBalance() < 0){
                payerQueue.add(Y);
            }
        }
        // This gives you only pending transactions.


        // lets add the Done transactions as well, for more clarity to the user.
        for(Expense expense : expenses) {
            if(expense.getExpenseType() == ExpenseType.DUMMY){
                // convert the expense to a transaction and it to the transaction list.
                UserExpense hadPaid = expense.getUserExpenses().stream()
                        .filter(uexp -> uexp.getUserExpenseType() == UserExpenseType.PAID_BY)
                        .findFirst().get();
                UserExpense hadReceived = expense.getUserExpenses().stream()
                        .filter(uexp -> uexp.getUserExpenseType() == UserExpenseType.HAD_TO_PAY)
                        .findFirst().get();
                Integer amount = hadPaid.getAmount();

                Transaction transaction = new Transaction(hadPaid.getUser(), hadReceived.getUser(), amount, TransactionType.DONE);
                transactions.add(transaction);
            }
        }
        return transactions;
    }
}

// B -> D : 1000 DONE
// A -> C : 500 PENDING
// A -> D : 250 PENDING


// your actual output
// B -> D : 1000 DONE
// A -> C : 500 PENDING
// A -> D : 250 PENDING
