package com.F2PE;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class F2PEPlugin
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(F2PEscaperPlugin.class);
		RuneLite.main(args);
	}
}