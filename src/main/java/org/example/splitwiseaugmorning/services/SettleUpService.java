package org.example.splitwiseaugmorning.services;

import org.example.splitwiseaugmorning.exceptions.GroupNotFoundException;
import org.example.splitwiseaugmorning.models.*;
import org.example.splitwiseaugmorning.repositories.GroupRepository;
import org.example.splitwiseaugmorning.repositories.UserExpenseRepository;
import org.example.splitwiseaugmorning.repositories.UserRepository;
import org.example.splitwiseaugmorning.strategies.SettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Service
public class SettleUpService {

    private GroupRepository groupRepository;
    private SettleUpStrategy settleUpStrategy;
    private UserRepository userRepository;
    private UserExpenseRepository userExpenseRepository;

    @Autowired
    public SettleUpService(
            GroupRepository groupRepository,
            UserRepository userRepository,
            UserExpenseRepository userExpenseRepository,
            @Qualifier("HeapSettleUpStrategy") SettleUpStrategy settleUpStrategy) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.userExpenseRepository = userExpenseRepository;
        this.settleUpStrategy = settleUpStrategy;
    }

    public List<Transaction> settleUpUser(Long userId){
        // 0. verify if user exists
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();

        // 1. given a User, get all of UserExpense Objects.
        List<UserExpense> userExpenses = userExpenseRepository.findAllByUser(user);

        // 2. get the actual Expense from UserExpense objects.
        Set<Expense> expenses = new HashSet<>();
        for(UserExpense userExpense : userExpenses){
            expenses.add(userExpense.getExpense());
        }

        // 3. I have all unique expenses.
        // Set: Exp1, Exp2, Exp4
        List<Transaction> settleUpTransactions = settleUpStrategy.settleUp(expenses.stream().toList());
        // output of settleUpTransactions:
        // N -> S: 1000
        // J -> Sm : 500
        // M -> Su : 2000
        // Y -> N : 500
        // J -> A : 3000

        // 4. pick only those settleUpTransactions where Navdeep is involved.
        List<Transaction> finalSettleUpTransactions = settleUpTransactions;
        for(Transaction transaction : settleUpTransactions){
            if(transaction.getPaidBy() == user || transaction.getReceivedBy() == user){
                finalSettleUpTransactions.add(transaction);
            }
        }
        // N -> S: 1000
        // Y -> N : 500
        return finalSettleUpTransactions;
    }

    public List<Transaction> settleUpGroup(Long groupId) throws GroupNotFoundException {
        // 1. get the group.
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if(optionalGroup.isEmpty()){
            throw new GroupNotFoundException("Group not found");
        }
        Group group = optionalGroup.get();

        // 2. get the list of expenses in group
        List<Expense> expenses = group.getExpenses();

        List<Transaction> transactions = settleUpStrategy.settleUp(expenses);
        return transactions;
    }
}


// Goa Group
// Exp1 : [
//          {S, 1000, HAD_PAID},
//          {N, 500, HAD_TO_PAY},   output
//          {J, 1000, HAD_PAY},
//          {N, 500, HAD_TO_PAY}    output
//          {Su, 500, HAD_TO_PAY}
//          {Y, 200, HAD_TO_PAY}
//          {Sm, 300, HAD_TO_PAY}
//          {S, 200, HAD_TO_PAY}
//          {J, 300, HAD_TO_PAY}
//        ]

// individual expense
// Exp2 : [
//          {S, 1000, HAD_PAID},
//          {N, 500, HAD_TO_PAY},   output
//          {S, 500, HAD_TO_PAY},
//        ]

// Exp3 : [
//          {J, 1000, HAD_PAID},
//          {A, 500, HAD_TO_PAY},
//          {J, 500, HAD_TO_PAY},
//        ]


// Trip Expense
// Exp4 : [
//          {S, 1000, HAD_PAID},
//          {M, 2000, HAD_PAID}
//          {N, 1000, HAD_TO_PAY},  output
//          {S, 1000, HAD_TO_PAY},
//          {M, 1000, HAD_TO_PAY}
//        ]
