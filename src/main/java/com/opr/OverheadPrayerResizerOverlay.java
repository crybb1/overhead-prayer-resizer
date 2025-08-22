package com.opr;

import javax.inject.Inject;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import net.runelite.api.Client;
import net.runelite.api.HeadIcon;
import net.runelite.api.Player;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.game.SpriteManager;
import net.runelite.api.SpriteID;

public class OverheadPrayerResizerOverlay extends Overlay
{
    private final Client client;
    private final OverheadPrayerResizerPlugin plugin;
    private final OverheadPrayerResizerConfig config;
    private final SpriteManager spriteManager;

    @Inject
    public OverheadPrayerResizerOverlay(
            Client client,
            OverheadPrayerResizerPlugin plugin,
            OverheadPrayerResizerConfig config,
            SpriteManager spriteManager)
    {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.spriteManager = spriteManager;

        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_SCENE);
        setPriority(OverlayPriority.MED);
        setMovable(false);
    }

    @Override
    public java.awt.Dimension render(Graphics2D g)
    {
        if (client.getGameState() != net.runelite.api.GameState.LOGGED_IN) return null;

        for (Player p : client.getPlayers())
        {
            if (p == null) continue;
            if (!config.showSelf() && p == client.getLocalPlayer()) continue;

            HeadIcon icon = plugin.getHeadIcon(p);
            if (icon == null) continue;

            BufferedImage img = spriteFor(icon);
            if (img == null) continue;

            int size = config.iconSize(); // pixels
            int zOffset = config.verticalOffset(); // lift above head

            net.runelite.api.Point drawPt = plugin.getActorCanvasPoint(p, zOffset);
            if (drawPt == null) continue;

            int x = drawPt.getX() - size / 2;
            int y = drawPt.getY() - size; // anchor top-center-ish

            // Draw scaled
            g.drawImage(img, x, y, size, size, null);
        }
        return null;
    }

    private BufferedImage spriteFor(HeadIcon icon)
    {
        Integer spriteId = null;
        switch (icon)
        {
            case MELEE:        spriteId = SpriteID.PRAYER_PROTECT_FROM_MELEE; break;
            case RANGED:       spriteId = SpriteID.PRAYER_PROTECT_FROM_MISSILES; break;
            case MAGIC:        spriteId = SpriteID.PRAYER_PROTECT_FROM_MAGIC; break;
            case RETRIBUTION:  spriteId = SpriteID.PRAYER_RETRIBUTION; break;
            case SMITE:        spriteId = SpriteID.PRAYER_SMITE; break;
            case REDEMPTION:   spriteId = SpriteID.PRAYER_REDEMPTION; break;
            default: return null;
        }
        return spriteManager.getSprite(spriteId, 0);
    }
}
