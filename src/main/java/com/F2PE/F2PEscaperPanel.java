package com.F2PE;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Locale;
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
    private JLabel chocolateHeader = new JLabel("<html><h3>Chocolate Grind Money Maker</h3></html>");
    private JLabel chocolateBarPriceLabel = new JLabel();
    private JLabel chocolateDustPriceLabel = new JLabel();
    private JLabel chocolateAnswer = new JLabel();

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
            label.setVisible(true);
            add(label, c);
            c.gridy++;
        }
    }
    private void hideLabels(JLabel[] labels) {
        for (JLabel label : labels) {
            label.setVisible(false);
        }
    }
    private void setupLabels(JLabel label, String text, int price, int desiredPrice, boolean isBuy) {
        if ((isBuy && price <= desiredPrice) || (!isBuy && price >= desiredPrice)) {
            label.setText("<html>" + text + price + "gp) <font color='green'>&#x2714;</font></html>");
        } else {
            label.setText("<html>" + text + price + "gp) <font color='red'>&#x2718;</font></html>");
        }
    }
    private void setupProfitLabels(JLabel label, int profit, int iterationsHour) {
        int hourlyProfit = profit*iterationsHour;
        String hourlyProfitDisplay = String.format(Locale.US, "%,d", hourlyProfit).replace(',', '.');
        if (profit > 0) {
            if (profit > 100) {
                label.setText("<html>Current Profit: <font color='green'>"+profit + "gp (Good Profit) ("+hourlyProfitDisplay+"GP/H) </font></html>");
            } else {
                label.setText("<html>Current Profit: <font color='yellow'>" + profit + "gp (Moderate Profit) ("+hourlyProfitDisplay+"GP/H) </font></html>");
            }
        } else {
            label.setText("<html>Current Profit: <font color='red'>"+profit + "gp (Loss) ("+hourlyProfitDisplay+"GP/H) </font></html>");
        }
    }
    @Inject
    public F2PEscaperPanel(ItemManager itemManager, F2PEscaperConfig config) {
        //getting config settings
        super();
        this.itemManager = itemManager;
        boolean showProcessing = config.showProcessing();
        log.info("LUNAAA"+showProcessing);

        //setting layout
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
        setupLabels(pieDishPriceLabel,"Buy Pie Dish: (50gp | ",pieDishPrice,50, true);
        setupLabels(pastryDoughPriceLabel,"Buy Pastry Dough: (256gp | ",pastryDoughPrice,256, true);
        setupLabels(pieShellPriceLabel,"Sell Pie Shell: (484gp | ",pieShellPrice,484, false);
        setupProfitLabels(pieShellAnswer,pieShellProfit,125);

        //Red Dye moneymaker
        this.result = itemManager.search("Redberries");
        int redberriesPrice = findItemPrice(result, "Redberries");
        this.result = itemManager.search("Red Dye");
        int redDyePrice = findItemPrice(result, "Red dye");
        int redDyeProfit = redDyePrice-((redberriesPrice*3)+5);
        setupLabels(redberryPriceLabel,"Buy Redberries: (70gp | ",redberriesPrice,70, true);
        setupLabels(redDyePriceLabel,"Sell Red Dye: (386gp | ",redDyePrice,386, false);
        setupProfitLabels(redDyeAnswer,redDyeProfit,1000);

        //Griding Chocolate Bar moneymaker
        this.result = itemManager.search("chocolate bar");
        int chocolateBarPrice = findItemPrice(result, "Chocolate bar");
        this.result = itemManager.search("chocolate dust");
        int chocolateDustPrice = findItemPrice(result, "Chocolate dust");
        int chocolateProfit = chocolateDustPrice-chocolateBarPrice;
        setupLabels(chocolateBarPriceLabel,"Buy Chocolate Bar: (20gp | ",chocolateBarPrice,20, true);
        setupLabels(chocolateDustPriceLabel,"Sell Chocolate Dust: (43gp | ",chocolateDustPrice,43, false);
        setupProfitLabels(chocolateAnswer,chocolateProfit, 1800);

        //Displaying
        JLabel[] headerLabels = {headerLabel};
        displayLabels(headerLabels);
        updateDisplay(config);

    }
    public void updateDisplay(F2PEscaperConfig config){
        boolean showProcessing = config.showProcessing();
        JLabel[] pieShellLabels = {pieShellHeader, pieDishPriceLabel, pastryDoughPriceLabel, pieShellPriceLabel, pieShellAnswer};
        JLabel[] redDyeLabels = {redDyeHeader, redberryPriceLabel, redDyePriceLabel, redDyeAnswer};
        JLabel[] chocolateLabels = {chocolateHeader, chocolateBarPriceLabel, chocolateDustPriceLabel, chocolateAnswer};

        if(showProcessing){
            pieShellHeader.setHorizontalAlignment(JLabel.CENTER);
            redDyeHeader.setHorizontalAlignment(JLabel.CENTER);
            chocolateHeader.setHorizontalAlignment(JLabel.CENTER);

            displayLabels(pieShellLabels);
            displayLabels(redDyeLabels);
            displayLabels(chocolateLabels);

        } else {
            hideLabels(pieShellLabels);
            hideLabels(redDyeLabels);
            hideLabels(chocolateLabels);
        }

        repaint();
    }
}