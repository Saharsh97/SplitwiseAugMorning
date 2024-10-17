package org.example.splitwiseaugmorning.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBalance {
    private User user;
    private Integer balance;

    public UserBalance(User user, Integer balance) {
        this.user = user;
        this.balance = balance;
    }
}
