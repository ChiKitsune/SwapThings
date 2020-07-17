package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ForgeRegistries;

public class InventoryBomb {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("inventorybomb").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return inventoryBombLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return inventoryBombLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return inventoryBombLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
 
 private static int inventoryBombLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName) {
  ItemStack defItemStack=ItemStack.EMPTY;
  ItemStack tempItemStack=ItemStack.EMPTY;

  defItemStack=new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Configs.inventoryBombItem)));
  if (defItemStack.isEmpty()) defItemStack=new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:dead_bush")));
  defItemStack.setCount(defItemStack.getMaxStackSize());
  
  List<NonNullList<ItemStack>> allInventories;
  for(ServerPlayerEntity targetedPlayer : targetPlayers) {
   allInventories = ImmutableList.of(targetedPlayer.inventory.mainInventory, targetedPlayer.inventory.armorInventory, targetedPlayer.inventory.offHandInventory);
   for(List<ItemStack> list : allInventories) {
    for(int i = 0; i < list.size(); ++i) {
     tempItemStack = list.get(i);
       if (!tempItemStack.isEmpty()) {
//        targetedPlayer.dropItem(tempItemStack, true, false);
        ibDropItem(tempItemStack,targetedPlayer);
       }
       list.set(i, defItemStack.copy());
    }
 }
   
   ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " just found out what their inventory would look like if it only had " + TextFormatting.DARK_GREEN + defItemStack.getDisplayName().getString() + TextFormatting.GOLD + " in it."));
  }
  
  return 0;
 }
 
 @Nullable
 private static ItemEntity ibDropItem(ItemStack droppedItem, ServerPlayerEntity targetedPlayer) {
  if (droppedItem.isEmpty()) {
   return null;
  } else {
   double d0 = targetedPlayer.getPosY() - (double)0.3F + (double)targetedPlayer.getEyeHeight();
   ItemEntity itementity = new ItemEntity(targetedPlayer.world, targetedPlayer.getPosX(), d0, targetedPlayer.getPosZ(), droppedItem);
   itementity.setPickupDelay(20);
   itementity.setThrowerId(targetedPlayer.getUniqueID());
   float f = rand.nextFloat() * 0.5F;
   float f1 = rand.nextFloat() * ((float)Math.PI * 2F);
   itementity.setMotion((double)(-MathHelper.sin(f1) * f), (double)0.2F, (double)(MathHelper.cos(f1) * f));
   
   if (itementity.captureDrops() != null) itementity.captureDrops().add(itementity);
   else
    itementity.world.addEntity(itementity);
   return itementity;
  }
 }

}
