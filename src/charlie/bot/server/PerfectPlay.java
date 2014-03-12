/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.bot.server;

import charlie.advisor.BasicStrategy;
import charlie.advisor.StrategyCard;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.dealer.Dealer;
import charlie.plugin.IBot;
import charlie.util.Play;
import java.util.Random;

/**
 *
 * @author Phil
 */
public class PerfectPlay implements Runnable{
    
    private IBot bot;
    private Hid hid;
    private Hand hand;
    private Dealer dealer;
    private Card upCard;
    private Random random;
    
    public PerfectPlay(IBot bot, Dealer dealer, Hid hid, Hand hand, 
            Card upCard){
        this.bot = bot;
        this.hid = hid;
        this.hand = hand;
        this.dealer = dealer;
        this.upCard = upCard;
        random = new Random(1);
    }
    
    public void setUpCard(Card upCard){
        this.upCard = upCard;
    }
    
    @Override
    public void run(){
        
            System.out.println("play recieved " + hand.size());
        for (int x = 0; x < hand.size(); x++) {
            System.out.println(hand.getCard(x).getName());
        }
        if (!(hand.isBroke() || hand.isBlackjack()
                || hand.getValue() == 21)) {
            try {
                //Thread.
                Thread.sleep(2500);
            } catch (InterruptedException ie) {
                // log exception?
            }
            Play play = new BasicStrategy().advise(this.hand, this.upCard);

            if (play == Play.HIT || play == Play.SPLIT) {
                this.dealer.hit(this.bot, this.hid);
                //this.dealer.hit(this, this.hid);
                this.bot.play(this.hid);
                //this.play(this.hid);
            }
            if (play == Play.DOUBLE_DOWN) {
                if (this.hand.size() == 2) {
                    this.dealer.doubleDown(this.bot, this.hid);
                } else {
                    this.dealer.hit(this.bot, this.hid);
                    this.bot.play(this.hid);
                }
                //this.play(this.hid);
            }
            if (play == Play.STAY) {
                this.dealer.stay(this.bot, this.hid);
            }
        }
    }
}
