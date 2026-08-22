package carminite.mixin;

import carminite.events.hooks.CommonHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Slime.class)
public class SlimeMixin {

	@Inject(
		method = "jumpFromGround()V",
		at = @At("TAIL")
	)
	private void carminite$livingJump(CallbackInfo ci) {
		CommonHooks.onLivingJump((LivingEntity) (Object) this);
	}
}