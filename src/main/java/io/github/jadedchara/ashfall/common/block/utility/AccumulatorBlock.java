package io.github.jadedchara.ashfall.common.block.utility;

import io.github.jadedchara.ashfall.common.block.blockentity.AccumulatorBlockEntity;
import io.github.jadedchara.ashfall.common.block.blockentity.MortarNPestleBlockEntity;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class AccumulatorBlock extends BlockWithEntity implements BlockEntityProvider {
    public AccumulatorBlockEntity entity;
    public BlockPos locale;
    public ArrayList<Block> powerblocks;
    public AccumulatorBlock(Settings settings) {
        super(settings);

    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        this.locale = pos;
        AccumulatorBlockEntity ent = new AccumulatorBlockEntity(pos, state);
        this.entity = ent;
        return ent;
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
            this.entity.defineAbility();
            return ActionResult.CONSUME;
        }
    }
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
