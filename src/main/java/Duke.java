import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Duke {

    // Colors used to Display Text
    public static final String COLOR_RESET = "\u001B[0m";
    public static final String COLOR_BLUE = "\u001B[34m";
    public static final String COLOR_CYAN = "\u001B[36m";
    public static final String COLOR_PURPLE = "\u001B[35m";
    public static final String COLOR_RED = "\u001B[31m";

    // The General Strings Used by the ChatBot
    private static final String logo = "\n" +
            "\t\t\t\t\t /$$      /$$ /$$                 /$$$$$$$              /$$\n" +
            "\t\t\t\t\t| $$  /$ | $$| $$                | $$__  $$            | $$\n" +
            "\t\t\t\t\t| $$ /$$$| $$| $$$$$$$   /$$$$$$ | $$  \\ $$  /$$$$$$  /$$$$$$\n" +
            "\t\t\t\t\t| $$/$$ $$ $$| $$__  $$ /$$__  $$| $$$$$$$  /$$__  $$|_  $$_/\n" +
            "\t\t\t\t\t| $$$$_  $$$$| $$  \\ $$| $$  \\ $$| $$__  $$| $$  \\ $$  | $$\n" +
            "\t\t\t\t\t| $$$/ \\  $$$| $$  | $$| $$  | $$| $$  \\ $$| $$  | $$  | $$ /$$\n" +
            "\t\t\t\t\t| $$/   \\  $$| $$  | $$|  $$$$$$/| $$$$$$$/|  $$$$$$/  |  $$$$/\n" +
            "\t\t\t\t\t|__/     \\__/|__/  |__/ \\______/ |_______/  \\______/    \\___/\n";

    private static final String line = "________________________________________________________________________________________________________________";

    private static final String bye = "\n" +
            "\t\t\t  /$$$$$$                            /$$ /$$$$$$$                            /$$\n" +
            "\t\t\t /$$__  $$                          | $$| $$__  $$                          | $$\n" +
            "\t\t\t| $$  \\__/  /$$$$$$   /$$$$$$   /$$$$$$$| $$  \\ $$ /$$   /$$  /$$$$$$       | $$\n" +
            "\t\t\t| $$ /$$$$ /$$__  $$ /$$__  $$ /$$__  $$| $$$$$$$ | $$  | $$ /$$__  $$      | $$\n" +
            "\t\t\t| $$|_  $$| $$  \\ $$| $$  \\ $$| $$  | $$| $$__  $$| $$  | $$| $$$$$$$$      |__/\n" +
            "\t\t\t| $$  \\ $$| $$  | $$| $$  | $$| $$  | $$| $$  \\ $$| $$  | $$| $$_____/\n" +
            "\t\t\t|  $$$$$$/|  $$$$$$/|  $$$$$$/|  $$$$$$$| $$$$$$$/|  $$$$$$$|  $$$$$$$       /$$\n" +
            "\t\t\t\\______/  \\______/  \\______/  \\_______/|_______/  \\____  $$ \\_______/      |__/\n" +
            "\t\t\t                                                   /$$  | $$\n" +
            "\t\t\t                                                  |  $$$$$$/\n" +
            "\t\t\t                                                   \\______/\n";

    // The Global Variables used by the ChatBot
    private static final Scanner cmdReader = new Scanner(System.in);
    public final UI ui;
    private Storage storage;
    private final Parser parser;
    private TaskList taskList;

    public Duke() {
        this.parser = new Parser();
        this.ui = new UI();
        try {
            this.storage = new Storage("." + File.separator + "data" + File.separator + "WhoBotData.txt");
            this.taskList = new TaskList(storage);
        } catch (DukeException ex) {
            ui.echo(ex.getMessage(), UI.TYPE.ERROR);
            System.exit(0);
        }
    }


    public void run() {
        ui.greeting();
        while (true) {
            try {
                String command;
                System.out.print(COLOR_PURPLE + "> " + COLOR_RESET);
                command = cmdReader.nextLine().trim();
                if (parser.parse(command, ui, storage, taskList) == -1) {
                    break;
                };
            } catch (DukeException ex) {
                ui.echo(ex.getMessage(), UI.TYPE.ERROR);
            }
        }
    }

    //Main Method
    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.run();
    }
}
