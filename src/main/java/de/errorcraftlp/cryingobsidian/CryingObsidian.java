package de.errorcraftlp.cryingobsidian;

import de.errorcraftlp.cryingobsidian.block.BlockCryingObsidian;
import de.errorcraftlp.cryingobsidian.block.BlockCryingObsidianAdvanced;
import de.errorcraftlp.cryingobsidian.block.BlockCryingObsidianDecoration;
import de.errorcraftlp.cryingobsidian.item.ItemCryingObsidian;
import de.errorcraftlp.cryingobsidian.misc.CryingObsidianDataFixer;
import de.errorcraftlp.cryingobsidian.tileentiy.TileEntityCryingObsidianAdvanced;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.datafix.FixTypes;
import net.minecraftforge.common.util.CompoundDataFixer;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import org.dv.minecraft.cryingobsidian.Reference;

@EventBusSubscriber
@Mod(
        modid = Reference.MOD_ID,
        name = Reference.NAME,
        version = Reference.VERSION,
        updateJSON = Reference.UPDATE_JSON,
        acceptedMinecraftVersions = Reference.ACCEPTED_MC_VERSIONS,
        dependencies = Reference.DEPENDENCIES)
public class CryingObsidian {
    // Blocks/Items
    public static Block cryingObsidianBlock = new BlockCryingObsidian();
    public static Block cryingObsidianBlockAdvanced = new BlockCryingObsidianAdvanced();
    public static Block cryingObsidianBlockDecoration = new BlockCryingObsidianDecoration();
    public static Item cryingObsidianItem = new ItemCryingObsidian();

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        final IForgeRegistry<Block> registry = event.getRegistry();
        registry.registerAll(cryingObsidianBlock, cryingObsidianBlockAdvanced, cryingObsidianBlockDecoration);

        // Register tile entity
        GameRegistry.registerTileEntity(TileEntityCryingObsidianAdvanced.class, Reference.MOD_ID + ":crying_obsidian_advanced_tile_entity");
    }

    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();

        // Item Blocks
        registry.register(new ItemBlock(cryingObsidianBlock).setRegistryName(cryingObsidianBlock.getRegistryName()));
        registry.register(new ItemBlock(cryingObsidianBlockAdvanced).setRegistryName(cryingObsidianBlockAdvanced.getRegistryName()));
        registry.register(new ItemBlock(cryingObsidianBlockDecoration).setRegistryName(cryingObsidianBlockDecoration.getRegistryName()));

        // Item
        registry.register(cryingObsidianItem);
    }

    @EventHandler
    public void init(@SuppressWarnings("unused") final FMLInitializationEvent event) {
        // Register data fixer
        final CompoundDataFixer dataFixer = FMLCommonHandler.instance().getDataFixer();
        final ModFixs tileEntityFixer = dataFixer.init(Reference.MOD_ID, 1);
        tileEntityFixer.registerFix(FixTypes.BLOCK_ENTITY, new CryingObsidianDataFixer());

        // Register crying obsidian blocks in ore dictionary
        OreDictionary.registerOre("obsidian", cryingObsidianBlock);
        OreDictionary.registerOre("obsidian", cryingObsidianBlockDecoration);
        OreDictionary.registerOre("obsidian", cryingObsidianBlockAdvanced);
    }
}
