package me.cortex.nvidium.mixin.sodium;

import me.jellysquid.mods.sodium.client.gl.shader.ShaderLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Mixin(value = ShaderLoader.class, remap = false)
public class MixinShaderLoader {
    @Overwrite(remap = false)
    public static String getShaderSource(Identifier name) {
        name = new Identifier(name.getNamespace(), "shaders/" + name.getPath());
        Optional<Resource> shader = MinecraftClient.getInstance().getResourceManager().getResource(name);
        String s = "";
        try {
            s = IOUtils.toString(shader.get().getInputStream(), StandardCharsets.UTF_8);
        } catch (Exception ignore) {}
        return s;
    }
}
