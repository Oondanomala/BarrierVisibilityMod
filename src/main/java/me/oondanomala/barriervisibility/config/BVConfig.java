package me.oondanomala.barriervisibility.config;

import me.oondanomala.barriervisibility.BarrierVisibility;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class BVConfig extends GuiConfig {
    public BVConfig(GuiScreen guiScreen) {
        super(
                guiScreen,
                new ConfigElement(BarrierVisibility.config.configuration.getCategory(Configuration.CATEGORY_CLIENT)).getChildElements(),
                BarrierVisibility.MODID,
                false,
                true,
                GuiConfig.getAbridgedConfigPath(BarrierVisibility.config.configuration.toString()),
                BarrierVisibility.NAME + " Config"
        );
    }
}
