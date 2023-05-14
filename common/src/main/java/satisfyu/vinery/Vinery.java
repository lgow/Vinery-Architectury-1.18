package satisfyu.vinery;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.fuel.FuelRegistry;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import satisfyu.vinery.registry.*;
import satisfyu.vinery.world.VineryFeatures;

public class Vinery {

    public static final String MODID = "vinery";
    public static final RegistrarManager REGISTRIES = RegistrarManager.get(MODID);
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final CreativeTabRegistry.TabSupplier VINERY_TAB = CreativeTabRegistry.create(new VineryIdentifier("vinery_tab"), () ->
            new ItemStack(ObjectRegistry.RED_GRAPE.get()));
    
    public static void init() {
        VineryEffects.init();
        ObjectRegistry.init();
        VineryBlockEntityTypes.init();
        VineryStorageTypes.init();
        VineryScreenHandlerTypes.init();
        VineryRecipeTypes.init();
        VineryEntites.init();
        VineryFeatures.init();
        VinerySoundEvents.init();
    }

    public static void commonSetup(){
        VineryCompostableItems.init();
        VineryFlammableBlocks.init();
        VineryItemGroup.addItems();

        FuelRegistry.register(300, ObjectRegistry.CHERRY_FENCE.get(), ObjectRegistry.CHERRY_FENCE_GATE.get(), ObjectRegistry.STACKABLE_LOG.get(), ObjectRegistry.FERMENTATION_BARREL.get());

        AxeItemHooks.addStrippable(ObjectRegistry.CHERRY_LOG.get(), ObjectRegistry.STRIPPED_CHERRY_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.CHERRY_WOOD.get(), ObjectRegistry.STRIPPED_CHERRY_WOOD.get());
        AxeItemHooks.addStrippable(ObjectRegistry.OLD_CHERRY_LOG.get(), ObjectRegistry.STRIPPED_OLD_CHERRY_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.OLD_CHERRY_WOOD.get(), ObjectRegistry.STRIPPED_OLD_CHERRY_WOOD.get());
    }
}
