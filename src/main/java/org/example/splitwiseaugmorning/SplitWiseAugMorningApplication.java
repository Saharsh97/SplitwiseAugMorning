package org.example.splitwiseaugmorning;

import org.example.splitwiseaugmorning.commands.CommandExecutor;
import org.example.splitwiseaugmorning.commands.RegisterCommand;
import org.example.splitwiseaugmorning.commands.SettleUpGroupCommand;
import org.example.splitwiseaugmorning.commands.SettleUpUserCommand;
import org.example.splitwiseaugmorning.controllers.SettleUpController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SplitWiseAugMorningApplication implements CommandLineRunner {

	@Autowired
	private CommandExecutor commandExecutor;

	@Autowired
	private SettleUpController settleUpController;

	@Override
	public void run(String... args) throws Exception {
		commandExecutor.addCommand(new RegisterCommand());
		commandExecutor.addCommand(new SettleUpUserCommand(settleUpController));
		commandExecutor.addCommand(new SettleUpGroupCommand(settleUpController));
		// add your commands as needed.

		// SettleUpGroup 	101
		Scanner scanner = new Scanner(System.in);
		while (true){
			String input = scanner.nextLine();
			commandExecutor.executeApplicableCommands(input);
		}
	}

	public static void runAppServer(String[] args) {
		SpringApplication.run(SplitWiseAugMorningApplication.class, args);
	}
}

// how do I get the input?

// Register Navdeep nav@scaler 7827569029
// Register Saubhagya s@scaler 99992020298
// Register ...

// CreateGroup goa
// AddUserToGroup Navdeep goa
// AddUserToGroup Saubhgya goa

// SettleUpGroup 	101				(groupId for goa)
// SettleUpUser 	2 				(userId)
