package supertracker.command;

import supertracker.ui.Ui;

/**
 * Represents a command to quit the application.
 */
public class QuitCommand implements Command {
    /**
     * Executes the QuitCommand by displaying a farewell message.
     */
    @Override
    public void execute() {
        Ui.sayGoodbye();
    }

    /**
     * Indicates whether executing this command should result in quitting the application.
     *
     * @return Always returns true, as executing this command triggers application quit.
     */
    @Override
    public boolean isQuit() {
        return true;
    }
}
