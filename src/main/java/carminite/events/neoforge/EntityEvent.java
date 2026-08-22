package carminite.events.neoforge;

import carminite.events.CarminiteEvent;
import net.minecraft.world.entity.Entity;

public abstract class EntityEvent extends CarminiteEvent {
	private final Entity entity;

	public EntityEvent(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}
}