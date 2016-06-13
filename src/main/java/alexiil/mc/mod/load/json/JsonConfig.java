package alexiil.mc.mod.load.json;

import java.util.Map;

import alexiil.mc.mod.load.baked.BakedAction;
import alexiil.mc.mod.load.baked.BakedConfig;
import alexiil.mc.mod.load.baked.BakedFactory;
import alexiil.mc.mod.load.baked.BakedRenderingPart;
import alexiil.mc.mod.load.baked.func.BakedFunction;

public class JsonConfig extends JsonConfigurable<JsonConfig, BakedConfig> {
    public final String[] renders;
    public final String[] functions;
    public final String[] factories;
    public final String[] actions;
    public final JsonVariable[] variables;

    public JsonConfig(String parent, String[] render, String[] functions, String[] factories, String[] actions, JsonVariable[] variables) {
        super("");
        this.renders = render;
        this.functions = functions;
        this.factories = factories;
        this.actions = actions;
        this.variables = variables;
    }

    @Override
    protected BakedConfig actuallyBake(Map<String, BakedFunction<?>> functions) {
        for (String func : this.functions) {
            JsonFunction function = ConfigManager.getAsFunction(func);
            if (function != null) {
                BakedFunction<?> bf = function.bake(functions);
                functions.put(function.name, bf);
            }
        }

        BakedRenderingPart[] array = new BakedRenderingPart[this.renders.length];
        for (int i = 0; i < this.renders.length; i++) {
            JsonRenderingPart jrp = ConfigManager.getAsRenderingPart(this.renders[i]);
            array[i] = jrp.bake(functions);
        }

        BakedAction[] actions;
        if (this.actions == null || this.actions.length == 0) {
            actions = new BakedAction[0];
        }
        else {
            actions = new BakedAction[this.actions.length];
            for (int i = 0; i < this.actions.length; i++) {
                JsonAction ja = ConfigManager.getAsAction(this.actions[i]);
                actions[i] = ja.bake(functions);
            }
        }

        BakedFactory[] factories;
        if (this.factories == null || this.factories.length == 0) {
            factories = new BakedFactory[0];
        }
        else {
            factories = new BakedFactory[this.factories.length];
            for (int i = 0; i < this.factories.length; i++) {
                JsonFactory jf = ConfigManager.getAsFactory(this.factories[i]);
                factories[i] = jf.bake(functions);
            }
        }
        return new BakedConfig(array, actions, factories);
    }

    @Override
    protected JsonConfig actuallyConsolidate() {
        if (parent == null)
            return this;

        JsonConfig parent = ConfigManager.getAsConfig(this.parent);
        if (parent == null)
            return this;

        String[] renders = consolidateArray(parent.renders, this.renders);
        String[] functions = consolidateArray(this.functions, parent.functions);
        String[] factories = consolidateArray(parent.factories, this.factories);
        String[] actions = consolidateArray(parent.actions, this.actions);
        JsonVariable[] variables = consolidateArray(parent.variables, this.variables);

        return new JsonConfig("", renders, functions, factories, actions, variables);
    }
}
