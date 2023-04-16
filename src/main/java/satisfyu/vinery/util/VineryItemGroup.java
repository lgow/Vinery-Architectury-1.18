package satisfyu.vinery.util;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import satisfyu.vinery.Vinery;
import satisfyu.vinery.registry.ObjectRegistry;

public class VineryItemGroup {
    public static ItemGroup VINERY;

    public static void registerItemGroup() {
        VINERY = FabricItemGroup.builder(new Identifier(Vinery.MODID, "vinery"))
                .displayName(Text.literal("Vinery"))
                .icon(() -> new ItemStack(ObjectRegistry.JUNGLE_RED_GRAPE)).build();
    }
}
