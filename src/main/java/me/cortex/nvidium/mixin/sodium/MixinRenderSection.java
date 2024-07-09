package me.cortex.nvidium.mixin.sodium;

import me.cortex.nvidium.sodiumCompat.IRenderSectionExtension;
import org.embeddedt.embeddium.impl.render.chunk.RenderSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = RenderSection.class, remap = false)
public class MixinRenderSection implements IRenderSectionExtension {
    @Unique
    private volatile boolean isEnqueued;
    @Unique private volatile boolean isSeen;

    @Override
    public boolean isSubmittedRebuild() {
        return isEnqueued;
    }

    @Override
    public void isSubmittedRebuild(boolean state) {
        isEnqueued = state;
    }

    @Override
    public boolean isSeen() {
        return isSeen;
    }

    @Override
    public void isSeen(boolean state) {
        isSeen = state;
    }
}
