package org.example.splitwiseaugmorning.repositories;

import org.example.splitwiseaugmorning.models.Expense;
import org.example.splitwiseaugmorning.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByGroup(Group group);
}
