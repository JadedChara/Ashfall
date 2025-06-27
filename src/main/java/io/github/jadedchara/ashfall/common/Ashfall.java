package io.github.jadedchara.ashfall.common;

import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import io.github.jadedchara.ashfall.common.effect.ParalysisEffect;
import io.github.jadedchara.ashfall.common.effect.WardenTouchEffect;
import io.github.jadedchara.ashfall.common.item.ItemRegistry;
import io.github.jadedchara.ashfall.common.oversight.handler.MNPScreenHandler;
import io.github.jadedchara.ashfall.common.recipe.MortarRecipe;
import io.github.jadedchara.ashfall.common.recipe.serializers.MortarSerializer;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
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

	//Effects
	public static final StatusEffect PARALYSIS_EFFECT = new ParalysisEffect();
	public static final StatusEffect WARDEN_TOUCH_EFFECT = new WardenTouchEffect();

	//POTIONS
	public static final Potion PARALYSIS_POTION =
			Registry.register(
					Registries.POTION,
					new Identifier("ashfall", "paralysis"),
					new Potion(
							new StatusEffectInstance(
									PARALYSIS_EFFECT,
									3600,
									0)));

	public static final Potion WARDEN_TOUCH_POTION =
			Registry.register(
					Registries.POTION,
					new Identifier("ashfall", "warden_touch"),
					new Potion(
							new StatusEffectInstance(
									WARDEN_TOUCH_EFFECT,
									3600,
									0)));



	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.


		LOGGER.info("Hello Fabric world!");
		//REGISTRIES
		BlockRegistry.init();
		ItemRegistry.init();
		Registry.register(Registries.RECIPE_SERIALIZER, MortarSerializer.ID,
				MortarSerializer.INSTANCE);
		Registry.register(
				Registries.RECIPE_TYPE,
				new Identifier(Ashfall.MOD_ID, MortarRecipe.Type.ID),
				MortarRecipe.Type.INSTANCE
		);
		//EFFECTS
		Registry.register(
				Registries.STATUS_EFFECT,
				new Identifier(
						"ashfall",
						"paralysis"),
				PARALYSIS_EFFECT
		);
		Registry.register(
				Registries.STATUS_EFFECT,
				new Identifier(
						"ashfall",
						"warden_touch"),
				WARDEN_TOUCH_EFFECT
		);
		//POTIONS
		BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, Items.SHROOMLIGHT, PARALYSIS_POTION);
		BrewingRecipeRegistry.registerPotionRecipe(Potions.AWKWARD, ItemRegistry.HARDENED_SCULK_TENDRILS, WARDEN_TOUCH_POTION);
	}
}