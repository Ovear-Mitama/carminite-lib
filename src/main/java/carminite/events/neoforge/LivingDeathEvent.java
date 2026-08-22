package carminite.events.neoforge;

import carminite.events.CarminiteEvents;
import carminite.events.ICancellableEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class LivingDeathEvent extends LivingEvent implements ICancellableEvent {
	private final DamageSource source;

	public LivingDeathEvent(LivingEntity entity, DamageSource source) {
		super(entity);
		this.source = source;
	}

	public DamageSource getSource() {
		return source;
	}

	@Override
	public LivingDeathEvent post() {
		CarminiteEvents.LIVING_DEATH.invoker().onLivingDeath(this);
		return this;
	}
}
