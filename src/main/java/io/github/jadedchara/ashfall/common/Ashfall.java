package io.github.jadedchara.ashfall.common;

import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import io.github.jadedchara.ashfall.common.item.ItemRegistry;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ashfall implements ModInitializer {
	public static final String MOD_ID = "ashfall";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		BlockRegistry.init();
		ItemRegistry.init();
	}
}