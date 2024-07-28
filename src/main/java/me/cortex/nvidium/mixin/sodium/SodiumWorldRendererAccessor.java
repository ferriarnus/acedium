package me.cortex.nvidium.mixin.sodium;

import org.embeddedt.embeddium.impl.render.EmbeddiumWorldRenderer;
import org.embeddedt.embeddium.impl.render.chunk.RenderSectionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EmbeddiumWorldRenderer.class, remap = false)
public interface SodiumWorldRendererAccessor {
    @Accessor
    RenderSectionManager getRenderSectionManager();
}
