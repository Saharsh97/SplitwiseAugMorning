package org.example.splitwiseaugmorning.commands;

public class RegisterCommand implements Command{

    // Register Navdeep nav@scaler 7827569029
    // SettleUpGroup 	101
    @Override
    public boolean matches(String input) {
        String[] words = input.split(" ");
        return words[0].equals("register");
    }

    @Override
    public void execute(String input) {

    }
}
