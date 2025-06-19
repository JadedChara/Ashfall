package io.github.jadedchara.ashfall.common.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class ParalysisEffect extends StatusEffect {
    public ParalysisEffect() {
        super(StatusEffectCategory.NEUTRAL, 0xa1a1a1);
    }
    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {return true;}

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            entity.setMovementSpeed(0);
            entity.setJumping(false);
        }
    }
}
