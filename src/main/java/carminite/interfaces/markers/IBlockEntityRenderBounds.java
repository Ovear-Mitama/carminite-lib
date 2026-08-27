package carminite.interfaces.markers;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

/**
 * Implemented by {@link net.minecraft.client.renderer.blockentity.BlockEntityRenderer}
 * implementations that want shape-based render bounds instead of the plain
 * distance-based {@code shouldRender} check. 26.1 dropped
 * {@code BlockEntityRenderer.getRenderBoundingBox}, so this restores the
 * capability via a redirect in {@code BlockEntityRenderDispatcher}: when the
 * camera is inside the custom bounds the block entity is always rendered,
 * otherwise the default distance check applies.
 */
public interface IBlockEntityRenderBounds {
	AABB getRenderBoundingBox(BlockEntity blockEntity);
}
