/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.advisor.section4;

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
 * First test in for section 4 your hand: 2,2-A,A their hand: 7A
 * @author Gregory Cremins, Phil Picnic 
 * @version 2-19-2014
 * 
 */
public class Test00_22AA_7A {
    
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
            hand.hit(new Card(3, DIAMONDS));
            hand.hit(new Card(3, SPADES));
            Card upCard = new Card(1, HEARTS);
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
