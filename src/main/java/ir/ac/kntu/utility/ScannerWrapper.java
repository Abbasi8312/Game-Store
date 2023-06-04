package ir.ac.kntu.utility;

import java.util.Scanner;

public class ScannerWrapper {
    private static ScannerWrapper scannerWrapper = new ScannerWrapper();

    private final Scanner scanner;

    private ScannerWrapper() {
        scanner = new Scanner(System.in);
    }

    public static ScannerWrapper getScannerWrapper() {
        if (scannerWrapper == null) {
            scannerWrapper = new ScannerWrapper();
        }
        return scannerWrapper;
    }

    public String next() {
        return scanner.next();
    }

    public Double nextDouble() {
        return scanner.nextDouble();
    }

    public Integer nextInt() {
        return scanner.nextInt();
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}
