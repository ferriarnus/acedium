package me.cortex.nvidium.config;

import me.cortex.nvidium.Nvidium;
import org.embeddedt.embeddium.api.options.structure.OptionStorage;

public class NvidiumConfigStore implements OptionStorage<NvidiumConfig> {
    public static final NvidiumConfigStore INSTANCE = new NvidiumConfigStore();

    private NvidiumConfigStore() {
    }

    @Override
    public NvidiumConfig getData() {
        return Nvidium.config;
    }

    @Override
    public void save() {
        Nvidium.config.save();
    }
}
