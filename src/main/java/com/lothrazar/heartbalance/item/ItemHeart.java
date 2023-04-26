package com.lothrazar.heartbalance.item;

import com.lothrazar.heartbalance.ConfigRegistryHearts;
import com.lothrazar.heartbalance.ModRegistry;
import com.lothrazar.library.item.ItemFlib;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemHeart extends ItemFlib {

  private static final int COOLDOWN = 20;
  final int healAmt;

  public ItemHeart(Properties properties, int value) {
    super(properties, new Settings().tooltip());
    healAmt = value;
  }

  public int getHealing() {
    return this.healAmt;
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand handIn) {
    ItemStack itemstack = player.getItemInHand(handIn);
    if (player.isHurt() && !player.getCooldowns().isOnCooldown(itemstack.getItem())) {
      player.heal(getHealing());
      player.getCooldowns().addCooldown(itemstack.getItem(), COOLDOWN);
      //      ItemStackUtil.shrink(player, itemstack);
      itemstack.shrink(1);
      player.swing(handIn);
      if (world.isClientSide && ConfigRegistryHearts.DO_SOUND_USE.get()) {
        player.playSound(ModRegistry.HEART_SOUND.get(), 0.2F, 0.95F);
      }
      return InteractionResultHolder.success(itemstack);
    }
    return InteractionResultHolder.pass(player.getItemInHand(handIn));
  }
}
