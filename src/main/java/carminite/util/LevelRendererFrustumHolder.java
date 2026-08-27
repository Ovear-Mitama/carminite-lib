package carminite.util;

import net.minecraft.client.renderer.culling.Frustum;
import org.jspecify.annotations.Nullable;

public final class LevelRendererFrustumHolder {
    private static final ThreadLocal<@Nullable Frustum> FRUSTUM = new ThreadLocal<>();

    public static void setFrustum(Frustum frustum) {
        FRUSTUM.set(frustum);
    }

    public static @Nullable Frustum getFrustum() {
        return FRUSTUM.get();
    }

    public static void clear() {
        FRUSTUM.remove();
    }
}