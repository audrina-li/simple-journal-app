package ui;

import model.Event;
import model.Journal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Some of the code is cited from the TellerApp
public class JournalApp {
    private Scanner input = new Scanner(System.in).useDelimiter("\\n");
    private Map<Integer, Journal> journals = new HashMap<>();
    private Map<Integer, Event> events = new HashMap<>();
    private int journalNum = 1;
    private int eventNum = 1;

    // EFFECTS: runs the journal application
    public JournalApp() {
        runJournal();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runJournal() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = input.next();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            createJournal();
        } else if (command.equals("2")) {
            addEvent();
        } else if (command.equals("3")) {
            modifyEvent();
        } else if (command.equals("4")) {
            viewJournal();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> create a new journal");
        System.out.println("\t2 -> add a new event");
        System.out.println("\t3 -> modify an event");
        System.out.println("\t4 -> view all events in a journal");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: create a new journal
    private void createJournal() {
        System.out.println("\nEnter date (YYYY-MM-DD):");
        String date = input.next();
        int year = Integer.valueOf(date.substring(0,4));
        int month = Integer.valueOf(date.substring(5,7));
        int day = Integer.valueOf(date.substring(8));

        System.out.println("\nEnter title:");
        String title = input.next();

        journals.put(journalNum, new Journal(year,month,day,title));
        System.out.println("\nSuccessfully created a new journal.\nReference number:" + Integer.toString(journalNum));

        journalNum = journalNum + 1;
    }

    // MODIFIES: this
    // EFFECTS: add a new event
    private void addEvent() {
        System.out.println("\nSelect a journal by reference number:");
        Journal myJournal = journals.get(input.nextInt());

        System.out.println("\nEnter time of event (HH:MM):");
        String time = input.next();
        int hour = Integer.parseInt(time.substring(0,2));
        int minute = Integer.parseInt(time.substring(3));

        System.out.println("\nEnter description of event:");
        String description = input.next();

        Event e = new Event(hour,minute,description);

        System.out.println("\nMark it as the highlight of the day?\n (Y for yes, N for no)");
        String response = input.next();

        if (response.equals("Y")) {
            e.highlightEvent();
        }

        myJournal.addEvent(e);
        events.put(eventNum, e);
        System.out.println("\nSuccessfully added a new event.\nReference number:" + Integer.toString(eventNum));

        eventNum = eventNum + 1;
    }

    // MODIFIES: this
    // EFFECTS: modify an existing event
    private void modifyEvent() {
        System.out.println("\nSelect an event by reference number:");
        int key = input.nextInt();
        Event event = events.get(key);

        System.out.println("\nEnter adjusted time (HH:MM):");
        String time = input.next();
        int hour = Integer.valueOf(time.substring(0,2));
        int minute = Integer.valueOf(time.substring(3));

        System.out.println("\nEnter adjusted description:");
        String description = input.next();

        event.makeChanges(hour,minute,description);
        events.put(key, event);
        System.out.println("\nSuccessfully modified the event.");
    }

    // EFFECTS: view events in a journal
    private void viewJournal() {
        System.out.println("\nSelect a journal by entering its reference number:");
        Journal journal = journals.get(input.nextInt());
        ArrayList<Event> events = journal.displayEvents();

        if (events.isEmpty()) {
            System.out.println("This is an empty journal.");
        } else {
            for (Event e: events) {
                String min = Integer.toString(e.getMinute());
                if (min.length() == 1) {
                    System.out.println(e.getHour() + ":" + "0" + min + " " + e.getDescription());
                } else {
                    System.out.println(e.getHour() + ":" + e.getMinute() + " " + e.getDescription());
                }
            }
        }
    }
}