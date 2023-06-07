package ir.ac.kntu.utility;

import java.util.Scanner;

public class ScannerWrapper {
    private static final Scanner INSTANCE = new Scanner(System.in);

    public static String nextLine() {
        return INSTANCE.nextLine();
    }

    public static String next() {
        return INSTANCE.next();
    }

    public static void close() {
        INSTANCE.close();
    }
}
