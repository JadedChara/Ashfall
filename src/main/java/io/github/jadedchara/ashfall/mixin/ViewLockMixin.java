package io.github.jadedchara.ashfall.mixin;

import io.github.jadedchara.ashfall.common.Ashfall;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Entity.class)
public class ViewLockMixin {
    @Inject(method="changeLookDirection", at=@At("HEAD"), cancellable = true)
    public void stopView(double cdX, double cdY, CallbackInfo ci){
        if (this.getClass().isAssignableFrom(LivingEntity.class)) {
            ci.cancel();
        }
    }
}
