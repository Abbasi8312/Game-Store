package ir.ac.kntu.menu;

import ir.ac.kntu.database.Database;
import ir.ac.kntu.utility.ScannerWrapper;

public abstract class Menu {
    protected final ScannerWrapper scannerWrapper;

    protected final Database database;

    protected String input;

    public Menu() {
        scannerWrapper = ScannerWrapper.getScannerWrapper();
        database = Database.getDatabase();
    }

    protected void getInput() {
        input = scannerWrapper.nextLine();
    }

    protected boolean canContinue() {
        if (input.equals("**")) {
            System.out.println("Exiting...");
            System.exit(0);
            return false;
        } else {
            return !input.equals("*");
        }
    }
}
