package carminite.mixin;

import carminite.events.hooks.CommonHooks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {

	@Inject(
		method = "die(Lnet/minecraft/world/damagesource/DamageSource;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerPlayer;gameEvent(Lnet/minecraft/core/Holder;)V",
			shift = At.Shift.AFTER
		),
		cancellable = true
	)
	private void carminite$livingDeath(
		DamageSource source,
		CallbackInfo ci
	) {
		if (CommonHooks.onLivingDeath((LivingEntity) (Object) this, source)) {
			ci.cancel();
		}
	}
}