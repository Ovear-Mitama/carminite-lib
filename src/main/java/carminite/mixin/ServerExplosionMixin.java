package carminite.mixin;

import carminite.events.hooks.EventHooks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ServerExplosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ServerExplosion.class)
public class ServerExplosionMixin {

    @Unique
    private List<BlockPos> carminite$toBlow;

    @Shadow
    @Final
    private ServerLevel level;

    @Inject(
        method = "explode()I",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/ServerExplosion;hurtEntities()V"
        )
    )
    private void carminite$captureBlocks(
        CallbackInfoReturnable<Integer> cir,
        @Local(name = "toBlow") List<BlockPos> toBlow
    ) {
        carminite$toBlow = toBlow;
    }

    @ModifyExpressionValue(
        method = "hurtEntities()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/level/ServerLevel;getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"
        )
    )
    private List<Entity> carminite$onExplosionDetonate(List<Entity> entities) {
        EventHooks.onExplosionDetonate(this.level, (ServerExplosion) (Object) this, entities, carminite$toBlow);
        return entities;
    }

    @Inject(
        method = "hurtEntities()V",
        at = @At("RETURN")
    )
    private void carminite$clearToBlow(CallbackInfo ci) {
        carminite$toBlow = null;
    }
}