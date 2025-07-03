package io.github.jadedchara.ashfall.common.enchantment.types;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class InfectiousEnchantment extends Enchantment {
    public InfectiousEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
    @Override
    public int getMinPower(int level) {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(
                target instanceof LivingEntity && Math.round(
                        (Math.random()*(12-(level*2)))
                ) == 2
        ) {
            if(user.hasStatusEffect(StatusEffects.POISON)){
                ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.POISON,400));
            }
            if(user.hasStatusEffect(StatusEffects.HUNGER)){
                ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER,400));
            }
            if(user.hasStatusEffect(StatusEffects.WITHER)){
                ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER,400));
            }
            if(user.hasStatusEffect(StatusEffects.NAUSEA)){
                ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA,400));
            }
            if(user.hasStatusEffect(StatusEffects.MINING_FATIGUE)){
                ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE,400));
            }
        }

        super.onTargetDamaged(user, target, level);
    }
}
