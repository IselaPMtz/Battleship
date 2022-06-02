package battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    ArrayList<Player> aPlayers = new ArrayList<>();//Add Players
    private final int NUMPLAYERS = 2;

    public Game() {
    }

    public void playGame() {

        //add Players with battlefields
        for (int i = 1; i <= NUMPLAYERS; i++) {
            System.out.printf("Player %s, place your ships on the game field %n%n", i);
            Player player = new Player(String.valueOf(i));
            BattleField bField = new BattleField();
            bField.setShips();
            player.setBattleField(bField);
            aPlayers.add(player);
            System.out.printf("%n");
            showMovePlayerMessage();
        }
        //Attack
        while (aPlayers.get(0).getBattleField().getCountCellsShipHit() != BattleField.TOTAL_CELLS
                && aPlayers.get(1).getBattleField().getCountCellsShipHit() != BattleField.TOTAL_CELLS) {

            for (Player player : aPlayers) {
                player.getBattleField().showPlayerBattlefield();
                showPlayerTurnMessage(player.getNumPlayer());
                switch (player.getNumPlayer()) {
                    case "1":
                        //attack P2 battlefield
                        player.getBattleField().playerAttack(aPlayers.get(1).getBattleField());
                        break;
                    case "2":
                        //attack P1 battlefield
                        player.getBattleField().playerAttack(aPlayers.get(0).getBattleField());
                        break;
                }
                showMovePlayerMessage();
            }
        }
    }

    private void showMovePlayerMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player");
        String readString = scanner.nextLine();
    }

    private void showPlayerTurnMessage(String noPlayer) {
        System.out.printf("Player %s, it's your turn:", noPlayer);
    }


}
