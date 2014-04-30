package charlie.bot.test;

import java.util.Random;

/**
 *
 * @author Phil Picinic
 */
public class Shoe1 extends charlie.card.Shoe{
    
    @Override
    public void init(){
        super.ran = new Random(System.currentTimeMillis());
        
        super.numDecks = 1;
        
        super.load();
        
        super.shuffle();
    }
}
