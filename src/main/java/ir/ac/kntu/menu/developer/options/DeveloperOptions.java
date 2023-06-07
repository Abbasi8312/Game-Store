package ir.ac.kntu.menu.developer.options;

import ir.ac.kntu.database.DB;
import ir.ac.kntu.menu.admin.profile.ProfileMenu;
import ir.ac.kntu.menu.developer.DeveloperMenu;
import ir.ac.kntu.model.role.GameDeveloper;

public class DeveloperOptions extends DeveloperMenu {
    public DeveloperOptions(DB db, GameDeveloper gameDeveloper) {
        super(db, gameDeveloper);
    }

    public void developerOptions() {
        System.out.println("1. Profile");
        System.out.println("2. New game");
        System.out.println("3. Modify game");
        System.out.println("4. Scheduled events");
        System.out.println("5. Inbox");
        System.out.println("6. Feedbacks");
        getInput();
        clearScreen();
        while (canContinue()) {
            if (input.equals("1")) {
                new ProfileMenu(db).changeProfile(currentDeveloper.account);
            } else {
                System.out.println("Invalid input");
            }
            System.out.println("1. Profile");
            System.out.println("2. New game");
            System.out.println("3. Modify game");
            System.out.println("4. Scheduled events");
            System.out.println("5. Inbox");
            System.out.println("6. Feedbacks");
            getInput();
            clearScreen();
        }
    }
}
