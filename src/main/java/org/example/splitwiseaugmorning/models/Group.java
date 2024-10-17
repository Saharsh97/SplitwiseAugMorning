package org.example.splitwiseaugmorning.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Group extends BaseModel {
    private String name;

    // G : A
    // 1 : 1
    // M : 1
    @ManyToOne
    private User admin;

    @ManyToMany(mappedBy = "groups")
    private List<User> users;

    // G : Exp
    // 1 : M
    // 1 : 1
    @OneToMany
    private List<Expense> expenses;

}
// Group for goa trip.
// exp1
// exp2
// exp3
// ....
// exp100
// N Sm, S, Y, Sn
