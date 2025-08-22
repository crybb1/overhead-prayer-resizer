package com.opr;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("overheadprayerresizer")
public interface OverheadPrayerResizerConfig extends Config
{
    @Range(min = 8, max = 96)
    @ConfigItem(
            keyName = "iconSize",
            name = "Icon size (px)",
            description = "Size of the overhead prayer icon in pixels",
            position = 0
    )
    default int iconSize()
    {
        return 28; // roughly default size
    }

    @ConfigItem(
            keyName = "verticalOffset",
            name = "Vertical offset",
            description = "Raise/lower icon above head (larger = higher)",
            position = 1
    )
    default int verticalOffset()
    {
        return 50;
    }

    @ConfigItem(
            keyName = "showSelf",
            name = "Show on self",
            description = "Also draw the icon on your own character",
            position = 2
    )
    default boolean showSelf()
    {
        return true;
    }
}
