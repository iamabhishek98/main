package models.project;

import models.member.Member;
import models.member.MemberList;
import models.reminder.Reminder;
import models.task.Task;
import models.task.TaskList;

import java.util.ArrayList;
import java.util.HashMap;

public class NullProject implements IProject {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public MemberList getMembers() {
        return null;
    }

    @Override
    public TaskList getTasks() {
        return null;
    }

    @Override
    public int getNumOfMembers() {
        return 0;
    }

    @Override
    public int getNumOfTasks() {
        return 0;
    }

    @Override
    public void addMember(Member newMember) {
        /*
        Empty method
         */
    }

    @Override
    public String editMember(int memberIndexNumber, String updatedMemberDetails) {
        /*
        Empty method
         */
        return null;
    }

    @Override
    public void removeMember(Member toBeRemoved) {
        /*
        Empty method
         */
    }

    @Override
    public void addTask(Task newTask) {
        /*
        Empty method
         */
    }

    @Override
    public void removeTask(int taskIndexNumber) {
        /*
        Empty method
         */
    }

    @Override
    public boolean memberIndexExists(int indexNumber) {
        return false;
    }

    @Override
    public Task getTask(int taskIndex) {
        return null;
    }

    @Override
    public String[] editTask(int taskIndexNumber, String updatedTaskDetails) {
        /*
        Empty method
         */
        return new String[0];
    }

    @Override
    public String[] editTaskRequirements(int taskIndexNumber, String updatedTaskRequirements) {
        return null;
    }

    @Override
    public ArrayList<String> getCredits() {
        return null;
    }

    @Override
    public void createAssignment(Task task, Member member) {
        /*
        Empty method
         */
    }

    @Override
    public void removeAssignment(Member member, Task task) {
        /*
        Empty method
         */
    }

    @Override
    public boolean containsAssignment(Task task, Member member) {
        return false;
    }

    @Override
    public HashMap<String, ArrayList<String>> getMembersIndividualTaskList() {
        return null;
    }

    @Override
    public HashMap<String, ArrayList<String>> getTasksAndAssignedMembers() {
        return null;
    }

    @Override
    public void addReminderToList(Reminder reminder) {
        /*
        Empty method
         */
    }

    @Override
    public ArrayList<Reminder> getReminderList() {
        return null;
    }

    @Override
    public void markReminder(Boolean isDone, int index) {
        /*
        Empty method
         */
    }

    @Override
    public Reminder getReminder(int index) {
        return null;
    }

}
