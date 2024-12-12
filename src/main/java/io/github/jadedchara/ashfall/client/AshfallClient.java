package io.github.jadedchara.ashfall.client;

import io.github.jadedchara.ashfall.client.renderer.block.MNPBlockEntityRenderer;
import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

public class AshfallClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.register(BlockRegistry.MORTAR_N_PESTLE_BLOCK_ENTITY, MNPBlockEntityRenderer::new);
	}
}