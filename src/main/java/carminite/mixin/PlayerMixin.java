package carminite.mixin;

import carminite.events.hooks.CommonHooks;
import carminite.events.hooks.EventHooks;
import carminite.interfaces.extensions.IPlayerExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerMixin implements IPlayerExtension {

	@Override
	public boolean carminite$hasCorrectToolForDrops(BlockState state, Level level, BlockPos pos) {
		return EventHooks.doPlayerHarvestCheck((Player) (Object) this, state, level, pos);
	}

	@Inject(
		method = "tick()V",
		at = @At("HEAD")
	)
	private void carminite$playerTickPre(CallbackInfo ci) {
		EventHooks.firePlayerTickPre((Player) (Object) this);
	}

	@Inject(
		method = "tick()V",
		at = @At("TAIL")
	)
	private void carminite$playerTickPost(CallbackInfo ci) {
		EventHooks.firePlayerTickPost((Player) (Object) this);
	}

	@Inject(
		method = "die(Lnet/minecraft/world/damagesource/DamageSource;)V",
		at = @At("HEAD"),
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

	@Inject(
		method = "attack(Lnet/minecraft/world/entity/Entity;)V",
		at = @At("HEAD"),
		cancellable = true
	)
	private void carminite$attackEntity(
		Entity entity,
		CallbackInfo ci
	) {
		if (!CommonHooks.onPlayerAttackTarget((Player) (Object) this, entity)) {
			ci.cancel();
		}
	}
}