package carminite.interfaces.markers;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;

public interface IExtendedBoundingBoxBlockEntityRenderer {
	AABB getRenderBoundingBox(BlockEntity blockEntity);
}