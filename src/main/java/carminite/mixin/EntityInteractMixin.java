package carminite.mixin;

import carminite.interfaces.extensions.IEntityExtension;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityInteractMixin {

	@Inject(
		method = "interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/InteractionResult;",
		at = @At("HEAD"),
		cancellable = true
	)
	private void carminite$allowRiderInteract(Player player, InteractionHand hand, Vec3 pos, CallbackInfoReturnable<InteractionResult> cir) {
		Entity self = (Entity) (Object) this;
		if (self.isVehicle() && self.hasPassenger(player) && ((IEntityExtension) (Object) this).canRiderInteract()) {
			cir.setReturnValue(self.level().isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER);
		}
	}
}
