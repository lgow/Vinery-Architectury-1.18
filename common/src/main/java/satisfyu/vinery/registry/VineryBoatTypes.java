package satisfyu.vinery.registry;


import de.cristelknight.doapi.DoApiExpectPlatform;
import de.cristelknight.doapi.terraform.boat.TerraformBoatType;
import de.cristelknight.doapi.terraform.boat.item.TerraformBoatItemHelper;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import satisfyu.vinery.VineryIdentifier;

public class VineryBoatTypes {

    public static ResourceLocation PALM_BOAT_TYPE_LOCATION = new VineryIdentifier("cherry");
    public static RegistrySupplier<Item> CHERRY_BOAT = TerraformBoatItemHelper.registerBoatItem(ObjectRegistry.ITEMS_D, "cherry_boat", PALM_BOAT_TYPE_LOCATION, false);

    public static RegistrySupplier<Item> CHERRY_CHEST_BOAT = TerraformBoatItemHelper.registerBoatItem(ObjectRegistry.ITEMS_D, "cherry_chest_boat", PALM_BOAT_TYPE_LOCATION, true);

    public static void init() {
        DoApiExpectPlatform.registerBoatType(PALM_BOAT_TYPE_LOCATION, new  TerraformBoatType.Builder().item(CHERRY_BOAT).chestItem(CHERRY_CHEST_BOAT).build());
    }
}
