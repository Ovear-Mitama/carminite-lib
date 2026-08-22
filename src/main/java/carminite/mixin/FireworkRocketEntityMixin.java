package carminite.mixin;

import carminite.events.hooks.EventHooks;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin extends Projectile {
	public FireworkRocketEntityMixin(EntityType<? extends Projectile> type, Level level) {
		super(type, level);
	}

	@Override
	protected void onHit(HitResult result) {
		if (result.getType() == HitResult.Type.MISS || !EventHooks.onProjectileImpact(this, result)) {
			super.onHit(result);
		}
	}
}