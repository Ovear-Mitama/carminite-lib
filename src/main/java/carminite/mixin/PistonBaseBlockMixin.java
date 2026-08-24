package carminite.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PistonBaseBlock.class)
public class PistonBaseBlockMixin {

    @ModifyExpressionValue(
        method = "isPushable(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;ZLnet/minecraft/core/Direction;)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;getPistonPushReaction()Lnet/minecraft/world/level/material/PushReaction;"
        )
    )
    private static PushReaction carminite$modifyPistonPushReaction(
        PushReaction original,
        @Local(argsOnly = true, name = "state") BlockState state
    ) {
        PushReaction newReaction = state.getBlock().carminite$getPistonPushReaction(state);
        if (newReaction != null) {
            return newReaction;
        }

        return original;
    }
}