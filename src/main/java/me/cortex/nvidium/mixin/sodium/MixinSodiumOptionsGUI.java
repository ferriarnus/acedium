package me.cortex.nvidium.mixin.sodium;

import me.cortex.nvidium.NvidiumWorldRenderer;
import me.cortex.nvidium.config.ConfigGuiBuilder;
import me.cortex.nvidium.sodiumCompat.INvidiumWorldRendererGetter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.embeddedt.embeddium.api.options.structure.OptionFlag;
import org.embeddedt.embeddium.api.options.structure.OptionPage;
import org.embeddedt.embeddium.api.options.structure.OptionStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.*;

@Mixin(value = SodiumOptionsGUI.class, remap = false)
public class MixinSodiumOptionsGUI {
    @Shadow @Final private List<OptionPage> pages;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void addNvidiumOptions(Screen prevScreen, CallbackInfo ci) {
        //ConfigGuiBuilder.addNvidiumGui(pages);
    }

    @Inject(method = "applyChanges", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void applyShaderReload(CallbackInfo ci, HashSet<OptionStorage<?>> dirtyStorages, EnumSet<OptionFlag> flags, MinecraftClient client) {
        if (client.world != null) {
            SodiumWorldRenderer swr = SodiumWorldRenderer.instanceNullable();
            if (swr != null) {
                NvidiumWorldRenderer pipeline = ((INvidiumWorldRendererGetter)((SodiumWorldRendererAccessor)swr).getRenderSectionManager()).getRenderer();
                if (pipeline != null)
                    pipeline.reloadShaders();
            }
        }
    }
}
