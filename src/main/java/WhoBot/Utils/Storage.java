package WhoBot.Utils;

import WhoBot.Main.WhoBotException;
import WhoBot.Task.Deadline;
import WhoBot.Task.Event;
import WhoBot.Task.Task;
import WhoBot.Task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {

    public String filename;
    private final File taskFile;

    public Storage(String filename) throws WhoBotException {
        this.filename = filename;
        taskFile = new File(this.filename);
        if (!taskFile.exists()) {
            try {
                if (taskFile.getParentFile() != null && !taskFile.getParentFile().exists()) {
                    taskFile.getParentFile().mkdirs();
                }
                taskFile.createNewFile();
            } catch (IOException e) {
                throw new WhoBotException("Oops, The file to store my data could not be created. If you continue, tasks won't be stored permanently.");
            }
        }
    }

    public void readData(ArrayList<Task> LIST) throws WhoBotException {
        try {
            Scanner taskReader = new Scanner(taskFile);
            while(taskReader.hasNextLine()) {
                String[] data = taskReader.nextLine().split(" \\| ");
                if (data[0].equals("T")) {
                    Todo tempTodo = new Todo(data[2]);
                    if (data[1].equals("X")) {
                        tempTodo.markAsDone();
                    }
                    LIST.add(tempTodo);
                } else if (data[0].equals("D")) {
                    Deadline tempDeadline = new Deadline(data[2]);
                    if (data[1].equals("X")) {
                        tempDeadline.markAsDone();
                    }
                    LIST.add(tempDeadline);
                } else if (data[0].equals("E")) {
                    Event tempEvent = new Event(data[2]);
                    if (data[1].equals("X")) {
                        tempEvent.markAsDone();
                    }
                    LIST.add(tempEvent);
                }
            }
        } catch (FileNotFoundException e) {
            throw new WhoBotException("Oops, The file to store my data could not be created. If you continue, tasks won't be stored permanently.");
        }
    }

    // Method to Save List to WhoBot's Memory
    public void saveMemory(ArrayList<Task> LIST) throws WhoBotException {
        try {
            FileWriter dataWriter = new FileWriter(taskFile);
            for (Task tempTask : LIST) {
                String type = tempTask.getType();
                if (type.equals("T")) {
                    dataWriter.write(type + " | " + tempTask.getStatusIcon().charAt(1) + " | " + tempTask.getTask());
                } else if (type.equals("E")) {
                    Event tempEvent = (Event) tempTask;
                    dataWriter.write(type + " | " + tempTask.getStatusIcon().charAt(1) + " | " + tempTask.getTask()
                            + " /at " + tempEvent.getTiming().format(DateTimeFormatter.ofPattern("d/M/yyyy HH:mm")));
                } else if (type.equals("D")) {
                    Deadline tempDeadline = (Deadline) tempTask;
                    dataWriter.write(type + " | " + tempTask.getStatusIcon().charAt(1) + " | " + tempTask.getTask()
                            + " /by " +
                            (tempDeadline.hasTime()
                                    ? tempDeadline.getDeadline().format(DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"))
                                    : tempDeadline.getDeadline().format(DateTimeFormatter.ofPattern("d/M/yyyy"))));
                }
                dataWriter.write("\n");
            }
            dataWriter.close();
        } catch (IOException e) {
            throw new WhoBotException("Oops, The file to store my data could not be created. If you continue, tasks won't be stored permanently.");
        }
    }
}
