package com.F2PE;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("F2P Escaper")
public interface F2PEscaperConfig extends Config {
	@ConfigItem(
			keyName = "showProcessing",
			name = "Show Processing methods",
			description = "Show processing moneymaking methods",
			position = 0
	)
	default boolean showProcessing() { return true; }
}
