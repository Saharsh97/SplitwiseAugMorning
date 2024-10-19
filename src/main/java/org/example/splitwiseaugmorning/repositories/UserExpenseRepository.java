package org.example.splitwiseaugmorning.repositories;

import org.example.splitwiseaugmorning.models.User;
import org.example.splitwiseaugmorning.models.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserExpenseRepository extends JpaRepository<UserExpense, Long> {
    List<UserExpense> findAllByUser(User user);
}
