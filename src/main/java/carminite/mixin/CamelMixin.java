package carminite.mixin;

import carminite.events.hooks.CommonHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.camel.Camel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camel.class)
public class CamelMixin {

	@Inject(
		method = "executeRidersJump(FLnet/minecraft/world/phys/Vec3;)V",
		at = @At("TAIL")
	)
	private void carminite$livingJump(CallbackInfo ci) {
		CommonHooks.onLivingJump((LivingEntity) (Object) this);
	}
}