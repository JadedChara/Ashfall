package io.github.jadedchara.ashfall.mixin;

import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.effect.ParalysisEffect;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.ai.control.LookControl;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public class ParalysisMixin extends Input {
    @Shadow @Final private GameOptions settings;

    @Inject(method="tick",at=@At("HEAD"),cancellable = true)
    private void preventInput(CallbackInfo ci){
        if (MinecraftClient.getInstance().player.hasStatusEffect(Ashfall.PARALYSIS_EFFECT)){

            ci.cancel();
        }
    }
}
