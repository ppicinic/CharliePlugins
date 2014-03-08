/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charlie.advisor;

import charlie.card.Card;
import charlie.card.Hand;
import charlie.util.Play;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Phil Picinic
 * This is a singleton class that creates a single instance of the Black Jack
 * Strategy Card.
 */
public class StrategyCard {
 
    // hashmap of arraylists to represent the card
    private final HashMap<String, ArrayList<Play>> card;
    
    private static volatile StrategyCard instance = null;   
    
    /**
     * Constructor
     * creates and fills in the card
     */
    private StrategyCard(){
        card = new HashMap<>();
        ArrayList<Play> temp;
        
        for(int i = 9; i < 17; i++){
            String hand = "" + i;
            card.put(hand, new ArrayList<Play>(11));
            temp = card.get(hand);
            fill(temp, Play.HIT);
            if(i >= 12 && i <= 16){
                
                for(int y = 2; y < 12; y++){
                    
                    if(y <= 6){
                        temp.set(y, Play.STAY);
                    }
                }
                if(i == 12){
                    temp.set(2, Play.HIT);
                    temp.set(2, Play.HIT);
                }
                
            }
            else if(i == 11 || i == 10){
                for(int y = 2; y < 10; y++){
                    temp.set(y, Play.DOUBLE_DOWN);
                }
                if(i == 11){
                    temp.set(10, Play.DOUBLE_DOWN);
                    temp.set(11, Play.HIT);
                }else{
                    temp.set(10, Play.HIT);
                    temp.set(11, Play.HIT);
                }
            }
            else{
                for(int y = 2; y < 12; y++){
                    if(y >= 3 && y <=6){
                        temp.set(y, Play.DOUBLE_DOWN);
                    }
                }
            }
            
        }
        card.put("17+", new ArrayList<Play>(11));
        temp = card.get("17+");
        fill(temp, Play.STAY);
        card.put("A, 8-10", temp);
        card.put("10, 10", temp);
        card.put("5-8", new ArrayList<Play>(11));
        temp = card.get("5-8");
        fill(temp, Play.HIT);
        card.put("A, 7", new ArrayList<Play>(11));
        temp = card.get("A, 7");
        fill(temp, Play.DOUBLE_DOWN);
        for(int y = 2; y < 12; y++){
            if(y == 2 || y == 7 || y == 8 ){
                temp.set(y, Play.STAY);
            }
            else if(y >= 9){
                temp.set(y, Play.HIT);
            }
        }
        temp = (ArrayList<Play>) temp.clone();
        temp.set(2, Play.HIT);
        temp.set(7, Play.HIT);
        temp.set(8, Play.HIT);
        card.put("A, 6", temp);
        temp = (ArrayList<Play>) temp.clone();
        temp.set(3, Play.HIT);
        card.put("A, 5", temp);
        card.put("A, 4", temp);
        temp = (ArrayList<Play>) temp.clone();
        temp.set(4, Play.HIT);
        card.put("A, 3", temp);
        card.put("A, 2", temp);
        
        card.put("A,A 8,8", new ArrayList<Play>());
        temp = card.get("A,A 8,8");
        fill(temp, Play.SPLIT);
        temp = (ArrayList<Play>) temp.clone();
        temp.set(7, Play.STAY);
        temp.set(10, Play.STAY);
        temp.set(11, Play.STAY);
        card.put("9, 9", temp);
        temp = (ArrayList<Play>) card.get("A,A 8,8").clone();
        for(int y = 8; y < 12; y++){
            temp.set(y, Play.HIT);
        }
        card.put("7, 7", temp);
        card.put("3, 3", temp);
        card.put("2, 2", temp);
        
        temp = (ArrayList<Play>) temp.clone();
        temp.set(7, Play.HIT);
        card.put("6, 6", temp);
        temp = (ArrayList<Play>) temp.clone();
        for(int y = 2; y <= 4; y++){
            temp.set(y, Play.HIT);
        }
        card.put("4, 4", temp);
        temp = card.get("10");
        card.put("5, 5", temp);
        
    }
    
    /**
     * gets an instance of the strategy card
     * if there isn't one, one will be created
     * @return the strategy card
     */
    public static StrategyCard getStrategyCard(){
        if (instance == null) {
            synchronized(StrategyCard.class){
                if (instance == null){
                    instance = new StrategyCard();
                }
            }
        }
        
        return instance;
    }
    
    /**
     * fills an array list with a decided move
     * @param array the array to fill
     */
    private void fill(ArrayList<Play> array, Play play){
        for(int i = 0; i < 12; i++){
            array.add(i, play);
        }
    }
    
    /**
     * gets the play suggested by the card
     * @param myHand the player's hand
     * @param upCard the dealer's up card
     * @return the play that is suggested
     */
    public Play getPlay(Hand myHand, Card upCard){
        String hand = createHandString(myHand);
        int cardVal = upCard.value();
        if(upCard.isFace()){
            cardVal = 10;
        }
        if(upCard.isAce()){
            cardVal = 11;
        }
        ArrayList<Play> temp = card.get(hand);
        return temp.get(cardVal);
    }
    
    /**
     * creates a hand string code based on the hand
     * @param hand the player's hand
     * @return the hand string code
     */
    private String createHandString(Hand hand){
        // detect pair
        if(hand.isPair()){
            if(hand.getCard(0).isAce() || hand.getCard(0).value() == 8){
                return "A,A 8,8";
            }
            if(hand.getCard(0).isFace()){
                return "10, 10";
            }
            int v = hand.getCard(0).value();
            return v + ", " + v;
        }
        boolean containsAce = false;
        for(int i = 0; i < hand.size(); i++){
            if(hand.getCard(i).isAce()){
                containsAce = true;
                break;
            }
        }
        if(containsAce){
            int acecount = 0;
            int value = 0;
            for(int i = 0; i < hand.size(); i++){
                if(hand.getCard(i).isAce()){
                    acecount++;
                }
                else{
                    value += hand.getCard(i).value();
                }
                
            }
            for(int i = 1; i < acecount; i++){
                value += 1;
            }
            if(value <= 10){
                if(value >= 8){
                    return "A, 8-10";
                }
                return "A, " + value;
            }
        }
        int value = 0;
        for(int i = 0; i < hand.size(); i++){
            value += hand.getCard(i).value();
        }
        if(value >= 17){
            return "17+";
        }
        if(value <= 8){
            return "5-8";
        }
        return "" + value;
    }
}
