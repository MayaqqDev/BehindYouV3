package net.wyvest.behindyou

import gg.essential.universal.ChatColor
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import net.minecraft.util.ChatComponentText
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.common.MinecraftForge.EVENT_BUS
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.wyvest.behindyou.commands.BehindYouCommand
import net.wyvest.behindyou.config.BehindYouConfig
import net.wyvest.behindyou.utils.Updater
import org.lwjgl.input.Keyboard
import java.io.File


@Mod(
    name = BehindYou.NAME,
    modid = BehindYou.ID,
    version = BehindYou.VERSION,
    modLanguageAdapter = "gg.essential.api.utils.KotlinAdapter"
)
object BehindYou {
    const val NAME = "BehindYouv2"
    const val VERSION = "2.1.0"
    const val ID = "behindyouv2"
    val mc: Minecraft
        get() = Minecraft.getMinecraft()

    fun sendMessage(message: String) {
        if (mc.thePlayer == null)
            return
        val text = ChatComponentText(EnumChatFormatting.DARK_PURPLE.toString() + "[$NAME] " + ChatColor.RESET.toString() + " " + message)
        Minecraft.getMinecraft().thePlayer.addChatMessage(text)
    }

    lateinit var jarFile: File
    val modDir = File(File(File(mc.mcDataDir, "config"), "Wyvest"), NAME)
    val keybind = KeyBinding("Behind Keybind", Keyboard.KEY_R, "Behind You")

    @Mod.EventHandler
    fun onFMLPreInitialization(event: FMLPreInitializationEvent) {
        if (!modDir.exists()) modDir.mkdirs()
        jarFile = event.sourceFile
    }

    @Mod.EventHandler
    fun onFMLInitialization(event: FMLInitializationEvent) {
        BehindYouConfig.initialize()
        BehindYouCommand.register()
        ClientRegistry.registerKeyBinding(keybind)
        EVENT_BUS.register(Listener)
        Updater.update()
    }
}