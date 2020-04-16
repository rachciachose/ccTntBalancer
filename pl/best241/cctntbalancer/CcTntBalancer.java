// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.cctntbalancer;

import org.bukkit.Bukkit;
import java.util.Iterator;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.v1_7_R4.Block;
import pl.best241.cctntbalancer.listeners.BlockPhysicsListener;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.Listener;
import pl.best241.cctntbalancer.listeners.EntityExplodeListener;
import org.bukkit.Location;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.plugin.java.JavaPlugin;

public class CcTntBalancer extends JavaPlugin
{
    private static final HashMap<Integer, Float> blocks;
    private static final ArrayList<Location> lastExplosions;
    private static CcTntBalancer plugin;
    
    public void onEnable() {
        CcTntBalancer.plugin = this;
        this.getServer().getPluginManager().registerEvents((Listener)new EntityExplodeListener(), (Plugin)this);
        this.getServer().getPluginManager().registerEvents((Listener)new BlockPhysicsListener(), (Plugin)this);
        loadNewBlockDurability();
        reflectBlockHardness();
    }
    
    public static CcTntBalancer getPlugin() {
        return CcTntBalancer.plugin;
    }
    
    public static void loadNewBlockDurability() {
        CcTntBalancer.blocks.put(49, 73.8f);
        CcTntBalancer.blocks.put(116, 73.8f);
        CcTntBalancer.blocks.put(130, 73.8f);
        CcTntBalancer.blocks.put(145, 73.8f);
        CcTntBalancer.blocks.put(120, 73.8f);
        CcTntBalancer.blocks.put(8, 16.0f);
        CcTntBalancer.blocks.put(9, 16.0f);
        CcTntBalancer.blocks.put(10, 19.5f);
        CcTntBalancer.blocks.put(11, 19.5f);
        CcTntBalancer.blocks.put(2, 10.0f);
        CcTntBalancer.blocks.put(3, 10.0f);
    }
    
    public static void reflectBlockHardness() {
        try {
            final Field blocksDurability = Block.class.getDeclaredField("durability");
            blocksDurability.setAccessible(true);
            for (final int id : CcTntBalancer.blocks.keySet()) {
                blocksDurability.set(Block.getById(id), CcTntBalancer.blocks.get(id));
            }
        }
        catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex3) {
            final Exception ex2;
            final Exception ex = ex2;
            Logger.getLogger(CcTntBalancer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void addLastExplosion(final Location loc, final int ticks) {
        CcTntBalancer.lastExplosions.add(loc);
        Bukkit.getScheduler().runTaskLater((Plugin)CcTntBalancer.plugin, (Runnable)new Runnable() {
            @Override
            public void run() {
                CcTntBalancer.lastExplosions.remove(loc);
            }
        }, (long)ticks);
    }
    
    public static boolean canPhysicsWork(final Location loc, final double radius) {
        for (final Location tntLoc : CcTntBalancer.lastExplosions) {
            if (tntLoc.distance(loc) <= radius) {
                return false;
            }
        }
        return true;
    }
    
    static {
        blocks = new HashMap<Integer, Float>();
        lastExplosions = new ArrayList<Location>();
    }
}
