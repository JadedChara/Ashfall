package io.github.jadedchara.ashfall.mixin;

import io.github.jadedchara.ashfall.common.Ashfall;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(PlayerEntity.class)
abstract class InventoryLock extends LivingEntity{
    @Shadow public abstract void stopFallFlying();

    protected InventoryLock(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    //@Shadow

    @Inject(method = "openHandledScreen", at = @At("HEAD"), cancellable = true)
    private void StopInventory(NamedScreenHandlerFactory a, CallbackInfoReturnable<OptionalInt> ci) {
        if (this.hasStatusEffect(Ashfall.PARALYSIS_EFFECT)){
            this.stopFallFlying();
            ci.cancel();
        }
    }
}
