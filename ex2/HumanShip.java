import oop.ex2.GameGUI;

import java.awt.*;

/**
 * Controlled by the user. The keys are: left-arrow and right-arrow to
 * turn, up-arrow to accelerate, ’d’ to fire a shot, ’s’ to turn on the shield, ’a’ to teleport.
 * You can assume there will be at most one human controlled ship in a game, but you’re not
 * required to enforce this
 */
public class HumanShip
        extends SpaceShip{
    public HumanShip() {
        super();
    }

    // -----------------METHODS -------------------------//

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        GameGUI gui = game.getGUI();
        boolean accelerate;
        accelerate = false;

        // check if tereport was pressed
        if (gui.isTeleportPressed()){
            teleport();
        }

        // check if user pressed accelerate and turn
        if (gui.isUpPressed()){
            accelerate = true;
        }int turn = whereToTurn(gui);

        // move according to accelerate and turn:
        this.getPhysics().move(accelerate, turn);

        // Shield activation
        if (gui.isShieldsPressed()){
            shieldOn();
        }else setIsShieldOn(false);

        // Firing a shot
        if (gui.isShotPressed()){
            fire(game);
        }

        //Regeneration of the 1 unit of energy of this round
        addEnergyEndOfRound(); //the current energy is raised by 1 point

        takeOffOneFireCoolDownCounter(); // if counter is on, update it (-1)
    }

    /**
     * override func from spaceship
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * @return the image of this ship.
     */
    public Image getImage() {
        if (getIsShieldOn()) {
            // present the image with Shield
            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        } else {
            // return the image without the Shield
            return GameGUI.SPACESHIP_IMAGE;
        }
    }

    /**
     *  check if ship should go right or left
     *  using gui
     *  if both left and right bottoms are being pressed, ship would not turn (return 0
     * @param gui - GameGui object
     * @return int -1 for right, 0 for no change, 1 for left
     */
    private int whereToTurn(GameGUI gui){
        int turn = 0;
        if (gui.isRightPressed() && (gui.isLeftPressed())){ // if both were pressed, do nothing
            turn = 0;
        }else if (gui.isRightPressed()){ // if right was pressed
            turn = -1;
        }else if (gui.isLeftPressed()){ // if left was pressed
            turn = 1;
        }return turn;
    }
}

