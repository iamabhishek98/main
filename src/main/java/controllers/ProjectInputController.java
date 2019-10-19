package controllers;

import java.text.ParseException;
import java.util.Scanner;
import models.data.IProject;
import models.data.Project;
import models.member.IMember;
import models.member.Member;
import models.task.ITask;
import models.task.Task;
import repositories.ProjectRepository;
import util.factories.MemberFactory;
import util.factories.TaskFactory;
import views.CLIView;

public class ProjectInputController implements IController {
    private Scanner manageProjectInput;
    private ProjectRepository projectRepository;
    private CLIView consoleView;
    private MemberFactory memberFactory;

    /**
     * Constructor for ProjectInputController takes in a View model and a ProjectRepository.
     * ProjectInputController is responsible for handling user input when user chooses to manage a project.
     * @param consoleView The main UI of ArchDuke.
     * @param projectRepository The object holding all projects.
     */
    public ProjectInputController(CLIView consoleView, ProjectRepository projectRepository) {
        this.manageProjectInput = new Scanner(System.in);
        this.projectRepository = projectRepository;
        this.consoleView = consoleView;
        this.memberFactory = new MemberFactory();
    }

    /**
     * Allows the user to manage the project by branching into the project of their choice.
     * @param input User input containing project index number (to add to project class).
     */
    public void onCommandReceived(String input) {
        int projectNumber = Integer.parseInt(input);
        Project projectToManage = projectRepository.getItem(projectNumber);
        this.consoleView.consolePrint("Now managing: " + projectToManage.getDescription());
        boolean isManagingAProject = true;
        while (isManagingAProject) {
            if (manageProjectInput.hasNextLine()) {
                String projectCommand = manageProjectInput.nextLine();
                if (projectCommand.length() == 4 && ("exit").equals(projectCommand.substring(0, 4))) {
                    isManagingAProject = false;
                    consoleView.exitProject(projectToManage.getDescription());
                } else if (projectCommand.length() >= 11 && ("add member ").equals(projectCommand.substring(0, 11))) {
                    String memberDetails = projectCommand.substring(11);
                    int numberOfCurrentMembers = projectToManage.getNumOfMembers();
                    memberDetails = memberDetails + " x/" + numberOfCurrentMembers;
                    IMember newMember = memberFactory.create(memberDetails);
                    if (newMember.getName() != null) {
                        projectToManage.addMember((Member) newMember);
                        consoleView.addMember(projectToManage, newMember.getDetails());
                    } else {
                        consoleView.consolePrint("Failed to add member. Please ensure you have entered "
                                + "at least the name of the new member.");
                    }
                } else if (projectCommand.length() >= 12 && ("edit member ").equals(projectCommand.substring(0, 12))) {
                    int memberIndexNumber = Integer.parseInt(projectCommand.substring(12).split(" ")[0]);
                    if (projectToManage.getNumOfMembers() >= memberIndexNumber) {
                        String updatedMemberDetails = projectCommand.substring(projectCommand.indexOf("n/"));
                        consoleView.editMember(projectToManage, memberIndexNumber, updatedMemberDetails);
                    } else {
                        consoleView.consolePrint("The member index entered is invalid.");
                    }
                } else if (projectCommand.length() >= 14 && ("delete member ").equals(projectCommand.substring(0,14))) {
                    int memberIndexNumber = Integer.parseInt(projectCommand.substring(14).split(" ")[0]);
                    if (projectToManage.getNumOfMembers() >= memberIndexNumber) {
                        Member memberToRemove = projectToManage.getMembers().getMember(memberIndexNumber);
                        projectToManage.removeMember(memberToRemove);
                        consoleView.consolePrint("Removed member with the index number " + memberIndexNumber);
                    } else {
                        consoleView.consolePrint("The member index entered is invalid.");
                    }
                } else if (projectCommand.length() == 12 && ("view members").equals(projectCommand)) {
                    consoleView.viewAllMembers(projectToManage);
                } else if (projectCommand.length() == 12 && ("view credits").equals(projectCommand)) {
                    // TODO view all credits.
                    consoleView.consolePrint("Not implemented yet");
                } else if (projectCommand.length() >= 9 && ("add task ").equals(projectCommand.substring(0, 9))) {
                    try {
                        TaskFactory taskFactory = new TaskFactory();
                        ITask newTask = taskFactory.createTask(projectCommand.substring(9));
                        if (newTask.getDetails() != null) {
                            consoleView.addTask(projectToManage, (Task) newTask);
                        } else {
                            consoleView.consolePrint("Failed to create new task. Please ensure all"
                                    + "necessary parameters are given");
                        }
                    } catch (NumberFormatException | ParseException e) {
                        consoleView.consolePrint("Please enter your task format correctly");
                    }
                } else if (projectCommand.length() >= 10 && ("view tasks").equals(projectCommand.substring(0,10))) {
                    if (("view tasks").equals(projectCommand)) {
                        consoleView.viewAllTasks(projectToManage);
                    } else if (projectCommand.length() >= 11) {
                        String sortCriteria = projectCommand.substring(11);
                        consoleView.viewSortedTasks(projectToManage, sortCriteria);
                    }
                } else if (projectCommand.length() == 19 && ("view assigned tasks").equals(projectCommand)) {
                    consoleView.consolePrint(projectToManage.getAssignedTaskList().toArray(new String[0]));
                } else if (projectCommand.length() > 25
                        && ("view task requirements i/").equals(projectCommand.substring(0, 25))) {
                    int taskIndex = Integer.parseInt(projectCommand.substring(25));
                    if (projectToManage.getNumOfTasks() >= taskIndex && taskIndex > 0) {
                        if (projectToManage.getTask(taskIndex).getNumOfTaskRequirements() == 0) {
                            consoleView.consolePrint("This task has no specific requirements.");
                        } else {
                            consoleView.viewTaskRequirements(projectToManage, taskIndex);
                        }
                    } else {
                        consoleView.consolePrint("The task index entered is invalid.");
                    }
                } else if (projectCommand.length() > 23
                        && ("edit task requirements ").equals(projectCommand.substring(0, 23))) {
                    String[] updatedTaskRequirements = projectCommand.split(" [ir]m?/");
                    int taskIndexNumber = Integer.parseInt(updatedTaskRequirements[1]);
                    boolean haveRemove = false;
                    if (projectCommand.contains(" rm/")) {
                        haveRemove = true;
                    }
                    if (projectToManage.getNumOfTasks() >= taskIndexNumber && taskIndexNumber > 0) {
                        consoleView.editTaskRequirements(projectToManage, taskIndexNumber, updatedTaskRequirements,
                                haveRemove);
                    } else {
                        consoleView.consolePrint("The task index entered is invalid.");
                    }
                } else if (projectCommand.length() > 10 && ("edit task ").equals(projectCommand.substring(0, 10))) {
                    String [] updatedTaskDetails = projectCommand.split(" [itpdcs]\\/");
                    int taskIndexNumber = Integer.parseInt(updatedTaskDetails[1]);
                    if (projectToManage.getNumOfTasks() >= taskIndexNumber && taskIndexNumber > 0) {
                        consoleView.editTask(projectToManage, projectCommand, taskIndexNumber);
                    } else {
                        consoleView.consolePrint("The task index entered is invalid.");
                    }
                } else if (projectCommand.length() >= 12 && ("delete task ").equals(projectCommand.substring(0,12))) {
                    int taskIndexNumber = Integer.parseInt(projectCommand.substring(12).split(" ")[0]);
                    if (projectToManage.getNumOfTasks() >= taskIndexNumber) {
                        consoleView.removeTask(projectToManage, taskIndexNumber);
                    } else {
                        consoleView.consolePrint("The task index entered is invalid.");
                    }
                } else if (projectCommand.length() >= 12 && ("assign task ").equals(projectCommand.substring(0,12))) {
                    AssignmentController assignmentController = new AssignmentController(projectToManage);
                    assignmentController.parseAssignmentInput(projectCommand.substring(12));
//                    if (!assignmentController.getErrorMessages().isEmpty()) {
//                        consoleView.consolePrint(assignmentController.getErrorMessages().toArray(new String[0]));
//                    }
//                    if (!assignmentController.getValidTaskIndexes().isEmpty()) {
//                        if (!assignmentController.getValidMembersToAssign().isEmpty()) {
//                            consoleView.assignTasks(projectToManage, assignmentController.getValidTaskIndexes(),
//                                assignmentController.getValidMembersToAssign());
//                        }
//                        if (!assignmentController.getValidMembersToUnassign().isEmpty()) {
//                            consoleView.unassignTasks(projectToManage, assignmentController.getValidTaskIndexes(),
//                                assignmentController.getValidMembersToUnassign());
//                        }
//                    } else {
//                        consoleView.consolePrint("No valid task indexes found. Try again!");
//                    }
                } else if ("bye".equals(projectCommand)) {
                    consoleView.end();
                } else {
                    consoleView.consolePrint("Invalid command. Try again!");
                }
            } else {
                consoleView.consolePrint("Please enter a command.");
            }
        }
    }

    private void manageAssignment(IProject projectToManage, String details) {

    }
}
