package io.github.jadedchara.ashfall.mixin;

import io.github.jadedchara.ashfall.common.oversight.accessor.LivingEntityAccessor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements LivingEntityAccessor {

    @Inject(method="applyDamage",at=@At("TAIL"),cancellable = true)
    public void appliedDamage(DamageSource source, float amount, CallbackInfo ci){

        this.damageAmount = amount;
    };

    public float damageAmount = 0.0F;
    @Override
    public float getFilteredDamage(){
        return this.damageAmount;
    }





}
