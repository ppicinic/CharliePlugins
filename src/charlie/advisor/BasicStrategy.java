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
 * @author Phil
 */
public class BasicStrategy implements IAdvisor {
    
    public BasicStrategy(){
        
    }

    @Override
    public Play advise(Hand myHand, Card upCard) {
        return StrategyCard.getStrategyCard().getPlay(myHand, upCard);
    }
}
