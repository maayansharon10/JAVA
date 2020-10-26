import oop.ex2.*;

/**
 * This class has a single static method (createSpaceships(String[])),
 * which is currently empty. It is used by the supplied driver to create all the spaceship objects
 * according to the command line arguments
 */
public class SpaceShipFactory {

    /**
     * return a SpaceShip array according to arguments from command line
     * @param args array of strings
     * @return an array of spaceship with all the spaceships from command line
     */
    public static SpaceShip[] createSpaceShips(String[] args) {

        SpaceShip[] spaceShipArray;
        // create a list of spaceships we would like to return
        spaceShipArray = new SpaceShip[args.length];
        // go through all args, and place a new ship, according to the string
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("h")) {
                spaceShipArray[i] = new HumanShip();
            } else if (args[i].equals("r")) {
                spaceShipArray[i] = new RunnerShip();
            } else if (args[i].equals("b")) {
                spaceShipArray[i] = new BasherShip();
            } else if (args[i].equals("a")) {
                spaceShipArray[i] = new AggressiveShip();
            } else if (args[i].equals("d")) {
                spaceShipArray[i] = new DrunkardShip();
            } else if (args[i].equals("s")) {
                spaceShipArray[i] = new SpecialShip();
            }
        }
        return spaceShipArray;
    }
}

