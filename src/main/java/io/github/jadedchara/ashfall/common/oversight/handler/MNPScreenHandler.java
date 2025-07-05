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
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.collection.DefaultedList;
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
        public MortarInputInventory(ScreenHandler handler) {
            super(handler, 3, 1);
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
    public MortarInputInventory input;
    public CraftingResultInventory output = new CraftingResultInventory();
    public PlayerEntity player;
    public int xp;
    public World world;
    public ScreenHandlerContext context;

    public MNPScreenHandler(@Nullable ScreenHandlerType<?> type, int d, Inventory n, PlayerInventory p,BlockPos bp) {
        super(type,d);
        this.player = p.player;
        this.world = p.player.getWorld();
        this.xp = 0;
        this.input = new MortarInputInventory(this);
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
        this.addSlot(new CraftingResultSlot(this.player,this.input,this.output, 3,80,17){
          @Override
          public void onTakeItem(PlayerEntity player, ItemStack stack) {
              this.onCrafted(stack);

              DefaultedList<ItemStack> defaultedList =
                      player.getWorld().getRecipeManager().getRemainingStacks(
                              MortarRecipe.Type.INSTANCE,
                              MNPScreenHandler.this.input,
                              player.getWorld()
                      );

              for(int i = 0; i < defaultedList.size(); ++i) {
                  ItemStack itemStack = MNPScreenHandler.this.input.getStack(i);
                  ItemStack itemStack2 = defaultedList.get(i);
                  if (!itemStack.isEmpty()) {
                      MNPScreenHandler.this.input.removeStack(i, 1);
                      itemStack = MNPScreenHandler.this.input.getStack(i);

                  }

                  if (!itemStack2.isEmpty()) {
                      if (itemStack.isEmpty()) {
                          MNPScreenHandler.this.input.setStack(i, itemStack2);
                      } else if (ItemStack.canCombine(itemStack, itemStack2)) {
                          itemStack2.increment(itemStack.getCount());
                          MNPScreenHandler.this.input.setStack(i, itemStack2);
                      } else if (!MNPScreenHandler.this.player.getInventory().insertStack(itemStack2)) {
                          MNPScreenHandler.this.player.dropItem(itemStack2, false);
                      }
                  }
              }
              player.getWorld().playSound(
                      bp.getX(),
                      bp.getY(),
                      bp.getZ(),
                      SoundEvents.BLOCK_GRINDSTONE_USE,
                      SoundCategory.NEUTRAL,
                      0.5F,
                      0.5F,
                      false
              );
              player.addExperience(MNPScreenHandler.this.xp);
              MNPScreenHandler.this.xp = 0;

          }
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

        ItemStack itemStack = ItemStack.EMPTY;
        Slot storedslot = this.slots.get(invSlot);
        if (storedslot.hasStack()) {
            ItemStack itemStack2 = storedslot.getStack();
            itemStack = itemStack2.copy();
            if (invSlot == 0) {
                this.context.run((world, pos) -> itemStack2.getItem().onCraft(itemStack2, world, player));
                if (!this.insertItem(itemStack2, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                storedslot.onQuickTransfer(itemStack2, itemStack);
            } else if (invSlot >= 3 && invSlot < 39) {
                if (!this.insertItem(itemStack2, 1, 3, false)) {
                    if (invSlot < 30) {
                        if (!this.insertItem(itemStack2, 30, 39, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.insertItem(itemStack2, 3, 30, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 10, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                storedslot.setStack(ItemStack.EMPTY);
            } else {
                storedslot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            storedslot.onTakeItem(player, itemStack2);
            if (invSlot == 0) {
                player.dropItem(itemStack2, false);
            }
        }

        return itemStack;
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
                MortarRecipe craftingRecipe = optional.get();
                //System.out.println(craftingRecipe.output);
                if (r.shouldCraftRecipe(w, serverPlayerEntity, craftingRecipe)) {
                    ItemStack itemStack2 = craftingRecipe.craft(i, w.getRegistryManager());
                    if (itemStack2.isItemEnabled(w.getEnabledFeatures())) {
                        itemStack = itemStack2;
                        this.xp = craftingRecipe.experience;
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


    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.dropInventory(this.player, this.input);
    }
}
