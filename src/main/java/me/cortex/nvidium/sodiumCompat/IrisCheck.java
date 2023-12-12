package me.cortex.nvidium.sodiumCompat;

import net.irisshaders.iris.api.v0.IrisApi;
import net.neoforged.fml.loading.FMLLoader;

public class IrisCheck {
    public static final boolean IRIS_LOADED = FMLLoader.getLoadingModList().getModFileById("oculus") != null;

    private static boolean checkIrisShaders() {
        return IrisApi.getInstance().isShaderPackInUse();
    }

    public static boolean checkIrisShouldDisable() {
        return !(IRIS_LOADED && checkIrisShaders());
    }
}
