package io.github.jadedchara.ashfall.common.item;


import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import io.github.jadedchara.ashfall.common.item.tool.ReinforcedShearsItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ItemRegistry {
    public static List<ItemStack> ODDITIES = new ArrayList<ItemStack>();
    public static List<ItemStack> GEAR = new ArrayList<ItemStack>();
    public static List<ItemStack> FOOD = new ArrayList<ItemStack>();
    public static List<ItemStack> UTILITIES = new ArrayList<ItemStack>();

    public static Item register(Item i, String d, List<ItemStack> g){
        Identifier itemID = new Identifier(Ashfall.MOD_ID,d);
        g.add(i.getDefaultStack());
        return Registry.register(Registries.ITEM,itemID,i);
    }
    public static Item REINFORCED_SHEARS = register(
            new ReinforcedShearsItem(
                    new FabricItemSettings().maxDamage(4)
            ),
            "reinforced_shears",
            GEAR
    );
    public static Item HARDENED_SCULK_TENDRILS = register(
            new Item(
                    new FabricItemSettings()
                            .food(new FoodComponent.Builder()
                                    .hunger(5)
                                    .saturationModifier(0.1F)
                                    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 400, 1), 1.0F)
                                    .statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 400, 1), 1.0F)
                                    .build()
                            )
            ),
            "hardened_sculk_tendrils",
            ODDITIES
    );
    public static Item NETHERITE_ROD = register(
            new Item(new FabricItemSettings().fireproof()),
            "netherite_rod",
            ODDITIES);
    public static Item SCULK_NETHERITE_ROD = register(
            new Item(new FabricItemSettings().fireproof()),
            "sculk_netherite_rod",
            ODDITIES);
    public static Item BLAZE_NETHERITE_ROD = register(
            new Item(new FabricItemSettings().fireproof()),
            "blaze_netherite_rod",
            ODDITIES);

    //Item Groups
    public static ItemGroup ASHFALL_ODDITIES = FabricItemGroup.builder()
            .icon(()->new ItemStack(HARDENED_SCULK_TENDRILS))
            .entries((displayContext, entries) -> {
                for(ItemStack i : ODDITIES){
                    entries.add(i);
                }
            })
            .build();
    public static ItemGroup ASHFALL_GEAR = FabricItemGroup.builder()
            .icon(()->new ItemStack(REINFORCED_SHEARS))
            .entries((displayContext, entries) -> {
                for(ItemStack i : GEAR){
                    entries.add(i);
                }
            })
            .build();

    public static ItemGroup ASHFALL_FOOD = FabricItemGroup.builder()
            .icon(()->new ItemStack(HARDENED_SCULK_TENDRILS))
            .entries((displayContext, entries) -> {
                for(ItemStack i : FOOD){
                    entries.add(i);
                }
            })
            .build();

    public static ItemGroup ASHFALL_UTILITIES = FabricItemGroup.builder()
            .icon(()->new ItemStack(BlockRegistry.MORTAR_N_PESTLE))
            .entries((displayContext, entries) -> {
                for(ItemStack i : UTILITIES){
                    entries.add(i);
                }
            })
            .build();

    //-------------------------------------------------------------
    public static void init(){
        FuelRegistry.INSTANCE.add(HARDENED_SCULK_TENDRILS,200);
        CompostingChanceRegistry.INSTANCE.add(HARDENED_SCULK_TENDRILS,0.1F);
        Registry.register(Registries.ITEM_GROUP,Identifier.of("ashfall","oddities"),ASHFALL_ODDITIES);
        Registry.register(Registries.ITEM_GROUP,Identifier.of("ashfall","food"),ASHFALL_FOOD);
        Registry.register(Registries.ITEM_GROUP,Identifier.of("ashfall","gear"),ASHFALL_GEAR);
        Registry.register(Registries.ITEM_GROUP,Identifier.of("ashfall","utilities"),ASHFALL_UTILITIES);
    }

}
