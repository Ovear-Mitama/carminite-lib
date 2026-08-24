package carminite.interfaces.extensions;

import carminite.interfaces.markers.ISpecialStickyBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import org.jspecify.annotations.Nullable;

public interface IBlockStateExtension {
	default boolean carminite$isStickyBlock() {
		Block block = ((BlockState) this).getBlock();
		if (block instanceof ISpecialStickyBlock specialStickyBlock)
			return specialStickyBlock.isStickyBlock((BlockState) this);
		return block == Blocks.SLIME_BLOCK || block == Blocks.HONEY_BLOCK;
	}

	default boolean carminite$canStickTo(BlockState other) {
		Block block = ((BlockState) this).getBlock();
		if (block instanceof ISpecialStickyBlock stickTo)
			return stickTo.canStickTo((BlockState) this, other);
		if (block == Blocks.HONEY_BLOCK && other.getBlock() == Blocks.SLIME_BLOCK) return false;
		if (block == Blocks.SLIME_BLOCK && other.getBlock() == Blocks.HONEY_BLOCK) return false;
		return carminite$isStickyBlock() || other.carminite$isStickyBlock();
	}

	default boolean carminite$canEntityDestroy(BlockGetter level, BlockPos pos, Entity entity) {
		return ((BlockState) this).getBlock().carminite$canEntityDestroy(((BlockState) this), level, pos, entity);
	}

	@Nullable
	default PathType carminite$getBlockPathType(BlockGetter level, BlockPos pos, @Nullable Mob mob) {
		return ((BlockState) this).getBlock().carminite$getBlockPathType(((BlockState) this), level, pos, mob);
	}
}