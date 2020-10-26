import oop.ex2.SpaceShipPhysics;

// TODO: 30/03/2019 Come up with a unique behavior which is interesting and/or successful and/or
//  makes the game more fun

/**
 * special ship is like a sniper:
 *
 * 1) if some other ship discover it, meaning it is in a relatively large distance and angle,
 * then special feels threatened and attempt teleport.
 *
 * 2)it only accelerate when some other ship is at it's range (a relatively large distance)
 * other wise, stay in place.
 * it only turn when some other ship is at a relatively narrow angle.
 * it attempt shieldOn only when someship is relatively close to it
 * 4)it fires shots only when the angle is relatively precise
 *
 */

public class SpecialShip
        extends SpaceShip{
    public SpecialShip() {
        super();
    }


    // ------------------DATA MEMBERS -------------------//

    // -----------------METHODS -------------------------//

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        //get the closest ship physics
        SpaceShipPhysics closestPhysics = findClosestShip(game);

        //1) if should teleport - then attempt teleporting
        if (shouldSpecialTeleport(closestPhysics)){
            teleport();
        }
        // 2)move -
        //A) ship accelerate in every round
        boolean accelerate = shouldSpecialAccelerate(closestPhysics);
        //B) check if ship should turn
        int turn ;
        if (shouldSpcecialTurn(closestPhysics)) {
            turn = runAwayTo(closestPhysics);// if should turn -
        }else {
            turn = 0;
        }// -->> move according to A)accelerate and B) turn:
        this.getPhysics().move(accelerate, turn);

        //3) Attempt to put a shield on
        if (shouldSpecialPutShieldOn(closestPhysics)){
            shieldOn();
        }

        //4) attempt firing a shot
        if (shouldSpecialfire(closestPhysics)){
            fire(game);
        }

        //5) Regeneration of the 1 unit of energy of this round
        addEnergyEndOfRound(); //the current energy is raised by 1 point

        takeOffOneFireCoolDownCounter(); // if counter is on, update it (-1)
    }


    /**
     * check if ship should teleport. if it should - return true, false otherwise
     *  If the nearest ship is closer than 0.5 units,
     * and if its angle to the Runner is less than 0.5 radians, the Runner feels threatened and will
     * attempt to teleport
     * @param otherPhysics SpaceShipPhysics
     * @return true if if closest ship is close enough and it's angle to runner is less then threshold, false otherwise
     */
    private boolean shouldSpecialTeleport(SpaceShipPhysics otherPhysics){

        // checks if other ship is close enough
        double otherDistance = getDistanceFromOther(otherPhysics);
        boolean isDistanceCloseEnough = IsShipCloseEnough(otherDistance, 0.5 );
        boolean isAngleClose = isAngleCloseEnough(otherPhysics, 0.5);
        return (isDistanceCloseEnough && isAngleClose);
    }


    /**
     * check if ship should accelerate. if it should - return true, false otherwise
     *  If the nearest ship is closer than 0.5 units,
     * the Special feels threatened and will acceletare
     * @param otherPhysics - SpaceShipPhysics  of closest ship
     * @return true if closest ship is close enough and it's angle to runner is less then threshold
     * false otherwise
     */
    private boolean shouldSpecialAccelerate(SpaceShipPhysics otherPhysics) {

        // checks if other ship is close enough
        double otherDistance = getDistanceFromOther(otherPhysics);
        return (IsShipCloseEnough(otherDistance, 0.5));
    }


    /**
     * check where ship should turn.
      * @param otherPhysics SpaceShipPhysics
     * @return -1 for right, 1 for left, 0 for do not turn at all
     */
    private boolean shouldSpcecialTurn(SpaceShipPhysics otherPhysics){
        return  isAngleCloseEnough(otherPhysics, 0.03);
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
        // return to which direction we should turn to
        return whereToTurnTo(angleFacingOther);
    }

    /**
     * check if ship should go right or left in order to avoid collision
     * @param angleFacingOther - double
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
     * check if ship should attempt shield on. if it should - return true, false otherwise
     *  If the nearest ship is in distance smaller than 0.04 units (includes),
     * it will attempt to shield on
     * @param otherPhysics SpaceShipPhysics object
     * @return true - if should fire, false otherwise
     */
    private boolean shouldSpecialPutShieldOn(SpaceShipPhysics otherPhysics){

        // checks if other ship is close enough
        double otherDistance = getDistanceFromOther(otherPhysics);
        return IsShipCloseEnough(otherDistance, 0.04 );
    }

    /**
     * check if ship should fire. if it should - return true, false otherwise
     *  If the nearest ship is closer than 0.5 units,
     * and if its angle to the Runner is less than 0.1 radians, the Runner feels threatened and will
     * attempt to teleport
     * @param otherPhysics SpaceShipPhysics
     * @return boolean, true is should teleport, false otherwise.
     */
    private boolean shouldSpecialfire(SpaceShipPhysics otherPhysics) {

        // checks if other ship is close enough
        double otherDistance = getDistanceFromOther(otherPhysics);
        // if closest ship is close enough and it's angle to runner is less then threshold - return true
        return (isAngleCloseEnough(otherPhysics, 0.15) && IsShipCloseEnough(otherDistance, 0.5));
    }

}


