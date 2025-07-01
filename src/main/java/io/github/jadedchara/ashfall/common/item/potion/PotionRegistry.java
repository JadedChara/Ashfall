package io.github.jadedchara.ashfall.common.item.potion;

import io.github.jadedchara.ashfall.common.Ashfall;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PotionRegistry {
    public static final Potion PARALYSIS_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("ashfall", "paralysis"),
                    new Potion(
                            new StatusEffectInstance(
                                    Ashfall.PARALYSIS_EFFECT,
                                    3600,
                                    0))
            );
    public static final Potion LONG_PARALYSIS_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("ashfall", "long_paralysis"),
                    new Potion(
                            new StatusEffectInstance(
                                    Ashfall.PARALYSIS_EFFECT,
                                    4800,
                                    0))
            );

    public static final Potion WARDEN_TOUCH_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("ashfall", "warden_touch"),
                    new Potion(
                            new StatusEffectInstance(
                                    Ashfall.WARDEN_TOUCH_EFFECT,
                                    3600,
                                    0))
            );

    public static final Potion STRONGER_WARDEN_TOUCH_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("ashfall", "strong_warden_touch"),
                    new Potion(
                            new StatusEffectInstance(
                                    Ashfall.WARDEN_TOUCH_EFFECT,
                                    1800,
                                    1))
            );

    public static final Potion LONGER_WARDEN_TOUCH_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("ashfall", "long_warden_touch"),
                    new Potion(
                            new StatusEffectInstance(
                                    Ashfall.WARDEN_TOUCH_EFFECT,
                                    9600,
                                    0))
            );
    public static final Potion HEROIC_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("ashfall", "hero_of_the_village"),
                    new Potion(
                            new StatusEffectInstance(
                                    StatusEffects.HERO_OF_THE_VILLAGE,
                                    2400,
                                    0))
            );
    public static final Potion LONGER_HEROIC_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("ashfall", "long_hero_of_the_village"),
                    new Potion(
                            new StatusEffectInstance(
                                    StatusEffects.HERO_OF_THE_VILLAGE,
                                    4800,
                                    0))
            );
    public static final Potion WITHER_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("ashfall", "wither"),
                    new Potion(
                            new StatusEffectInstance(
                                    StatusEffects.WITHER,
                                    2400,
                                    0))
            );
    public static final Potion LONGER_WITHER_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("ashfall", "long_wither"),
                    new Potion(
                            new StatusEffectInstance(
                                    StatusEffects.WITHER,
                                    4800,
                                    0))
            );
    public static final Potion UNLUCK_POTION =
            Registry.register(
                    Registries.POTION,
                    new Identifier("ashfall", "unluck"),
                    new Potion(
                            new StatusEffectInstance(
                                    StatusEffects.UNLUCK,
                                    2400,
                                    0))
            );

    public static void init(){}
}
