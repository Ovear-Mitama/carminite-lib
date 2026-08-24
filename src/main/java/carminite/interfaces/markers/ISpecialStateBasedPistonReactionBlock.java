package carminite.interfaces.markers;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jspecify.annotations.NonNull;

public interface ISpecialStateBasedPistonReactionBlock {
    @NonNull
    PushReaction getPistonPushReaction(BlockState state);
}