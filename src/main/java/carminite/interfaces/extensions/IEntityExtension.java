package carminite.interfaces.extensions;

import net.minecraft.world.entity.Entity;

/**
 * Recreates NeoForge's {@code IEntityExtension.canRiderInteract} on Fabric,
 * injected onto {@link Entity} via the classtweaker's
 * {@code transitive-inject-interface}.
 */
public interface IEntityExtension {
	default boolean canRiderInteract() {
		return false;
	}
}
