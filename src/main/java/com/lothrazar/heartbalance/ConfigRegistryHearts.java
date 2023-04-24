package com.lothrazar.heartbalance;

import com.lothrazar.library.config.ConfigTemplate;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ConfigRegistryHearts extends ConfigTemplate {

  private static ForgeConfigSpec CONFIG;
  public static IntValue INIT_HEARTS;
  public static DoubleValue CHANCE;
  public static BooleanValue DO_SOUND_USE;
  public static BooleanValue DO_SOUND_PICKUP;
  public static BooleanValue DO_PICKUP;
  static {
    final ForgeConfigSpec.Builder BUILDER = builder();
    BUILDER.comment("General settings").push(ModMain.MODID);
    DO_SOUND_USE = BUILDER.comment("\r\nPlay sounds on heart pickup").define("soundOnUse", true);
    DO_SOUND_PICKUP = BUILDER.comment("\r\nPlay sounds on heart pickup").define("soundOnPickup", true);
    DO_PICKUP = BUILDER.comment("\r\nIf true, then a player with full health walking into a heart will get it as an item (false will vanish)").define("allowPickup", true);
    INIT_HEARTS = BUILDER.comment("\r\nEdit players maximum hearts, for all players as an offset: "
        + "Zero means normal 10 hearts no changes; -7 means players start with 3 hearts; 5 means you spawn with 15 hearts")
        .defineInRange("spawnHeartOffset", 0, -9, 90);
    CHANCE = BUILDER.comment("\r\nChance for a kill to drop a half heart refill")
        .defineInRange("lootHeartChances", 0.1, 0, 1);
    BUILDER.pop(); // one pop for every push
    CONFIG = BUILDER.build();
  }

  public ConfigRegistryHearts() {
    CONFIG.setConfig(setup(ModMain.MODID));
  }
}
