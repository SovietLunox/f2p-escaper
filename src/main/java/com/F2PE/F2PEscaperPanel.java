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
    private JLabel pieShellPricesLabel = new JLabel();
    private JLabel pieShellAnswer = new JLabel();
    private final JLabel redDyeHeader = new JLabel("<html><h3>Red Dye Money Maker</h3></html>");
    private JLabel redDyePricesLabel = new JLabel();
    private JLabel redDyeAnswer = new JLabel();
    private final JLabel chocolateHeader = new JLabel("<html><h3>Chocolate Grind Money Maker</h3></html>");
    private JLabel chocolateDustPricesLabel = new JLabel();
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
    private JLabel diamondNecklacePricesLabel = new JLabel();
    private JLabel diamondNecklaceAnswer = new JLabel();
    private final JLabel bronzeBarHeader = new JLabel("<html><h3>Bronze Bar Money Maker</h3></html>");
    private JLabel bronzeBarPricesLabel = new JLabel();
    private JLabel bronzeBarAnswer = new JLabel();



    //Arrays
    private List<JSeparator> separators = new ArrayList<>();
    private final JLabel[] processingHeaderArray = {processingHeader};
    private final JLabel[] pieShellLabels = {pieShellHeader, pieShellPricesLabel, pieShellAnswer};
    private final JLabel[] redDyeLabels = {redDyeHeader, redDyePricesLabel, redDyeAnswer};
    private final JLabel[] chocolateLabels = {chocolateHeader, chocolateDustPricesLabel, chocolateAnswer};
    private final JLabel[] collectingHeaderArray = {collectingHeader};
    private final JLabel[] sheepShearLabels = {sheepShearHeader, woolPriceLabel, woolAnswer};
    private final JLabel[] beerLabels = {beerHeader, beerPriceLabel, beerAnswer};
    private final JLabel[] skillingHeaderArray = {skillingHeader};
    private final JLabel[] diamondNecklaceLabels = {diamondHeaderLabel,diamondRequirements, diamondNecklacePricesLabel, diamondNecklaceAnswer};
    private final JLabel[] bronzeBarLabels = {bronzeBarHeader,bronzeBarPricesLabel,bronzeBarAnswer};
    private final JPanel F2PEscaperPanel = new JPanel();
    private GridBagConstraints c;


    //Setup Functions
    private int[] updatePrices(String... itemNames) {
        int[] prices = new int[itemNames.length];
        for (int i = 0; i < itemNames.length; i++) {
            this.result = itemManager.search(itemNames[i]);
            prices[i] = findItemPrice(result, itemNames[i]);
        }
        return prices;
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
        int [] pieShellPrices = updatePrices("Pie dish","Pastry dough","Pie shell");
        int pieShellProfit = pieShellPrices[2]-pieShellPrices[1]-pieShellPrices[0];
        setupLabels(pieShellPricesLabel,"Buy Pie Dish: (50gp | ",pieShellPrices[0],50, true);
        setupLabels(pieShellPricesLabel,"Buy Pastry Dough: (256gp | ",pieShellPrices[1],256, true);
        setupLabels(pieShellPricesLabel,"Sell Pie Shell: (484gp | ",pieShellPrices[2],484, false);
        setupProfitLabels(pieShellAnswer,pieShellProfit,125);

        //Red Dye moneymaker
        int [] redDyePrices = updatePrices("Redberries","Red dye");
        int redDyeProfit = redDyePrices[1]-((redDyePrices[0]*3)+5);
        setupLabels(redDyePricesLabel,"Buy Redberries: (70gp | ",redDyePrices[0],70, true);
        setupLabels(redDyePricesLabel,"Sell Red Dye: (386gp | ",redDyePrices[1],386, false);
        setupProfitLabels(redDyeAnswer,redDyeProfit,1000);

        //Griding Chocolate Bar moneymaker
        int [] chocolateDustPrices = updatePrices("Chocolate bar", "Chocolate dust");
        int chocolateProfit = chocolateDustPrices[1]-chocolateDustPrices[0];
        setupLabels(chocolateDustPricesLabel,"Buy Chocolate Bar: (20gp | ",chocolateDustPrices[0],20, true);
        setupLabels(chocolateDustPricesLabel,"Sell Chocolate Dust: (43gp | ",chocolateDustPrices[1],43, false);
        setupProfitLabels(chocolateAnswer,chocolateProfit, 1800);
    }
    private void collectingSetup(){
        //Sheep Shearing moneymaker
        int [] woolPrice = updatePrices("Wool");
        setupLabels(woolPriceLabel,"Sell Wool: (133gp | ",woolPrice[0],133, false);
        setupProfitLabels(woolAnswer, woolPrice[0],350);

        //Beer buying moneymaker
        int [] beerPrice = updatePrices("Beer");
        setupLabels(beerPriceLabel,"Sell Beer: (105gp | ",beerPrice[0],105, false);
        setupProfitLabels(beerAnswer, woolPrice[0],400);
    }
    private void skillingSetup(){
        //Crafting diamond jewelry moneymaker
        int [] diamondNecklacePrices = updatePrices("Diamond","Gold bar","Diamond necklace");
        int diamondNecklaceProfit = diamondNecklacePrices[2]-diamondNecklacePrices[1]-diamondNecklacePrices[0];
        setupLabels(diamondNecklacePricesLabel,"Buy Diamond: (1790gp | ",diamondNecklacePrices[0],1790, true);
        setupLabels(diamondNecklacePricesLabel,"Buy Gold bar: (110gp | ",diamondNecklacePrices[1],110, true);
        setupLabels(diamondNecklacePricesLabel,"Sell Diamond Necklace: (2041gp | ",diamondNecklacePrices[2],2041, false);
        setupProfitLabels(diamondNecklaceAnswer, diamondNecklaceProfit,1000);

        //Bronze smithing moneymaker
        int [] bronzeBarPrices = updatePrices("Tin ore","Copper ore","Bronze bar");
        int bronzeBarProfit = bronzeBarPrices[2]-bronzeBarPrices[1]-bronzeBarPrices[0];
        setupLabels(bronzeBarPricesLabel,"Buy Tin ore: (65gp | ",bronzeBarPrices[0],65, true);
        setupLabels(bronzeBarPricesLabel,"Buy Copper ore: (90gp | ",bronzeBarPrices[1],90, true);
        setupLabels(bronzeBarPricesLabel,"Sell Bronze bar: (210gp | ",bronzeBarPrices[2],210, false);
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
        String labelText = label.getText();
        StringBuilder updatedText = new StringBuilder();

        if (labelText.endsWith("</html>")) {
            labelText = labelText.substring(0, labelText.lastIndexOf("</html>"));
            updatedText.append(labelText);
        } else {
            updatedText.append("<html>");
        }

        if ((isBuy && price <= desiredPrice) || (!isBuy && price >= desiredPrice)) {
            updatedText.append(text).append(price).append("gp) <font color='green'>&#x2714;</font>");
        } else {
            updatedText.append(text).append(price).append("gp) <font color='red'>&#x2718;</font>");
        }

        updatedText.append("<br></html>");
        label.setText(updatedText.toString());
    }

    private void resetLabels(JLabel[]... labelArrays) {
        for (JLabel[] labels : labelArrays) {
            for (JLabel label : labels) {
                label.setText("");
            }
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
            resetLabels(pieShellLabels,redDyeLabels,chocolateLabels);
            processingSetup();

            displayLabels(processingHeaderArray,pieShellLabels,redDyeLabels,chocolateLabels);
            endBlock();
        } else {
            hideLabels(processingHeaderArray,chocolateLabels,pieShellLabels,redDyeLabels);
        }
        if(showCollecting){
            resetLabels(pieShellLabels,sheepShearLabels,beerLabels);
            collectingSetup();

            displayLabels(collectingHeaderArray,sheepShearLabels,beerLabels);
            endBlock();
        } else {
            hideLabels(collectingHeaderArray, sheepShearLabels, beerLabels);
        }
        if(showSkilling){
            resetLabels(diamondNecklaceLabels,bronzeBarLabels);
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