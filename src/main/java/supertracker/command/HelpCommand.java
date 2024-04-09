package supertracker.command;
import java.util.Scanner;
public class HelpCommand implements Command{
    public HelpCommand(){
    }

    @Override
    public void execute() {
        Ui.helpOpeningMessage();
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();

        
    }

    @Override
    public boolean isQuit() {
        return false;
    }
}
