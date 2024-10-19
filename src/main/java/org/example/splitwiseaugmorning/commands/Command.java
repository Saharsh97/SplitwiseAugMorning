package org.example.splitwiseaugmorning.commands;

public interface Command {
    boolean matches(String input);

    void execute(String input);
}
