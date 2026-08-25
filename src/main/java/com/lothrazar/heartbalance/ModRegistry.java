package com.lothrazar.heartbalance;

import com.lothrazar.heartbalance.item.ItemHeart;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegisterEvent;

@EventBusSubscriber(modid=ModMain.MODID)
public class ModRegistry {

  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModMain.MODID);
  public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, ModMain.MODID);
  private static final ResourceKey<CreativeModeTab> TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(ModMain.MODID, "tab"));

  @SubscribeEvent
  public static void onCreativeModeTabRegister(RegisterEvent event) {
    event.register(Registries.CREATIVE_MODE_TAB, helper -> {
      helper.register(TAB, CreativeModeTab.builder().icon(() -> new ItemStack(HALF_HEART.get()))
          .title(Component.translatable("itemGroup." + ModMain.MODID))
          .displayItems((enabledFlags, populator) -> {
            for (DeferredHolder<Item, ? extends Item> entry : ITEMS.getEntries()) {
              populator.accept(entry.get());
            }
          }).build());
    });
  }

  public static final DeferredHolder<Item,ItemHeart> REFILL_HEART = ITEMS.registerItem("refill_heart", props -> new ItemHeart(props, 20));
  public static final DeferredHolder<Item,ItemHeart> HALF_HEART = ITEMS.registerItem("half_heart", props -> new ItemHeart(props, 1));
  public static final DeferredHolder<Item,ItemHeart> FULL_HEART = ITEMS.registerItem("full_heart", props -> new ItemHeart(props, 2));
  public static final DeferredHolder<SoundEvent,SoundEvent> HEART_SOUND = SOUNDS.register("heart_get", () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(ModMain.MODID, "heart_get")));
}
