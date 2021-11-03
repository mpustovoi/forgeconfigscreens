package fuzs.configmenusforge.client.gui.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.Objects;

// all this for changing background texture >.<
public abstract class CustomBackgroundContainerObjectSelectionList<E extends AbstractOptionList.Entry<E>> extends AbstractOptionList<E> {
    private final ResourceLocation background;
    private boolean renderSelection = true;
    private boolean renderHeader;
    private boolean renderBackground = true;
    private boolean renderTopAndBottom = true;
    @Nullable
    private E hovered;

    public CustomBackgroundContainerObjectSelectionList(Minecraft minecraft, ResourceLocation background, int i, int j, int k, int l, int m) {
        super(minecraft, i, j, k, l, m);
        this.background = background;
    }

    @Override
    public void setRenderSelection(boolean bl) {
        this.renderSelection = bl;
    }

    @Override
    protected void setRenderHeader(boolean bl, int i) {
        this.renderHeader = bl;
        this.headerHeight = i;
        if (!bl) {
            this.headerHeight = 0;
        }
    }

    @Override
    public void setRenderBackground(boolean bl) {
        this.renderBackground = bl;
    }

    @Override
    public void setRenderTopAndBottom(boolean bl) {
        this.renderTopAndBottom = bl;
    }

    private int getRowBottom(int i) {
        return this.getRowTop(i) + this.itemHeight;
    }

    @Override
    public void render(MatrixStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        int k = this.getScrollbarPosition();
        int l = k + 6;
        Tessellator tesselator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        this.hovered = this.isMouseOver(i, j) ? this.getEntryAtPosition(i, j) : null;
        if (this.renderBackground) {
            this.minecraft.getTextureManager().bind(this.background);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            float g = 32.0F;
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.vertex(this.x0, this.y1, 0.0D).uv((float)this.x0 / 32.0F, (float)(this.y1 + (int)this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            bufferBuilder.vertex(this.x1, this.y1, 0.0D).uv((float)this.x1 / 32.0F, (float)(this.y1 + (int)this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            bufferBuilder.vertex(this.x1, this.y0, 0.0D).uv((float)this.x1 / 32.0F, (float)(this.y0 + (int)this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            bufferBuilder.vertex(this.x0, this.y0, 0.0D).uv((float)this.x0 / 32.0F, (float)(this.y0 + (int)this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            tesselator.end();
        }

        int m = this.getRowLeft();
        int n = this.y0 + 4 - (int)this.getScrollAmount();
        if (this.renderHeader) {
            this.renderHeader(poseStack, m, n, tesselator);
        }

        this.renderList(poseStack, m, n, i, j, f);
        if (this.renderTopAndBottom) {
            this.minecraft.getTextureManager().bind(this.background);
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(519);
            float h = 32.0F;
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            bufferBuilder.vertex(this.x0, this.y0, -100.0D).uv(0.0F, (float)this.y0 / 32.0F).color(64, 64, 64, 255).endVertex();
            bufferBuilder.vertex(this.x0 + this.width, this.y0, -100.0D).uv((float)this.width / 32.0F, (float)this.y0 / 32.0F).color(64, 64, 64, 255).endVertex();
            bufferBuilder.vertex(this.x0 + this.width, 0.0D, -100.0D).uv((float)this.width / 32.0F, 0.0F).color(64, 64, 64, 255).endVertex();
            bufferBuilder.vertex(this.x0, 0.0D, -100.0D).uv(0.0F, 0.0F).color(64, 64, 64, 255).endVertex();
            bufferBuilder.vertex(this.x0, this.height, -100.0D).uv(0.0F, (float)this.height / 32.0F).color(64, 64, 64, 255).endVertex();
            bufferBuilder.vertex(this.x0 + this.width, this.height, -100.0D).uv((float)this.width / 32.0F, (float)this.height / 32.0F).color(64, 64, 64, 255).endVertex();
            bufferBuilder.vertex(this.x0 + this.width, this.y1, -100.0D).uv((float)this.width / 32.0F, (float)this.y1 / 32.0F).color(64, 64, 64, 255).endVertex();
            bufferBuilder.vertex(this.x0, this.y1, -100.0D).uv(0.0F, (float)this.y1 / 32.0F).color(64, 64, 64, 255).endVertex();
            tesselator.end();
            RenderSystem.depthFunc(515);
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            RenderSystem.disableTexture();
            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.vertex(this.x0, this.y0 + 4, 0.0D).color(0, 0, 0, 0).endVertex();
            bufferBuilder.vertex(this.x1, this.y0 + 4, 0.0D).color(0, 0, 0, 0).endVertex();
            bufferBuilder.vertex(this.x1, this.y0, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferBuilder.vertex(this.x0, this.y0, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferBuilder.vertex(this.x0, this.y1, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferBuilder.vertex(this.x1, this.y1, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferBuilder.vertex(this.x1, this.y1 - 4, 0.0D).color(0, 0, 0, 0).endVertex();
            bufferBuilder.vertex(this.x0, this.y1 - 4, 0.0D).color(0, 0, 0, 0).endVertex();
            tesselator.end();
        }

        int q = this.getMaxScroll();
        if (q > 0) {
            RenderSystem.disableTexture();
            int r = (int)((float)((this.y1 - this.y0) * (this.y1 - this.y0)) / (float)this.getMaxPosition());
            r = MathHelper.clamp(r, 32, this.y1 - this.y0 - 8);
            int s = (int)this.getScrollAmount() * (this.y1 - this.y0 - r) / q + this.y0;
            if (s < this.y0) {
                s = this.y0;
            }

            bufferBuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder.vertex(k, this.y1, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferBuilder.vertex(l, this.y1, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferBuilder.vertex(l, this.y0, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferBuilder.vertex(k, this.y0, 0.0D).color(0, 0, 0, 255).endVertex();
            bufferBuilder.vertex(k, s + r, 0.0D).color(128, 128, 128, 255).endVertex();
            bufferBuilder.vertex(l, s + r, 0.0D).color(128, 128, 128, 255).endVertex();
            bufferBuilder.vertex(l, s, 0.0D).color(128, 128, 128, 255).endVertex();
            bufferBuilder.vertex(k, s, 0.0D).color(128, 128, 128, 255).endVertex();
            bufferBuilder.vertex(k, s + r - 1, 0.0D).color(192, 192, 192, 255).endVertex();
            bufferBuilder.vertex(l - 1, s + r - 1, 0.0D).color(192, 192, 192, 255).endVertex();
            bufferBuilder.vertex(l - 1, s, 0.0D).color(192, 192, 192, 255).endVertex();
            bufferBuilder.vertex(k, s, 0.0D).color(192, 192, 192, 255).endVertex();
            tesselator.end();
        }

        this.renderDecorations(poseStack, i, j);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }

    @Override
    protected void renderList(MatrixStack poseStack, int i, int j, int k, int l, float f) {
        int m = this.getItemCount();
        Tessellator tesselator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();

        for(int n = 0; n < m; ++n) {
            int o = this.getRowTop(n);
            int p = this.getRowBottom(n);
            if (p >= this.y0 && o <= this.y1) {
                int q = j + n * this.itemHeight + this.headerHeight;
                int r = this.itemHeight - 4;
                E entry = this.getEntry(n);
                int s = this.getRowWidth();
                int v;
                if (this.renderSelection && this.isSelectedItem(n)) {
                    v = this.x0 + this.width / 2 - s / 2;
                    int u = this.x0 + this.width / 2 + s / 2;
                    RenderSystem.disableTexture();
                    float g = this.isFocused() ? 1.0F : 0.5F;
                    RenderSystem.color4f(g, g, g, 1.0F);
                    bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
                    bufferBuilder.vertex(v, q + r + 2, 0.0D).endVertex();
                    bufferBuilder.vertex(u, q + r + 2, 0.0D).endVertex();
                    bufferBuilder.vertex(u, q - 2, 0.0D).endVertex();
                    bufferBuilder.vertex(v, q - 2, 0.0D).endVertex();
                    tesselator.end();
                    RenderSystem.color4f(0.0F, 0.0F, 0.0F, 1.0F);
                    bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
                    bufferBuilder.vertex(v + 1, q + r + 1, 0.0D).endVertex();
                    bufferBuilder.vertex(u - 1, q + r + 1, 0.0D).endVertex();
                    bufferBuilder.vertex(u - 1, q - 1, 0.0D).endVertex();
                    bufferBuilder.vertex(v + 1, q - 1, 0.0D).endVertex();
                    tesselator.end();
                    RenderSystem.enableTexture();
                }

                v = this.getRowLeft();
                entry.render(poseStack, n, o, v, s, r, k, l, Objects.equals(this.hovered, entry), f);
            }
        }

    }

    @Nullable
    protected E getHovered() {
        return this.hovered;
    }
}
