package satisfyu.vinery.client.screen.recipe;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import org.jetbrains.annotations.Nullable;
import satisfyu.vinery.client.recipebook.AbstractPrivateRecipeScreenHandler;
import satisfyu.vinery.client.recipebook.PrivateRecipeBookWidget;

import java.util.Iterator;
import java.util.List;

@Environment(EnvType.CLIENT)
public class PrivateRecipeBookRecipeArea {
    private final List<PrivateAnimatedResultButton> resultButtons = Lists.newArrayListWithCapacity(20);
    @Nullable
    private PrivateAnimatedResultButton hoveredResultButton;
    private final PrivateRecipeAlternativesWidget alternatesWidget = new PrivateRecipeAlternativesWidget();
    private MinecraftClient client;
    private List<? extends Recipe<Inventory>> resultCollections = ImmutableList.of();
    private ToggleButtonWidget nextPageButton;
    private ToggleButtonWidget prevPageButton;
    private int pageCount;
    private int currentPage;
    @Nullable
    private Recipe<?> lastClickedRecipe;
    private AbstractPrivateRecipeScreenHandler cookingPanScreenHandler;

    public PrivateRecipeBookRecipeArea() {
        for(int i = 0; i < 20; ++i) {
            this.resultButtons.add(new PrivateAnimatedResultButton());
        }

    }

    public void initialize(MinecraftClient client, int parentLeft, int parentTop, AbstractPrivateRecipeScreenHandler cookingPanScreenHandler) {
        this.client = client;
        this.cookingPanScreenHandler = cookingPanScreenHandler;

        for(int i = 0; i < this.resultButtons.size(); ++i) {
            this.resultButtons.get(i).setPos(parentLeft + 11 + 25 * (i % 5), parentTop + 31 + 25 * (i / 5));
        }

        this.nextPageButton = new ToggleButtonWidget(parentLeft + 93, parentTop + 137, 12, 17, false);
        this.nextPageButton.setTextureUV(1, 208, 13, 18, PrivateRecipeBookWidget.TEXTURE);
        this.prevPageButton = new ToggleButtonWidget(parentLeft + 38, parentTop + 137, 12, 17, true);
        this.prevPageButton.setTextureUV(1, 208, 13, 18, PrivateRecipeBookWidget.TEXTURE);
    }

    public void setResults(List<? extends Recipe<Inventory>> resultCollections, boolean resetCurrentPage) {
        this.resultCollections = resultCollections;
        this.pageCount = (int)Math.ceil((double)resultCollections.size() / 20.0);
        if (this.pageCount <= this.currentPage || resetCurrentPage) {
            this.currentPage = 0;
        }

        this.refreshResultButtons();
    }

    private void refreshResultButtons() {
        int i = 20 * this.currentPage;

        for(int j = 0; j < this.resultButtons.size(); ++j) {
            PrivateAnimatedResultButton animatedResultButton = this.resultButtons.get(j);
            if (i + j < this.resultCollections.size()) {
                Recipe<?> recipe = this.resultCollections.get(i + j);
                animatedResultButton.showResultCollection(recipe, cookingPanScreenHandler);
                animatedResultButton.visible = true;
            } else {
                animatedResultButton.visible = false;
            }
        }

        this.hideShowPageButtons();
    }

    private void hideShowPageButtons() {
        this.nextPageButton.visible = this.pageCount > 1 && this.currentPage < this.pageCount - 1;
        this.prevPageButton.visible = this.pageCount > 1 && this.currentPage > 0;
    }

    public void draw(MatrixStack matrices, int x, int y, int mouseX, int mouseY, float delta) {
        if (this.pageCount > 1) {
            int var10000 = this.currentPage + 1;
            String string = "" + var10000 + "/" + this.pageCount;
            int i = this.client.textRenderer.getWidth(string);
            this.client.textRenderer.draw(matrices, string, (float)(x - i / 2 + 73), (float)(y + 141), -1);
        }

        this.hoveredResultButton = null;

        for (PrivateAnimatedResultButton animatedResultButton : this.resultButtons) {
            animatedResultButton.render(matrices, mouseX, mouseY, delta);
            if (animatedResultButton.visible && animatedResultButton.isHovered()) {
                this.hoveredResultButton = animatedResultButton;
            }
        }

        this.prevPageButton.render(matrices, mouseX, mouseY, delta);
        this.nextPageButton.render(matrices, mouseX, mouseY, delta);
        this.alternatesWidget.render(matrices, mouseX, mouseY, delta);
    }

    public void drawTooltip(MatrixStack matrices, int x, int y) {
        if (this.client.currentScreen != null && this.hoveredResultButton != null && !this.alternatesWidget.isVisible()) {
            this.client.currentScreen.renderTooltip(matrices, this.hoveredResultButton.getTooltip(this.client.currentScreen), x, y);
        }

    }

    @Nullable
    public Recipe<?> getLastClickedRecipe() {
        return this.lastClickedRecipe;
    }

    public void hideAlternates() {
        this.alternatesWidget.setVisible(false);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button, int areaLeft, int areaTop, int areaWidth, int areaHeight) {
        this.lastClickedRecipe = null;
        if (this.alternatesWidget.isVisible()) {
            if (this.alternatesWidget.mouseClicked(mouseX, mouseY, button)) {
                this.lastClickedRecipe = this.alternatesWidget.getLastClickedRecipe();
            } else {
                this.alternatesWidget.setVisible(false);
            }

            return true;
        } else if (this.nextPageButton.mouseClicked(mouseX, mouseY, button)) {
            ++this.currentPage;
            this.refreshResultButtons();
            return true;
        } else if (this.prevPageButton.mouseClicked(mouseX, mouseY, button)) {
            --this.currentPage;
            this.refreshResultButtons();
            return true;
        } else {
            Iterator<PrivateAnimatedResultButton> var10 = this.resultButtons.iterator();

            PrivateAnimatedResultButton animatedResultButton;
            do {
                if (!var10.hasNext()) {
                    return false;
                }
                animatedResultButton = var10.next();
            } while(!animatedResultButton.mouseClicked(mouseX, mouseY, button));
            if (button == 0) {
                this.lastClickedRecipe = animatedResultButton.currentRecipe();
            }
            if (button == 1 && !this.alternatesWidget.isVisible() && animatedResultButton.hasResult()) {
                // Implement maybe later
                // this.alternatesWidget.showAlternativesForResult(this.client, animatedResultButton.getRecipe(), animatedResultButton.x, animatedResultButton.y, areaLeft + areaWidth / 2, areaTop + 13 + areaHeight / 2, (float)animatedResultButton.getWidth());
            }
            return true;
        }
    }


    public MinecraftClient getClient() {
        return this.client;
    }
}