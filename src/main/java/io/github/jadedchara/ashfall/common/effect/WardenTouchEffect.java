package io.github.jadedchara.ashfall.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class WardenTouchEffect extends StatusEffect {
    public WardenTouchEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x4dacb5);
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {return true;}

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.removeStatusEffect(StatusEffects.DARKNESS);
        entity.removeStatusEffect(StatusEffects.WEAKNESS);
        entity.removeStatusEffect(StatusEffects.MINING_FATIGUE);
    }
}
