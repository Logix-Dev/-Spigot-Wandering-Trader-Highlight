package net.plugins.logix.wanderingtraderhighlight;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpawnListener implements Listener
{
    private static final PotionEffect GLOW_POTION_EFFECT = new PotionEffect(PotionEffectType.GLOWING, 20 * (60 + 60 + 30), 1, true);

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event)
    {
        if (event.getEntity() instanceof WanderingTrader)
        {
            // Cast the entity to WanderingTrader.
            WanderingTrader trader = (WanderingTrader)event.getEntity();

            // Get the nearest player
            Player nearestPlayer = getClosestPlayer(trader.getLocation());

            // If the nearest player was found...
            if (nearestPlayer != null)
            {
                // Send a message to all online players
                String messageForEveryoneElse = ChatColor.GRAY + "A Wandering Trader has spawned near " + ChatColor.BLUE + nearestPlayer.getDisplayName();
                String messageForNearestPlayer = ChatColor.GRAY + "A Wandering Trader has spawned near you!";

                for (Player onlinePlayer : WanderingTraderHighlight.getInstance().getServer().getOnlinePlayers())
                {
                    onlinePlayer.sendMessage(onlinePlayer == nearestPlayer ? messageForNearestPlayer : messageForEveryoneElse);
                }
            }

            // Make the trader glow!
            trader.addPotionEffect(GLOW_POTION_EFFECT);
        }
    }

    /**
     * Gets the player closest to the trader. May return null.
     * @param traderLocation The location of the Wandering Trader
     * @return The player closest to the trader
     */
    private static Player getClosestPlayer(Location traderLocation)
    {
        double closestDistance = Double.MAX_VALUE;
        Player closestPlayer = null;

        for (Player onlinePlayer : WanderingTraderHighlight.getInstance().getServer().getOnlinePlayers())
        {
            Location playerLocation = onlinePlayer.getLocation();
            double distance = getDistance(traderLocation, playerLocation);
            if (distance < closestDistance)
            {
                closestPlayer = onlinePlayer;
                closestDistance = distance;
            }
        }

        return closestPlayer;
    }

    /**
     * @param locationA The first location
     * @param locationB The second location
     * @return The distance between the two given {@link Location}s
     */
    private static double getDistance(Location locationA, Location locationB)
    {
        return getDistance(
                locationA.getX(), locationA.getY(), locationA.getZ(),
                locationB.getX(), locationB.getY(), locationB.getZ()
        );
    }

    /**
     * @param x1 The {@code x} coordinate of the first location
     * @param y1 The {@code y} coordinate of the first location
     * @param z1 The {@code z} coordinate of the first location
     * @param x2 The {@code x} coordinate of the second location
     * @param y2 The {@code y} coordinate of the second location
     * @param z2 The {@code z} coordinate of the second location
     * @return The distance between the two given vectors
     */
    private static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        double x = Math.pow(x2 - x1, 2);
        double y = Math.pow(y2 - y1, 2);
        double z = Math.pow(z2 - z1, 2);

        return Math.sqrt(x + y + z);
    }
}