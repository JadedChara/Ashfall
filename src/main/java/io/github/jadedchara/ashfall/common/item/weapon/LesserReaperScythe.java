package io.github.jadedchara.ashfall.common.item.weapon;

import com.google.common.collect.ImmutableMultimap;
import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import io.github.jadedchara.ashfall.common.block.utility.AccumulatorBlock;
import io.github.jadedchara.ashfall.common.block.utility.FocusingStoneBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.DataOutput;
import java.util.Optional;

public class LesserReaperScythe extends SwordItem{
    public int attackDamage;
    public float attackSpeed;
    public int effectDuration;
    public int cooldownDuration;
    public int distance;

    public BlockPos savedLocale;
    public Block boundBlock;

    public LesserReaperScythe(ToolMaterials material, int ad, float as,Item.Settings settings) {
        super(material,ad,as,settings);
        NbtCompound pos = this.getDefaultStack().getOrCreateSubNbt("savedPos");
        try{
            this.savedLocale = new BlockPos(
                    pos.getInt("localX"),
                    pos.getInt("localY"),
                    pos.getInt("localZ")

                    );
        }catch(Exception e){
            this.savedLocale = null;
        }

        this.attackDamage = ad;
        this.attackSpeed = as;
        this.effectDuration = 600;
        this.cooldownDuration = 0;
        this.distance = 10;

    }

    @Override
    public TypedActionResult use(World w, PlayerEntity p, Hand h){

        try{
            if(
                    !(p.getWorld().getBlockState(this.savedLocale).getBlock() instanceof AccumulatorBlock)
                    && !this.savedLocale.isWithinDistance(p.getBlockPos(),distance)
            ){
                this.savedLocale = null;
                this.boundBlock = null;
            }else{
                this.boundBlock = p.getWorld().getBlockState(this.savedLocale).getBlock();
                this.setStatus (((AccumulatorBlock)this.boundBlock).getPower(),p);
                p.getItemCooldownManager().set(this,this.cooldownDuration);
            }
        }catch(Exception e){
            BlockPos testlocale = p.supportingBlockPos.orElse(p.getBlockPos().add(0,-1,0));
            Block testcore = p.getWorld().getBlockState(testlocale).getBlock();
            if(testcore.equals(BlockRegistry.ACCUMULATOR)){
                this.boundBlock = testcore;
                this.savedLocale = testlocale;
                //set status
                ((AccumulatorBlock)this.boundBlock).boundEntity = p;
                this.setStatus (((AccumulatorBlock)this.boundBlock).getPower(),p);
                p.getItemCooldownManager().set(this,this.cooldownDuration);
            }else if(testcore.equals(BlockRegistry.FOCUSING_STONE_BLOCK)){
                this.setStatus (((FocusingStoneBlock)testcore).getPower(),p);
                p.getItemCooldownManager().set(this,this.cooldownDuration);
            }
        }
        try{
            //p.getStackInHand(h).
            p.getStackInHand(h).getOrCreateSubNbt("savedPos").putInt("localX",this.savedLocale.getX());
            p.getStackInHand(h).getOrCreateSubNbt("savedPos").putInt("localY",this.savedLocale.getY());
            p.getStackInHand(h).getOrCreateSubNbt("savedPos").putInt("localZ",this.savedLocale.getZ());
        }catch(Exception e){
            System.out.println("An NBT error has occurred.");
        }
        return TypedActionResult.success(p.getStackInHand(h));
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

    public void setStatus(int n, PlayerEntity p){
        switch (n){
            case 0:
                //Does NOTHING LOL
                break;
            case 1:
                //STRENGTH
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH,this.effectDuration));
                break;
            case 2:
                //INVISIBILITY
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY,this.effectDuration));
                break;
            case 3:
                //JUMP_BOOST
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST,this.effectDuration));
                break;
            case 4:
                //FIRE_RESISTANCE
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE,this.effectDuration));
                break;
            case 5:
                //WARDEN_TOUCH
                p.addStatusEffect(new StatusEffectInstance(Ashfall.WARDEN_TOUCH_EFFECT,this.effectDuration));
                break;
            case 6:
                //SPEED
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED,this.effectDuration));
                break;
            case 7:
                //NIGHT_VISION
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION,this.effectDuration));
                break;
            case 8:
                //REGENERATION
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,this.effectDuration));
                break;
            case 9:
                //WATER_BREATHING
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING,this.effectDuration));
                break;
            case 10:
                //HEALTH_BOOST
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST,this.effectDuration/2));
                break;
            //case 2:
        }
        System.out.println("Power: " + n);
    }

}
