package io.github.jadedchara.ashfall.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class CombustionEffect extends StatusEffect {
    public CombustionEffect() {
        super(StatusEffectCategory.HARMFUL,0xFF9B00);
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {return true;}

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(!entity.isFrozen() && !entity.isTouchingWaterOrRain()){
            entity.setOnFire(true);
        }
    }
}