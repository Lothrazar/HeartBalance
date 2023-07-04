package com.lothrazar.heartbalance;

import java.util.UUID;
import com.lothrazar.heartbalance.item.ItemHeart;
import com.lothrazar.library.events.EventFlib;
import com.lothrazar.library.util.LevelWorldUtil;
import com.lothrazar.library.util.SoundUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HeartEvents extends EventFlib {

  public static final UUID ID = UUID.fromString("55550aa2-eff2-4a81-b92b-a1cb95f15555");

  private static void forceHearts(Player player) {
    AttributeInstance healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
    if (healthAttribute == null) {
      return;
    }
    AttributeModifier oldHealthModifier = healthAttribute.getModifier(ID);
    if (oldHealthModifier != null) {
      //delete and replace
      healthAttribute.removeModifier(oldHealthModifier);
    }
    //always apply to player if they do not have
    int h = 2 * ConfigRegistryHearts.INIT_HEARTS.get();
    AttributeModifier healthModifier = new AttributeModifier(ID, ModMain.MODID, h, AttributeModifier.Operation.ADDITION);
    healthAttribute.addPermanentModifier(healthModifier);
  }

  @SubscribeEvent
  public void onEntityJoinWorld(EntityJoinLevelEvent event) {
    if (event.getEntity() instanceof Player player) {
      forceHearts(player);
    }
  }

  @SubscribeEvent
  public void onPlayerCloneDeath(PlayerEvent.Clone event) {
    forceHearts(event.getEntity());
  }

  @SubscribeEvent
  public void onPlayerPickup(EntityItemPickupEvent event) {
    if (event.getEntity() instanceof Player) {
      Player player = event.getEntity();
      ItemEntity itemEntity = event.getItem();
      ItemStack resultStack = itemEntity.getItem();
      if (!resultStack.isEmpty() && resultStack.getItem() instanceof ItemHeart) {
        ItemHeart heart = (ItemHeart) resultStack.getItem();
        //try to heal one by one
        boolean healed = false;
        while (!resultStack.isEmpty() && player.isHurt()) {
          player.heal(heart.getHealing());
          resultStack.shrink(1);
          itemEntity.setItem(resultStack);
          healed = true;
        }
        if (healed && ConfigRegistryHearts.DO_SOUND_PICKUP.get()) {
          SoundUtil.playSoundFromServer((ServerPlayer) player, ModRegistry.HEART_SOUND.get(), 0.3F, 0.95F);
        }
        //all done. so EITHER player is fully healed
        // OR we ran out of items... so do we cancel?
        //dont cancel if healed = true, there might be more remaining
        if (!ConfigRegistryHearts.DO_PICKUP.get() ||
            itemEntity.getItem().isEmpty()) {
          itemEntity.remove(Entity.RemovalReason.DISCARDED);
          //cancel to block the pickup
          event.setCanceled(true);
        }
      }
    }
  }

  @SubscribeEvent
  public void onLivingDeathEvent(LivingDeathEvent event) {
    Level world = event.getEntity().level();
    if (world.isClientSide || event.getSource() == null
        || world.random.nextDouble() >= ConfigRegistryHearts.CHANCE.get()) {
      return;
    }
    //if config is at 10, and you roll in 10-100 you were cancelled,
    //else here we continue so our roll was < 10 so the percentage worked
    Entity trueSource = event.getSource().getEntity();
    if (trueSource instanceof Player && !(trueSource instanceof FakePlayer)) {
      //killed by me  
      if (event.getEntity().getType().getCategory() == MobCategory.MONSTER) {
        //drop
        BlockPos pos = event.getEntity().blockPosition();
        LevelWorldUtil.dropItemStackRandomMotion(world, pos, new ItemStack(ModRegistry.HALF_HEART.get()), 0.03F);
      }
    }
  }
}
