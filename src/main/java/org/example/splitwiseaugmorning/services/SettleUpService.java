package org.example.splitwiseaugmorning.services;

import org.example.splitwiseaugmorning.exceptions.GroupNotFoundException;
import org.example.splitwiseaugmorning.models.Expense;
import org.example.splitwiseaugmorning.models.Group;
import org.example.splitwiseaugmorning.models.Transaction;
import org.example.splitwiseaugmorning.repositories.GroupRepository;
import org.example.splitwiseaugmorning.strategies.SettleUpStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SettleUpService {

    private GroupRepository groupRepository;
    private SettleUpStrategy settleUpStrategy;

    @Autowired
    public SettleUpService(GroupRepository groupRepository, @Qualifier("HeapSettleUpStrategy") SettleUpStrategy settleUpStrategy) {
        this.groupRepository = groupRepository;
        this.settleUpStrategy = settleUpStrategy;
    }

    public List<Transaction> settleUpUser(Long userId){
        return new ArrayList<>();
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
