package carminite.mixin;

import carminite.events.hooks.EventHooks;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerList.class)
public class PlayerListMixin {

	@Inject(
		method = "placeNewPlayer(Lnet/minecraft/network/Connection;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/server/network/CommonListenerCookie;)V",
		at = @At("TAIL")
	)
	private void carminite$playerLoggedIn(
		Connection connection,
		ServerPlayer player,
		CommonListenerCookie cookie,
		CallbackInfo ci
	) {
		EventHooks.firePlayerLoggedIn(player);
	}

	@Inject(
		method = "remove(Lnet/minecraft/server/level/ServerPlayer;)V",
		at = @At("HEAD")
	)
	private void carminite$playerLoggedOut(
		ServerPlayer player,
		CallbackInfo ci
	) {
		EventHooks.firePlayerLoggedOut(player);
	}

	@Inject(
		method = "respawn(Lnet/minecraft/server/level/ServerPlayer;ZLnet/minecraft/world/entity/Entity$RemovalReason;)Lnet/minecraft/server/level/ServerPlayer;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerPlayer;setHealth(F)V",
			shift = At.Shift.AFTER
		)
	)
	private void carminite$playerRespawn(
		ServerPlayer serverPlayer,
		boolean keepAllPlayerData,
		Entity.RemovalReason removalReason,
		CallbackInfoReturnable<ServerPlayer> cir
	) {
		EventHooks.firePlayerRespawnEvent(serverPlayer, keepAllPlayerData);
	}
}