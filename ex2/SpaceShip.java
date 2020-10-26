import java.awt.Image;
import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game. 
 * It is your decision whether SpaceShip.java will be an interface, an abstract class,
 *  a base class for the other spaceships or any other option you will choose.
 *  
 * @author oop
 */
public abstract class SpaceShip {
    // ------------------DATA MEMBERS -------------------//

    /**
     * object (from the helper package),
     * that represents the position, direction and velocity of the ship
     */
    private SpaceShipPhysics ssPhysics;

    /**
     * max amount of energy ship could have during the game
     */
    private int maxEnergy;

    /**
     * A current energy level, which is between 0 and the maximal energy level.
     */
    private int currentEnergy;

    /**
     * health level - between 0 and 22
     */
    private int currentHealthLevel;

    /**
     * boolean true if Shield is on, false other wise
     */
    private boolean isShieldOn;


    /**
     * fire Cool Down Counter - int, as long and it is positive - ship cannot fire.
     * once it is non negative - ship can shoot
     */
    private int fireCoolDownCounter;

    //constants :
    private final int TELEPORT_COST = 140;
    private final int FIRING_A_SHOT_COST = 19;
    private final int SHIELD_COST = 3;
    private final int INITIAL_CURRENT_HEALTH_LEVEL = 22;
    private final int INITIAL_MAX_ENERGY = 210;
    private final int INITIAL_CURRENT_ENERGY = 190;

    // ---------------- CONSTRUCTOR --------------------//

    /**
     * Constructor initialling a spaceship object
     */
    public SpaceShip() {
        // set all relevant variables to defult mode
        setAllToDefalut();
    }

    // -----------------METHODS -------------------------//



    //~~~~~~~~~~~~~~abstract methods ~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     *
     * @param game the game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game);


    //~~~~~~~~~~~~~~health related methods ~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * return: healthLevel (int)
     * @return int
     */
    public int getHealth() {
        return this.currentHealthLevel;
    }


    /**
     * set current health with the param
     * @param points - int
     */
    private void setCurrentHealth(int points){
        this.currentHealthLevel = points;
    }
    /**
     * remove one point from health level
     */
    private void removeOneHealthLevel() {
        this.currentHealthLevel -= 1;
    }


    //~~~~~~~~~~~~~~energy/points related methods ~~~~~~~~~~~~~~~~~~~~~~~~~

    // max energy -
    /**
     * set max energy with a new int
     * @param points -  int
     */
    private void setMaxEnergy(int points){
        this.maxEnergy = points;
    }

    /**
     * return an int max energy
     * @return an int represent max energy
     */
    private int getMaxEnergy(){
        return this.maxEnergy;
    }

    /**
     * assuming there are enough points to remove, remove some points form max energy
     *
     * @param pointsAmount int
     */
    private void removeMaxEnergyPoints(int pointsAmount) {
        this.maxEnergy -= pointsAmount;
    }


    /**
     * add points to max energy data member
     * @param points int
     */
    private void addToMaxEnergy(int points) {
        this.currentEnergy += points; // at the end of each round, the current energy is raised by 1 point
    }

    // current energy -
    /**
     * return an int - current energy
     * @return an int represent current energy
     */
    private int getCurrentEnergy(){
        return this.currentEnergy;
    }

    /**
     * set current energy with a new int
     * @param points -  int
     */
    private void setCurrentEnergy(int points){
        this.currentEnergy = points;
    }

    /**
     * add one point of energy. Is used at the end of each round
     */
    protected void addEnergyEndOfRound() {
        this.currentEnergy += 1; // at the end of each round, the current energy is raised by 1 point
    }


    /**
     * add points to current energy data member
     * @param points int
     */
    private void addToCurrentEnergy(int points) {
        this.currentEnergy += points; // at the end of each round, the current energy is raised by 1 point
    }

    /**
     * assuming there are enough points to remove, remove some points form current energy
     * @param pointsAmount int
     */
    private void removeSomeEnergyPoints(int pointsAmount) {
        this.currentEnergy -= pointsAmount;
    }

    /**
     * assuming the Shields are down.
     * Getting hit (either getting shot at or colliding) while the shields are down reduces the
     * maximal energy level by 10, but does not reduce the current energy level. If, however, the
     * current energy level is above the new maximum, it changes to the new maximum.
     */
    private void gettingHitEnergyActions() {
        removeMaxEnergyPoints(10);
        if (getCurrentEnergy() > getMaxEnergy()) { // if the cur energy is now higher than the max energy
            setCurrentEnergy(getMaxEnergy());// change the cur energy to the new max
        }
    }

    /**
     * checks if ship has enough point to preform a certain action
     * @param actionPointsNeeded int
     * @return true if ship has enough point to preform the action, false otherwise
     */
    private boolean areThereEnoughPointsForAction(int actionPointsNeeded) {
        // if after deduction current energy is non-negative -
        // ship has enough energy points to preform action - return true, else - return false
        return(this.currentEnergy - actionPointsNeeded >= 0);

    }

    //~~~~~~~~~~~~~~collision and hit related methods ~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip(){
        if (this.getIsShieldOn()) { // if Shield is on -
            bashingActivate(); // hen the ship has its shields up and collides with another ship
        } else { // if Shield is off
            gettingHitEnergyActions();
            removeOneHealthLevel();// remove heath level and in case ship is dead - reset ship
        }
    }


    /**
     * Bashing is when the ship has its shields up and collides with another ship. When a ship
     * bashes another, its maximal energy level goes up by 18, and so does the current energy
     * level (for example if the ship’s energy is 4 out of 190, colliding with another ship while the
     * shields are up brings the energy to 22 out of 208).
     */
    private void bashingActivate() {
        addToCurrentEnergy(18);
        addToMaxEnergy(18);
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        // check if shield is off
        if (!this.getIsShieldOn()) {
            // assuming shields are down
            gettingHitEnergyActions();
            removeOneHealthLevel();
        }
    }


    //~~~~~~~~~~~~~~~~~~~~reset related methods~~~~~~~~~~~~~~~~~~~~~~
    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset() {
        setAllToDefalut();
    }


    private void setAllToDefalut() {
        //physics
        this.ssPhysics = new SpaceShipPhysics();
        // energy
        setCurrentEnergy(this.INITIAL_CURRENT_ENERGY);
        setMaxEnergy(this.INITIAL_MAX_ENERGY);
        // health
        setCurrentHealth(this.INITIAL_CURRENT_HEALTH_LEVEL);
        // Shield
        setIsShieldOn(false);
        //fire counter restart at 0
        setFireCoolDownCounter(0);
    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~dead related methods~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Checks if this ship is dead.
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        // if it has non-positive number of health points-ship *is* dead  - return true
        // if health points is positive - ship is *not* dead - return false
        return (this.currentHealthLevel <= 0);
    }


    // ~~~~~~~~~~~~~~~~~~physics related methods ~~~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Gets the physics object that controls this ship.
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {

        return this.ssPhysics;
    }


    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~image related methods~~~~~~~~~~~~~~~~~~~~
    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * @return the image of this ship.
     */
    public Image getImage() {
        if (this.getIsShieldOn()) {
            // present the image with Shield
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        } else {
            // return the image without the Shield
            return GameGUI.ENEMY_SPACESHIP_IMAGE;
        }
    }

    //~~~~~~~~~~~~~~~~~~~~~~~fire methods ~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Attempts to fire a shot.
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (isShipCooledDown()) { // if according to counter we can fire a shot
            if (areThereEnoughPointsForAction(this.FIRING_A_SHOT_COST)) { // check energy point for action,
                //if there are enough -
                removeSomeEnergyPoints(this.FIRING_A_SHOT_COST); //remove points for action
                game.addShot(ssPhysics);
                setFireCoolDownCounter(7);
            }
        }
    }


    /**
     * return true if ship is allowed to shot, false otherwise
     *
     * @return true if if fire cool down counter is at 0 , false otherwise
     */
    private boolean isShipCooledDown() {
        return (getFireCoolDownCounter() == 0);
        }


    /**
     * take off one point from fire cool down counter
     */
    protected void takeOffOneFireCoolDownCounter() {
        if (getFireCoolDownCounter() > 0) {
            this.fireCoolDownCounter -= 1;
        }
    }


    /**
     * set fire cool down counter to some int
     * @param number int
     */
    private void setFireCoolDownCounter(int number){
        this.fireCoolDownCounter = number;
    }


    /**
     * returns an int represent fire cool down counter
     * @return fire cool down counter
     */
    private int getFireCoolDownCounter(){
        return this.fireCoolDownCounter;
    }


    //~~~~~~~~~~~~~~shield methods ~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {

        if (areThereEnoughPointsForAction(this.SHIELD_COST)) {
            removeSomeEnergyPoints(this.SHIELD_COST); //remove points for action
            this.setIsShieldOn(true); // activate Shield
            getImage(); //

        }
    }

    /**
     * get is Shield On
     * @return Is shield on - boolean
     */
    protected boolean getIsShieldOn() {
        return this.isShieldOn;
    }

    /**
     * set is Shield On
     * @param value - boolean
     */
    protected void setIsShieldOn(boolean value) {
        this.isShieldOn = value;
    }


    //~~~~~~~~~~~~~~teleport methods ~~~~~~~~~~~~~~~~~~~~~~~~~
    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (areThereEnoughPointsForAction(this.TELEPORT_COST)) { //check if has enough points for this action
            removeSomeEnergyPoints(this.TELEPORT_COST); //remove points according to action 'point price'
            this.ssPhysics = new SpaceShipPhysics(); // randomly place the ship at some point on screen
        }
    }

    //~~~~~~~~~~~~~~other ship related methods ~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * find closest ship to this one and return it's physics
     * @param game - SpaceWars object
     * @return - SpaceShipPhysics object
     */
    protected  SpaceShipPhysics findClosestShip(SpaceWars game) {
        //Check who is the closest ship to this ship, and get it physics
        SpaceShipPhysics closestPhysics = game.getClosestShipTo(this).getPhysics();

        return closestPhysics;
    }

    /**
     * find angle to other ship
     * @param other - spaceship physics
     * @return double representing angleFacingOther
     */
    protected double findAngleToOther(SpaceShipPhysics other) {
        // find angle facing other ship -
        double angleFacingOther = this.getPhysics().angleTo(other);
        return angleFacingOther;
    }


    protected boolean isAngleCloseEnough(SpaceShipPhysics other, double threshold){
        // find angle to other
        double angleToOther = findAngleToOther(other);
        //check if the angle is less them threshold of radiands (doesn't matter if
        return  (angleToOther<threshold && -threshold <angleToOther);
    }

    /**
     * return the distance from other ship, relative to current ship
     * @param otherPhysics - SpaceShipPhysics
     * @return double - distance from other ship
     */
    protected double getDistanceFromOther(SpaceShipPhysics otherPhysics) {
        double distanceFromOther = this.getPhysics().distanceFrom(otherPhysics);
        return distanceFromOther;
    }


    /**
     * check if the distance of this ship is smaller than threshold from other
     * @param otherShipDistance double
     * @param threshold double
     * @return true if it is close enough, false otherwise
     */
    protected boolean IsShipCloseEnough(double otherShipDistance, double threshold) {
        return  (otherShipDistance < threshold);
        }

}



