package net.plugins.logix.wanderingtraderhighlight;

import org.bukkit.ChatColor;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Objects;

@SuppressWarnings({"unused", "ConstantConditions"})
public final class WanderingTraderHighlight extends JavaPlugin
{
    private static WanderingTraderHighlight instance;
    private Scoreboard scoreboard;

    @SuppressWarnings("SpellCheckingInspection")
    private static final String TEAM = "WTHLT";

    @Override
    public void onEnable()
    {
        instance = this;
        scoreboard = Objects.requireNonNull(getServer().getScoreboardManager()).getMainScoreboard();

        getServer().getPluginManager().registerEvents(new SpawnListener(), this);
        getServer().getLogger().info("Wandering Trader Highlight ready! :)");

        scoreboard.registerNewTeam(TEAM);
        scoreboard.getTeam(TEAM).setColor(ChatColor.BLUE);
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
        scoreboard.getTeam(TEAM).unregister();
    }

    public void addToTeam(WanderingTrader trader)
    {
        scoreboard.getTeam(TEAM).addEntry(trader.getUniqueId().toString());
    }

    public static WanderingTraderHighlight getInstance()
    {
        return instance;
    }
}