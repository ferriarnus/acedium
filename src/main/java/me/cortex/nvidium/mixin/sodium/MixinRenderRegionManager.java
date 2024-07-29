package me.cortex.nvidium.mixin.sodium;

import me.cortex.nvidium.Nvidium;
import me.cortex.nvidium.NvidiumWorldRenderer;
import me.cortex.nvidium.sodiumCompat.INvidiumWorldRendererSetter;
import org.embeddedt.embeddium.impl.gl.device.CommandList;
import org.embeddedt.embeddium.impl.render.chunk.compile.ChunkBuildOutput;
import org.embeddedt.embeddium.impl.render.chunk.region.RenderRegion;
import org.embeddedt.embeddium.impl.render.chunk.region.RenderRegionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;

@Mixin(value = RenderRegionManager.class, remap = false)
public abstract class MixinRenderRegionManager implements INvidiumWorldRendererSetter {


    @Shadow protected abstract void uploadMeshes(CommandList commandList, RenderRegion region, Collection<ChunkBuildOutput> results);

    @Shadow protected abstract void uploadResorts(CommandList commandList, RenderRegion region, Collection<ChunkBuildOutput> results);

    @Unique private NvidiumWorldRenderer renderer;


    @Redirect(method = "uploadMeshes(Lorg/embeddedt/embeddium/impl/gl/device/CommandList;Ljava/util/Collection;)V", at = @At(value = "INVOKE", target = "Lorg/embeddedt/embeddium/impl/render/chunk/region/RenderRegionManager;uploadMeshes(Lorg/embeddedt/embeddium/impl/gl/device/CommandList;Lorg/embeddedt/embeddium/impl/render/chunk/region/RenderRegion;Ljava/util/Collection;)V"))
    private void redirectUpload(RenderRegionManager instance, CommandList cmdList, RenderRegion pass, Collection<ChunkBuildOutput> uploadQueue) {
        if (Nvidium.IS_ENABLED) {
            uploadQueue.forEach(renderer::uploadBuildResult);
        } else {
            uploadMeshes(cmdList, pass, uploadQueue);
        }
    }

    @Redirect(method = "uploadMeshes(Lorg/embeddedt/embeddium/impl/gl/device/CommandList;Ljava/util/Collection;)V", at = @At(value = "INVOKE", target = "Lorg/embeddedt/embeddium/impl/render/chunk/region/RenderRegionManager;uploadResorts(Lorg/embeddedt/embeddium/impl/gl/device/CommandList;Lorg/embeddedt/embeddium/impl/render/chunk/region/RenderRegion;Ljava/util/Collection;)V"))
    private void redirectTS(RenderRegionManager instance, CommandList cmdList, RenderRegion pass, Collection<ChunkBuildOutput> uploadQueue) {
        if (Nvidium.IS_ENABLED) {
            //uploadQueue.forEach(renderer::uploadBuildResult);
        } else {
            uploadResorts(cmdList, pass, uploadQueue);
        }
    }

    @Override
    public void setWorldRenderer(NvidiumWorldRenderer renderer) {
        this.renderer = renderer;
    }
}
