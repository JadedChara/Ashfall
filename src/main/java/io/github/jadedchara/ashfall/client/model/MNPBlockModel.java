package io.github.jadedchara.ashfall.client.model;

import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.block.blockentity.MortarNPestleBlockEntity;
import net.minecraft.util.Identifier;
//import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public class MNPBlockModel extends DefaultedBlockGeoModel<MortarNPestleBlockEntity> {
    public Identifier MODEL = buildFormattedModelPath(
            new Identifier(
                    Ashfall.MOD_ID,
                    "mortar_n_pestle"
            )
    );
    public Identifier TEXTURE = buildFormattedTexturePath(
            new Identifier(
                    Ashfall.MOD_ID,
                    "mortar_n_pestle"
            )
    );
    public Identifier ANIMATION = buildFormattedAnimationPath(
            new Identifier(
                    Ashfall.MOD_ID,
                    "mortar_n_pestle"
            )
    );

    public MNPBlockModel() {
        super(new Identifier(Ashfall.MOD_ID,"mortar_n_pestle"));
        //this.get
    }

    @Override
    public Identifier getModelResource(MortarNPestleBlockEntity ga) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(MortarNPestleBlockEntity ga) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(MortarNPestleBlockEntity ga) {
        return ANIMATION;
    }
}
