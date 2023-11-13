package com.F2PE;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ItemManager itemManager;
    private static final Dimension PREFERRED_SIZE = new Dimension(PluginPanel.PANEL_WIDTH - 20, 30);
    private static final Dimension MINIMUM_SIZE = new Dimension(0, 30);

    private List<ItemPrice> result;
    private final JLabel titleLabel = new JLabel("<html><h1><b><font color='yellow'>F2P</font> <font color='red'>Escaper</font></h1></b></html>");
    private final JLabel headerDescLabel = new JLabel("<html><h3>Below are popular moneymaking methods for free to play players.\nThey are shown with updated prices, pick the best one available.</h3></html>");
    private final JLabel headerLabel = new JLabel("<html><h3>Item: (Wiki GP | Current GP)</h3></html>");
    private final JLabel processingHeader = new JLabel("<html><h2>Processing</h2></html>");
    private final JLabel pieShellHeader = new JLabel("<html><h3>Pie Shell Money Maker</h3></html>");
    private JLabel pieDishPriceLabel = new JLabel();
    private JLabel pastryDoughPriceLabel = new JLabel();
    private JLabel pieShellPriceLabel = new JLabel();
    private JLabel pieShellAnswer = new JLabel();
    private final JLabel redDyeHeader = new JLabel("<html><h3>Red Dye Money Maker</h3></html>");
    private JLabel redberryPriceLabel = new JLabel();
    private JLabel redDyeAnswer = new JLabel();
    private JLabel redDyePriceLabel = new JLabel();
    private final JLabel chocolateHeader = new JLabel("<html><h3>Chocolate Grind Money Maker</h3></html>");
    private JLabel chocolateBarPriceLabel = new JLabel();
    private JLabel chocolateDustPriceLabel = new JLabel();
    private JLabel chocolateAnswer = new JLabel();
    private final JLabel collectingHeader = new JLabel("<html><h2>Collecting</h2></html>");
    private final JLabel sheepShearHeader = new JLabel("<html><h3>Sheep Shear Money Maker</h3></html>");
    private JLabel woolAnswer = new JLabel();
    private JLabel woolPriceLabel = new JLabel();
    private final JLabel beerHeader = new JLabel("<html><h3>Beer buying Money Maker</h3></html>");
    private JLabel beerAnswer = new JLabel();
    private JLabel beerPriceLabel = new JLabel();
    private final JLabel skillingHeader = new JLabel("<html><h2>Skilling</h2></html>");
    private final JLabel diamondHeaderLabel = new JLabel("<html><h3>Diamond Necklace Money Maker</h3></html>");
    private final JLabel diamondRequirements = new JLabel("<html><h3>Requires 56+ Crafting</h3></html>");
    private JLabel diamondPriceLabel = new JLabel();
    private JLabel goldBarPriceLabel = new JLabel();
    private JLabel diamondNecklacePriceLabel = new JLabel();
    private JLabel diamondNecklaceAnswer = new JLabel();
    private final JLabel bronzeBarHeader = new JLabel("<html><h3>Bronze Bar Money Maker</h3></html>");
    private JLabel tinPriceLabel = new JLabel();
    private JLabel copperPriceLabel = new JLabel();
    private JLabel bronzeBarPriceLabel = new JLabel();
    private JLabel bronzeBarAnswer = new JLabel();



    //Arrays
    private List<JSeparator> separators = new ArrayList<>();
    private final JLabel[] processingHeaderArray = {processingHeader};
    private final JLabel[] pieShellLabels = {pieShellHeader, pieDishPriceLabel, pastryDoughPriceLabel, pieShellPriceLabel, pieShellAnswer};
    private final JLabel[] redDyeLabels = {redDyeHeader, redberryPriceLabel, redDyePriceLabel, redDyeAnswer};
    private final JLabel[] chocolateLabels = {chocolateHeader, chocolateBarPriceLabel, chocolateDustPriceLabel, chocolateAnswer};
    private final JLabel[] collectingHeaderArray = {collectingHeader};
    private final JLabel[] sheepShearLabels = {sheepShearHeader, woolPriceLabel, woolAnswer};
    private final JLabel[] beerLabels = {beerHeader, beerPriceLabel, beerAnswer};
    private final JLabel[] skillingHeaderArray = {skillingHeader};
    private final JLabel[] diamondNecklaceLabels = {diamondHeaderLabel,diamondRequirements, diamondPriceLabel, goldBarPriceLabel, diamondNecklacePriceLabel, diamondNecklaceAnswer};
    private final JLabel[] bronzeBarLabels = {bronzeBarHeader,tinPriceLabel,copperPriceLabel,bronzeBarPriceLabel,bronzeBarAnswer};
    private final JPanel F2PEscaperPanel = new JPanel();
    private GridBagConstraints c;


    //Setup Functions
    private int updatePrices(String itemName) {
        this.result = itemManager.search(itemName);
        return findItemPrice(result, itemName);
    }
    private static int findItemPrice(List<ItemPrice> itemPrices, String itemName) {
        for (ItemPrice itemPrice : itemPrices) {
            if (itemPrice.getName().equals(itemName)) {
                return itemPrice.getWikiPrice();
            }
        }
        return 0;
    }
    private void processingSetup(){
        //Pie Shell moneymaker
        int pieDishPrice = updatePrices("Pie dish");
        int pastryDoughPrice = updatePrices("Pastry dough");
        int pieShellPrice = updatePrices("Pie shell");
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
    }
    private void collectingSetup(){
        //Sheep Shearing moneymaker
        int woolPrice = updatePrices("Wool");
        setupLabels(woolPriceLabel,"Sell Wool: (133gp | ",woolPrice,133, false);
        setupProfitLabels(woolAnswer, woolPrice,350);

        //Beer buying moneymaker
        int beerPrice = updatePrices("Beer");
        setupLabels(beerPriceLabel,"Sell Beer: (105gp | ",beerPrice,105, false);
        setupProfitLabels(beerAnswer, woolPrice,400);
    }
    private void skillingSetup(){
        //Crafting diamond jewelry moneymaker
        int diamondPrice = updatePrices("Diamond");
        int goldBarPrice = updatePrices("Gold bar");
        int diamondNecklacePrice = updatePrices("Diamond necklace");
        int diamondNecklaceProfit = diamondNecklacePrice-goldBarPrice-diamondPrice;
        setupLabels(diamondPriceLabel,"Buy Diamond: (1790gp | ",diamondPrice,1790, true);
        setupLabels(goldBarPriceLabel,"Buy Gold bar: (110gp | ",goldBarPrice,110, true);
        setupLabels(diamondNecklacePriceLabel,"Sell Diamond Necklace: (2041gp | ",diamondNecklacePrice,2041, false);
        setupProfitLabels(diamondNecklaceAnswer, diamondNecklaceProfit,1000);

        //Bronze smithing moneymaker
        int tinPrice = updatePrices("Tin ore");
        int copperPrice = updatePrices("Copper ore");
        int bronzeBarPrice = updatePrices("Bronze bar");
        int bronzeBarProfit = bronzeBarPrice-copperPrice-tinPrice;
        setupLabels(tinPriceLabel,"Buy Tin ore: (65gp | ",tinPrice,65, true);
        setupLabels(copperPriceLabel,"Buy Copper ore: (90gp | ",copperPrice,90, true);
        setupLabels(bronzeBarPriceLabel,"Sell Bronze bar: (210gp | ",bronzeBarPrice,210, false);
        setupProfitLabels(bronzeBarAnswer, bronzeBarProfit,600);
    }
    //Display Functions
    private void displayLabels(JLabel[]... labelArrays) {
        for (JLabel[] labels : labelArrays) {
            for (int i = 0; i < labels.length; i++) {
                JLabel label = labels[i];
                label.setVisible(true);
                add(label, c);

                // Center headers
                if (i == 0) {label.setHorizontalAlignment(JLabel.CENTER);}
                c.gridy++;
            }
        }
    }
    private void hideLabels(JLabel[]... labelArrays) {
        for (JLabel[] labels : labelArrays) {
            for (JLabel label : labels) {
                label.setVisible(false);
            }
        }
    }
    private void setupLabels(JLabel label, String text, int price, int desiredPrice, boolean isBuy) {
        if ((isBuy && price <= desiredPrice) || (!isBuy && price >= desiredPrice)) {
            label.setText("<html>" + text + price + "gp) <font color='green'>&#x2714;</font></html>");
        } else {
            label.setText("<html>" + text + price + "gp) <font color='red'>&#x2718;</font></html>");
        }
    }
    private void endBlock(){
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBackground(Color.WHITE);
        separator.setPreferredSize(new Dimension(200, 1)); // Adjust the width as needed
        c.gridy++;
        add(separator, c);
        c.gridy++;
        separators.add(separator);
    }
    private void removeSeparators() {
        for (JSeparator separator : separators) {
            remove(separator);
        }
        separators.clear();
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

        //Displaying
        JLabel[] headerLabels = {titleLabel, headerDescLabel, headerLabel};
        displayLabels(headerLabels);
        updateDisplay(config);

    }
    public void updateDisplay(F2PEscaperConfig config){
        boolean showProcessing = config.showProcessing();
        boolean showCollecting = config.showCollecting();
        boolean showSkilling = config.showSkilling();
        boolean showRequirements = config.showRequirements();

        removeSeparators();
        endBlock();
        endBlock();
        if(showProcessing){
            processingSetup();

            displayLabels(processingHeaderArray,pieShellLabels,redDyeLabels,chocolateLabels);
            endBlock();
        } else {
            hideLabels(processingHeaderArray,chocolateLabels,pieShellLabels,redDyeLabels);
        }
        if(showCollecting){
            collectingSetup();

            displayLabels(collectingHeaderArray,sheepShearLabels,beerLabels);
            endBlock();
        } else {
            hideLabels(collectingHeaderArray, sheepShearLabels, beerLabels);
        }
        if(showSkilling){
            skillingSetup();

            displayLabels(skillingHeaderArray,bronzeBarLabels);
            if(showRequirements){
                displayLabels(diamondNecklaceLabels);
            }
            endBlock();
        } else {
            hideLabels(skillingHeaderArray,diamondNecklaceLabels,bronzeBarLabels);
        }

        repaint();
    }
}