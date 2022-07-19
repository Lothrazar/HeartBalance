package com.lothrazar.heartbalance.item;

import com.lothrazar.heartbalance.ConfigManager;
import com.lothrazar.heartbalance.ModRegistry;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemHeart extends Item {

  final int healAmt;

  public ItemHeart(Properties properties, int value) {
    super(properties.tab(CreativeModeTab.TAB_COMBAT));
    healAmt = value;
  }

  public int getHealing() {
    return this.healAmt;
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    TranslatableComponent t = new TranslatableComponent(getDescriptionId() + ".tooltip");
    t.withStyle(ChatFormatting.GRAY);
    tooltip.add(t);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand handIn) {
    ItemStack itemstack = player.getItemInHand(handIn);
    if (player.isHurt() && !player.getCooldowns().isOnCooldown(itemstack.getItem())) {
      player.heal(getHealing());
      player.getCooldowns().addCooldown(itemstack.getItem(), 20);
      itemstack.shrink(1);
      player.swing(handIn);
      if (world.isClientSide && ConfigManager.DO_SOUND_USE.get()) {
        player.playSound(ModRegistry.HEART_GET, 0.2F, 0.95F);
      }
      return InteractionResultHolder.success(itemstack);
    }
    return InteractionResultHolder.pass(player.getItemInHand(handIn));
  }
}
