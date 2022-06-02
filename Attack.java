package battleship;

import java.util.Scanner;

public class Attack {

    private Scanner inputScan = new Scanner(System.in);
    private char shotL;
    private int shotN = 0;

    public Scanner getInputScan() {
        return inputScan;
    }

    public char getShotL() {
        return shotL;
    }

    public int getShotN() {
        return shotN;
    }

    public Attack() {
        attackShip();
    }


    private void attackShip() {
        setShotCoordinates(inputScan.nextLine());
    }

    public void setShotCoordinates(String inputCoordinates) {
        String startInput = inputCoordinates.substring(0, 1);
        shotL = startInput.charAt(0);

        int l = inputCoordinates.indexOf(startInput);
        String endInput = inputCoordinates.substring(l + 1);
        shotN = Integer.parseInt(endInput);

    }

    public void setShotCoordinates() {
        String startInput = inputScan.nextLine().substring(0, 1);
        shotL = startInput.charAt(0);

        int l = inputScan.nextLine().indexOf(startInput);
        String endInput = inputScan.nextLine().substring(l + 1);
        shotN = Integer.parseInt(endInput.substring(1));
    }

    public void showHitMessage() {
        System.out.printf("%nYou hit a ship!%n");
    }

    public void showMissMessage() {
        System.out.printf("%nYou missed!%n");
    }

}
