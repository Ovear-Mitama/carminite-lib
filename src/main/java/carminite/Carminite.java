package carminite;

import carminite.datamap.DataMapReloadListener;
import carminite.multipart.IMultiPartEntity;
import carminite.multipart.PartEntity;
import carminite.util.ServerLifecycleHooks;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;

import net.minecraft.server.packs.PackType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Carminite implements ModInitializer {
	public static final String MOD_ID = "carminite-lib";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		ServerLifecycleHooks.init();
		registerServerMultipartEvents();
		registerReloadListeners();
	}

	private static void registerServerMultipartEvents() {
		ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
			if (entity instanceof IMultiPartEntity partEntity && partEntity.isMultipartEntity()) {
				PartEntity<?>[] parts = partEntity.getParts();
				if (parts != null) {
					for (PartEntity<?> part : parts) {
						world.carminite$getPartEntityMap().put(part.getId(), part);
					}
				}
			}
		});
		ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
			if (entity instanceof IMultiPartEntity partEntity && partEntity.isMultipartEntity()) {
				PartEntity<?>[] parts = partEntity.getParts();
				if (parts != null) {
					for (PartEntity<?> part : parts) {
						world.carminite$getPartEntityMap().remove(part.getId());
					}
				}
			}
		});
	}

	private static void registerReloadListeners() {
		ResourceLoader.get(PackType.SERVER_DATA).registerReloadListener(id("data_map"), new DataMapReloadListener());
	}
}