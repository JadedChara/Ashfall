package io.github.jadedchara.ashfall.common.block.blockentity;

import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import net.minecraft.block.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class AccumulatorBlockEntity extends BlockEntity {
    public ArrayList<Block> powerblocks = new ArrayList<>();

    public AccumulatorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.ACCUMULATOR_BLOCK_ENTITY,pos, state);
    }

    public int defineAbility(){
        /*this.powerblocks = new ArrayList<>();
        for(int i = -1; i<1; i++){
            for(int j = -1; j<1; j++){
                this.powerblocks.add(this.getWorld().getBlockState(pos.add(i,-1,j)).getBlock());
            }
        }
        System.out.print(this.powerblocks);*/

        /*if(
                //Redstone
                   this.getWorld().getBlockState(pos.add(1,0,0)).getBlock() instanceof RedstoneBlock
                && this.getWorld().getBlockState(pos.add(-1,0,0)).getBlock() instanceof RedstoneBlock
                && this.getWorld().getBlockState(pos.add(0,0,1)).getBlock() instanceof RedstoneBlock
                && this.getWorld().getBlockState(pos.add(0,0,-1)).getBlock() instanceof RedstoneBlock
                //Resilirite
                && this.getWorld().getBlockState(pos.add(1,0,0)).getBlock() instanceof BlockRegistry.RESILIRITE_BLOCK
                && this.getWorld().getBlockState(pos.add(-1,0,0)).getBlock() instanceof BlockRegistry.RESILIRITE_BLOCK
                && this.getWorld().getBlockState(pos.add(0,0,1)).getBlock() instanceof BlockRegistry.RESILIRITE_BLOCK
                && this.getWorld().getBlockState(pos.add(0,0,-1)).getBlock() instanceof BlockRegistry.RESILIRITE_BLOCK
                //Focusing Stone
                && this.getWorld().getBlockState(pos.add(0,-1,0)).getBlock() instanceof FocusingStoneBlock
        ){
            if((FocusingStoneBlock) this.getWorld().getBlockState(pos.add(0,-1,0)).getBlock().getPower = 0){
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
            return ((FocusingStoneBlock) this.getWorld().getBlockState(pos.add(0,-1,0)).getBlock().getPower);
        }*/
        //

        return 0;
    }
}
