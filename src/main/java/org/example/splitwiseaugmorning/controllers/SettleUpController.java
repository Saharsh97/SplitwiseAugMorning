package org.example.splitwiseaugmorning.controllers;

import org.example.splitwiseaugmorning.controllers.dtos.SettleUpGroupRequestDTO;
import org.example.splitwiseaugmorning.controllers.dtos.SettleUpGroupResponseDTO;
import org.example.splitwiseaugmorning.controllers.dtos.SettleUpUserRequestDTO;
import org.example.splitwiseaugmorning.controllers.dtos.SettleUpUserResponseDTO;
import org.example.splitwiseaugmorning.controllers.enums.ResponseStatus;
import org.example.splitwiseaugmorning.models.Transaction;
import org.example.splitwiseaugmorning.services.SettleUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SettleUpController {

    private SettleUpService settleUpService;

    @Autowired
    public SettleUpController(SettleUpService settleUpService) {
        this.settleUpService = settleUpService;
    }

    public SettleUpUserResponseDTO settleUpUser(SettleUpUserRequestDTO requestDTO){
        SettleUpUserResponseDTO responseDTO = new SettleUpUserResponseDTO();
        try{
            List<Transaction> transactionList = settleUpService.settleUpUser(requestDTO.getUserId());

            responseDTO.setStatus(ResponseStatus.SUCCESS);
            responseDTO.setMessage("generated transactions successfully");
            responseDTO.setSettleUpTransaction(transactionList);
        } catch (Exception e){
            responseDTO.setStatus(ResponseStatus.FAILURE);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setSettleUpTransaction(new ArrayList<>());
        }
        return responseDTO;
    }

    public SettleUpGroupResponseDTO settleUpGroup(SettleUpGroupRequestDTO requestDTO){
        SettleUpGroupResponseDTO responseDTO = new SettleUpGroupResponseDTO();
        try{
            List<Transaction> transactionList = settleUpService.settleUpGroup(requestDTO.getGroupId());

            responseDTO.setStatus(ResponseStatus.SUCCESS);
            responseDTO.setMessage("generated transactions successfully");
            responseDTO.setSettleUpTransaction(transactionList);
        } catch (Exception e){
            responseDTO.setStatus(ResponseStatus.FAILURE);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setSettleUpTransaction(new ArrayList<>());
        }
        return responseDTO;
    }
}
