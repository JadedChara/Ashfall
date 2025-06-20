package io.github.jadedchara.ashfall.mixin;

import io.github.jadedchara.ashfall.common.Ashfall;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityNavigation.class)
public class MobLockMixin {
    @Shadow
    @Final
    protected MobEntity entity;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void stopTick(CallbackInfo c) {
        if (entity.hasStatusEffect(Ashfall.PARALYSIS_EFFECT)) {
            c.cancel();
        }
    }
}