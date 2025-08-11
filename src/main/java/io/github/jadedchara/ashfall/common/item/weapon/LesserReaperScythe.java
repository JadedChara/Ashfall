package io.github.jadedchara.ashfall.common.item.weapon;

import com.google.common.collect.ImmutableMultimap;
import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LesserReaperScythe extends SwordItem{
    public float attackDamage = 5;
    public LesserReaperScythe(Item.Settings settings) {
        super(ToolMaterials.NETHERITE,5,-3.95F,settings);
    }

    @Override
    public TypedActionResult use(World w, PlayerEntity p, Hand h){
        Block core = p.getWorld().getBlockState(p.supportingBlockPos.get()).getBlock();
        if(core.equals(BlockRegistry.ACCUMULATOR)){

            p.giveItemStack(new ItemStack(Items.DIAMOND));
            p.getItemCooldownManager().set(this,300);
        }
        return TypedActionResult.success(p.getStackInHand(h));
    }

    @Override
    public boolean onClicked(ItemStack s, ItemStack oS, Slot i, ClickType cT, PlayerEntity p, StackReference csR) {
        Block core = p.getWorld().getBlockState(p.supportingBlockPos.get()).getBlock();
        //.use

        if(core.equals(BlockRegistry.ACCUMULATOR)){

            p.giveItemStack(new ItemStack(Items.DIAMOND));
            p.getItemCooldownManager().set(this,300);
        }
            //Add detection for surrounding blocks relative to "core" to define special attributes, and upgrades

        return super.onClicked(s, oS, i, cT, p, csR);
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        target.playSound(SoundEvents.ENTITY_AXOLOTL_SPLASH,0.5F,0.5F);
        attacker.playSound(SoundEvents.BLOCK_BELL_RESONATE,0.5F,0.5F);
        return true;
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isOf(BlockRegistry.ACCUMULATOR);
    }
}
