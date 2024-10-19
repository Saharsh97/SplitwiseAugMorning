package org.example.splitwiseaugmorning.commands;

import org.example.splitwiseaugmorning.controllers.SettleUpController;
import org.example.splitwiseaugmorning.controllers.dtos.SettleUpGroupRequestDTO;
import org.example.splitwiseaugmorning.controllers.dtos.SettleUpGroupResponseDTO;
import org.example.splitwiseaugmorning.controllers.dtos.SettleUpUserRequestDTO;
import org.example.splitwiseaugmorning.controllers.dtos.SettleUpUserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettleUpUserCommand implements Command{

    private SettleUpController settleUpController;

    @Autowired
    public SettleUpUserCommand(SettleUpController settleUpController) {
        this.settleUpController = settleUpController;
    }

    // SettleUpGroup 	101
    @Override
    public boolean matches(String input) {
        String[] words = input.split(" ");
        return words[0].equals("settleUpUser");
    }

    @Override
    public void execute(String input) {
        String[] words = input.split(" ");
        SettleUpUserRequestDTO requestDTO = new SettleUpUserRequestDTO();
        requestDTO.setUserId(Long.parseLong(words[1]));

        SettleUpUserResponseDTO responseDTO = settleUpController.settleUpUser(requestDTO);

        System.out.println(responseDTO.getSettleUpTransaction());
        System.out.println(responseDTO.getStatus());
    }
}
