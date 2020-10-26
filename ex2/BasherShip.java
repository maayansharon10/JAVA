import oop.ex2.SpaceShipPhysics;

/**
 * This ship attempts to collide with other ships. It will always accelerate, and will
 * constantly turn towards the closest ship. If it gets within a distance of 0.19 units (inclusive)
 * from another ship, it will attempt to turn on its shields.
 */

public class BasherShip
        extends SpaceShip{
    public BasherShip() {
        super();
    }

    // -----------------METHODS -------------------------//

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {

        // ship accelerate in every round
        boolean accelerate = true;
        // move according to accel and turn:

        // Check who is the closest ship to self and get the closest ship physics
        SpaceShipPhysics closestPhysics = findClosestShip(game);

        //1)  chase after closest ship -
        // find angle -
        int turn = chaseAfter(closestPhysics);
        this.getPhysics().move(accelerate, turn);

        //2) attempt to put its shield on
        if (shouldBasherPutShieldOn(closestPhysics)){
            shieldOn();
        }
        //Regeneration of the 1 unit of energy of this round
        addEnergyEndOfRound(); //the current energy is raised by 1 point

        takeOffOneFireCoolDownCounter(); // if counter is on, update it (-1)
    }

    /**
     * find angle facing ship and return the direction ship need to turn to in
     * order to chase after other ship
     *
     * @param other of type SpaceShipPhysics
     * @return int -1 or 1
     */
    private int chaseAfter(SpaceShipPhysics other) {

        // find angle facing other ship -
        double angleFacingOther = this.getPhysics().angleTo(other);
        // turn to other direction
        return whereToTurnTo(angleFacingOther);
    }

    /**
     * check if ship should go right or left
     * runnerShip should do the SAME then the result.
     * ex -if ship need to turn right in order to face other - turn right (return -1)
     * or if ship need to turn left in order to face other - turn left (return 1)
     *
     * @param angleFacingOther - double
     * @return where to turn to int: 1 or -1
     */
    private int whereToTurnTo(double angleFacingOther) {

        // (if the closeset ship i
        if (angleFacingOther < 0) { //if ship need to turn right in order to face other
            return -1; // turn right
        } else { ////if ship need to turn left in order to face other
            return 1; //go left
        }
    }


    /**
     * check if ship should fire. if it should - return true, false otherwise
     *  If the nearest ship is indistance smaller than 0.19 units (includes),
     * it will attempt to fire
     * @param otherPhysics SpaceShipPhysics object
     * @return true - if should fire, false otherwise
     */
    private boolean shouldBasherPutShieldOn(SpaceShipPhysics otherPhysics){

        // checks if other ship is close enough
        double otherDistance = getDistanceFromOther(otherPhysics);
        // if closest ship is close enough
        return (IsShipCloseEnough(otherDistance, 0.19 ));
    }


    /**
     * check if the distance of this ship is smaller than threshold from other
     * override method from SpaceShip.
     * the difference - the distance is smaller then or equal to the threshold
     * @param otherShipDistance double
     * @param threshold double
     * @return true if it is close enough, false otherwise
     */
    protected boolean IsShipCloseEnough(double otherShipDistance, double threshold) {
        return (otherShipDistance <= threshold) ;  // if ship is closer than threshold
    }

}

