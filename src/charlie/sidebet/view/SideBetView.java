/*
 Copyright (c) 2014 Ron Coleman

 Permission is hereby granted, free of charge, to any person obtaining
 a copy of this software and associated documentation files (the
 "Software"), to deal in the Software without restriction, including
 without limitation the rights to use, copy, modify, merge, publish,
 distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to
 the following conditions:

 The above copyright notice and this permission notice shall be
 included in all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package charlie.sidebet.view;

import charlie.audio.Effect;
import charlie.audio.SoundFactory;
import charlie.card.Hid;
import charlie.plugin.ISideBetView;
import charlie.view.AMoneyManager;
import java.util.ArrayList;
import charlie.view.sprite.ChipButton;
import charlie.view.sprite.Chip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the side bet view
 * @author Ron Coleman, Ph.D.
 */
public class SideBetView implements ISideBetView {
    private final Logger LOG = LoggerFactory.getLogger(SideBetView.class);
    
    public final static int X = 400;
    public final static int Y = 200;
    public final static int DIAMETER = 50;
    
    protected Font font = new Font("Arial", Font.BOLD, 18);
    protected Font font2 = new Font("Arial", Font.BOLD, 15);
    protected BasicStroke stroke = new BasicStroke(3);
    protected Color winLoseColor;
    protected Color outcomeTextColor;
    protected String outcomeText;
    protected boolean gameOver = false;
    // See http://docs.oracle.com/javase/tutorial/2d/geometry/strokeandfill.html
    protected float dash1[] = {10.0f};
    protected BasicStroke dashed
            = new BasicStroke(3.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dash1, 0.0f);   

    protected List<ChipButton> buttons;
    protected int amt = 0;
    protected double payout = 0;
    protected AMoneyManager moneyManager;
    protected ArrayList<Chip> chips = new ArrayList();
    protected Random ran = new Random();
     

    public SideBetView() {
        LOG.info("side bet view constructed");
    }
    
    /**
     * Sets the money manager.
     * @param moneyManager 
     */
    @Override
    public void setMoneyManager(AMoneyManager moneyManager) {
        this.moneyManager = moneyManager;
        this.buttons = moneyManager.getButtons();
    }
    
    /**
     * Registers a click for the side bet.
     * @param x X coordinate
     * @param y Y coordinate
     */
    @Override
    public void click(int x, int y) {
        int oldAmt = amt;
        
        // Test if any chip button has been pressed.
        for(ChipButton button: buttons) {
            if(button.isPressed(x, y)) {
                amt += button.getAmt();
                LOG.info("A. side bet amount "+button.getAmt()+" updated new amt = "+amt);
                SoundFactory.play(Effect.CHIPS_IN);
                chips.add(new Chip(button.getImage(),X + (chips.size() * 5) + 15,Y + ran.nextInt(5) - 15, button.getAmt()));
        
            }
        
        }
        
        if(oldAmt == amt && x >= X && x <= (X + (DIAMETER/2)) && y >= Y && y <=(Y+ (DIAMETER/2))) {
            amt = 0;
            LOG.info("B. side bet amount cleared");
            SoundFactory.play(Effect.CHIPS_OUT);
            chips.clear();
        }
    }

    /**
     * Informs view the game is over and it's time to update the bankroll for the hand.
     * @param hid Hand id
     */
    @Override
    public void ending(Hid hid) {
        double bet = hid.getSideAmt();
        if(bet == 0)
            return;
        payout = hid.getSideAmt();
        LOG.info("side bet outcome = "+bet);
        gameOver = true;
        
        // Update the bankroll
        moneyManager.increase(bet);
        chips.clear();
        amt = 0;
        
        LOG.info("new bankroll = "+moneyManager.getBankroll());
    }

    /**
     * Informs view the game is starting
     */
    @Override
    public void starting() {
        gameOver = false;
        payout = 0;
    }

    /**
     * Gets the side bet amount.
     * @return Bet amount
     */
    @Override
    public Integer getAmt() {
        return amt;
    }

    /**
     * Updates the view
     */
    @Override
    public void update() 
    {
    }

    /**
     * Renders the view
     * @param g Graphics context
     */
    @Override
    public void render(Graphics2D g) {
        // Draw the at-stake place on the table
        g.setColor(Color.RED); 
        g.setStroke(dashed);
        g.drawOval(X-DIAMETER/2, Y-DIAMETER/2, DIAMETER, DIAMETER);
        
        // Draw the at-stake amount
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(""+amt, X-5, Y+5);
        
        //Draw table for side bet rules
        g.setColor(Color.RED);
        g.fillRect(X-DIAMETER/2 + 125, (Y-DIAMETER/2) + 100, DIAMETER + 105, DIAMETER + 75);
        g.setColor(Color.YELLOW);
        g.drawRect(X-DIAMETER/2 + 125, (Y-DIAMETER/2) + 100, DIAMETER + 105, DIAMETER + 75);
        
        //draw the sidebet rules
        g.setFont(font2);
        g.setColor(Color.GREEN);
        g.drawString("SUPER 7's pays 3:1", X-DIAMETER/2 + 130, Y-DIAMETER/2 + 125);
        g.drawString("ROYAL MATCHED", X-DIAMETER/2 + 130, Y-DIAMETER/2 + 145);
        g.drawString("pays 25:1 ", X-DIAMETER/2 + 130, Y-DIAMETER/2 + 165);
        g.drawString("EXACTLY 13 pays 1:1", X-DIAMETER/2 + 130, Y-DIAMETER/2 + 185);
        
        //render chips
        for(Chip c: chips)
        {
            c.render(g);
        }
        
        //renderWinLose
        if(payout != 0 && gameOver)
        {
            if(payout < 0)
            {
                winLoseColor = Color.RED;
                outcomeTextColor = Color.WHITE;
                outcomeText = "LOSE";
            }
            if(payout > 0)
            {
                winLoseColor = Color.GREEN;
                outcomeTextColor = Color.BLACK;
                outcomeText = "WIN!";
            }
            FontMetrics fm = g.getFontMetrics(font);
            int w = fm.charsWidth(outcomeText.toCharArray(), 0, outcomeText.length());
            int h = fm.getHeight();
            g.setColor(winLoseColor);
            g.fillRoundRect(X + DIAMETER/2, Y, w, h, 5, 5);
            g.setColor(outcomeTextColor);
            g.drawString(outcomeText, X + DIAMETER/2, Y + 15);
        }
        
        
    }
}
