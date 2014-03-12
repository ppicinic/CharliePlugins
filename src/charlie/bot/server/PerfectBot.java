package charlie.bot.server;

import charlie.advisor.BasicStrategy;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Dealer;
import charlie.dealer.Seat;
import charlie.plugin.IBot;
import charlie.util.Play;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Phil Picinic
 */
public class PerfectBot implements IBot{

    private Dealer dealer;
    private Seat seat;
    private Hid hid;
    private Hand hand;
    private Card upCard;
    private Random randomGen;
    
    public PerfectBot(){
        randomGen = new Random();
    }
    
    @Override
    public Hand getHand() {
        System.out.println("hand collected");
        this.hid = new Hid(this.seat);
        this.hand = new Hand(this.hid);
        return this.hand;
    }

    @Override
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    @Override
    public void sit(Seat seat) {
        this.seat = seat;
    }

    @Override
    public void startGame(List<Hid> hids, int shoeSize) {
        
    }

    @Override
    public void endGame(int shoeSize) {
        
    }

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

    @Override
    public void play(Hid hid) {
        if (this.hid.getSeat() == hid.getSeat()) {
            new Thread(new PerfectPlay(this, this.dealer, this.hid, this.hand, 
                    this.upCard)).start();
        }
    }
    
}
