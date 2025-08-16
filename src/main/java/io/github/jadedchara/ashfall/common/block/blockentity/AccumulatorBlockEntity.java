package io.github.jadedchara.ashfall.common.block.blockentity;

import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import io.github.jadedchara.ashfall.common.block.utility.FocusingStoneBlock;
import net.minecraft.block.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class AccumulatorBlockEntity extends BlockEntity {
    public ArrayList<Block> powerblocks = new ArrayList<>();
    public int power;

    public AccumulatorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.ACCUMULATOR_BLOCK_ENTITY,pos, state);

    }

    public int defineAbility(){
        if(
                //Redstone
                   this.getWorld().getBlockState(pos.add(1,0,0)).getBlock() instanceof RedstoneBlock
                && this.getWorld().getBlockState(pos.add(-1,0,0)).getBlock() instanceof RedstoneBlock
                && this.getWorld().getBlockState(pos.add(0,0,1)).getBlock() instanceof RedstoneBlock
                && this.getWorld().getBlockState(pos.add(0,0,-1)).getBlock() instanceof RedstoneBlock
                //Focusing Stone
                && this.getWorld().getBlockState(pos.add(0,-1,0)).getBlock() instanceof FocusingStoneBlock
        ){
            if(((FocusingStoneBlock) this.getWorld().getBlockState(pos.add(0,-1,0)).getBlock()).getPower() == 0){
                this.getWorld().playSound(
                        (double) pos.getX(),
                        (double) pos.getY(),
                        (double) pos.getZ(),
                        SoundEvents.BLOCK_BEACON_DEACTIVATE,
                        SoundCategory.NEUTRAL,
                        0.5F,
                        0.5F,
                        false
                );
            }else{

            }
            this.power = ((FocusingStoneBlock) this.getWorld().getBlockState(pos.add(0,-1,0)).getBlock()).getPower();
            this.markDirty();
            return (((FocusingStoneBlock) this.getWorld().getBlockState(pos.add(0,-1,0)).getBlock()).getPower());
        }
        //

        return 0;
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
