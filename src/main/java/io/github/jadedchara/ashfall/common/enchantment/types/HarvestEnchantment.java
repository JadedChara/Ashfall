package io.github.jadedchara.ashfall.common.enchantment.types;

import io.github.jadedchara.ashfall.common.oversight.accessor.LivingEntityAccessor;
import io.github.jadedchara.ashfall.mixin.LivingEntityMixin;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class HarvestEnchantment extends Enchantment {
    public HarvestEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
    @Override
    public int getMinPower(int level) {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(target instanceof LivingEntity) {
            user.setHealth(
                    user.getHealth() + (

                            ((LivingEntityAccessor)target).getFilteredDamage() * 0.1F * level
                    )
            );
        }

        super.onTargetDamaged(user, target, level);
    }
}
