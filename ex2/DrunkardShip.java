import oop.ex2.SpaceShipPhysics;
import java.util.Random;

/**
 * Its pilot had a tad too much to drink.
 * the pilot is constantly moving, but in zig-zag
 * attempt teleport is randomize
 * attempt turn is randomize
 * attempt shield on is randomize
 * fire whenever annother ship is in it's range, but doesn't matter if other ship is facing it.
 */

public class DrunkardShip
        extends SpaceShip{

    // ------------------DATA MEMBERS -------------------//
    // constants
    private final int TELEPORT_THRESHOLD = 60;
    private final int TURN_THRESHOLD = 60;
    private final int SHIELD_THRESHOLD = 50;

    public DrunkardShip() {
        super();
    }


    // -----------------METHODS -------------------------//

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        //get the closest ship pyysics
        SpaceShipPhysics closestPhysics = findClosestShip(game);

        //1) if should teleport - then attempt teleporting
        if (shouldDrankardTeleport()){
            teleport();
        }
        // 2)move -
        //A) ship accelerate in every round
        boolean accelerate = true;
        //B) find angle -
        int turn = shouldDrankardTurn();
        // -->> move according to A)accel and B) turn:
        this.getPhysics().move(accelerate, turn);

        //3) Attempt to put a shield on
        if (shouldDrunkPutShieldOn()){
            shieldOn();
        }

        //4) attempt firing a shot
        if (shouldDrunkFire(closestPhysics)){
            fire(game);
        }

        //5) Regeneration of the 1 unit of energy of this round
        addEnergyEndOfRound(); //the current energy is raised by 1 point

        takeOffOneFireCoolDownCounter(); // if counter is on, update it (-1)
    }


    /**
     * check if ship should teleport.
     * if should - return true, false other wise
     * detemine by a generating a random number, and checking if it is abouve some threshold
     * @return if should - return true, false other wise
     */
    private boolean shouldDrankardTeleport(){

        // randomize a number from one to 100-
        int randomNumber = randomInt(100);

        // check if number is bigger then threshold == 60:
        return  (randomNumber <= TELEPORT_THRESHOLD);
    }


    /**
     * check where ship should turn.
     * detemine by a generating a random number and checking if it is abuve some threshold.
     * if abouve - turn right, if not - left
     * @return -1 for right, 1 for left
     */
    private int shouldDrankardTurn(){

        // randomize a number from one to 100-
        int randomNumber = randomInt(100);

        // check if number is bigger then threshold == 60:
        if (randomNumber >= TURN_THRESHOLD){
            return -1; // turn right
        }else{
            return 1; // turn left
        }
    }

    /**
     * check if ship should fire. if it should - return true, false otherwise
     * * detemine by a generating a random number, divided by 2 and checking if it is abuve some threshold.
     *  if abouve - return true, false otherwise
     * @return true - if should put shield on , false otherwise
     */
    private boolean shouldDrunkPutShieldOn(){

        // randomize a number from one to 100-
        int randomNumber = randomInt(100);

        // check if number is bigger then threshold == 60:
        return  ((randomNumber/2) > SHIELD_THRESHOLD);
    }

    /**
     * check if ship should fire. if it should - return true, false otherwise
     *  determine if the nearest ship is in distance smaller than 0.5 units
     * @param otherPhysics SpaceShipPhysics object
     * @return true - if should fire, false otherwise
     */
    private boolean shouldDrunkFire(SpaceShipPhysics otherPhysics){

        // checks if other ship is close enough
        double otherDistance = getDistanceFromOther(otherPhysics);
        return (IsShipCloseEnough(otherDistance, 0.3 ));  // if closest ship is close enough
    }

    /**
     * recives an int as max number and returns a random int from 0 (includes) to max.
     * @param max - int
     * @return random int
     */
    private int randomInt(int max){
        Random rand = new Random();
        return rand.nextInt(max);
    }
}