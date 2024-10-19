package org.example.splitwiseaugmorning.commands;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandExecutor {
    private List<Command> commands;

    public CommandExecutor(List<Command> commands) {
        this.commands = new ArrayList<>();
    }

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    public void removeCommand(Command command) {
        this.commands.remove(command);
    }

    // SettleUpGroup 	101
    public void executeApplicableCommands(String input){
        for(Command command : commands){
            // 1. RegisterCommand
            // 2. SettleUpUser
            // 3. SettleUpGroup
            if(command.matches(input)){
                command.execute(input);
                return;
            }
        }
        throw new RuntimeException("input doesnt match with any of the commands");
    }
}
