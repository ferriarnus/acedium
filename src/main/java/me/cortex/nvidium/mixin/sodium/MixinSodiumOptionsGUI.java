package me.cortex.nvidium.mixin.sodium;

import me.cortex.nvidium.NvidiumWorldRenderer;
import me.cortex.nvidium.sodiumCompat.INvidiumWorldRendererGetter;
import me.cortex.nvidium.sodiumCompat.NvidiumOptionFlags;
import net.minecraft.client.MinecraftClient;
import org.embeddedt.embeddium.api.options.structure.OptionFlag;
import org.embeddedt.embeddium.api.options.structure.OptionStorage;
import org.embeddedt.embeddium.impl.gui.EmbeddiumVideoOptionsScreen;
import org.embeddedt.embeddium.impl.render.EmbeddiumWorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.*;

@Mixin(value = EmbeddiumVideoOptionsScreen.class, remap = false)
public class MixinSodiumOptionsGUI {

    @Inject(method = "applyChanges", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void applyShaderReload(CallbackInfo ci, HashSet<OptionStorage<?>> dirtyStorages, EnumSet<OptionFlag> flags, MinecraftClient client) {
        if (client.world != null && flags.contains(NvidiumOptionFlags.REQUIRES_SHADER_RELOAD)) {
            EmbeddiumWorldRenderer swr = EmbeddiumWorldRenderer.instanceNullable();
            if (swr != null) {
                NvidiumWorldRenderer pipeline = ((INvidiumWorldRendererGetter)((SodiumWorldRendererAccessor)swr).getRenderSectionManager()).getRenderer();
                if (pipeline != null)
                    pipeline.reloadShaders();
            }
        }
    }
}
