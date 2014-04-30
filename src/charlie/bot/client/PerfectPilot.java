package charlie.bot.client;

import charlie.actor.Courier;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Seat;
import charlie.plugin.IGerty;
import charlie.view.AMoneyManager;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Phil
 */
public class PerfectPilot implements IGerty{
    
    private static final Logger log = LoggerFactory.getLogger("PerfectPilot");
    private static final int minBetAmt = 100;
    private Courier courier;
    private AMoneyManager moneyManager;
    //shoe size
    private int shoeSize;
    private Hand hand;
    private Card upCard;
    //num games
    private int gameCount;
    //card count
    private int count;
    private int aceCount;
    private int amt;
    private int totalBet;
    private int numBlackJacks;
    private int numCharlies;
    private int numWins;
    private int numBreaks;
    private int numLoses;
    DecimalFormat df = new DecimalFormat("#.##");
    protected Font font = new Font("Arial", Font.BOLD, 15);
    protected Font font2 = new Font("Arial", Font.BOLD, 9);
    protected float dash1[] = {10.0f};
    protected BasicStroke dashed
            = new BasicStroke(3.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);  
    private PerfectStrategy strategy;
    
    public PerfectPilot(){
        gameCount = 0;
        count = 0;
        aceCount = 4;
        strategy = new PerfectStrategy();
    }

    @Override
    public void go() {
        amt = 0;
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
        totalBet += amt;
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
        
        //Render counting system
        g.setFont(font);
        g.setColor(Color.BLUE);
        g.drawString("Advanced Omega 2", 0, 255);
        //Render rectangle container
        g.setColor(Color.RED);
        g.fillRect(0, 260, 200, 140);
        g.setColor(Color.YELLOW);
        g.setStroke(dashed);
        g.drawRect(0, 260, 200, 140);
        //Render the Shoe size
        g.setColor(Color.orange);
        g.drawString("Shoe count : " + df.format(shoeSize), 5, 280);
        //Render the count
        g.drawString("Count: " + count, 5, 300);
        //Render the games played
        g.drawString("Games Played : " + gameCount, 5, 320);
        //Render the max bet
        g.drawString("Max bet: N/A", 5, 340);
        //Render the mean bet
        if(gameCount == 0)
        {
            g.drawString("Mean Bet: 0", 5, 360);
        }
        else
        {
            g.drawString("Mean Bet: " + df.format(totalBet /(double) gameCount), 5, 360);
        }
        //Render the counts
        g.setFont(font2);
        g.drawString("BlackJacks|Charlies|Wins|Breaks|Loses", 5, 380);
        g.setFont(font);
        g.drawString(numBlackJacks + "|" + numCharlies + "|" + numWins + "|" + numBreaks + "|" + numLoses, 5, 395);
        
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
        numLoses++;
    }

    @Override
    public void win(Hid hid) {
        numWins++;
        
    }

    @Override
    public void blackjack(Hid hid) {
        numBlackJacks++;
        
    }

    @Override
    public void charlie(Hid hid) {
        numCharlies++;
       
    }

    @Override
    public void lose(Hid hid) {
        numLoses++;
        
    }

    @Override
    public void push(Hid hid) {
        numBreaks++;
        
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
           PerfectPilotPlay p = new PerfectPilotPlay(this, courier, hid, hand, upCard, strategy, count);
           Thread t = new Thread(p);
           t.start();
       }
    }
}
