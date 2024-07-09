package me.cortex.nvidium.mixin.sodium;

import me.cortex.nvidium.Nvidium;
import org.embeddedt.embeddium.api.options.structure.OptionImpl;
import org.embeddedt.embeddium.impl.Embeddium;
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
            Embeddium.options().performance.useTranslucentFaceSorting = false;
        return builder.build();
    }
}
