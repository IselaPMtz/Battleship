package battleship;

public class Player {

    private String numPlayer;
    private BattleField battleField;

    public String getNumPlayer() {
        return numPlayer;
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public void setBattleField(BattleField battleField) {
        this.battleField = battleField;
    }

    public Player(String numPlayer) {
        this.numPlayer = numPlayer;
    }


}
