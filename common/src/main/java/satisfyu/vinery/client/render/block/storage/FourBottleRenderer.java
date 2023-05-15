package satisfyu.vinery.client.render.block.storage;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.cristelknight.doapi.block.entity.StorageBlockEntity;
import de.cristelknight.doapi.client.render.block.storage.StorageTypeRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import satisfyu.vinery.block.WineBottleBlock;
import satisfyu.vinery.util.ClientUtil;

public class FourBottleRenderer implements StorageTypeRenderer {
    @Override
    public void render(StorageBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, NonNullList<ItemStack> itemStacks) {
        matrices.translate(-0.13, 0.335, 0.125);
        matrices.scale(0.9f, 0.9f, 0.9f);
        for (int i = 0; i < itemStacks.size(); i++) {
            ItemStack stack = itemStacks.get(i);
            if (!stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem) {
                matrices.pushPose();
                if(i == 0){
                    matrices.translate(-0.35f, 0, 0f);
                }
                else if(i == 1){
                    matrices.translate(0, -0.33f, 0f);
                }
                else if(i == 2){
                    matrices.translate(-0.7f, -0.33f, 0f);
                }
                else if(i == 3){
                    matrices.translate(-0.35f, -0.66f, 0f);
                }
                else {
                    matrices.popPose();
                    continue;
                }
                matrices.mulPose(Axis.XN.rotationDegrees(90));
                ClientUtil.renderBlock(blockItem.getBlock().defaultBlockState().setValue(WineBottleBlock.COUNT, 0), matrices, vertexConsumers, entity);
                matrices.popPose();
            }
        }
    }
}
