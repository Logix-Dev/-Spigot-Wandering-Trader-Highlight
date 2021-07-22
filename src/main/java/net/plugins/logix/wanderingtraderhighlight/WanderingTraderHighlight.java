package net.plugins.logix.wanderingtraderhighlight;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class WanderingTraderHighlight extends JavaPlugin
{
    private static WanderingTraderHighlight instance;

    @Override
    public void onEnable()
    {
        instance = this;
        getServer().getPluginManager().registerEvents(new SpawnListener(), this);
        getServer().getLogger().info("Wandering Trader Highlight ready! :)");
    }

    public static WanderingTraderHighlight getInstance()
    {
        return instance;
    }
}