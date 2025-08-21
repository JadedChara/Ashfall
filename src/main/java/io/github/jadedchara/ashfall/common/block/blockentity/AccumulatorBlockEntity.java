package io.github.jadedchara.ashfall.common.block.blockentity;

import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import io.github.jadedchara.ashfall.common.block.utility.FocusingStoneBlock;
import net.minecraft.block.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class AccumulatorBlockEntity extends BlockEntity {
    public int power = 0;

    public AccumulatorBlockEntity(BlockPos p, BlockState state) {
        super(BlockRegistry.ACCUMULATOR_BLOCK_ENTITY,p, state);
        this.getPos();
        this.markDirty();
    }

    public int defineAbility(World w, BlockPos bp){
        if(
                //Redstone
                   this.getWorld().getBlockState(this.pos.add(1,0,0)).getBlock().equals(Blocks.REDSTONE_BLOCK)
                && this.getWorld().getBlockState(this.pos.add(-1,0,0)).getBlock().equals(Blocks.REDSTONE_BLOCK)
                && this.getWorld().getBlockState(this.pos.add(0,0,1)).getBlock().equals(Blocks.REDSTONE_BLOCK)
                && this.getWorld().getBlockState(this.pos.add(0,0,-1)).getBlock().equals(Blocks.REDSTONE_BLOCK)
                //Focusing Stone
                && this.getWorld().getBlockState(bp).getBlock().equals(BlockRegistry.FOCUSING_STONE_BLOCK)
        ){
            if(((FocusingStoneBlock) this.getWorld().getBlockState(bp).getBlock()).getPower(w,bp) == 0){
                this.getWorld().playSound(
                        (double) this.pos.getX(),
                        (double) this.pos.getY(),
                        (double) this.pos.getZ(),
                        SoundEvents.BLOCK_BEACON_DEACTIVATE,
                        SoundCategory.NEUTRAL,
                        0.5F,
                        0.5F,
                        false
                );
            }
            this.power =
                    ((FocusingStoneBlock) this
                            .getWorld()
                            .getBlockState(bp)
                            .getBlock())
                            .getPower(w,bp);
            this.markDirty();
            //w.updateListeners(bp,this.getCachedState(),this.getCachedState(),0);
            return (((FocusingStoneBlock) this
                    .getWorld()
                    .getBlockState(bp)
                    .getBlock())
                    .getPower(w,bp));
        }else{
            return 0;
        }
    }
    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("power", this.power);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.power = nbt.getInt("power");
    }
}
