package me.oondanomala.barriervisibility;

import me.oondanomala.barriervisibility.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
    public static final String VERSION = "2.2.2";
    public static final Logger LOGGER = LogManager.getLogger(NAME);
    public static Config config;

    /**
     * Whether the mod is enabled and
     * barriers are currently visible.<br>
     * <tt>true</tt> if they should be visible,
     * <tt>false</tt> otherwise.
     */
    public static boolean enabled = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Config(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(config);
        ClientCommandHandler.instance.registerCommand(new BarrierCommand());
        if (config.enableKeybind) {
            BarrierKeybind barrierKeybind = new BarrierKeybind();
            MinecraftForge.EVENT_BUS.register(barrierKeybind);
            ClientRegistry.registerKeyBinding(barrierKeybind.keybind);
        }
    }

    public static void toggleBarriers() {
        enabled = !enabled;
        if (enabled) {
            showChatMessage("Barrier blocks are now visible.");
        } else {
            showChatMessage("We are back to normal.");
        }
        // Reload all blocks
        Minecraft.getMinecraft().renderGlobal.loadRenderers();
    }

    public static void showChatMessage(String message) {
        if (Minecraft.getMinecraft().thePlayer == null) return;
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "[" + EnumChatFormatting.BLUE + EnumChatFormatting.BOLD + NAME + EnumChatFormatting.GRAY + "] " + message));
    }
}
