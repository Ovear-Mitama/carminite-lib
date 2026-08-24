package carminite.mixin;

import carminite.interfaces.markers.ISpecialPathTypeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WalkNodeEvaluator.class)
public class WalkNodeEvaluatorMixin {

    @Inject(
        method = "getPathTypeFromState(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/pathfinder/PathType;",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void carminite$getPathTypeFromState(
        BlockGetter level,
        BlockPos pos,
        CallbackInfoReturnable<PathType> cir
    ) {
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        if (block instanceof ISpecialPathTypeBlock specialPathTypeBlock) {
            cir.setReturnValue(specialPathTypeBlock.getBlockPathType(state, level, pos, null));
        }
    }
}