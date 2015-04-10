package alexiil.mods.load.coremod;

import java.io.File;
import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import alexiil.mods.load.ProgressDisplayer;
import alexiil.mods.load.Translation;

@IFMLLoadingPlugin.MCVersion("1.8")
@IFMLLoadingPlugin.TransformerExclusions({ "alexiil.mods.load.coremod" })
@IFMLLoadingPlugin.SortingIndex(Integer.MAX_VALUE - 80)
public class LoadingScreenLoadPlugin implements IFMLLoadingPlugin {
    // The only reason this coremod exists is this static method: its the first time our code is called
    static {
        ProgressDisplayer.start();
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { "alexiil.mods.load.coremod.BetterLoadingScreenTransformer" };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        Translation.addTranslations((File) data.get("coremodLocation"));
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}