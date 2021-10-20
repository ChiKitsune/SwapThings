package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class InventoryEqualizer {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("inventoryequalizer").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return inventoryEqualizerLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),null,null,"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
    return inventoryEqualizerLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null,null,"someone");
    }).then(Commands.argument("item", ItemArgument.item()).executes((cmd_2arg) -> {
     return inventoryEqualizerLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),ItemArgument.getItem(cmd_2arg, "item"),null,"someone");
     }).then(Commands.argument("stackAmt",IntegerArgumentType.integer()).executes((cmd_3arg) -> {
      return inventoryEqualizerLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),ItemArgument.getItem(cmd_3arg, "item"),IntegerArgumentType.getInteger(cmd_3arg, "stackAmt"),"someone");
      }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_4arg) -> {
       return inventoryEqualizerLogic(cmd_4arg.getSource(),EntityArgument.getPlayers(cmd_4arg, "targetedPlayer"),ItemArgument.getItem(cmd_4arg, "item"),IntegerArgumentType.getInteger(cmd_4arg, "stackAmt"),StringArgumentType.getString(cmd_4arg, "fromName"));
      })))));
 }
 
 private static int inventoryEqualizerLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, ItemInput itemInput, Integer stackAmt, String fromName) {
  ArchCommand.ReloadConfig();
  ItemStack defItemStack=ItemStack.EMPTY;
  ItemStack tempItemStack=ItemStack.EMPTY;
  Integer tempStackAmt=1;
  if (stackAmt!=null) tempStackAmt=stackAmt;
  if (itemInput!=null) {
   try {
   defItemStack=itemInput.createItemStack(1, true);
   } catch (CommandSyntaxException e) {
    e.printStackTrace();
    defItemStack=new ItemStack(Items.DEAD_BUSH);
   }
  }
  
//  defItemStack=new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Configs.INVENTORY_BOMB_ITEM.get())));
//  if (defItemStack.isEmpty()) defItemStack=new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:dead_bush")));
  defItemStack.setCount(tempStackAmt);
  
  List<NonNullList<ItemStack>> allInventories;
  for(ServerPlayer targetedPlayer : targetPlayers) {
   allInventories = ImmutableList.of(targetedPlayer.getInventory().items, targetedPlayer.getInventory().armor, targetedPlayer.getInventory().offhand);
   for(List<ItemStack> list : allInventories) {
    for(int i = 0; i < list.size(); ++i) {
     tempItemStack = list.get(i);
       if (!tempItemStack.isEmpty()) {
        ibDropItem(tempItemStack,targetedPlayer);
       }
       list.set(i, defItemStack.copy());
    }
 }
   
   ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " just found out what their inventory would look like if it only had " + ChatFormatting.DARK_GREEN + defItemStack.getHoverName().getString() + ChatFormatting.GOLD + " in it."));
  }
  
  return 0;
 }
 
 @Nullable
 private static ItemEntity ibDropItem(ItemStack droppedItem, ServerPlayer targetedPlayer) {
  if (droppedItem.isEmpty()) {
   return null;
  } else {
   double d0 = targetedPlayer.getY() - (double)0.3F + (double)targetedPlayer.getEyeHeight();
   ItemEntity itementity = new ItemEntity(targetedPlayer.level, targetedPlayer.getX(), d0, targetedPlayer.getZ(), droppedItem);
   itementity.setPickUpDelay(20);
   itementity.setThrower(targetedPlayer.getUUID());
   float f = rand.nextFloat() * 0.5F;
   float f1 = rand.nextFloat() * ((float)Math.PI * 2F);
   itementity.setDeltaMovement((double)(-Mth.sin(f1) * f), (double)0.2F, (double)(Mth.cos(f1) * f));
   
   if (itementity.captureDrops() != null) itementity.captureDrops().add(itementity);
   else
    itementity.level.addFreshEntity(itementity);
   return itementity;
  }
 }

}
