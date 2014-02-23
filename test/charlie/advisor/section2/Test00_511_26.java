/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.advisor.section2;

import charlie.advisor.BasicStrategy;
import charlie.card.Card;
import static charlie.card.Card.Suit.*;
//import static charlie.card.Card.Suit.HEARTS;
//import static charlie.card.Card.Suit.DIAMONDS;
//import static charlie.card.Card.Suit.SPADES;
import charlie.card.Hand;
import charlie.card.Hid;
import static charlie.dealer.Seat.YOU;
import charlie.util.Play;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * First test in for section 2 your hand: 5-11 their hand: 2-6
 * @author Gregory Cremins, Phil Picnic 
 * @version 2-19-2014
 * 
 */
public class Test00_511_26 {
    
    /**
     * Method to test the given parameters will throw exception if there was
     * an error in play decisions
     */
    @Test
    public void test()
    {
        try{
            Hid hid = new Hid(YOU);
            Hand hand = new Hand(hid);
            hand.hit(new Card(2, DIAMONDS));
            hand.hit(new Card(3, CLUBS));
            Card upCard = new Card(4, HEARTS);
            Play play = new BasicStrategy().advise(hand, upCard);
            assertTrue(play == Play.HIT);
        }
        catch(Exception e)
        {
            System.out.println(e);
            fail();
        }
        
    }
    
}
