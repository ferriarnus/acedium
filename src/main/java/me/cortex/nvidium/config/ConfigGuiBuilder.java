package me.cortex.nvidium.config;

import com.google.common.collect.ImmutableList;
import me.cortex.nvidium.Nvidium;
import me.cortex.nvidium.sodiumCompat.NvidiumOptionFlags;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.embeddedt.embeddium.api.OptionGUIConstructionEvent;
import org.embeddedt.embeddium.api.options.OptionIdentifier;
import org.embeddedt.embeddium.api.options.control.ControlValueFormatter;
import org.embeddedt.embeddium.api.options.control.CyclingControl;
import org.embeddedt.embeddium.api.options.control.SliderControl;
import org.embeddedt.embeddium.api.options.control.TickBoxControl;
import org.embeddedt.embeddium.api.options.structure.OptionFlag;
import org.embeddedt.embeddium.api.options.structure.OptionGroup;
import org.embeddedt.embeddium.api.options.structure.OptionImpact;
import org.embeddedt.embeddium.api.options.structure.OptionImpl;
import org.embeddedt.embeddium.api.options.structure.OptionPage;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ConfigGuiBuilder {
    private static final NvidiumConfigStore store = NvidiumConfigStore.INSTANCE;

    //Options
    public static final Identifier ENABLED = Identifier.of(Nvidium.MOD_ID, "enabled");
    public static final Identifier SHADERS = Identifier.of(Nvidium.MOD_ID, "shaders");
    public static final Identifier REGION_LOADED = Identifier.of(Nvidium.MOD_ID, "region_loaded");
    public static final Identifier TEMPORAL = Identifier.of(Nvidium.MOD_ID, "temporal");
    public static final Identifier ASYNC_BSF = Identifier.of(Nvidium.MOD_ID, "adync_bsf");
    public static final Identifier AUTO_MEM = Identifier.of(Nvidium.MOD_ID, "auto_mem");
    public static final Identifier MEM = Identifier.of(Nvidium.MOD_ID, "mem");
    public static final Identifier TS = Identifier.of(Nvidium.MOD_ID, "ts");
    public static final Identifier STATISTICS = Identifier.of(Nvidium.MOD_ID, "statistics");

    //Group
    public static final Identifier GENERAL = Identifier.of(Nvidium.MOD_ID, "general");
    public static final Identifier SHADER_GROUP = Identifier.of(Nvidium.MOD_ID, "shader_group");
    public static final Identifier ALL = Identifier.of(Nvidium.MOD_ID, "all");

    //Page
    public static OptionIdentifier<Void> NVIDIUM = OptionIdentifier.create(Nvidium.MOD_ID, Nvidium.MOD_ID);

    public static OptionPage addNvidiumGui() {
        List<OptionGroup> groups = new ArrayList<>();

        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(boolean.class, store)
                        .setId(ENABLED)
                        .setName(Text.literal("Disable nvidium"))
                        .setTooltip(Text.literal("Used to disable nvidium (DOES NOT SAVE, WILL RE-ENABLE AFTER A RE-LAUNCH)"))
                        .setControl(TickBoxControl::new)
                        .setImpact(OptionImpact.HIGH)
                        .setBinding((opts, value) -> Nvidium.FORCE_DISABLE = value, opts -> Nvidium.FORCE_DISABLE)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build()
                )
                .setId(GENERAL)
                .build());

        if (Nvidium.IS_COMPATIBLE && !Nvidium.IS_ENABLED && !Nvidium.FORCE_DISABLE) {
            groups.add(OptionGroup.createBuilder()
                    .add(OptionImpl.createBuilder(boolean.class, store)
                            .setId(SHADERS)
                            .setName(Text.literal("Nvidium disabled due to shaders being loaded"))
                            .setTooltip(Text.literal("Nvidium disabled due to shaders being loaded"))
                            .setControl(TickBoxControl::new)
                            .setImpact(OptionImpact.VARIES)
                            .setBinding((opts, value) -> {}, opts -> false)
                            .setFlags()
                            .build()
                    )
                    .setId(SHADER_GROUP)
                    .build());
        }
        groups.add(OptionGroup.createBuilder()
                .add(OptionImpl.createBuilder(int.class, store)
                        .setId(REGION_LOADED)
                        .setName(Text.translatable("nvidium.options.region_keep_distance.name"))
                        .setTooltip(Text.translatable("nvidium.options.region_keep_distance.tooltip"))
                        .setControl(option -> new SliderControl(option, 32, 256, 1, x->Text.literal(x==32?"Vanilla":(x==256?"Keep All":x+" chunks"))))
                        .setImpact(OptionImpact.VARIES)
                        .setEnabled(Nvidium.IS_ENABLED)
                        .setBinding((opts, value) -> opts.region_keep_distance = value, opts -> opts.region_keep_distance)
                        .setFlags()
                        .build()
                ).add(OptionImpl.createBuilder(boolean.class, store)
                        .setId(TEMPORAL)
                        .setName(Text.translatable("nvidium.options.enable_temporal_coherence.name"))
                        .setTooltip(Text.translatable("nvidium.options.enable_temporal_coherence.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setImpact(OptionImpact.MEDIUM)
                        .setEnabled(Nvidium.IS_ENABLED)
                        .setBinding((opts, value) -> opts.enable_temporal_coherence = value, opts -> opts.enable_temporal_coherence)
                        .setFlags()
                        .build()
                ).add(OptionImpl.createBuilder(boolean.class, store)
                        .setId(ASYNC_BSF)
                        .setName(Text.translatable("nvidium.options.async_bfs.name"))
                        .setTooltip(Text.translatable("nvidium.options.async_bfs.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setImpact(OptionImpact.HIGH)
                        .setEnabled(Nvidium.IS_ENABLED)
                        .setBinding((opts, value) -> opts.async_bfs = value, opts -> opts.async_bfs)
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build()
                ).add(OptionImpl.createBuilder(boolean.class, store)
                        .setId(AUTO_MEM)
                        .setName(Text.translatable("nvidium.options.automatic_memory_limit.name"))
                        .setTooltip(Text.translatable("nvidium.options.automatic_memory_limit.tooltip"))
                        .setControl(TickBoxControl::new)
                        .setImpact(OptionImpact.VARIES)
                        .setEnabled(Nvidium.IS_ENABLED)
                        .setBinding((opts, value) -> opts.automatic_memory = value, opts -> opts.automatic_memory)
                        .setFlags()
                        .build())
                .add(OptionImpl.createBuilder(int.class, store)
                        .setId(MEM)
                        .setName(Text.translatable("nvidium.options.max_gpu_memory.name"))
                        .setTooltip(Text.translatable("nvidium.options.max_gpu_memory.tooltip"))
                        .setControl(option -> new SliderControl(option, 2048, 32768, 512, ControlValueFormatter.translateVariable("nvidium.options.mb")))
                        .setImpact(OptionImpact.VARIES)
                        .setEnabled(Nvidium.IS_ENABLED && !Nvidium.config.automatic_memory)
                        .setBinding((opts, value) -> opts.max_geometry_memory = value, opts -> opts.max_geometry_memory)
                        .setFlags(Nvidium.SUPPORTS_PERSISTENT_SPARSE_ADDRESSABLE_BUFFER?new OptionFlag[0]:new OptionFlag[]{OptionFlag.REQUIRES_RENDERER_RELOAD})
                        .build()
                ).add(OptionImpl.createBuilder(TranslucencySortingLevel.class, store)
                        .setId(TS)
                        .setName(Text.translatable("nvidium.options.translucency_sorting.name"))
                        .setTooltip(Text.translatable("nvidium.options.translucency_sorting.tooltip"))
                        .setControl(
                                opts -> new CyclingControl<>(
                                        opts,
                                        TranslucencySortingLevel.class,
                                        new Text[]{
                                                Text.translatable("nvidium.options.translucency_sorting.none"),
                                                Text.translatable("nvidium.options.translucency_sorting.sections"),
                                                Text.translatable("nvidium.options.translucency_sorting.quads")
                                        }
                                )
                        )
                        .setBinding((opts, value) -> opts.translucency_sorting_level = value, opts -> opts.translucency_sorting_level)
                        .setEnabled(Nvidium.IS_ENABLED)
                        .setImpact(OptionImpact.MEDIUM)
                        //Technically, only need to reload when going from NONE->SECTIONS
                        .setFlags(OptionFlag.REQUIRES_RENDERER_RELOAD)
                        .build()
                ).add(OptionImpl.createBuilder(StatisticsLoggingLevel.class, store)
                        .setId(STATISTICS)
                        .setName(Text.translatable("nvidium.options.statistics_level.name"))
                        .setTooltip(Text.translatable("nvidium.options.statistics_level.tooltip"))
                        .setControl(
                                opts -> new CyclingControl<>(
                                        opts,
                                        StatisticsLoggingLevel.class,
                                        new Text[]{
                                                Text.translatable("nvidium.options.statistics_level.none"),
                                                Text.translatable("nvidium.options.statistics_level.frustum"),
                                                Text.translatable("nvidium.options.statistics_level.regions"),
                                                Text.translatable("nvidium.options.statistics_level.sections"),
                                                Text.translatable("nvidium.options.statistics_level.quads")
                                        }
                                )
                        )
                        .setBinding((opts, value) -> opts.statistics_level = value, opts -> opts.statistics_level)
                        .setEnabled(Nvidium.IS_ENABLED)
                        .setImpact(OptionImpact.LOW)
                        .setFlags(NvidiumOptionFlags.REQUIRES_SHADER_RELOAD)
                        .build()
                )
                .setId(ALL)
                .build());
        return new OptionPage(NVIDIUM, Text.translatable("nvidium.options.pages.nvidium"), ImmutableList.copyOf(groups));
    }

    @SubscribeEvent
    public static void registerPageEvent(OptionGUIConstructionEvent event) {
        event.addPage(addNvidiumGui());
    }
}
