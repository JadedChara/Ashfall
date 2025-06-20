package io.github.jadedchara.ashfall.mixin;

import io.github.jadedchara.ashfall.common.Ashfall;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
abstract class InventoryLockMixin{

    @Shadow public abstract boolean equals(KeyBinding other);

    @Shadow private boolean pressed;

    @Inject(method = "isPressed", at = @At("RETURN"), cancellable = true)
    private void StopInventory(CallbackInfoReturnable<Boolean> ci) {
        ci.setReturnValue(checkForEffect(Ashfall.PARALYSIS_EFFECT));
    }
    @Inject(method = "wasPressed", at = @At("RETURN"), cancellable = true)
    private void StopInventoryAlt(CallbackInfoReturnable<Boolean> ci) {
        //ci.setReturnValue(checkForEffect(Ashfall.PARALYSIS_EFFECT));
    }

    public boolean checkForEffect(StatusEffect e){
        try {
            ClientPlayerEntity p = MinecraftClient.getInstance().player;
            int slot = p.getInventory().selectedSlot;
            GameOptions o = MinecraftClient.getInstance().options;
            if(p.hasStatusEffect(e)){

                if(
                        this.equals(o.attackKey)
                                || this.equals(o.inventoryKey)
                                || this.equals(o.hotbarKeys[0])
                                || this.equals(o.hotbarKeys[1])
                                || this.equals(o.hotbarKeys[2])
                                || this.equals(o.hotbarKeys[3])
                                || this.equals(o.hotbarKeys[4])
                                || this.equals(o.hotbarKeys[5])
                                || this.equals(o.hotbarKeys[6])
                                || this.equals(o.hotbarKeys[7])
                                || this.equals(o.hotbarKeys[8])
                                || this.equals(o.dropKey)
                                || this.equals(o.forwardKey)
                                || this.equals(o.attackKey)
                                || this.equals(o.leftKey)
                                || this.equals(o.rightKey)
                                || this.equals(o.jumpKey)
                                || this.equals(o.sneakKey)
                                || this.equals(o.pickItemKey)
                                || this.equals(o.sprintKey)
                                || this.equals(o.swapHandsKey)
                                || this.equals(o.useKey)
                ){
                    if (!MinecraftClient.getInstance().isPaused()){
                        p.closeHandledScreen();
                        p.stopUsingItem();
                        p.stopFallFlying();
                        p.stopRiding();
                        p.getInventory().selectedSlot = slot;
                        return false;
                    }
                }
            }
        }catch (Exception err) {
            //ignore
        }
        return this.pressed;
    }

}
