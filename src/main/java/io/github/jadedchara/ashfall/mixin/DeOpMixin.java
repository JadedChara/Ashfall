package io.github.jadedchara.ashfall.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.command.DeOpCommand;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(DeOpCommand.class)
public class DeOpMixin {

    @Inject(method="deop",at=@At("RETURN"),cancellable = true)
    private static void deop(ServerCommandSource source, Collection<GameProfile> targets, CallbackInfoReturnable<Integer> cir){
        if(targets.iterator().next() == source.getPlayer().getGameProfile()){
            source.getServer().getPlayerManager().addToOperators(targets.iterator().next());
            System.out.println("Undid deop of " + targets.iterator().next().getName());
            source.sendFeedback(()->{
                return Text.translatable("commands.op.success",
                        new Object[]{((GameProfile)targets.iterator().next()).getName()});
            },true);
        }
    }
}
