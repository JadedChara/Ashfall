package io.github.jadedchara.ashfall.common.block.utility;

import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import io.github.jadedchara.ashfall.common.block.blockentity.AccumulatorBlockEntity;
import io.github.jadedchara.ashfall.common.block.blockentity.FocusingStoneBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class FocusingStoneBlock extends BlockWithEntity implements BlockEntityProvider {
    public FocusingStoneBlockEntity entity;
    public BlockPos locale;
    public ArrayList<Block> powerblocks;

    public FocusingStoneBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        this.locale = pos;
        FocusingStoneBlockEntity ent = new FocusingStoneBlockEntity(pos, state);
        this.entity = ent;
        return ent;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return super.getTicker(world, state, type);
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public int getPower(){
        //this.entity.getPoweringBlocks();
        if(
                //Resilirite Corners
                   this.entity.getPoweringBlocks().get(0) == BlockRegistry.RESILIRITE_BLOCK
                && this.entity.getPoweringBlocks().get(1) == BlockRegistry.RESILIRITE_BLOCK
                && this.entity.getPoweringBlocks().get(2) == BlockRegistry.RESILIRITE_BLOCK
                && this.entity.getPoweringBlocks().get(3) == BlockRegistry.RESILIRITE_BLOCK
        ){
            if(
                    //Obsidian Edges - strength
                       this.entity.getPoweringBlocks().get(4) == Blocks.OBSIDIAN
                    && this.entity.getPoweringBlocks().get(5) == Blocks.OBSIDIAN
                    && this.entity.getPoweringBlocks().get(6) == Blocks.OBSIDIAN
                    && this.entity.getPoweringBlocks().get(7) == Blocks.OBSIDIAN
            ){
                return 1;
            }else if(
                    //Glass Edges - invisibility
                       this.entity.getPoweringBlocks().get(4) == Blocks.GLASS
                    && this.entity.getPoweringBlocks().get(5) == Blocks.GLASS
                    && this.entity.getPoweringBlocks().get(6) == Blocks.GLASS
                    && this.entity.getPoweringBlocks().get(7) == Blocks.GLASS
            ){
                return 2;
            }else if(
                    //Slime Edges - jump boost
                       this.entity.getPoweringBlocks().get(4) == Blocks.SLIME_BLOCK
                    && this.entity.getPoweringBlocks().get(5) == Blocks.SLIME_BLOCK
                    && this.entity.getPoweringBlocks().get(6) == Blocks.SLIME_BLOCK
                    && this.entity.getPoweringBlocks().get(7) == Blocks.SLIME_BLOCK
            ){
                return 3;
            }else if(
                //Magma Block Edges - fire resistance
                       this.entity.getPoweringBlocks().get(4) == Blocks.MAGMA_BLOCK
                    && this.entity.getPoweringBlocks().get(5) == Blocks.MAGMA_BLOCK
                    && this.entity.getPoweringBlocks().get(6) == Blocks.MAGMA_BLOCK
                    && this.entity.getPoweringBlocks().get(7) == Blocks.MAGMA_BLOCK
            ){
                return 4;
            }else if(
                //Sculk Edges - warden's touch
                       this.entity.getPoweringBlocks().get(4) == Blocks.SCULK
                    && this.entity.getPoweringBlocks().get(5) == Blocks.SCULK
                    && this.entity.getPoweringBlocks().get(6) == Blocks.SCULK
                    && this.entity.getPoweringBlocks().get(7) == Blocks.SCULK
            ){
                return 5;
            }else if(
                //Copper Edges - speed
                       this.entity.getPoweringBlocks().get(4) == Blocks.COPPER_BLOCK
                    && this.entity.getPoweringBlocks().get(5) == Blocks.COPPER_BLOCK
                    && this.entity.getPoweringBlocks().get(6) == Blocks.COPPER_BLOCK
                    && this.entity.getPoweringBlocks().get(7) == Blocks.COPPER_BLOCK
            ){
                return 6;
            }else if(
                //Froglight Edges - night vision
                       (this.entity.getPoweringBlocks().get(4) == Blocks.OCHRE_FROGLIGHT ||
                               this.entity.getPoweringBlocks().get(4) == Blocks.PEARLESCENT_FROGLIGHT ||
                               this.entity.getPoweringBlocks().get(4)== Blocks.VERDANT_FROGLIGHT)
                    && (this.entity.getPoweringBlocks().get(5) == Blocks.OCHRE_FROGLIGHT ||
                               this.entity.getPoweringBlocks().get(5) == Blocks.PEARLESCENT_FROGLIGHT ||
                               this.entity.getPoweringBlocks().get(5)== Blocks.VERDANT_FROGLIGHT)
                    && (this.entity.getPoweringBlocks().get(6) == Blocks.OCHRE_FROGLIGHT ||
                               this.entity.getPoweringBlocks().get(6) == Blocks.PEARLESCENT_FROGLIGHT ||
                               this.entity.getPoweringBlocks().get(6)== Blocks.VERDANT_FROGLIGHT)
                    && (this.entity.getPoweringBlocks().get(7) == Blocks.OCHRE_FROGLIGHT ||
                               this.entity.getPoweringBlocks().get(7) == Blocks.PEARLESCENT_FROGLIGHT ||
                               this.entity.getPoweringBlocks().get(7)== Blocks.VERDANT_FROGLIGHT)
            ){
                return 7;
            }else if(
                //Moss Edges - regeneration
                    this.entity.getPoweringBlocks().get(4) == Blocks.MOSS_BLOCK
                            && this.entity.getPoweringBlocks().get(5) == Blocks.MOSS_BLOCK
                            && this.entity.getPoweringBlocks().get(6) == Blocks.MOSS_BLOCK
                            && this.entity.getPoweringBlocks().get(7) == Blocks.MOSS_BLOCK
            ){
                return 8;
            }/*else if(
                //Moss Edges
                    this.entity.getPoweringBlocks().get(4) == Blocks.OBSIDIAN
                            && this.entity.getPoweringBlocks().get(5) == Blocks.OBSIDIAN
                            && this.entity.getPoweringBlocks().get(6) == Blocks.OBSIDIAN
                            && this.entity.getPoweringBlocks().get(7) == Blocks.OBSIDIAN
            ){
                return 9;
            }*/else{
                return 0;
            }
        }else{
            return 0;
        }

        //return 0;
    }
}
