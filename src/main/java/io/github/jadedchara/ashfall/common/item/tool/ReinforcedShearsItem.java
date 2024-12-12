package io.github.jadedchara.ashfall.common.item.tool;


import io.github.jadedchara.ashfall.common.item.ItemRegistry;
import net.minecraft.block.*;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;



public class ReinforcedShearsItem extends ShearsItem {
    //public static Identifier SCULKCATALYSTLOOT = new Identifier("reinforcedshearing/sculk_loot");
    public ReinforcedShearsItem(Settings settings) {
        super(settings);

    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context){
        Block interactor = context.getWorld().getBlockState(context.getBlockPos()).getBlock();

        if(interactor instanceof SculkCatalystBlock) {
            if(!context.getWorld().isClient()){
                context.getPlayer().playSound(SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 0.5F,1F);
            }
            int r = (int) (Math.random()*10)+1;
            if(r==1){
                context.getPlayer().dropItem(new ItemStack(ItemRegistry.HARDENED_SCULK_TENDRILS,1),false);
            }else{
                context.getPlayer().dropItem(new ItemStack(Blocks.SCULK_VEIN,1),false);
            };
            if(!context.getPlayer().isCreative()){
                context.getStack().setDamage(context.getStack().getDamage()+1);
            }
            if(context.getStack().getDamage() == 4 && !context.getPlayer().isCreative()){
                context.getStack().decrement(1);
                if(!context.getWorld().isClient()){
                    context.getPlayer().playSound(SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS,0.5F,1F);
                }
            }
            return ActionResult.SUCCESS;
        }else if(interactor instanceof EndPortalFrameBlock && context.getWorld().getBlockState(context.getBlockPos()).get(Properties.EYE) == true){
            if(!context.getWorld().isClient()){
                context.getPlayer().playSound(SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 0.5F,0.5F);
            }
            context.getPlayer().dropItem(new ItemStack(Items.ENDER_EYE,1),false);
            //context.getPlayer().giveItemStack(new ItemStack(Items.ENDER_EYE, 1));
            BlockPos bp = context.getBlockPos();
            BlockState bs = context.getWorld().getBlockState(bp);
            BlockPattern.Result res =
                    ((EndPortalFrameBlock)bs.getBlock()).getCompletedFramePattern().searchAround(context.getWorld(),
                            bp);
            if (res != null) {
                BlockPos b2 = res.getFrontTopLeft().add(-3, 0, -3);

                for(int i = 0; i < 3; ++i) {
                    for(int j = 0; j < 3; ++j) {
                        context.getWorld().removeBlock(b2.add(i, 0, j), false);
                    }
                }

                //context.getWorld().syncGlobalEvent(1038, b2.add(1, 0, 1), 0);
            }

            context.getWorld().breakBlock(bp,false);
            context.getWorld().setBlockState(bp, bs.with(Properties.EYE, false), 3);


            if(!context.getPlayer().isCreative()){
                context.getStack().setDamage(context.getStack().getDamage()+1);
            }
            if(context.getStack().getDamage() == 4 && !context.getPlayer().isCreative()){
                context.getStack().decrement(1);
                if(!context.getWorld().isClient()){
                    context.getPlayer().playSound(SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS,0.5F,1F);
                }
            }
            return ActionResult.SUCCESS;
        }else{
            return ActionResult.FAIL;
        }
    }

    @Override
    public boolean isDamageable(){
        return true;
    }
}
