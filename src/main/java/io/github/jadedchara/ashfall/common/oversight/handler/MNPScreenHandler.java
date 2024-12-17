package io.github.jadedchara.ashfall.common.oversight.handler;

import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.recipe.MortarRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MNPScreenHandler extends ScreenHandler {
    //public Inventory store = new SimpleInventory(1);
    public Inventory input;
    public Optional<MortarRecipe> currentRecipe;

    //public List<MortarRecipe> recipes;
    public World world;

    public MNPScreenHandler(@Nullable ScreenHandlerType<?> type, int d, Inventory n, PlayerInventory p) {
        super(type, d);
        this.world = p.player.getWorld();
        this.input = n;

        int m;
        int l;
        int j = 9;

        // The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(p, l + m * 9 + 9, 8 + l * 18, 84 + m * 18)).setStack(p.getStack(j));
                j++;
            }
        }
        // The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(p, m, 8 + m * 18, 142)).setStack(p.getStack(m));
        }
        // Our inventory
        this.addSlot(new Slot(input,1,62,53));
        this.addSlot(new Slot(input,0,80,53));
        this.addSlot(new Slot(input,2,98,53));
        this.addSlot(new Slot(input, 3,80,17));

    }

    public MNPScreenHandler(int i, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        this(Ashfall.MNP_SCREEN_HANDLER,i,new SimpleInventory(4){
            @Override
            public int getMaxCountPerStack() {
                return 64;
            }
        },playerInventory);
    }

    public void setResult(ItemStack in){
        this.input.setStack(3,in);
    }

    @Override
    public ItemStack quickMove(PlayerEntity pe,int i){
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        this.currentRecipe = pe.getWorld().getRecipeManager().getFirstMatch(
                Ashfall.MNP_TYPE,
                new SimpleInventory(
                        input.getStack(0),
                        input.getStack(1),
                        input.getStack(2),
                        input.getStack(3)
                ),
                pe.getWorld()
        );
        //System.out.println("Current Recipe:" + this.currentRecipe);

        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (i < this.input.size()) {
                if (!this.insertItem(originalStack, this.input.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.input.size(), false)) {
                return ItemStack.EMPTY;
            }


            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        //this.setResult(this.currentRecipe.orElseThrow().getResult());
        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
       return this.input.canPlayerUse(player);
    }


}
