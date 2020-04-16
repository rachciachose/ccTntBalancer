// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.cctntbalancer.listeners;

import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.Material;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import pl.best241.cctntbalancer.CcTntBalancer;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.Listener;

public class BlockPhysicsListener implements Listener
{
    @EventHandler
    public static void blockPhisycsListener(final BlockPhysicsEvent event) {
        if (event.getBlock().isLiquid()) {
            final Location location = event.getBlock().getLocation();
            if (!CcTntBalancer.canPhysicsWork(location, 2.0)) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public static void onLiquidSpread(final BlockFromToEvent event) {
        final Block block = event.getToBlock();
        if (block.isLiquid()) {
            final Location location = block.getLocation();
            if (!CcTntBalancer.canPhysicsWork(location, 6.0)) {
                Bukkit.getScheduler().runTask((Plugin)CcTntBalancer.getPlugin(), () -> event.getToBlock().setType(Material.AIR));
                event.setCancelled(true);
            }
        }
    }
}
