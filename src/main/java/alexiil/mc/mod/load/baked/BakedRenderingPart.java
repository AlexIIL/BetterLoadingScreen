package alexiil.mc.mod.load.baked;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;

import alexiil.mc.mod.load.baked.func.BakedFunction;
import alexiil.mc.mod.load.baked.func.FunctionException;
import alexiil.mc.mod.load.baked.insn.BakedInstruction;
import alexiil.mc.mod.load.render.MinecraftDisplayerRenderer;
import alexiil.mc.mod.load.render.RenderingStatus;

public class BakedRenderingPart extends BakedTickable {
    public final BakedInstruction[] instructions;
    public final BakedRender render;
    public final BakedFunction<Boolean> shouldRender;

    public BakedRenderingPart(BakedInstruction[] instructions, BakedRender render, BakedFunction<Boolean> shouldRender) {
        this.instructions = instructions;
        this.render = render;
        this.shouldRender = shouldRender;
    }

    public void render(RenderingStatus status, MinecraftDisplayerRenderer renderer) throws FunctionException {
        GlStateManager.pushMatrix();
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.pushMatrix();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        for (BakedInstruction insn : instructions) {
            insn.render(status);
        }
        render.render(status, renderer);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
    }

    @Override
    public void tick(RenderingStatus status, MinecraftDisplayerRenderer renderer) throws FunctionException {
        render.populateVariableMap(status, renderer);
        if (shouldRender.call(status)) {
            render(status, renderer);
        }
    }
}
