import oop.ex2.*;

/**
 * This spaceship attempts to run away from the fight. It will always accelerate, and
 * will constantly turn away from the closest ship. If the nearest ship is closer than 0.25 units,
 * and if its angle to the Runner is less than 0.23 radians, the Runner feels threatened and will
 * attempt to teleport.
 */

public class RunnerShip
        extends SpaceShip {
    public RunnerShip() {
        super();
    }


    // -----------------METHODS -------------------------//

    /**
     * Does the actions of this ship for this round.
     *      * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {

        //get the closest ship pyysics
        SpaceShipPhysics closestPhysics = findClosestShip(game);

        //1) if should teleport - then attempt teleporting
        if (shouldRunnerTeleport(closestPhysics)){
            teleport();
        }
        // 2)run away form other ship -
        //A) ship accelerate in every round
        boolean accelerate = true;
        //B) find angle -
        int turn = runAwayTo(closestPhysics);
        // move according to A)accel and B) turn:
        this.getPhysics().move(accelerate, turn);

        //Regeneration of the 1 unit of energy of this round
        addEnergyEndOfRound(); //the current energy is raised by 1 point

        takeOffOneFireCoolDownCounter(); // if counter is on, update it (-1)
    }

    /**
     * find angle facing ship and return the direction ship need to turn to in
     * order to run away from other
     * @param other of type SpaceShipPhysics
     * @return int -1 or 1
     */
    private int runAwayTo(SpaceShipPhysics other) {

        // find angle facing other ship -
        double angleFacingOther = findAngleToOther(other);
        // turn to otherdirection
        int turn = whereToTurnTo(angleFacingOther);
        return turn;
    }

    /**
     * check if ship should go right or left
     * runnerShip should do the OPPOSITE then the result.
     * ex -if ship need to turn right in order to face other - turn left (return 1)
     * or if ship need to turn left in order to face other - turn right (return -1)
     *
     * @param angleFacingOther double
     * @return where to turn to int: 1 or -1
     */
    private int whereToTurnTo(double angleFacingOther) {

        // (if the closeset ship i
        if (angleFacingOther < 0) { //if ship need to turn right in order to face other
            return 1; // turn left
        } else { ////if ship need to turn left in order to face other
            return -1; //go right
        }
    }

    /**
     * check if ship should teleport. if it should - return true, false otherwise
     *  If the nearest ship is closer than 0.25 units,
     * and if its angle to the Runner is less than 0.23 radians, the Runner feels threatened and will
     * attempt to teleport
     * @param otherPhysics SpaceShipPhysics object
     * @return true if should attempt teleporting, false otherwise
     */
    private boolean shouldRunnerTeleport(SpaceShipPhysics otherPhysics){

        // checks if other ship is close enough
        double otherDistance = getDistanceFromOther(otherPhysics);
        // if closest ship is close enough and it's angle to runner is less then threshold
        return (IsShipCloseEnough(otherDistance, 0.25 ) && isAngleCloseEnough(otherPhysics, 0.23));

    }

}




