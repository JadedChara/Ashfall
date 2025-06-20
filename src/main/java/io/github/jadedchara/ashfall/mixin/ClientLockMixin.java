package io.github.jadedchara.ashfall.mixin;

import io.github.jadedchara.ashfall.common.Ashfall;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MinecraftClient.class)
public abstract class ClientLockMixin {

    @Shadow
    public static MinecraftClient getInstance() {
        return null;
    }

    @ModifyVariable(method="handleInputEvents",at=@At(value= "FIELD",shift=At.Shift.BEFORE,target="Lnet/minecraft/entity/player/PlayerInventory;selectedSlot:I"))
    public int surpressHotbar(int fixed){
        try{
            if(this.getInstance().player.hasStatusEffect(Ashfall.PARALYSIS_EFFECT) && !this.getInstance().isPaused()){
                fixed = this.getInstance().player.getInventory().selectedSlot;
            }
        }catch(Exception e){
            //ignore
        }
        return fixed;
    }

}
