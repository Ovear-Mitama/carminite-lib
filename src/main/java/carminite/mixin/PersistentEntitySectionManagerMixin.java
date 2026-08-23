package carminite.mixin;

import carminite.events.neoforge.EntityJoinLevelEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.PersistentEntitySectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PersistentEntitySectionManager.class)
public class PersistentEntitySectionManagerMixin<T extends EntityAccess> {

    @Inject(
        method = "addEntity(Lnet/minecraft/world/level/entity/EntityAccess;Z)Z",
        at = @At("HEAD"),
        cancellable = true
    )
    private void carminite$entityJoinLevel(
        T entity,
        boolean loaded,
        CallbackInfoReturnable<Boolean> cir
    ) {
        if (entity instanceof Entity actualEntity && new EntityJoinLevelEvent(actualEntity, actualEntity.level(), loaded).post().isCanceled()) {
            cir.setReturnValue(false);
        }
    }
}