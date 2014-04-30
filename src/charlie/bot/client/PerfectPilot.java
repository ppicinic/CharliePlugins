package charlie.bot.client;

import charlie.actor.Courier;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.plugin.IGerty;
import charlie.view.AMoneyManager;
import java.awt.Graphics2D;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Phil
 */
public class PerfectPilot implements IGerty{
    
    private static final Logger log = LoggerFactory.getLogger("PerfectPilot");
    private static final int minBetAmt = 5;
    private Courier courier;
    private AMoneyManager moneyManager;
    private int shoeSize;
    private Hand hand;
    private Card upCard;
    private int gameCount;
    private int count;
    private int aceCount;
    
    public PerfectPilot(){
        gameCount = 0;
        count = 0;
        aceCount = 4;
    }

    @Override
    public void go() {
        int amt = 0;
        moneyManager.clearBet();
        if(count > 0){
            for(int i = 0; i < count; i++){
                moneyManager.upBet(minBetAmt);
                amt += minBetAmt;
            }
        }else{
            moneyManager.upBet(minBetAmt);
            amt += minBetAmt;
        }
        courier.bet(amt, 0);
        gameCount++;
        log.info("game number: " + gameCount);
    }

    @Override
    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    @Override
    public void setMoneyManager(AMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
    }

    @Override
    public void update() {
        
    }

    @Override
    public void render(Graphics2D g) {
        
    }

    @Override
    public void startGame(List<Hid> hids, int shoeSize) {
        this.shoeSize = shoeSize;
        for(Hid hid: hids){
            if(hid.getSeat() == Seat.YOU){
                hand = new Hand(hid);
            }
        }
    }

    @Override
    public void endGame(int shoeSize) {
        this.shoeSize = shoeSize;
        log.info("shoe size: " + shoeSize);
    }

    @Override
    public void deal(Hid hid, Card card, int[] values) {
        if(hid.getSeat() == Seat.YOU){
            hand.hit(card);
        }
        // TODO store up card
        if(hid.getSeat() == Seat.DEALER){
            upCard = card;            
        }
        // TODO count card
        log.info("card rank: " + card.getRank());
        int rank = card.getRank();
        if(rank == 2 || rank == 3 || rank == 7){
            count += 1;
        }else if(rank == 4 || rank == 5 || rank == 6){
            count += 2;
        }else if(rank == 9){
            count -= 1;
        }else if(rank == 10 || rank == 11 || rank == 12 || rank == 13){
            count -= 2;
        }else if(rank == 1){
            aceCount--;
        }
        log.info("count: " + count);
        log.info("acecount: " + aceCount);
    }

    @Override
    public void insure() {
        
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
       count = 0;
       aceCount = 4;
       log.info("reset count");
    }

    @Override
    public void play(Hid hid) {
       if(gameCount < 101 && hid.getSeat() == Seat.YOU){
           PerfectPilotPlay p = new PerfectPilotPlay(this, courier, hid, hand, upCard);
           Thread t = new Thread(p);
           t.start();
       }
    }
}
