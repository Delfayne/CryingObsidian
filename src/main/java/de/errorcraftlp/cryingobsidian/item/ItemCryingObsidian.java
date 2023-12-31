package de.errorcraftlp.cryingobsidian.item;

import de.errorcraftlp.cryingobsidian.misc.CryingObsidianConfig;
import de.errorcraftlp.cryingobsidian.misc.Utils;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dv.minecraft.cryingobsidian.Reference;

import java.util.List;

public class ItemCryingObsidian extends Item {
    public ItemCryingObsidian() {
        super();
        setTranslationKey("crying_obsidian_item");
        setRegistryName("crying_obsidian_item");
        setCreativeTab(CreativeTabs.MISC);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        Utils.setSpawnPointAtPlayer(world, player);
        return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @Override
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
        if (!player.world.isRemote && entity instanceof EntityLiving) {
            if (CryingObsidianConfig.enableRespawnWhitelist) {
                final ResourceLocation entityKey = EntityList.getKey(entity);
                for (final String whitelistEntry : CryingObsidianConfig.respawnWhitelist) {
                    final ResourceLocation entryKey = new ResourceLocation(whitelistEntry);
                    if (entryKey.equals(entityKey)) {
                        final NBTTagCompound itemNBT = stack.getOrCreateSubCompound(Reference.MOD_ID);
                        itemNBT.setUniqueId("EntityUUID", entity.getUniqueID());

                        player.sendMessage(new TextComponentTranslation("message.entity_linked"));
                        return true;
                    }
                }
                final TextComponentTranslation message = new TextComponentTranslation("message.entity_whitelist");
                message.getStyle().setColor(TextFormatting.RED);
                player.sendMessage(message);
            } else {
                final NBTTagCompound itemNBT = stack.getOrCreateSubCompound(Reference.MOD_ID);
                itemNBT.setUniqueId("EntityUUID", entity.getUniqueID());

                player.sendMessage(new TextComponentTranslation("message.entity_linked"));
            }
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack stack, final World world, final List<String> tooltip, final ITooltipFlag tooltipFlag) {
        final NBTTagCompound itemNBT = stack.getSubCompound(Reference.MOD_ID);

        if (itemNBT != null && itemNBT.getUniqueId("EntityUUID") != null) {
            tooltip.add(I18n.format("desc.crying_obsidian_item"));
        }
    }
}
