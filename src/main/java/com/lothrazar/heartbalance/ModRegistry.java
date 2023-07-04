package com.lothrazar.heartbalance;

import com.lothrazar.heartbalance.item.ItemHeart;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MODID);
  public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ModMain.MODID);
  private static final ResourceKey<CreativeModeTab> TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, new ResourceLocation(ModMain.MODID, "tab"));

  @SubscribeEvent
  public static void onCreativeModeTabRegister(RegisterEvent event) {
    event.register(Registries.CREATIVE_MODE_TAB, helper -> {
      helper.register(TAB, CreativeModeTab.builder().icon(() -> new ItemStack(HALF_HEART.get()))
          .title(Component.translatable("itemGroup." + ModMain.MODID))
          .displayItems((enabledFlags, populator) -> {
            for (RegistryObject<Item> entry : ITEMS.getEntries()) {
              populator.accept(entry.get());
            }
          }).build());
    });
  }

  public static final RegistryObject<Item> REFILL_HEART = ITEMS.register("refill_heart", () -> new ItemHeart(new Item.Properties(), 20));
  public static final RegistryObject<Item> HALF_HEART = ITEMS.register("half_heart", () -> new ItemHeart(new Item.Properties(), 1));
  public static final RegistryObject<Item> FULL_HEART = ITEMS.register("full_heart", () -> new ItemHeart(new Item.Properties(), 2));
  public static final RegistryObject<SoundEvent> HEART_SOUND = SOUNDS.register("heart_get", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(ModMain.MODID, "heart_get")));
}
