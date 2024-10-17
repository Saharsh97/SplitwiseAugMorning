package org.example.splitwiseaugmorning.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.splitwiseaugmorning.models.enums.ExpenseType;

import java.util.List;

@Getter
@Setter
@Entity
public class Expense extends BaseModel {
    private String description;
    private Integer bill;

    // Exp: expType
    // 1  :  1
    // M  :  1
    // ManyToOne
    // Expense table has the expenseTypeID
    @Enumerated(value = EnumType.ORDINAL)
    // Ordinal -> ID
    // String -> Name: Real Dummy
    private ExpenseType expenseType;

    // Exp : Group
    // 1 : 1
    // M : 1
    @ManyToOne
    private Group group;

    @OneToMany
    private List<UserExpense> userExpenses;
}

// separate DB calls. will take more time : 50ms 100ms per DB call.
// 1st DB call -> get the Expense Object.
// 2nd DB call -> get the Group Object.

// take a Join: 10 microseconds.
// 1st DB call -> get the Expense Object -> also contains Group Object.



// bill was 2000.
// Exp 1: whoPaid: S : 1000, J : 1000
//        whoHadToPay: N: 500 Su: 500  Y: 200 Sm: 300 S: 200 J:300

// Exp1 : [
//          {S, 1000, HAD_PAID},
//          {N, 500, HAD_TO_PAY},
//          {J, 1000, HAD_PAY},
//          {N, 500, HAD_TO_PAY}
//          {Su, 500, HAD_TO_PAY}
//          {Y, 200, HAD_TO_PAY}
//          {Sm, 300, HAD_TO_PAY}
//          {S, 200, HAD_TO_PAY}
//          {J, 300, HAD_TO_PAY}
//        ]