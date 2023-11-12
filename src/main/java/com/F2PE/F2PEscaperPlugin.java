package com.F2PE;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.eventbus.EventBus;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.runelite.http.api.item.ItemPrice;
import net.runelite.client.game.ItemManager;

import java.awt.image.BufferedImage;
import java.util.List;

@Slf4j
@PluginDescriptor(
	name = "F2P Escaper",
	description = "Assists free-to-play players in finding optimal moneymaking methods.",
	tags = {"F2P", "moneymaking"}
)
public class F2PEscaperPlugin extends Plugin {

	//setting up config for later
	@Inject
	private ConfigManager configManager;
	@Inject
	private F2PEscaperConfig config;
	@Inject
	private EventBus eventBus;

	public F2PEscaperPlugin() {}

	@Inject
	public F2PEscaperPlugin(ConfigManager configManager) {
		this.configManager = configManager;
	}
	@Provides
	F2PEscaperConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(F2PEscaperConfig.class);
	}


	@Inject
	private Client client;
	@Inject
	private ClientToolbar clientToolbar;
	@Inject
	private ItemManager itemManager;

	private F2PEscaperPanel panel;
	private NavigationButton navButton;



	@Override
	protected void startUp() throws Exception {
		eventBus.register(this);
		panel = new F2PEscaperPanel(itemManager, config);
		final BufferedImage icon = ImageUtil.getResourceStreamFromClass(getClass(), "F2PE_icon.png");
		navButton = NavigationButton.builder()
				.tooltip("F2P Escaper")
				.priority(8)
				.icon(icon)
				.panel(panel)
				.build();

		clientToolbar.addNavigation(navButton);

	}

	@Override
	protected void shutDown() {
		clientToolbar.removeNavigation(navButton);
	}

	// Example: Add a listener to update the panel when the config changes
	@Subscribe
	private void onConfigChanged(ConfigChanged event) {
		if (event.getGroup().equals("F2P Escaper")) {
			// Retrieve the updated configuration
			F2PEscaperConfig updatedConfig = configManager.getConfig(F2PEscaperConfig.class);
			panel.updateDisplay(updatedConfig);

		}
	}
}