package alexiil.mods.load.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.ResourceLocation;
import alexiil.mods.load.baked.BakedRender;
import alexiil.mods.load.baked.func.FunctionException;
import alexiil.mods.load.baked.func.IBakedFunction;

public class BakedImageRender extends BakedRender {
    private final ResourceLocation res;
    private final IBakedFunction<Double> x, y, width, height, u, uWidth, v, vHeight;

    public BakedImageRender(String resourceLocation, IBakedFunction<Double> x, IBakedFunction<Double> y, IBakedFunction<Double> width,
            IBakedFunction<Double> height, IBakedFunction<Double> uMin, IBakedFunction<Double> uMax, IBakedFunction<Double> vMin,
            IBakedFunction<Double> vMax) {
        res = new ResourceLocation(resourceLocation);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.u = uMin;
        this.uWidth = uMax;
        this.v = vMin;
        this.vHeight = vMax;
    }

    @Override
    public void render(RenderingStatus status, MinecraftDisplayerRenderer renderer) throws FunctionException {
        double x = this.x.call(status);
        double y = this.y.call(status);
        double width = this.width.call(status);
        double height = this.height.call(status);
        double u = this.u.call(status);
        double uWidth = this.uWidth.call(status);
        double v = this.v.call(status);
        double vHeight = this.vHeight.call(status);
        Minecraft.getMinecraft().renderEngine.bindTexture(res);
        drawRect(x, y, width, height, u, v, uWidth, vHeight);
    }

    public void drawRect(double x, double y, double drawnWidth, double drawnHeight, double u, double v, double uWidth, double vHeight) {
        float f = 1 / 256F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer wr = tessellator.getWorldRenderer();
        wr.startDrawingQuads();
        wr.addVertexWithUV(x, y + drawnHeight, 0, u * f, (v + vHeight) * f);
        wr.addVertexWithUV(x + drawnWidth, y + drawnHeight, 0, (u + uWidth) * f, (v + vHeight) * f);
        wr.addVertexWithUV(x + drawnWidth, y, 0, (u + uWidth) * f, v * f);
        wr.addVertexWithUV(x, y, 0, u * f, v * f);
        tessellator.draw();
    }
}
