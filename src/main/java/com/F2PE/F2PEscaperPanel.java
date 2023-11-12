package com.F2PE;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import lombok.extern.slf4j.Slf4j;
import net.runelite.client.game.ItemManager;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.http.api.item.ItemPrice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Slf4j
class F2PEscaperPanel extends PluginPanel {

    private static final Logger log = LoggerFactory.getLogger(com.F2PE.F2PEscaperPanel.class);
    private final ItemManager itemManager;
    private static final String ACCOUNT_USERNAME = "Account Username";
    private static final String ACCOUNT_LABEL = "Account Label";
    private static final Dimension PREFERRED_SIZE = new Dimension(PluginPanel.PANEL_WIDTH - 20, 30);
    private static final Dimension MINIMUM_SIZE = new Dimension(0, 30);

    private List<ItemPrice> result;
    private JLabel headerLabel = new JLabel("<html><h2>Item: (Wiki GP | Current GP)</h2></html>");
    private JLabel pieShellHeader = new JLabel("<html><h3>Pie Shell Money Maker</h3></html>");
    private JLabel pieDishPriceLabel = new JLabel();
    private JLabel pastryDoughPriceLabel = new JLabel();
    private JLabel pieShellPriceLabel = new JLabel();
    private JLabel pieShellAnswer = new JLabel();
    private JLabel redDyeHeader = new JLabel("<html><h3>Red Dye Money Maker</h3></html>");
    private JLabel redberryPriceLabel = new JLabel();
    private JLabel redDyeAnswer = new JLabel();
    private JLabel redDyePriceLabel = new JLabel();


    private final JPanel F2PEscaperPanel = new JPanel();
    private GridBagConstraints c;


    private static int findItemPrice(List<ItemPrice> itemPrices, String itemName) {
        for (ItemPrice itemPrice : itemPrices) {
            if (itemPrice.getName().equals(itemName)) {
                return itemPrice.getWikiPrice();
            }
        }
        return 0;
    }
    private void displayLabels(JLabel[] labels) {
        for (JLabel label : labels) {
            add(label, c);
            c.gridy++;
        }
    }
    @Inject
    public F2PEscaperPanel(ItemManager itemManager) {
        super();
        this.itemManager = itemManager;
        setBorder(new EmptyBorder(18, 10, 0, 10));
        setBackground(ColorScheme.DARK_GRAY_COLOR);
        setLayout(new GridBagLayout());

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 0;
        c.insets = new Insets(0, 0, 3, 0);


        //Pie Shell moneymaker
        this.result = itemManager.search("Pie Dish");
        int pieDishPrice = findItemPrice(result, "Pie dish");
        this.result = itemManager.search("Pastry Dough");
        int pastryDoughPrice = findItemPrice(result, "Pastry dough");
        this.result = itemManager.search("Pie Shell");
        int pieShellPrice = findItemPrice(result, "Pie shell");
        int pieShellProfit = pieShellPrice-pieDishPrice-pastryDoughPrice;

        //Create Displays
        if(pieDishPrice<=50){
            pieDishPriceLabel = new JLabel("<html>Buy Pie Dish: (50gp | " + pieDishPrice + "gp) <font color='green'>&#x2714;</font></html>");
        } else {
            pieDishPriceLabel = new JLabel("<html>Buy Pie Dish: (50gp | " + pieDishPrice + "gp) <font color='red'>&#x2718;</font></html>");
        }

        if(pastryDoughPrice<=256){
            pastryDoughPriceLabel = new JLabel("<html>Buy Pastry Dough: (256gp | " + pastryDoughPrice + "gp) <font color='green'>&#x2714;</font></html>");
        } else {
            pastryDoughPriceLabel = new JLabel("<html>Buy Pastry Dough: (256gp | " + pastryDoughPrice + "gp) <font color='red'>&#x2718;</font></html>");
        }

        if(pieShellPrice>=484){
            pieShellPriceLabel = new JLabel("<html>Sell Pie Shell: (256gp | " + pieShellPrice + "gp) <font color='green'>&#x2714;</font></html>");
        } else {
            pieShellPriceLabel = new JLabel("<html>Sell Pie Shell: (256gp | " + pieShellPrice + "gp) <font color='red'>&#x2718;</font></html>");
        }

        if(pieShellProfit>0){
            if (pieShellProfit>100){
                pieShellAnswer = new JLabel("<html>Current Profit: <font color='green'>"+pieShellProfit+" (Good Profit)</font></html>");
            } else {
                pieShellAnswer = new JLabel("<html>Current Profit: <font color='yellow'>"+pieShellProfit+" (Moderate Profit)</font></html> ");
            }
        } else {
            pieShellAnswer = new JLabel("<html>Current Profit: <font color='red'>"+pieShellProfit+" (Loss)</font></html>");
        }


        //Red Dye moneymaker
        this.result = itemManager.search("Redberries");
        int redberriesPrice = findItemPrice(result, "Redberries");
        this.result = itemManager.search("Red Dye");
        int redDyePrice = findItemPrice(result, "Red dye");
        int redDyeProfit = redDyePrice-((redberriesPrice*3)+5);

        //Create Displays
        if(redberriesPrice<=70){
            redberryPriceLabel = new JLabel("<html>Buy Redberries: (70gp | " + redberriesPrice + "gp) <font color='green'>&#x2714;</font></html>");
        } else {
            redberryPriceLabel = new JLabel("<html>Buy Redberries: (70gp | " + redberriesPrice + "gp) <font color='red'>&#x2718;</font></html>");
        }
        if(redDyePrice>=386){
            redDyePriceLabel = new JLabel("<html>Sell Red Dye: (386gp | " + redDyePrice + "gp) <font color='green'>&#x2714;</font></html>");
        } else {
            redDyePriceLabel = new JLabel("<html>Sell Red Dye: (386gp | " + redDyePrice + "gp) <font color='red'>&#x2718;</font></html>");
        }
        if(redDyeProfit>0){
            if (redDyeProfit>100){
                redDyeAnswer = new JLabel("<html>Current Profit: <font color='green'>"+redDyeProfit+" (Good Profit)</font></html>");
            } else {
                redDyeAnswer = new JLabel("<html>Current Profit: <font color='yellow'>"+redDyeProfit+" (Moderate Profit)</font></html> ");
            }
        } else {
            redDyeAnswer = new JLabel("<html>Current Profit: <font color='red'>"+redDyeProfit+" (Loss)</font></html>");
        }

        //Displaying
        pieShellHeader.setHorizontalAlignment(JLabel.CENTER);
        redDyeHeader.setHorizontalAlignment(JLabel.CENTER);

        JLabel[] pieShellLabels = {headerLabel, pieShellHeader, pieDishPriceLabel, pastryDoughPriceLabel, pieShellPriceLabel, pieShellAnswer};
        JLabel[] redDyeLabels = {redDyeHeader, redberryPriceLabel, redDyePriceLabel, redDyeAnswer};
        displayLabels(pieShellLabels);
        displayLabels(redDyeLabels);


    }
}