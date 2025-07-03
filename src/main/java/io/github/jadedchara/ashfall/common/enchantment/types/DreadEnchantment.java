package io.github.jadedchara.ashfall.common.enchantment.types;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class DreadEnchantment extends Enchantment {
    public DreadEnchantment() {
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
        if(target instanceof LivingEntity) {
            if(level == 1){
                if(Math.round(Math.random()*10) == 5){
                    ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS,400));
                    ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.UNLUCK,400));
                }
            }else if(level == 2){
                if(Math.round(Math.random()*8) == 5){
                    ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS,400));
                    ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.UNLUCK,400));
                }
            }else if(level == 3){
                if(Math.round(Math.random()*6) == 5){
                    ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS,400));
                    ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.UNLUCK,400));
                }
            }
        }

        super.onTargetDamaged(user, target, level);
    }
}
