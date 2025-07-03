package io.github.jadedchara.ashfall.common.enchantment;

import io.github.jadedchara.ashfall.common.enchantment.types.DreadEnchantment;
import io.github.jadedchara.ashfall.common.enchantment.types.HarvestEnchantment;
import io.github.jadedchara.ashfall.common.enchantment.types.InfectiousEnchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EnchantmentRegistry {
    public static Enchantment DREAD = new DreadEnchantment();
    public static Enchantment HARVEST = new HarvestEnchantment();
    public static Enchantment INFECTIOUS = new InfectiousEnchantment();

    public static void init(){
        Registry.register(Registries.ENCHANTMENT, new Identifier("ashfall", "dread"), DREAD);
        Registry.register(Registries.ENCHANTMENT, new Identifier("ashfall", "harvest"), HARVEST);
        Registry.register(Registries.ENCHANTMENT, new Identifier("ashfall", "infectious"), INFECTIOUS);

    }
}
