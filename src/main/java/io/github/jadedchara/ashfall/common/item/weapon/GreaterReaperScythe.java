package io.github.jadedchara.ashfall.common.item.weapon;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterials;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class GreaterReaperScythe extends LesserReaperScythe{
    //public int attackDamage;
    //public float attackSpeed;
    //public int effectDuration;
    //public int cooldownDuration;

    //public BlockPos savedLocale;
    //public Block boundBlock;

    public GreaterReaperScythe(ToolMaterials material,int ad,float as,Settings settings) {
        super(material,ad,as,settings);
        NbtCompound tx = this.getDefaultStack().getOrCreateSubNbt("localX");
        NbtCompound ty = this.getDefaultStack().getOrCreateSubNbt("localY");
        NbtCompound tz = this.getDefaultStack().getOrCreateSubNbt("localZ");
        try{
            this.savedLocale = new BlockPos(
                    tx.getInt("localX"),
                    ty.getInt("localY"),
                    tz.getInt("localZ")

            );
        }catch(Exception e){
            //
        }
        this.attackDamage = ad;
        this.attackSpeed = as;
        this.effectDuration = 1200;
        this.cooldownDuration = 0;
        this.distance = 20;
    }
}
