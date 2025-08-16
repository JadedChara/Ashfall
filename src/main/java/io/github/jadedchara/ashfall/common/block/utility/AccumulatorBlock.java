package io.github.jadedchara.ashfall.common.block.utility;

import io.github.jadedchara.ashfall.common.block.blockentity.AccumulatorBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class AccumulatorBlock extends BlockWithEntity implements BlockEntityProvider {
    public AccumulatorBlockEntity entity;
    public int power; //for defining effects
    public int variant; //for other item interactions
    public LivingEntity boundEntity; //for remote functions
    public BlockPos locale;
    public AccumulatorBlock(Settings settings) {
        super(settings);

    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        this.locale = pos;
        AccumulatorBlockEntity ent = new AccumulatorBlockEntity(pos, state);
        this.entity = ent;
        //this.power = this.entity.defineAbility();
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
            //We're gonna' add a corrupted Accumulator schtick soon enough, maybe with an infection effect. idk
            return ActionResult.CONSUME;
        }
    }

    public int getPower() {
        this.power = this.entity.defineAbility();

        //System.out.println("AccumulatorBlock: " + this.power);
        return this.power;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
