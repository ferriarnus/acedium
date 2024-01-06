package me.cortex.nvidium.mixin.sodium;

import me.cortex.nvidium.Nvidium;
import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SodiumGameOptionPages.class)
public class MixinEmbeddiumGameOptionPages {

    @Redirect(at = @At(value = "INVOKE", target = "Lme/jellysquid/mods/sodium/client/gui/options/OptionImpl$Builder;build()Lme/jellysquid/mods/sodium/client/gui/options/OptionImpl;",
            ordinal = 2), method = "advanced", remap = false)
    private static OptionImpl faceculling(OptionImpl.Builder builder) {
        builder.setEnabled(!Nvidium.IS_ENABLED);
        if (Nvidium.IS_ENABLED)
            SodiumClientMod.options().performance.useTranslucentFaceSorting = false;
        return builder.build();
    }
}
