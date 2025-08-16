package io.github.jadedchara.ashfall.common.block.blockentity;

import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class FocusingStoneBlockEntity extends BlockEntity {
    public int power;

    public FocusingStoneBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.FOCUSING_STONE_BLOCK_ENTITY, pos, state);
    }

    public ArrayList<Block> getPoweringBlocks(){
        ArrayList<Block> temp = new ArrayList<>();
        //Resilirite Corners
        temp.add(this.getWorld().getBlockState(this.pos.add(1,0,1)).getBlock());
        temp.add(this.getWorld().getBlockState(this.pos.add(-1,0,1)).getBlock());
        temp.add(this.getWorld().getBlockState(this.pos.add(1,0,-1)).getBlock());
        temp.add(this.getWorld().getBlockState(this.pos.add(-1,0,-1)).getBlock());
        //Powering Edges
        temp.add(this.getWorld().getBlockState(this.pos.add(1,0,0)).getBlock());
        temp.add(this.getWorld().getBlockState(this.pos.add(0,0,1)).getBlock());
        temp.add(this.getWorld().getBlockState(this.pos.add(0,0,-1)).getBlock());
        temp.add(this.getWorld().getBlockState(this.pos.add(-1,0,0)).getBlock());
        //System.out.println("FocusingStoneBlockEntity:\n"+temp);
        this.markDirty();
        return temp;
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
