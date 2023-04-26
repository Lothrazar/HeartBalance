package com.lothrazar.heartbalance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ModMain.MODID)
public class ModMain {

  public static final String MODID = "heartbalance";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModMain() {
    new ConfigRegistryHearts();
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    bus.addListener(this::setup);
    ModRegistry.ITEMS.register(bus);
    ModRegistry.SOUNDS.register(bus);
  }

  private void setup(final FMLCommonSetupEvent event) {
    new HeartEvents();
  }
}
