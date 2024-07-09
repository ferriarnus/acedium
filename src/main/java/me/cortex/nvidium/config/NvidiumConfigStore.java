package me.cortex.nvidium.config;

import me.cortex.nvidium.Nvidium;
import org.embeddedt.embeddium.api.options.structure.OptionStorage;

public class NvidiumConfigStore implements OptionStorage<NvidiumConfig> {
    private final NvidiumConfig config;

    public NvidiumConfigStore() {
        config = Nvidium.config;
    }

    @Override
    public NvidiumConfig getData() {
        return config;
    }

    @Override
    public void save() {
        config.save();
    }
}
