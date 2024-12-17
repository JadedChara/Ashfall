package io.github.jadedchara.ashfall.common.block.blockentity;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import io.github.jadedchara.ashfall.api.oddity.ImplInventory;
import io.github.jadedchara.ashfall.cca.components.MNPComponent;
import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.block.BlockRegistry;
import io.github.jadedchara.ashfall.common.oversight.handler.MNPScreenHandler;
import io.github.jadedchara.ashfall.common.recipe.MortarRecipe;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class MortarNPestleBlockEntity extends BlockEntity implements GeoBlockEntity, ImplInventory,
        ExtendedScreenHandlerFactory {
    public DefaultedList<ItemStack> inventory = DefaultedList.ofSize(4, ItemStack.EMPTY);
    public PropertyDelegate prop;
    public MortarRecipe currentRecipe;

    //ANIMATIONS
    public static final RawAnimation IDLE = RawAnimation
            .begin()
            .thenLoop("animation.mortar_n_pestle.idle");
    public static final RawAnimation LOOP = RawAnimation
            .begin()
            .thenLoop("animation.mortar_n_pestle.grind");
    public AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);



    //DATA STORAGE
    public static final ComponentKey<MNPComponent> MNP = ComponentRegistry.getOrCreate(
            Identifier.of(Ashfall.MOD_ID, "mnp"),
            MNPComponent.class
    );
    public DefaultedList<ItemStack> items = DefaultedList.ofSize(4,ItemStack.EMPTY);
    public int status = 0;
    public void setStatus(int i) {
        try{
            status = MNP.maybeGet(this).map(MNPComponent::getValue).orElse(0);
        }catch(Exception err){
            this.status = i;
        }
    }



    //BASE STUFF
    public MortarNPestleBlockEntity(BlockPos pos, BlockState state) {
        super(BlockRegistry.MORTAR_N_PESTLE_BLOCK_ENTITY, pos, state);
        this.prop = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return 0;
            }

            @Override
            public void set(int index, int value) {

            }

            @Override
            public int size() {
                return 0;
            }
        };
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
       if(this.status > 0){
           return state.setAndContinue(LOOP);
       }else{
           return state.setAndContinue(IDLE);
       }

    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.items;
    }
    @Override
    public ScreenHandler createMenu(int id, PlayerInventory pi, PlayerEntity p) {
        return new MNPScreenHandler(Ashfall.MNP_SCREEN_HANDLER,id,this,pi);
    }
    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(this.pos);
    }
    @Override
    public void markDirty() {
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
    }

    public  void coreTick(World world, BlockPos pos, BlockState state){
        if(world.isClient()) {
            return;
        }
        Optional<MortarRecipe> test = world.getRecipeManager().getFirstMatch(Ashfall.MNP_TYPE,
                new SimpleInventory(
                        this.getStack(0),
                        this.getStack(1),
                        this.getStack(2),
                        this.getStack(3)
                ),world);
        if(test.isPresent() && !test.equals(Optional.empty()) && this.getStack(3) != ItemStack.EMPTY){
            this.currentRecipe = test.get();
            //System.out.println(this.currentRecipe);
            this.setStack(3,this.currentRecipe.getResult().copy());
        }
    }
    public BlockEntityTicker primeTicker = (
            world,
            pos,
            state,
            blockEntity
    ) -> ((MortarNPestleBlockEntity)blockEntity).coreTick(
            world,
            pos,
            state
    );
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);

    }
    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
