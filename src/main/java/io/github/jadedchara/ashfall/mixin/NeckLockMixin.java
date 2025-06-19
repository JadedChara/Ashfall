package io.github.jadedchara.ashfall.mixin;

import io.github.jadedchara.ashfall.common.Ashfall;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LookControl.class)
public class NeckLockMixin {
    @Shadow @Final protected MobEntity entity;

    @Inject(method="lookAt(DDDFF)V",at=@At("HEAD"),cancellable = true)
    public void stopLook(double x, double y, double z, float maxYawChange, float maxPitchChange, CallbackInfo ci){
        if(this.entity.hasStatusEffect(Ashfall.PARALYSIS_EFFECT)){
            this.entity.stopUsingItem();
            ci.cancel();
        }
    }
}
