package io.github.jadedchara.ashfall.client.model;

import io.github.jadedchara.ashfall.common.Ashfall;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class MNPBlockModel extends GeoModel {
    public Identifier MODEL = Identifier.of(Ashfall.MOD_ID,"geo/block/mortar_n_pestle.geo.json");
    public Identifier TEXTURE = Identifier.of(Ashfall.MOD_ID,"textures/block/mortar_n_pestle.png");
    public Identifier ANIMATION = Identifier.of(Ashfall.MOD_ID,"animations/block/mortar_n_pestle.animation.json");

    @Override
    public Identifier getModelResource(GeoAnimatable ga) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoAnimatable ga) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(GeoAnimatable ga) {
        return ANIMATION;
    }
}
