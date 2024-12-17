package io.github.jadedchara.ashfall.api.oddity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.List;

public interface IAshenHandler {
    public abstract Inventory getInventory();

    public abstract PlayerInventory getPlayerInventory();

    public abstract List<Slot> getSlots();

    public abstract Slot addSlot(Slot slot);
    public default ItemStack quickMove(PlayerEntity player, int slotIndex) {
        Slot slot = this.getSlots().get(slotIndex);
        ItemStack stack = slot.getStack();
        if (!stack.isEmpty()) {
            slot.onTakeItem(player, stack);
        }
        return ItemStack.EMPTY;
    }
    public default boolean canUse(PlayerEntity player) {
        return this.getInventory().canPlayerUse(player);
    }
}
