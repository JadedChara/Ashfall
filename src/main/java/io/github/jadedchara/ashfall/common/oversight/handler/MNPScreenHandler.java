package io.github.jadedchara.ashfall.common.oversight.handler;

import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.recipe.MortarRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class MNPScreenHandler extends AbstractRecipeScreenHandler<RecipeInputInventory> {

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        this.input.provideRecipeInputs(finder);
    }
    @Override
    public void clearCraftingSlots() {
        this.input.clear();
        this.output.clear();
    }

    @Override
    public boolean matches(Recipe<? super RecipeInputInventory> recipe) {
        return recipe.matches(this.input, this.world);
    }
    @Override
    public int getCraftingResultSlotIndex() {
        return 3;
    }
    @Override
    public int getCraftingWidth() {
        return 3;
    }
    @Override
    public int getCraftingHeight() {
        return 1;
    }
    @Override
    public int getCraftingSlotCount() {
        return 3;
    }
    @Override
    public RecipeBookCategory getCategory() {
        return null;
    }
    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != 3;
    }

    //Defining
    public class MortarInputInventory extends CraftingInventory implements RecipeInputInventory{
        public MortarInputInventory(ScreenHandler handler, int width, int height) {
            super(handler, width, height);
        }

        @Override
        public int getWidth() {return 3;}
        @Override
        public int getHeight() {return 1;}
        @Override
        public List<ItemStack> getInputStacks() {return super.getInputStacks();}
        @Override
        public int size() {return 3;}
        @Override
        public boolean isEmpty() {return super.isEmpty();}
        @Override
        public ItemStack getStack(int slot) {return super.getStack(slot);}
        @Override
        public ItemStack removeStack(int slot, int amount) {
            //super.getStack(slot).decrement(amount);
            return super.removeStack(slot,amount);
        }
        @Override
        public ItemStack removeStack(int slot) {
            //super.setStack(slot, super.getStack(slot).copyAndEmpty());
            //return super.getStack(slot);
            return super.removeStack(slot);
        }
        @Override
        public void setStack(int slot, ItemStack stack) {
            super.setStack(slot,stack);
        }
        @Override
        public void markDirty() {super.markDirty();}
        @Override
        public boolean canPlayerUse(PlayerEntity player) {return true;}
        @Override
        public void provideRecipeInputs(RecipeMatcher finder) {}
        @Override
        public void clear() {super.clear();}
    }
    public MortarInputInventory input = new MortarInputInventory(this,3,1);
    public CraftingResultInventory output = new CraftingResultInventory();
    public PlayerEntity player;

    public World world;
    public ScreenHandlerContext context;

    public MNPScreenHandler(@Nullable ScreenHandlerType<?> type, int d, Inventory n, PlayerInventory p,BlockPos bp) {
        super(type,d);
        this.player = p.player;
        this.world = p.player.getWorld();
        this.input = new MortarInputInventory(this,3,1);
        this.input.setStack(0,n.getStack(0));
        this.input.setStack(1,n.getStack(1));
        this.input.setStack(2,n.getStack(2));
        this.context = ScreenHandlerContext.create(this.world,bp);
        //this.output = new CraftingResultInventory();

        //36 vanilla slots (27 main, 9 hotbar)
        //
        //
        //

        // The player inventory
        for (int m = 0; m < 3; m++) {
            for (int l = 0; l < 9; l++) {
                int slotNumber = 9 + m * 9 + l;
                this.addSlot(new Slot(p, slotNumber,  8 + l * 18, 84 + m * 18));
            }
        }

        // The player Hotbar
        for (int m = 0; m < 9; ++m) {
            this.addSlot(new Slot(p, m, 8 + m * 18, 142)).setStack(p.getStack(m));
        }
        // Our inventory
        this.addSlot(new Slot(this.input,1,62,53));
        this.addSlot(new Slot(this.input,0,80,53));
        this.addSlot(new Slot(this.input,2,98,53));
        this.addSlot(new Slot(this.output, 3,80,17){
            @Override
            public boolean canInsert(ItemStack it){return false;}
            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                super.onTakeItem(player, stack);
                MNPScreenHandler.this.onTakeOutput(player,stack);
            }
            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {return true;}
        });
        //System.out.println(this.slots.size());
        this.setPreviousTrackedSlotMutable(3,ItemStack.EMPTY);
    }

    public MNPScreenHandler(int i, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        this(Ashfall.MNP_SCREEN_HANDLER,i,new SimpleInventory(3){
            @Override
            public int getMaxCountPerStack() {return 64;}
        },playerInventory,packetByteBuf.readBlockPos());
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {

        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.input.size()) {
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
        this.updateResult(this.player,this.world,this.input,this.output);
        return newStack;
        
    }

    @Override
    public boolean canUse(PlayerEntity player) {return this.input.canPlayerUse(player);}

    public void updateResult(PlayerEntity p, World w, CraftingInventory i, CraftingResultInventory r) {
        if (!w.isClient) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)p;
            ItemStack itemStack = ItemStack.EMPTY;
            Optional<MortarRecipe> optional =
                    w.getServer().getRecipeManager().getFirstMatch(MortarRecipe.Type.INSTANCE, i, w);
            if (optional.isPresent()) {
                MortarRecipe craftingRecipe = (MortarRecipe) optional.get();
                System.out.println(craftingRecipe.output);
                if (r.shouldCraftRecipe(w, serverPlayerEntity, craftingRecipe)) {
                    ItemStack itemStack2 = craftingRecipe.craft(i, w.getRegistryManager());
                    if (itemStack2.isItemEnabled(w.getEnabledFeatures())) {
                        itemStack = itemStack2;
                        System.out.println(itemStack2);
                    }
                }
            }

            r.setStack(0, itemStack);
            this.setPreviousTrackedSlotMutable(3, itemStack);
            serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(
                    this.syncId,
                    this.nextRevision(),
                    3,
                    itemStack));
        }
    }
    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if(this.context != null){
            this.context.run((world, pos) -> {
                if (inventory == this.input) {
                    updateResult(this.player,this.world, this.input, this.output);

                }});
        }
    }

    protected void onTakeOutput(PlayerEntity p, ItemStack stack) {
        stack.onCraft(player.getWorld(), player, stack.getCount());
        this.updateResult(p,this.world,this.input, this.output);
        //this.output.unlockLastRecipe(player);
        this.input.getStack(0).decrement(stack.getCount());
        this.input.getStack(1).decrement(stack.getCount());
        this.input.getStack(2).decrement(stack.getCount());

        //this.input.removeStack(0,stack.getCount());
        //this.input.removeStack(1,stack.getCount());
        //this.input.removeStack(2,stack.getCount());
    }
    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.dropInventory(this.player, this.input);
    }
}
