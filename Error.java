package battleship;

public class Error {

    private String shipName;

    public Error() {
    }

    public Error(String shipName) {
        this.shipName = shipName;
    }

    public void errorMessage(String typeError) {

        switch (typeError) {

            case "LENGTH":
                System.out.printf("%nError! Wrong length of the %s! Try again:%n", shipName);
                break;

            case "ERROR_POSITION":
                System.out.printf("%nError! Wrong ship location! Try again:%n");
                break;

            case "SHIP_NEAR_BY":
                System.out.printf("%nError! You placed it too close to another one. Try again:%n");
                break;

            case "ERROR_COORDINATES":
                System.out.printf("%nError! You entered the wrong coordinates! Try again:%n%n");
                break;
        }
    }


}
