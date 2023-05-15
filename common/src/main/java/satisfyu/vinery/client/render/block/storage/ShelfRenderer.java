package satisfyu.vinery.client.render.block.storage;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.cristelknight.doapi.block.entity.StorageBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import de.cristelknight.doapi.client.render.block.storage.StorageTypeRenderer;
import satisfyu.vinery.util.ClientUtil;

public class ShelfRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {

        matrices.translate(-0.4, 0.5, 0.25);
        matrices.mulPose(Axis.YP.rotationDegrees(90));
        matrices.scale(0.5f, 0.5f, 0.5f);

        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if (!stack.isEmpty()) {
                matrices.pushPose();
                matrices.translate(0f, 0f, 0.2f * i);
                matrices.mulPose(Axis.YN.rotationDegrees(22.5f));
                ClientUtil.renderItem(stack, matrices, vertexConsumers, entity, entity.getLevel());
                matrices.popPose();
            }
        }
    }
}
