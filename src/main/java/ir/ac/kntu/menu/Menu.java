package ir.ac.kntu.menu;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.utility.ItemPrinter;
import ir.ac.kntu.utility.ListPagePrinting;
import ir.ac.kntu.utility.ScannerWrapper;

import java.util.List;

public abstract class Menu {
    protected final DB db;

    protected String input;

    public Menu(DB db) {
        this.db = db;
    }

    protected void getInput() {
        input = ScannerWrapper.nextLine();
    }

    protected boolean canContinue() {
        if (input.equals("**")) {
            exit();
        } else {
            return !input.equals("*");
        }
        return false;
    }

    public void exit() {
        clearScreen();
        System.out.println("Exiting...");
        System.exit(0);
    }

    public void clearScreen() {
        //System.out.println("\033[H\033[2J");
        System.out.println("Clear Console");
        System.out.flush();
    }

    public <T extends Enum<T>> T getOption(Class<T> tEnum) {
        String input = ScannerWrapper.nextLine();
        if (input.equals("**")) {
            exit();
        } else if (input.equals("*")) {
            return null;
        } else if (input.matches("^[0-9]+$")) {
            int choice = Integer.parseInt(input) - 1;
            T[] options = tEnum.getEnumConstants();
            if (choice >= 0 && choice < options.length) {
                return options[choice];
            }
        }
        return tEnum.getEnumConstants()[tEnum.getEnumConstants().length - 1];
    }

    public <T extends Enum<T>> T printMenuOptions(String title, Class<T> tEnum) {
        clearScreen();
        System.out.println("----------" + title + "----------");
        printEnumOptions(tEnum);
        System.out.print("Enter your choice : ");
        return getOption(tEnum);
    }

    public <T extends Enum<T>> void printEnumOptions(Class<T> tEnum) {
        T[] constants = tEnum.getEnumConstants();
        for (int i = 0; i < constants.length; i++) {
            String constant = constants[i].name();
            if (constant.equals("NONE")) {
                continue;
            }
            String capitalizedConstant = constant.substring(0, 1).toUpperCase() + constant.substring(1).toLowerCase();
            capitalizedConstant = capitalizedConstant.replace("_", " ");
            System.out.println((i + 1) + "." + capitalizedConstant);
        }
    }

    public <T> void printList(List<T> list, String objectsName) {
        printList(list, objectsName, (t, count) -> "No." + count + " " + t);
    }

    public <T> void printList(List<T> list, String objectsName, ItemPrinter<T> printer) {
        System.out.println("---" + list.size() + " " + objectsName + " found---");
        ListPagePrinting.printList(list, printer);
    }
}
