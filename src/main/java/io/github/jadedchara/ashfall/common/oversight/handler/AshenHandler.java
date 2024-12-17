package io.github.jadedchara.ashfall.common.oversight.handler;

import io.github.jadedchara.ashfall.api.oddity.IAshenHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AshenHandler<M> extends ScreenHandler implements IAshenHandler {
    public Inventory inventory;
    public PlayerInventory playerInventory;
    public AshenHandler(
            @Nullable ScreenHandlerType<?> type,
            int d,
            Inventory n,
            PlayerInventory p
    ) {
        super(type, d);
        this.inventory = n;
        this.playerInventory = p;
    }
    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public PlayerInventory getPlayerInventory() {
        return this.playerInventory;
    }

    @Override
    public List<Slot> getSlots() {
        return this.slots;
    }

    public @Nullable Slot getSlotSafely(int i) {
        return i >= 0 && i < this.slots.size() ? this.slots.get(i) : null;
    }

    @Override
    public Slot addSlot(Slot i) {
        return super.addSlot(i);
    }
    @Override
    public ItemStack quickMove(PlayerEntity p,int i){
        return IAshenHandler.super.quickMove(p,i);
    }
    @Override
    public boolean canUse(PlayerEntity p) {
        return IAshenHandler.super.canUse(p);
    }
}
