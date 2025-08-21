package io.github.jadedchara.ashfall.cca.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import io.github.jadedchara.ashfall.cca.ComponentInit;
import io.github.jadedchara.ashfall.common.block.blockentity.AccumulatorBlockEntity;
import io.github.jadedchara.ashfall.common.block.blockentity.FocusingStoneBlockEntity;
import io.github.jadedchara.ashfall.common.block.blockentity.MortarNPestleBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;

public class AccumulatorComponent implements Component, AutoSyncedComponent {
    private int value = 0;

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {
        this.value = nbtCompound.getInt("value");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {
        nbtCompound.putInt("value",this.value);
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    public void setValue(ArrayList<Block> val, FocusingStoneBlockEntity provider){
        this.value = 0;
        //provider.setStatus(val);
        ComponentInit.MNP.sync(provider);
    }
    public int getValue(){
        return this.value;
    }
}
