package org.example.splitwiseaugmorning.commands;

import org.example.splitwiseaugmorning.controllers.SettleUpController;
import org.example.splitwiseaugmorning.controllers.dtos.SettleUpGroupRequestDTO;
import org.example.splitwiseaugmorning.controllers.dtos.SettleUpGroupResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettleUpGroupCommand implements Command{


    private SettleUpController settleUpController;

    @Autowired
    public SettleUpGroupCommand(SettleUpController settleUpController) {
        this.settleUpController = settleUpController;
    }

    // SettleUpGroup 	101
    @Override
    public boolean matches(String input) {
        String[] words = input.split(" ");
        return words[0].equals("SettleUpGroup");
    }

    @Override
    public void execute(String input) {
        String[] words = input.split(" ");
        SettleUpGroupRequestDTO requestDTO = new SettleUpGroupRequestDTO();
        requestDTO.setGroupId(Long.parseLong(words[1]));

        SettleUpGroupResponseDTO responseDTO = settleUpController.settleUpGroup(requestDTO);

        System.out.println(responseDTO.getSettleUpTransaction());
        System.out.println(responseDTO.getStatus());
        System.out.println(responseDTO.getMessage());
    }
}
