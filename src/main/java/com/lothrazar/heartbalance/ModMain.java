package com.lothrazar.heartbalance;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ModMain.MODID)
public class ModMain {

  public static final String MODID = "heartbalance";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModMain(IEventBus bus, ModContainer modContainer) {
    modContainer.registerConfig(ModConfig.Type.COMMON, ConfigRegistryHearts.CONFIG);
//    new ConfigRegistryHearts();
//    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

    ModRegistry.ITEMS.register(bus);
    ModRegistry.SOUNDS.register(bus);
    NeoForge.EVENT_BUS.register(new HeartEvents());
  }

//  private void setup(final FMLCommonSetupEvent event) {
//    new HeartEvents();
//  }
}
