package io.github.jadedchara.ashfall.common.block.utility;

import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import io.github.jadedchara.ashfall.common.block.blockentity.MortarNPestleBlockEntity;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MortarNPestleBlock extends BlockWithEntity implements BlockEntityProvider {
    public MortarNPestleBlockEntity entity;
    public MortarNPestleBlock(Settings settings) {
        super(settings);
    }
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        MortarNPestleBlockEntity ent = new MortarNPestleBlockEntity(pos, state);
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
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;

    }
    @Nullable
    @Override
    public ActionResult onUse(BlockState bs, World w, BlockPos bp, PlayerEntity pe, Hand hand, BlockHitResult bhr){
        if (w.isClient) {
            return ActionResult.SUCCESS;
        }else {

            pe.openHandledScreen(bs.createScreenHandlerFactory(w, bp));
            pe.incrementStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            return ActionResult.CONSUME;
        }
    }

    @Nullable
    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }
    @Nullable
    @Override
    public void onStateReplaced(BlockState s, World w, BlockPos p, BlockState ns, boolean b) {
        if (s.getBlock() != ns.getBlock()) {
            BlockEntity blockEntity = w.getBlockEntity(p);
            if (blockEntity instanceof MortarNPestleBlockEntity mnpEntity) {
                ItemScatterer.spawn(w, p,mnpEntity );
                w.updateComparators(p,this);
            }
            super.onStateReplaced(s, w, p, ns, b);
        }
    }
}
