package alexiil.mc.mod.load.baked.insn;

import org.lwjgl.opengl.GL11;

import alexiil.mc.mod.load.baked.func.BakedFunction;
import alexiil.mc.mod.load.baked.func.FunctionException;
import alexiil.mc.mod.load.render.RenderingStatus;

public class BakedScaleFunctional extends BakedInstruction {
    private final BakedFunction<Double> x, y, z;

    public BakedScaleFunctional(BakedFunction<Double> x, BakedFunction<Double> y, BakedFunction<Double> z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void render(RenderingStatus status) throws FunctionException {
        GL11.glScaled(x.call(status), y.call(status), z.call(status));
    }
}
