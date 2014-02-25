/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.advisor;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.plugin.IAdvisor;
import charlie.util.Play;

/**
 *
 * @author Phil Picinic
 * Basic Strategy is the API level plugin for the advisor.
 * It provides and interface to the StrategyCard
 */
public class BasicStrategy implements IAdvisor {
    
    /**
     * Blank Constructor
     */
    public BasicStrategy(){
        super();
    }

    /**
     * gets the play suggested by the card
     * @param myHand the player's hand
     * @param upCard the dealer's up card
     * @return the play that is suggested
     */
    @Override
    public Play advise(Hand myHand, Card upCard) {
        return StrategyCard.getStrategyCard().getPlay(myHand, upCard);
    }
}
