package io.github.jadedchara.ashfall.common;

import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import io.github.jadedchara.ashfall.common.item.ItemRegistry;
import io.github.jadedchara.ashfall.common.oversight.handler.MNPScreenHandler;
import io.github.jadedchara.ashfall.common.recipe.MortarRecipe;
import io.github.jadedchara.ashfall.common.recipe.serializers.MortarSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ashfall implements ModInitializer {
	public static final String MOD_ID = "ashfall";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static RecipeType<MortarRecipe> MNP_TYPE = MortarRecipe.Type.INSTANCE;
	public static final ScreenHandlerType<MNPScreenHandler> MNP_SCREEN_HANDLER =
			Registry.register(
					Registries.SCREEN_HANDLER,
					Identifier.of("ashfall", "mnp_screen_handler"),
					new ExtendedScreenHandlerType<>(
							MNPScreenHandler::new
                    )
			);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.


		LOGGER.info("Hello Fabric world!");
		BlockRegistry.init();
		ItemRegistry.init();
		Registry.register(Registries.RECIPE_SERIALIZER, MortarSerializer.ID,
				MortarSerializer.INSTANCE);
		Registry.register(
				Registries.RECIPE_TYPE,
				new Identifier(Ashfall.MOD_ID, MortarRecipe.Type.ID),
				MNP_TYPE
		);
	}
}