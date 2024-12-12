package io.github.jadedchara.ashfall.client.renderer.block;

import io.github.jadedchara.ashfall.client.model.MNPBlockModel;
import io.github.jadedchara.ashfall.common.block.blockentity.MortarNPestleBlockEntity;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MNPBlockEntityRenderer extends GeoBlockRenderer<MortarNPestleBlockEntity> {
    public MNPBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(new MNPBlockModel());


    }
}
