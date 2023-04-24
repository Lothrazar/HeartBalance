package com.lothrazar.heartbalance;

import com.lothrazar.heartbalance.item.ItemHeart;
import com.lothrazar.library.registry.RegistryFactory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MODID);
  public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ModMain.MODID);

  @SubscribeEvent
  public static void buildContents(CreativeModeTabEvent.Register event) {
    RegistryFactory.buildTab(event, ModMain.MODID, HALF_HEART.get().asItem(), ITEMS);
  }

  public static final RegistryObject<Item> REFILL_HEART = ITEMS.register("refill_heart", () -> new ItemHeart(new Item.Properties(), 20));
  public static final RegistryObject<Item> HALF_HEART = ITEMS.register("half_heart", () -> new ItemHeart(new Item.Properties(), 1));
  public static final RegistryObject<Item> FULL_HEART = ITEMS.register("full_heart", () -> new ItemHeart(new Item.Properties(), 2));
  public static final RegistryObject<SoundEvent> HEART_SOUND = SOUNDS.register("heart_get", () -> SoundEvent.createVariableRangeEvent(make("heart_get")));

  private static ResourceLocation make(String s) {
    return new ResourceLocation(ModMain.MODID, s);
  }
}
