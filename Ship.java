package battleship;

import java.util.Scanner;

public class Ship {

    private String shipName;
    private int cells;
    private char startL;
    private int startN = 0;
    private char endL;
    private int endN = 0;
    private Orientation position;
    private int remainCells = 0;

    public int getRemainCells() {
        return remainCells;
    }

    public void setRemainCells(int remainCells) {
        this.remainCells = remainCells;
    }

    private Scanner inputScan = new Scanner(System.in);

    public String getShipName() {
        return shipName;
    }

    public int getCells() {
        return cells;
    }

    public char getStartL() {
        return startL;
    }

    public int getStartN() {
        return startN;
    }

    public char getEndL() {
        return endL;
    }

    public int getEndN() {
        return endN;
    }

    public Orientation getPosition() {
        return position;
    }


    public Scanner getInputScan() {
        return inputScan;
    }

    public Ship(String shipName, int cells) {
        this.shipName = shipName;
        this.cells = cells;
        this.remainCells = cells;
    }

    public enum Orientation {
        HORIZONTAL, VERTICAL, FORWARD, BACKWARD, UP, DOWN, ERROR;

        static {
            HORIZONTAL.forward = FORWARD;
            HORIZONTAL.backward = BACKWARD;
            VERTICAL.down = DOWN;
            VERTICAL.up = UP;
            ERROR.errorPosition = ERROR;
        }

        public Orientation forward;
        public Orientation backward;
        public Orientation down;
        public Orientation up;
        public Orientation errorPosition;
    }

    public void setShipCoordinates() {
        System.out.printf("%nEnter the coordinates of the %s (%d cells):%n%n", shipName, cells);
        splitShipCoordinates(inputScan.nextLine());
    }

    public void setShipCoordinates(String inputShipCoordinates) {
        splitShipCoordinates(inputShipCoordinates);
    }

    public void splitShipCoordinates(String inputCoordinates) {
        int l = inputCoordinates.indexOf(' ');
        if (l >= 0) {
            String startInput = inputCoordinates.substring(0, l);
            startL = startInput.charAt(0);
            startN = Integer.parseInt(startInput.substring(1));

            String endInput = inputCoordinates.substring(l + 1);
            endL = endInput.charAt(0);
            endN = Integer.parseInt(endInput.substring(1));
        }
    }

    public void getShipOrientation() {

        int orientation = Character.compare(startL, endL);

        if (orientation == 0) { //same letters
            if (startN < endN) {
                position = Orientation.HORIZONTAL.forward;
            } else if (startN > endN) {
                position = Orientation.HORIZONTAL.backward;
            } else if (startN == endN) {
                position = Orientation.ERROR.errorPosition;
            }

        } else {
            //position = "VERTICAL";
            if (startN == endN) { //same numbers
                int valStartL = (int) startL;
                int valEndL = (int) endL;
                if (valStartL < valEndL) {
                    position = Orientation.VERTICAL.down;

                } else if (valStartL > valEndL) {
                    position = Orientation.VERTICAL.up;
                }
            } else { //different numbers
                position = Orientation.ERROR.errorPosition;
            }
        }
    }

}
