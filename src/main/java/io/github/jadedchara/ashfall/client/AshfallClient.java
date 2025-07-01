package io.github.jadedchara.ashfall.client;

import io.github.jadedchara.ashfall.client.renderer.block.MNPBlockEntityRenderer;
import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import io.github.jadedchara.ashfall.common.oversight.MNPScreen;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AshfallClient implements ClientModInitializer {
	public boolean activeParalysis = false;
	public ManagedShaderEffect paralysisPerspective = ShaderEffectManager.getInstance().manage(
			new Identifier(
					"ashfall",
					"shaders/post/paralysis.json"
			)
	);
	public ManagedShaderEffect wardentouchPerspective = ShaderEffectManager.getInstance().manage(
			new Identifier(
					"ashfall",
					"shaders/post/wardentouch.json"
			)
	);
	public ManagedShaderEffect testShader = ShaderEffectManager.getInstance().manage(
			new Identifier(
					"ashfall",
					"shaders/post/paralysis.json"
			)
	);

	@Override
	public void onInitializeClient() {
		BlockEntityRendererRegistry.register(BlockRegistry.MORTAR_N_PESTLE_BLOCK_ENTITY, MNPBlockEntityRenderer::new);
		HandledScreens.register(Ashfall.MNP_SCREEN_HANDLER, MNPScreen::new);
		ShaderEffectRenderCallback.EVENT.register(td -> {
			try{
				if(MinecraftClient.getInstance().player.hasStatusEffect(Ashfall.PARALYSIS_EFFECT)){
					paralysisPerspective.render(td);
				}else if(MinecraftClient.getInstance().player.hasStatusEffect(Ashfall.WARDEN_TOUCH_EFFECT)){
					wardentouchPerspective.render(td);
					//MinecraftClient.getInstance().inGameHud.render();
				}else if (this.activeParalysis == true){
					testShader.render(td);
				}
			}catch(Exception e){
				//ignore
			}
		});
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(ClientCommandManager.literal("toggleshadertest").executes(context -> {
				context.getSource().sendFeedback(Text.literal("Toggling test shader..."));
				if(this.activeParalysis == true){
					this.activeParalysis = false;
				}else{
					this.activeParalysis = true;
				}
				return 1;
			}));

		});
	}
}