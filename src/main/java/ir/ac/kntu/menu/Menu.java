package ir.ac.kntu.menu;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.utility.ScannerWrapper;

public abstract class Menu {
    protected final ScannerWrapper scannerWrapper;

    protected final DB DB;

    protected String input;

    public Menu() {
        scannerWrapper = ScannerWrapper.getScannerWrapper();
        DB = DB.getDatabase();
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
