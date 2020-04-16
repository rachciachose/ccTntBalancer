// 
// Decompiled by Procyon v0.5.30
// 

package pl.best241.cctntbalancer.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.Location;
import pl.best241.cctntbalancer.CcTntBalancer;
import org.bukkit.event.entity.EntityExplodeEvent;
import java.util.Random;
import org.bukkit.event.Listener;

public class EntityExplodeListener implements Listener
{
    private static final Random random;
    
    @EventHandler
    public static void entityExplodeListener(final EntityExplodeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        final Location location = event.getLocation();
        CcTntBalancer.addLastExplosion(location, 25);
    }
    
    static {
        random = new Random();
    }
}
