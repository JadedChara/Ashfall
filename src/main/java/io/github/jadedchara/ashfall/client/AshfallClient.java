package io.github.jadedchara.ashfall.client;

import io.github.jadedchara.ashfall.client.renderer.block.MNPBlockEntityRenderer;
import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import io.github.jadedchara.ashfall.common.oversight.MNPScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class AshfallClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.register(BlockRegistry.MORTAR_N_PESTLE_BLOCK_ENTITY, MNPBlockEntityRenderer::new);
		HandledScreens.register(Ashfall.MNP_SCREEN_HANDLER, MNPScreen::new);
	}
}