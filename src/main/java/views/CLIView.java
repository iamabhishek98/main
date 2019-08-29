package views;

import controllers.ConsoleInputController;
import exceptions.DukeException;
import models.ITask;
import models.TaskList;

import java.util.Scanner;

public class CLIView {
    private final String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
    private final String horiLine = "\t____________________________________________________________";

    private ConsoleInputController consoleInputController;

    public CLIView() {
        this.consoleInputController = new ConsoleInputController(this);
    }

    /**
     * Method to call when View model is started.
     */
    public void start() {
        Scanner sc = new Scanner(System.in);
        consoleInputController.readData();

        System.out.println(horiLine);
        System.out.println("\tHello! I'm Duke");
        System.out.println("\tWhat can I do for you?");
        System.out.println(horiLine);

        while (true) {
            String command = sc.nextLine();
            consoleInputController.onCommandReceived(command);
        }
    }

    /**
     * Method to be called when user says bye to exit the program.
     */
    public void end() {
        System.out.println("\t Bye. Hope to see you again soon!");
        System.exit(0);
    }

    /**
     * Method to be called when user wishes to add a new Task.
     * @param newTask : A new task that is added by the user. Task is created by Factory.
     * @param taskList : List of tasks holding all the tasks.
     */
    public void addMessage(ITask newTask, TaskList taskList) {
        System.out.println(horiLine);
        System.out.println("\tGot it. I've added this task:");
        System.out.print("\t  ");
        System.out.println("[" + newTask.getInitials() + "]"
                + "[" + newTask.getStatusIcon() + "] "
                + newTask.getDescription()
        );
        String grammerTasks = taskList.getNumOfTasks() > 1 ? "tasks" : "task";
        System.out.println("\tNow you have " + taskList.getNumOfTasks() + " " + grammerTasks + " in your list.");
        System.out.println(horiLine);
    }

    /**
     * Method to be called when user calls "list".
     * Prints to View all the current tasks added.
     * @param taskList : List of tasks.
     */
    public void printAllTasks(TaskList taskList) {
        System.out.println(horiLine);
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < taskList.getAllTasks().size(); i++) {
            System.out.print("\t" + (i + 1));
            System.out.println(".[" + taskList.getAllTasks().get(i).getInitials() + "]"
                    + "[" + taskList.getAllTasks().get(i).getStatusIcon() + "] "
                    + taskList.getAllTasks().get(i).getDescription()
            );
        }
        System.out.println(horiLine);
    }

    /**
     * Method to be called when user prompts for a task to be marked as done.
     * @param taskList : list of tasks.
     * @param input : Will be parsed to find out which tasks does user want to be marked as done.
     */
    public void markDone(TaskList taskList, String input) {
        System.out.println(horiLine);
        String[] allInputs = input.split(" ");
        System.out.println("\tNice! I've marked this task as done:");
        System.out.print("\t  ");
        for (String i : allInputs) {
            if (!i.equals("done")) {
                int index = Integer.parseInt(i) - 1;
                ITask chosenToDos = taskList.getTask(index);
                chosenToDos.markAsDone();
                System.out.println("[" + chosenToDos.getStatusIcon() + "] " + chosenToDos.getDescription());
            }
        }
        System.out.println(horiLine);
    }

    /**
     * Method to be called when a Invalid Command is input by the user.
     * @param newException : Exception that is thrown when an Invalid Command is detected
     */
    public void invalidCommandMessage(DukeException newException) {
        System.out.println(horiLine);
        System.out.println("\t" + newException.getMessage());
        System.out.println(horiLine);
    }
}
