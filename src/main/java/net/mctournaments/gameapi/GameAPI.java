package net.mctournaments.gameapi;

import com.google.inject.Injector;
import com.harryfreeborough.modularity.additionalmodules.BukkitGuiceModule;
import com.harryfreeborough.modularity.loader.ModuleLoader;
import net.mctournaments.bukkit.module.McTournamentsGuiceModule;
import org.bukkit.plugin.java.JavaPlugin;

public final class GameAPI extends JavaPlugin {

    private Injector injector;

    @Override
    public void onEnable() {
        this.injector = new ModuleLoader()
                .addInjectorModules(new BukkitGuiceModule(this), new McTournamentsGuiceModule(this))
                .load();
    }

    @Override
    public void onDisable() {
    }
}
