package me.oondanomala.barriervisibility.config;

import me.oondanomala.barriervisibility.BarrierVisibility;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

public class Config {
    public final Configuration configuration;
    public boolean visibleBarrierBlocks;
    public boolean initialVisibleBarrierBlocks;

    public Config(File configFile) {
        configuration = new Configuration(configFile);
        loadConfiguration();
        visibleBarrierBlocks = initialVisibleBarrierBlocks;
    }

    public void loadConfiguration() {
        initialVisibleBarrierBlocks = configuration.getBoolean(
                "Default Visibility Setting",
                Configuration.CATEGORY_CLIENT,
                true,
                "Whether to default to showing barriers or not when launching the game."
        );

        // Remove old settings
        configuration.removeCategory(configuration.getCategory(Configuration.CATEGORY_GENERAL));

        configuration.save();
    }

    @SubscribeEvent
    public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.modID.equals(BarrierVisibility.MODID)) {
            loadConfiguration();
        }
    }
}
