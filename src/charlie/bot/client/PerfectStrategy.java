package charlie.bot.client;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;
import java.util.HashMap;

/**
 *
 * @author Phil
 */
public class PerfectStrategy {
    
    private HashMap<String, Integer[]> softDouble;
    private HashMap<String, Integer[]> hardDouble;
    private HashMap<String, Integer[]> softHit;
    private HashMap<String, Integer[]> hardHit;
    
    public PerfectStrategy(){
        softDouble = new HashMap<>();
        hardDouble = new HashMap<>();
        softHit = new HashMap<>();
        hardHit = new HashMap<>();
        
        Integer[] A2D = {1000,11,5,0,-3,1000,1000,1000,1000,1000};
        Integer[] A3D = {1000,10,2,-4,-8,1000,1000,1000,1000,1000};
        Integer[] A4D = {1000,8,0,-7,-11,1000,1000,1000,1000,1000};
        Integer[] A5D = {1000,6,-3,-10,-16,1000,1000,1000,1000,1000};
        Integer[] A6D = {2,-5,-9,-16,-19,23,1000,1000,1000,1000};
        Integer[] A7D = {2,-2,-8,-12,-12,1000,1000,1000,1000,1000};
        Integer[] A8D = {1000,9,6,3,2,1000,1000,1000,1000,1000};
        Integer[] A9D = {1000,16,13,10,9,1000,1000,1000,1000,1000};
        softDouble.put("A2", A2D);
        softDouble.put("A3", A3D);
        softDouble.put("A4", A4D);
        softDouble.put("A5", A5D);
        softDouble.put("A6", A6D);
        softDouble.put("A7", A7D);
        softDouble.put("A8", A8D);
        softDouble.put("A9", A9D);
        
        Integer[] D8 = {1000,17,12,7,5,1000,1000,1000,1000,1000};
        Integer[] D9 = {4,0,-3,-7,-9,7,17,1000,1000,1000};
        Integer[] D10 = {-13,-15,-17,-19,-21,-10,-6,-2,9,8};
        Integer[] D11 = {-17,-18,-19,-21,-23,-12,-9,-6,-6,2};
        hardDouble.put("8", D8);
        hardDouble.put("9", D9);
        hardDouble.put("10", D10);
        hardDouble.put("11", D11);
        
        Integer[] A2toA6 = {1000,1000,1000,1000,1000,1000,1000,1000,1000,1000};
        Integer[] A7 = {-1000,-1000,-1000,-1000,-1000,-1000,-22,1000,1000,0};
        Integer[] A8toA9 = {-1000,-1000,-1000,-1000,-1000,-1000,-1000,-1000,-1000,-1000};
        softHit.put("A2", A2toA6);
        softHit.put("A3", A2toA6);
        softHit.put("A4", A2toA6);
        softHit.put("A5", A2toA6);
        softHit.put("A6", A2toA6);
        softHit.put("A7", A7);
        softHit.put("A8", A8toA9);
        softHit.put("A9", A8toA9);
        
        Integer[] H2to11 = {1000,1000,1000,1000,1000,1000,1000,1000,1000,1000};
        Integer[] H12 = {5,2,0,-2,-2,1000,1000,1000,1000,1000};
        Integer[] H13 = {-1,-3,-5,-8,-8,1000,1000,1000,1000,1000};
        Integer[] H14 = {-6,-7,-10,-12,-12,1000,1000,1000,15,22};
        Integer[] H15 = {-10,-10,-14,-17,-17,22,21,15,6,18};
        Integer[] H16 = {-14,-17,-19,-22,-20,15,14,7,0,14};
        Integer[] H17 = {-1000,-1000,-1000,-1000,-1000,-1000,-1000,-1000,-1000,-10};
        Integer[] H18p = {-1000,-1000,-1000,-1000,-1000,-1000,-1000,-1000,-1000,-1000};
        hardHit.put("2", H2to11);
        hardHit.put("3", H2to11);
        hardHit.put("4", H2to11);
        hardHit.put("5", H2to11);
        hardHit.put("6", H2to11);
        hardHit.put("7", H2to11);
        hardHit.put("8", H2to11);
        hardHit.put("9", H2to11);
        hardHit.put("10", H2to11);
        hardHit.put("11", H2to11);
        hardHit.put("12", H12);
        hardHit.put("13", H13);
        hardHit.put("14", H14);
        hardHit.put("15", H15);
        hardHit.put("16", H16);
        hardHit.put("17", H17);
        hardHit.put("18", H18p);
        hardHit.put("19", H18p);
        hardHit.put("20", H18p);
    }
    
    public Play advise(Hand hand, Card card, int count){
        String handString = createHandString(hand);
        int spot = card.value();
        if(card.isAce()){
            spot = 11;
        }
        if(card.isFace()){
            spot = 10;
        }
        spot -= 2;
        if(handString.charAt(0) == 'A'){
            //check soft tables
            
            //first check double down table
            if(hand.size() == 2){
                Integer[] map = softDouble.get(handString);
                if(count >= map[spot]){
                    return Play.DOUBLE_DOWN;
                }
            }
            Integer[] map = softHit.get(handString);
            if(count < map[spot]){
                return Play.HIT;
            }else{
                return Play.STAY;
            }
        }else{
            int num = Integer.parseInt(handString);
            if(hand.size() == 2 && num >= 8 && num <= 11){
                Integer[] map = hardDouble.get(handString);
                if(count >= map[spot]){
                    return Play.DOUBLE_DOWN;
                }
            }
            Integer[] map = hardHit.get(handString);
            if(count < map[spot]){
                return Play.HIT;
            }else{
                return Play.STAY;
            }
        }
    }
    
    private String createHandString(Hand hand){
        boolean containsAce = false;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.getCard(i).isAce()) {
                containsAce = true;
                break;
            }
        }
        if (containsAce) {
            int acecount = 0;
            int value = 0;
            for (int i = 0; i < hand.size(); i++) {
                if (hand.getCard(i).isAce()) {
                    acecount++;
                } else {
                    value += hand.getCard(i).value();
                }

            }
            for (int i = 1; i < acecount; i++) {
                value += 1;
            }
            if (value < 10) {
                System.out.println("softace");
                return "A" + value;
            }
        }
        int value = 0;
        for(int i = 0; i < hand.size(); i++){
            if(hand.getCard(i).isAce()){
                value += 11;
            }else if(hand.getCard(i).isFace()){
                value += 10;
            }else{
                value += hand.getCard(i).value();
            }
        }
        return "" + hand.getValue();
    }
}
