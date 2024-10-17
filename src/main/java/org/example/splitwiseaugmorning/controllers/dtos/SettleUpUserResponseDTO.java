package org.example.splitwiseaugmorning.controllers.dtos;

import lombok.Getter;
import lombok.Setter;
import org.example.splitwiseaugmorning.controllers.enums.ResponseStatus;
import org.example.splitwiseaugmorning.models.Transaction;

import java.util.List;

@Getter
@Setter
public class SettleUpUserResponseDTO {
    public ResponseStatus status;
    public String message;
    public List<Transaction> settleUpTransaction;
}
