package io.github.jadedchara.ashfall.common.block;

import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.block.blockentity.AccumulatorBlockEntity;
import io.github.jadedchara.ashfall.common.block.blockentity.MortarNPestleBlockEntity;
import io.github.jadedchara.ashfall.common.block.utility.AccumulatorBlock;
import io.github.jadedchara.ashfall.common.block.utility.MortarNPestleBlock;
import io.github.jadedchara.ashfall.common.item.ItemRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.List;

public class BlockRegistry {
    public static Block bRegister(Block block, String name, List<ItemStack> g){
        BlockItem be = new BlockItem(block, new Item.Settings());
        Registry.register(Registries.ITEM, Identifier.of(Ashfall.MOD_ID,name),be);
        g.add(be.getDefaultStack());
        return Registry.register(Registries.BLOCK,Identifier.of(Ashfall.MOD_ID,name),block);

    }
    //Blocks

    public static final MortarNPestleBlock MORTAR_N_PESTLE =
            (MortarNPestleBlock) bRegister(new MortarNPestleBlock(
                    AbstractBlock.Settings.copy(
                            Blocks.STONE
                    ).dynamicBounds().nonOpaque().requiresTool().mapColor(
                            MapColor.STONE_GRAY
                    ).instrument(
                            Instrument.BASEDRUM
                    )
                    ),"mortar_n_pestle",
                    ItemRegistry.UTILITIES
            );
    public static final AccumulatorBlock ACCUMULATOR =
            (AccumulatorBlock) bRegister(new AccumulatorBlock(
                            AbstractBlock.Settings.copy(
                                    Blocks.OBSIDIAN
                            ).requiresTool().mapColor(
                                    MapColor.DEEPSLATE_GRAY
                            ).instrument(
                                    Instrument.IRON_XYLOPHONE
                            ).strength(
                                    50.0F,
                                    1200.0F
                            )
                    ),"accumulator"
                    ,ItemRegistry.ODDITIES
            );


    //BlockEntities
    public static final BlockEntityType MORTAR_N_PESTLE_BLOCK_ENTITY =
            Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(
                            Ashfall.MOD_ID,
                            "mortar_n_pestle_blockentity"),
                    FabricBlockEntityTypeBuilder.create(MortarNPestleBlockEntity::new,MORTAR_N_PESTLE).build()
            );
    public static final BlockEntityType ACCUMULATOR_BLOCK_ENTITY =
            Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                    Identifier.of(
                            Ashfall.MOD_ID,
                            "accumulator_blockentity"),
                    FabricBlockEntityTypeBuilder.create(AccumulatorBlockEntity::new,ACCUMULATOR).build()
            );

    public static void init(){}
}


