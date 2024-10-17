package org.example.splitwiseaugmorning.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.example.splitwiseaugmorning.models.enums.UserExpenseType;

@Getter
@Setter
@Entity
public class UserExpense extends BaseModel {
    @ManyToOne
    private User user;

    // UExp : Exp
    // 1    : 1
    // M    : 1
    @ManyToOne
    private Expense expense;

    private Integer amount;

    @Enumerated
    private UserExpenseType userExpenseType;
}


// Exp1 : [
//          {S, 1000, 1},
//          {N, 500, 2},
//          {J, 1000, 1},
//          {N, 500, 2}
//          {Su, 500, 2}
//          {Y, 200, 2}
//          {Sm, 300, 2}
//          {S, 200, 2}
//          {J, 300, 2}
//        ]
