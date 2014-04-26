package charlie.bot.server;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Dealer;
import charlie.dealer.Seat;
import charlie.plugin.IBot;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Phil Picinic, Gregory Cremins
 * @version 3-26-2014
 */
public class PerfectBot implements IBot{

    private Dealer dealer;
    private Seat seat;
    private Hid hid;
    private Hand hand;
    private Card upCard;
    private final Random randomGen;
    
    /**
     * Constructor for perfect bot
     */
    public PerfectBot(){
        randomGen = new Random();
    }
    /**
     * Method to get the hand of the bot
     * @return the bots current hand
     */
    @Override
    public Hand getHand() {
        System.out.println("hand collected");
        this.hid = new Hid(this.seat);
        this.hand = new Hand(this.hid);
        return this.hand;
    }
    /**
     * Method to set the Bot's dealer
     * @param dealer the bots dealer
     */
    @Override
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }
    /**
     * Method to sit the bot down
     * @param seat the seat the bot will take
     */
    @Override
    public void sit(Seat seat) {
        this.seat = seat;
    }
    /**
     * Method to start the game
     * @param hids the hands of the bot and the dealer
     * @param shoeSize the shoe size
     */
    @Override
    public void startGame(List<Hid> hids, int shoeSize) {
        
    }

    @Override
    public void endGame(int shoeSize) {
        
    }
    
    /**
     * Method to deal the card to the bot 
     * @param hid the current hand ID of the bot
     * @param card the card to deal to the bot
     * @param values values of all cards
     */

    @Override
    public void deal(Hid hid, Card card, int[] values) {
        if (this.hid.getSeat() == hid.getSeat()) {
            if (this.hand.size() > 2) {
                
            }
            //System.out.println("Card recieved");
        }
        if (hid.getSeat() == Seat.DEALER) {
            this.upCard = card;
        }
    }
    /**
     * Method to insure
     */
    @Override
    public void insure() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void bust(Hid hid) {
        
    }

    @Override
    public void win(Hid hid) {
        
    }

    @Override
    public void blackjack(Hid hid) {
        
    }

    @Override
    public void charlie(Hid hid) {
        
    }

    @Override
    public void lose(Hid hid) {
        
    }

    @Override
    public void push(Hid hid) {
        
    }

    @Override
    public void shuffling() {
        
    }
    /**
     * method to make a play
     * @param hid the hand ID of the player
     */
    @Override
    public void play(Hid hid) {
        if (this.hid.getSeat() == hid.getSeat()) {
            new Thread(new PerfectPlay(this, this.dealer, this.hid, this.hand, 
                    this.upCard)).start();
        }
    }
    
}
