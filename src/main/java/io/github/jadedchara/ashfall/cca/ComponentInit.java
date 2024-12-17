package io.github.jadedchara.ashfall.cca;

import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.block.BlockComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import io.github.jadedchara.ashfall.cca.components.MNPComponent;
import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.block.blockentity.MortarNPestleBlockEntity;
import net.minecraft.util.Identifier;

public class ComponentInit implements BlockComponentInitializer {
    public static final ComponentKey<MNPComponent> MNP = ComponentRegistry
            .getOrCreate(
                    Identifier.of(Ashfall.MOD_ID,"mnp"),
                    MNPComponent.class
            );
    @Override
    public void registerBlockComponentFactories(BlockComponentFactoryRegistry bcfRegistry) {
        bcfRegistry.registerFor(MortarNPestleBlockEntity.class,MNP,world -> new MNPComponent());
    }
}
