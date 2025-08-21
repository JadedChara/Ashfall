package io.github.jadedchara.ashfall.common.block.utility;

import io.github.jadedchara.ashfall.common.block.blockentity.AccumulatorBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;

public class AccumulatorBlock extends BlockWithEntity implements BlockEntityProvider {
    public AccumulatorBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos p, BlockState s) {
        return new AccumulatorBlockEntity(p, s);
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return super.getTicker(world,state,type);
    }



    @Nullable
    @Override
    public ActionResult onUse(BlockState bs, World w, BlockPos bp, PlayerEntity pe, Hand hand, BlockHitResult bhr){
        if (w.isClient) {
            return ActionResult.SUCCESS;
        }else {
            //We're gonna' add a corrupted Accumulator schtick soon enough, maybe with an infection effect. idk
            return ActionResult.CONSUME;
        }
    }

    public AccumulatorBlockEntity getEntity(World w,BlockPos bp){
        if(w.getBlockEntity(bp) instanceof AccumulatorBlockEntity){

            return (AccumulatorBlockEntity) w.getBlockEntity(bp);
        }
        else{
            return null;
        }
    }

    public int getPower(World w, BlockPos bp) {
        //bp = bp.add(0,-1,0);
        return this.getEntity(w,bp).defineAbility(w,bp.add(0,-1,0));

    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

}
