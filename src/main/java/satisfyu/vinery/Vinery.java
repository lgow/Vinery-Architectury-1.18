package satisfyu.vinery;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;

import net.minecraft.registry.tag.TagKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfyu.vinery.registry.*;
import satisfyu.vinery.util.VineryItemGroup;
import satisfyu.vinery.world.VineryBiomeModification;
import satisfyu.vinery.world.VineryFeatures;

public class Vinery implements ModInitializer {
    public static final String MODID = "vinery";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final TagKey<Block> ALLOWS_COOKING_ON_POT = TagKey.of(Registries.BLOCK.getKey(), new VineryIdentifier("allows_cooking_on_pot"));

    public void onInitialize() {
        VineryItemGroup.registerItemGroup();
        VineryEffects.init();
        ObjectRegistry.registerVineryItems();
        ObjectRegistry.registerVineryBlocks();
        VineryBlockEntityTypes.init();
        VineryScreenHandlerTypes.init();
        VineryRecipeTypes.init();
        VineryBoatTypes.init();
        VineryFeatures.init();
        VineryBiomeModification.init();
        VinerySoundEvents.init();
        VineryVillagers.init();
        VineryEntites.init();
        VineryCompostableItems.init();

        FabricLoader.getInstance().getModContainer(MODID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(new VineryIdentifier("bushy_leaves"), container, ResourcePackActivationType.NORMAL);
            ResourceManagerHelper.registerBuiltinResourcePack(new VineryIdentifier("apple_leaves"), container, ResourcePackActivationType.NORMAL);
        });
    }


}

