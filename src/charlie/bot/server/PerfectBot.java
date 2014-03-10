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
    
    public PerfectBot(){
        
    }
    
    @Override
    public Hand getHand() {
        return this.hand;
    }

    @Override
    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    @Override
    public void sit(Seat seat) {
        this.seat = seat;
        this.hid = new Hid(this.seat);
        this.hand = new Hand(this.hid);
        
    }

    @Override
    public void startGame(List<Hid> hids, int shoeSize) {
        this.hand = new Hand(this.hid);
    }

    @Override
    public void endGame(int shoeSize) {
        
    }

    @Override
    public void deal(Hid hid, Card card, int[] values) {
        if(this.hid.getSeat() == hid.getSeat()){
            this.hand.hit(card);
        }
        if(hid.getSeat() == Seat.DEALER){
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
        if(this.hid.getSeat() == hid.getSeat()){
            Play play = new BasicStrategy().advise(this.hand, this.upCard);
            
            if(play == Play.HIT || play == Play.SPLIT){
                this.dealer.hit(this, this.hid);
                this.play(this.hid);
            }
            if(play == Play.DOUBLE_DOWN){
                this.dealer.doubleDown(this, this.hid);
                this.play(this.hid);
            }
            if(play == Play.STAY){
                this.dealer.stay(this, this.hid);
            }            
        }
    }
    
}
