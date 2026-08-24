package carminite.interfaces.extensions;

import carminite.events.hooks.EventHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathType;
import org.jspecify.annotations.Nullable;

public interface IBlockExtension {
	default boolean carminite$canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
		return EventHooks.doPlayerHarvestCheck(player, state, level, pos);
	}

	default boolean carminite$canEntityDestroy(BlockState state, BlockGetter level, BlockPos pos, Entity entity) {
		if (entity instanceof EnderDragon) {
			return !((Block) this).defaultBlockState().is(BlockTags.DRAGON_IMMUNE);
		} else if ((entity instanceof WitherBoss) ||
			(entity instanceof WitherSkull)) {
			return state.isAir() || WitherBoss.canDestroy(state);
		}

		return true;
	}

	@Nullable
	default PathType carminite$getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
		return state.getBlock() == Blocks.LAVA ? PathType.LAVA : (state.is(Blocks.FIRE) || state.is(Blocks.LAVA)) ? PathType.FIRE : null;
	}

	@Nullable
	default PushReaction carminite$getPistonPushReaction(BlockState state) {
		return null;
	}
}