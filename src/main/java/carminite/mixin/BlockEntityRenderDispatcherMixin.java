package carminite.mixin;

import carminite.interfaces.markers.IBlockEntityRenderBounds;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockEntityRenderDispatcher.class)
public class BlockEntityRenderDispatcherMixin {

	@Redirect(
		method = "tryExtractRenderState",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;shouldRender(Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/phys/Vec3;)Z")
	)
	private boolean carminite$customShouldRender(BlockEntityRenderer renderer, BlockEntity blockEntity, Vec3 cameraPos) {
		if (renderer instanceof IBlockEntityRenderBounds bounds) {
			AABB box = bounds.getRenderBoundingBox(blockEntity);
			if (box != null && box.inflate(1.0).contains(cameraPos)) {
				return true;
			}
		}
		return renderer.shouldRender(blockEntity, cameraPos);
	}
}
