package io.github.jadedchara.ashfall.common.item.weapon;

import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import io.github.jadedchara.ashfall.common.block.utility.AccumulatorBlock;
import io.github.jadedchara.ashfall.common.block.utility.FocusingStoneBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LesserReaperScythe extends SwordItem{
    public int attackDamage;
    public float attackSpeed;
    public int effectDuration;
    public int cooldownDuration;
    public int distance;


    public LesserReaperScythe(ToolMaterials material, int ad, float as,Item.Settings settings) {
        super(material,ad,as,settings);
        this.getDefaultStack().getOrCreateSubNbt("savedPos");
        this.attackDamage = ad;
        this.attackSpeed = as;
        this.effectDuration = 600;
        this.cooldownDuration = 0;
        this.distance = 10;

    }

    @Override
    public TypedActionResult<ItemStack> use(World w, PlayerEntity p, Hand h){
        //if(Thread.currentThread().getName().equals("Render thread")){
            //return TypedActionResult.success(p.getStackInHand(h));
        //}
        NbtCompound pos = p.getStackInHand(h).getOrCreateSubNbt("savedPos");
        BlockPos tp;
        Block tb;
        try{
            if(pos.isEmpty()){
                tp = p.supportingBlockPos.get();
                tb = p.getWorld().getBlockState(tp).getBlock();
                if(tb instanceof AccumulatorBlock){
                    this.setStatus(((AccumulatorBlock) tb).getPower(w,tp),p);
                    pos.putInt("localX",tp.getX());
                    pos.putInt("localY",tp.getY());
                    pos.putInt("localZ",tp.getZ());
                    p.getItemCooldownManager().set(this,this.cooldownDuration);
                }else if(tb instanceof FocusingStoneBlock){
                    this.setStatus(((FocusingStoneBlock) tb).getPower(w,tp),p);
                    pos.putInt("localX",tp.getX());
                    pos.putInt("localY",tp.getY());
                    pos.putInt("localZ",tp.getZ());
                    p.getItemCooldownManager().set(this,this.cooldownDuration);
                }
            }else{
                tp = new BlockPos(
                        pos.getInt("localX"),
                        pos.getInt("localY"),
                        pos.getInt("localZ")
                );
                tb = p.getWorld().getBlockState(tp).getBlock();
                System.out.println(tb+"\n"+tp);
                if(tp.isWithinDistance(p.getBlockPos(),this.distance)){
                    if(tb instanceof AccumulatorBlock){
                        this.setStatus(((AccumulatorBlock) tb).getPower(w,tp),p);
                        pos.putInt("localX",tp.getX());
                        pos.putInt("localY",tp.getY());
                        pos.putInt("localZ",tp.getZ());
                        p.getItemCooldownManager().set(this,this.cooldownDuration);
                    }else if(tb instanceof FocusingStoneBlock){
                        this.setStatus(((FocusingStoneBlock) tb).getPower(w,tp),p);
                        pos.putInt("localX",tp.getX());
                        pos.putInt("localY",tp.getY());
                        pos.putInt("localZ",tp.getZ());
                        p.getItemCooldownManager().set(this,this.cooldownDuration);

                    }else{
                        pos.remove("localX");
                        pos.remove("localY");
                        pos.remove("localZ");
                    }
                }
            }
        }catch(Exception e){
            System.out.println("A Reaper's Scythe has encountered an error:\n"+e);
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
        //System.out.println("Power: " + n);
    }

}
