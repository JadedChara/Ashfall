package io.github.jadedchara.ashfall.mixin;

import io.github.jadedchara.ashfall.common.Ashfall;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class HandLockMixin {
    @Inject(method = "onMouseButton", at = @At("HEAD"), cancellable = true)
    private void StopClick(long w, int b, int a, int m, CallbackInfo ci) {
        halt(ci);
    }

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void StopScroll(long w, double h, double v, CallbackInfo ci) {
        halt(ci);
    }
    @Inject(method = "updateMouse", at = @At("HEAD"), cancellable = true)
    private void StopTurn(CallbackInfo ci) {
        halt(ci);
    }

    public void halt(CallbackInfo c){
        try{
            if (
                    MinecraftClient.getInstance().player.hasStatusEffect(Ashfall.PARALYSIS_EFFECT) &&
                            !MinecraftClient.getInstance().isPaused()
            ) {
                KeyBinding.unpressAll();
                MinecraftClient.getInstance().player.stopFallFlying();
                MinecraftClient.getInstance().player.stopUsingItem();
                MinecraftClient.getInstance().player.stopRiding();
                c.cancel();
            }
        }catch(Exception e){
            //ignore
        }
    }
}
