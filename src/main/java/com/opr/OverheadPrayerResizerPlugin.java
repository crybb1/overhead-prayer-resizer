package com.opr;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.HeadIcon;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
        name = "Overhead Prayer Resizer",
        description = "Resize player overhead prayer icons",
        tags = {"prayer", "overhead", "icon", "resize", "pvp"}
)
public class OverheadPrayerResizerPlugin extends Plugin
{
    @Inject private Client client;
    @Inject private OverlayManager overlayManager;
    @Inject private OverheadPrayerResizerOverlay overlay;

    @Provides
    OverheadPrayerResizerConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(OverheadPrayerResizerConfig.class);
    }

    @Override
    protected void startUp()
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
    }

    // Convenience to compute where to draw above an actor
    Point getActorCanvasPoint(Player p, int zOffset)
    {
        LocalPoint lp = p.getLocalLocation();
        if (lp == null) return null;

        int plane = client.getPlane();
        int height = p.getLogicalHeight() + zOffset;

        net.runelite.api.Point pt = net.runelite.api.Perspective.localToCanvas(client, lp, plane, height);
        return pt;
    }

    HeadIcon getHeadIcon(Player p)
    {
        return p.getOverheadIcon(); // returns active overhead (protects, smite, etc.) or null
    }
}
