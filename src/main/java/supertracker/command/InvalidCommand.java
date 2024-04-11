package supertracker.command;

import supertracker.ui.Ui;

/**
 * Represents an invalid command that does not match any valid command format.
 */
public class InvalidCommand implements Command {
    /**
     * Executes the invalid command by printing a message indicating that the command is invalid.
     */
    @Override
    public void execute() {
        Ui.printInvalidCommand();
    }

    /**
     * Indicates whether executing this command should result in quitting the application.
     *
     * @return Always returns false, as executing this command does not trigger application quit.
     */
    @Override
    public boolean isQuit() {
        return false;
    }
}
