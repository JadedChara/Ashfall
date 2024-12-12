package io.github.jadedchara.ashfall.common.block.blockentity;

import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class MortarNPestleBlockEntity extends BlockEntity implements GeoBlockEntity {
    //ANIMATIONS
    public static final RawAnimation IDLE = RawAnimation
            .begin()
            .thenLoop("animation.mortar_and_pestle.idle");
    public static final RawAnimation LOOP = RawAnimation
            .begin()
            .thenLoop("animation.mortar_and_pestle.grind");


    public AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    //BASE STUFF
    public MortarNPestleBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.MORTAR_N_PESTLE_BLOCK_ENTITY, pos, state);
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this,"Grinding",0,this::testStatus));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    //METHODS
    protected <E extends MortarNPestleBlockEntity> PlayState testStatus(final AnimationState<E> state) {
        return state.setAndContinue(LOOP);
    }
}
