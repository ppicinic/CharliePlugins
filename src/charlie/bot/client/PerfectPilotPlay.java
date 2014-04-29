package charlie.bot.client;

import charlie.actor.Courier;
import charlie.advisor.StrategyCard;
import charlie.card.Card;
import charlie.card.Hand;
import charlie.card.Hid;
import charlie.plugin.IPlayer;
import charlie.util.Play;
import java.util.Random;

/**
 *
 * @author Phillip Picnic, Gregory Cremins
 * @version 3-26-2014
 */
public class PerfectPilotPlay implements Runnable {

    private final IPlayer bot;
    private final Hid hid;
    private final Hand hand;
    private final Courier dealer;
    private Card upCard;
    private final Random random;

    /**
     * Constructor for perfect play
     * 
     * @param bot
     * @param dealer
     * @param hid
     * @param hand
     * @param upCard 
     */
    public PerfectPilotPlay(IPlayer bot, Courier dealer, Hid hid, Hand hand,
            Card upCard) {
        this.bot = bot;
        this.hid = hid;
        this.hand = hand;
        this.dealer = dealer;
        this.upCard = upCard;
        random = new Random(1);
    }

    /**
     * Method to set the up card for the bot
     * @param upCard the upcard to be set
     */
    public void setUpCard(Card upCard) {
        this.upCard = upCard;
    }
    /**
     * Method to choose the play for the bot
     */
    @Override
    public void run() {
        try {
                //Thread.
                Thread.sleep(2500);
            } catch (InterruptedException ie) {
                // log exception?
            }
        System.out.println("play recieved " + hand.size());
        for (int x = 0; x < hand.size(); x++) {
            System.out.println(hand.getCard(x).getName());
        }
        if (!(hand.isBroke() || hand.isBlackjack()
                || hand.getValue() == 21 || hand.isCharlie())) {
            
            Play play = StrategyCard.getStrategyCard().getBotPlay(this.hand, this.upCard);

            if (play == Play.HIT) {
                this.dealer.hit(hid);
                //this.dealer.hit(this, this.hid);
                this.bot.play(this.hid);
                //this.play(this.hid);
            }
            if (play == Play.DOUBLE_DOWN) {
                if (this.hand.size() == 2) {
                    this.dealer.dubble(hid);
                } else {
                    this.dealer.hit(this.hid);
                    this.bot.play(this.hid);
                }
                //this.play(this.hid);
            }
            if (play == Play.STAY) {
                this.dealer.stay(this.hid);
            }
        }
    }
}
