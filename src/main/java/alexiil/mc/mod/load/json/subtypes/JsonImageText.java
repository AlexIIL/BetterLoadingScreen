package alexiil.mc.mod.load.json.subtypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.util.ResourceLocation;

import alexiil.mc.mod.load.baked.func.BakedFunction;
import alexiil.mc.mod.load.baked.func.FunctionBaker;
import alexiil.mc.mod.load.baked.insn.BakedInstruction;
import alexiil.mc.mod.load.baked.insn.BakedPositionFunctional;
import alexiil.mc.mod.load.baked.render.BakedTextRenderStatic;
import alexiil.mc.mod.load.json.Area;
import alexiil.mc.mod.load.json.EPosition;
import alexiil.mc.mod.load.json.JsonImage;

public class JsonImageText extends JsonImage {
    public JsonImageText(ResourceLocation resourceLocation, String image, EPosition positionType, EPosition offsetPos, Area texture, Area position,
            String colour, String text) {
        super("", image, positionType, offsetPos, texture, position, colour, text, null);
        this.resourceLocation = resourceLocation;
    }

    @Override
    protected JsonImageText actuallyConsolidate() {
        return this;
    }

    @Override
    protected BakedTextRenderStatic actuallyBake(Map<String, BakedFunction<?>> functions) {
        BakedFunction<String> textFunc = FunctionBaker.bakeFunctionString(text, functions);
        BakedFunction<Double> xFunc = FunctionBaker.bakeFunctionDouble(position.x, functions);
        BakedFunction<Double> yFunc = FunctionBaker.bakeFunctionDouble(position.y, functions);
        BakedFunction<Double> colourFunc = FunctionBaker.bakeFunctionDouble(colour);
        return new BakedTextRenderStatic(textFunc, xFunc, yFunc, colourFunc, image);
    }

    @Override
    public List<BakedInstruction> bakeInstructions(Map<String, BakedFunction<?>> functions) {
        List<BakedInstruction> list = new ArrayList<BakedInstruction>();
        // if (getColour() != 0xFFFFFF) {
        // list.add(new BakedColourSimple(getRed(), getGreen(), getBlue(), 1));
        // }

        String x = positionType.getFunctionX("screenWidth", offsetPos.getFunctionX("('textWidth':variable)", "0"));
        String y = positionType.getFunctionY("screenHeight", offsetPos.getFunctionY("('textHeight':variable)", "0"));

        list.add(new BakedPositionFunctional(FunctionBaker.bakeFunctionDouble(x, functions), FunctionBaker.bakeFunctionDouble(y, functions),
            FunctionBaker.bakeFunctionDouble("0")));

        return list;
    }
}
