package alexiil.mods.load.baked.func.var;

import alexiil.mods.load.baked.func.FunctionException;
import alexiil.mods.load.baked.func.ICompilableFunction;
import alexiil.mods.load.render.RenderingStatus;

public class BakedVariablePercentage implements ICompilableFunction<Double> {
    @Override
    public Double call(RenderingStatus status) throws FunctionException {
        return status.progressState.getCurrentProgress().percentage;
    }
}