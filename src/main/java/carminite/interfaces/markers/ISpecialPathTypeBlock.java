package carminite.interfaces.markers;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import org.jspecify.annotations.Nullable;

public interface ISpecialPathTypeBlock {
    @Nullable
    default PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return state.getBlock() == Blocks.LAVA ? PathType.LAVA : (state.is(Blocks.FIRE) || state.is(Blocks.LAVA)) ? PathType.FIRE : null;
    }
}