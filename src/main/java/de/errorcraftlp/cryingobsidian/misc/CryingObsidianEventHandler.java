package de.errorcraftlp.cryingobsidian.misc;

import net.minecraft.entity.player.EntityPlayer.SleepResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.dv.minecraft.cryingobsidian.Reference;

@EventBusSubscriber(modid = Reference.MOD_ID)
public class CryingObsidianEventHandler {
    @SubscribeEvent
    public static void preventSleeping(final PlayerSleepInBedEvent event) {
        // Disable sleeping in beds if the config option is enabled
        if (CryingObsidianConfig.disableBeds) {
            event.setResult(SleepResult.OTHER_PROBLEM);
            final TextComponentTranslation message = new TextComponentTranslation("message.bed_disabled");
            message.getStyle().setColor(TextFormatting.RED);
            event.getEntityPlayer().sendMessage(message);
        }
    }
}
