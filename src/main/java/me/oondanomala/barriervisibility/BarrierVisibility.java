package me.oondanomala.barriervisibility;

import me.oondanomala.barriervisibility.command.BarrierCommand;
import me.oondanomala.barriervisibility.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = BarrierVisibility.MODID,
        name = BarrierVisibility.NAME,
        version = BarrierVisibility.VERSION,
        acceptedMinecraftVersions = "1.8.9",
        clientSideOnly = true,
        guiFactory = "me.oondanomala.barriervisibility.config.GuiFactory"
)
public class BarrierVisibility {
    public static final String MODID = "BV";
    public static final String NAME = "BarrierVisibility";
    public static final String VERSION = "2.1.0";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static Config config;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Config(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(config);
        ClientCommandHandler.instance.registerCommand(new BarrierCommand());
    }

    public static void showChatMessage(String message) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "[" + EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + BarrierVisibility.NAME + EnumChatFormatting.GRAY + "] " + message));
    }
}
