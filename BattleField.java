package battleship;

import java.util.ArrayList;

public class BattleField {
    //create battlefield & ships
    private String[][] battleField;
    private String[][] enemyBattleFieldFog;//Fog of war battleField
    private Ship[] battleships = {new Ship("Aircraft Carrier", 5),
            new Ship("Battleship", 4), new Ship("Submarine", 3),
            new Ship("Cruiser", 3), new Ship("Destroyer", 2)};

    public static final int TOTAL_CELLS = 17;
    private int countCellsShipHit = 0;

    public int getCountCellsShipHit() {
        return countCellsShipHit;
    }

    public BattleField() {
        enemyBattleFieldFog = createBattleField();
        battleField = createBattleField();
    }

    public void setShips() {
        printBattleField(battleField);

        for (Ship ship : battleships) {

            ship.setShipCoordinates();
            ship.getShipOrientation();

            while (!isCorrectShipSizePosition(ship)) {
                ship.setShipCoordinates(ship.getInputScan().nextLine());
                ship.getShipOrientation();
            }

            insertBattleShip(ship);
            System.out.printf("%n");
            printBattleField(battleField);
        }

    }

    private void endGame() {
        System.out.printf("%nYou sank the last ship. You won. Congratulations!");
    }

    public void showPlayerBattlefield() {
        printBattleField(enemyBattleFieldFog);
        System.out.println("---------------------");
        printBattleField(battleField);
    }

    public void playerAttack(BattleField battleFieldPlayer) {
        Attack attack = new Attack();
        shipAttack(attack, battleFieldPlayer);
    }

    private void shipAttack(Attack fireAttack, BattleField playerAttacked) {

        int row = 1;

        if (fireAttack.getShotL() <= 74 && fireAttack.getShotN() <= 10) {

            for (int i = 1; i < playerAttacked.battleField.length; i++) {
                //get row , column 0 are letters
                if (playerAttacked.battleField[i][0].equals(String.valueOf(fireAttack.getShotL()))) {
                    row = i;
                }
            }

            switch (playerAttacked.battleField[row][fireAttack.getShotN()]) {
                case " O":
                    playerAttacked.battleField[row][fireAttack.getShotN()] = " X";
                    enemyBattleFieldFog[row][fireAttack.getShotN()] = " X";
                    playerAttacked.countCellsShipHit++;

                    for (int i = 0; i < playerAttacked.battleships.length; i++) {

                        //get ship range and compare fireAttack
                        if (fireAttack.getShotL() >= playerAttacked.battleships[i].getStartL()
                                && fireAttack.getShotL() <= playerAttacked.battleships[i].getEndL()) {

                            if (fireAttack.getShotN() >= playerAttacked.battleships[i].getStartN()
                                    && fireAttack.getShotN() <= playerAttacked.battleships[i].getEndN()) {
                                playerAttacked.battleships[i].setRemainCells(playerAttacked.battleships[i].getRemainCells() - 1);

                                if (playerAttacked.battleships[i].getRemainCells() == 0) {
                                    System.out.println("You sank a ship!");
                                    return;
                                }
                            }
                        }
                    }

                    if (playerAttacked.countCellsShipHit == TOTAL_CELLS) {
                        endGame();
                        return;
                    }

                    fireAttack.showHitMessage();
                    break;

                case " ~":
                    playerAttacked.battleField[row][fireAttack.getShotN()] = " M";
                    enemyBattleFieldFog[row][fireAttack.getShotN()] = " M";
                    fireAttack.showMissMessage();
                    break;

                case " X":
                    printBattleField(enemyBattleFieldFog);
                    fireAttack.showHitMessage();
                    break;

                case " M":
                    printBattleField(enemyBattleFieldFog);
                    fireAttack.showMissMessage();
                    break;
            }
        } else {
            setError("ERROR_COORDINATES");
        }

    }

    //check if cells size is correct for the correct battleship
    //check if grid coordinates are empty before inserting a ship in it
    //check if there is already ship in the inputCoordinates
    //check if nearby ship is already there
    //check if ship overlap with another ship in field

    public boolean isCorrectShipSizePosition(Ship ship) {

        int count = 0;

        if (ship.getPosition().equals(Ship.Orientation.HORIZONTAL.forward)) {
            count = (ship.getEndN() - ship.getStartN()) + 1;
        }

        if (ship.getPosition().equals(Ship.Orientation.HORIZONTAL.backward)) {
            count = (ship.getStartN() - ship.getEndN()) + 1;
        }

        if (ship.getPosition().equals(Ship.Orientation.VERTICAL.down)) {
            count = (ship.getEndL() - ship.getStartL()) + 1;
        }

        if (ship.getPosition().equals(Ship.Orientation.VERTICAL.up)) {
            count = (ship.getStartL() - ship.getEndL()) + 1;
        }

        //compare ship size with cells
        if (ship.getCells() != count && count != 0) {
            Error error = new Error(ship.getShipName());
            error.errorMessage("LENGTH");
            return false;
        }

        if (ship.getPosition().equals(Ship.Orientation.ERROR.errorPosition)) {
            Error error = new Error();
            error.errorMessage("ERROR_POSITION");
            return false;
        }

        boolean isVal = true;
        //compare position,overlapping,nearby ship
        for (int i = 1; i < battleField.length; i++) {

            if (battleField[i][0].equals(String.valueOf(ship.getStartL()))) { //letters
                if (ship.getPosition().equals(Ship.Orientation.HORIZONTAL.forward)) {

                    for (int n = ship.getStartN(); n <= ship.getEndN(); n++) {
                        if (isCheckForErrors(i, n)) { //true if found error;
                            isVal = false;
                            break;
                        }
                    }
                    return isVal;
                }

                if (ship.getPosition().equals(Ship.Orientation.HORIZONTAL.backward)) {
                    for (int n = ship.getStartN(); n >= ship.getEndN(); n--) {
                        if (isCheckForErrors(i, n)) { //true if found error;
                            isVal = false;
                            break;
                        }
                    }
                    return isVal;
                }

                if (ship.getPosition().equals(Ship.Orientation.VERTICAL.down)) {
                    int n = ship.getStartN();
                    for (int j = ship.getStartL(); j <= (int) ship.getEndL(); j++) {
                        if (isCheckForErrors(i, n)) { //true if found error;
                            isVal = false;
                            break;
                        }
                        i++;
                    }
                    return isVal;
                }

                if (ship.getPosition().equals(Ship.Orientation.VERTICAL.up)) {
                    int n = ship.getStartN();
                    for (int j = ship.getStartL(); j >= (int) ship.getEndL(); j--) {
                        if (isCheckForErrors(i, n)) { //true if found error;
                            isVal = false;
                            break;
                        }
                        i--;
                    }
                    return isVal;
                }
            }
        }
        return true; //all good
    }

    private boolean isCheckForErrors(Integer i, Integer n) {

        if (battleField[i][n].equals(" O")) {
            return setError("ERROR_POSITION");
        }

        if (battleField[i][n].equals(" ~")) {
            if (i != 1 && i != 10 && n != 1 && n != 10) { //first and last row-column
                //search in 4 directions
                if (battleField[i - 1][n].equals(" O") ||
                        battleField[i + 1][n].equals(" O") ||
                        battleField[i][n - 1].equals(" O") ||
                        battleField[i][n + 1].equals(" O")) {
                    return setError("SHIP_NEAR_BY");
                }//if
            }

            if (i != 1 && i != 10 && n == 1) { //non-first and last row - first column
                //search in 4 directions
                if (battleField[i - 1][n].equals(" O") ||
                        battleField[i + 1][n].equals(" O") ||
                        battleField[i][n + 1].equals(" O")) {
                    return setError("SHIP_NEAR_BY");
                }//if
            }

            if (i != 1 && i != 10 && n == 10) { //non-first and last row - first column
                //search in 4 directions
                if (battleField[i - 1][n].equals(" O") ||
                        battleField[i + 1][n].equals(" O") ||
                        battleField[i][n - 1].equals(" O")) {
                    return setError("SHIP_NEAR_BY");
                }//if
            }

            if (i == 1 && n == 1) { // first row -first column
                if (battleField[i + 1][n].equals(" O") ||
                        battleField[i][n + 1].equals(" O")) {
                    return setError("SHIP_NEAR_BY");
                }//if
            }

            if (i == 1 && n == 10) { //first row - last column
                if (battleField[i - 1][n].equals(" O") ||
                        battleField[i][n + 1].equals(" O")) {
                    return setError("SHIP_NEAR_BY");
                }//if
            }

            if (i == 10 && n == 1) { //last row - first column
                if (battleField[i - 1][n].equals(" O") ||
                        battleField[i][n + 1].equals(" O")) {
                    return setError("SHIP_NEAR_BY");
                }//if
            }

            if (i == 10 && n == 10) { //last row - first column
                if (battleField[i - 1][n].equals(" O") ||
                        battleField[i][n - 1].equals(" O")) {
                    return setError("SHIP_NEAR_BY");
                }//if
            }
        }
        return false;
    }

    private boolean setError(String typeError) {
        Error error = new Error();
        error.errorMessage(typeError);
        return true;
    }

    private String[][] createBattleField() {
        String[][] battleFields = new String[11][11];
        battleFields[0][0] = " ";

        for (int row = 1; row < battleFields.length; row++) {
            battleFields[row][0] = String.valueOf((char) (64 + row));
            for (int col = 1; col < battleFields.length; col++) {
                battleFields[0][col] = " " + col;
                battleFields[row][col] = " ~";
            }
        }
        return battleFields;
    }//method

    public void insertBattleShip(Ship ship) {
        for (int i = 0; i < battleField.length; i++) {
            //column 0 are letters
            if (battleField[i][0].equals(String.valueOf(ship.getStartL()))) {
                //HORIZONTAL - same letters
                if (ship.getPosition().equals(Ship.Orientation.HORIZONTAL.forward)) {
                    for (int n = ship.getStartN(); n <= ship.getEndN(); n++) {
                        battleField[i][n] = " O";
                    }
                    break;
                } else if (ship.getPosition().equals(Ship.Orientation.HORIZONTAL.backward)) {
                    for (int n = ship.getEndN(); n <= ship.getStartN(); n++) {
                        battleField[i][n] = " O";
                    }
                    break;
                } else {
                    //VERTICAL - different letters same numbers
                    for (int a = 0; a < battleField.length - 1; a++) {
                        battleField[i][ship.getStartN()] = " O";
                        if (battleField[i][0].equals(String.valueOf(ship.getEndL()))) {
                            break;
                        }
                        if (ship.getPosition().equals(Ship.Orientation.VERTICAL.down)) {
                            i++;
                        } else if (ship.getPosition().equals(Ship.Orientation.VERTICAL.up)) {
                            i--;
                        }
                    }//for
                    break;
                }// else
            }//if
        }//for

    }//method

    private void printBattleField(String[][] battleFields) {

        for (String[] strings : battleFields) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.printf("%n");
        }
    }


}

