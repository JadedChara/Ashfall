package io.github.jadedchara.ashfall.mixin;

import io.github.jadedchara.ashfall.common.Ashfall;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Camera.class)
public abstract class PlayerNeckLockMixin {
    @Inject(method = "setRotation", at = @At("HEAD"), cancellable = true)
    public void stopTurn(float yaw, float pitch, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player.hasStatusEffect(Ashfall.PARALYSIS_EFFECT)) {
            ci.cancel();
        }
    }
}