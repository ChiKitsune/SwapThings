package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class InventorySlotClearer {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("inventoryslotclearer").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return inventorySlotClearerLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),null,"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return inventorySlotClearerLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null,"someone");
     }).then(Commands.argument("slotNum", StringArgumentType.string()).executes((cmd_2arg) -> {
      return inventorySlotClearerLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "slotNum"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_3arg) -> {
      return inventorySlotClearerLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "slotNum"),StringArgumentType.getString(cmd_3arg, "fromName"));
      })
     )));
 }
  
  private static int inventorySlotClearerLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String slotNum, String fromName) {
   ArchCommand.ReloadConfig();
   Integer selectedSlotNum=null;
   Float slotNumParsed=null;
   Integer modFloatResult=null;
   ItemStack tempStack=ItemStack.EMPTY;
   
   try {
    slotNumParsed=Float.parseFloat(slotNum);
   } catch (Exception e) {
    slotNumParsed=-1F;
   }
   
   for(ServerPlayer targetedPlayer : targetPlayers) {
    tempStack=ItemStack.EMPTY;
    
    if(slotNumParsed!=null && (slotNumParsed == 0 || (slotNumParsed % 1 == 0) ) && slotNum !=null) {
     selectedSlotNum=targetedPlayer.getInventory().selected;
    } else if(slotNumParsed!=null && slotNumParsed > 0 && slotNum !=null) {
     modFloatResult=Math.round((slotNumParsed % 1)*100) -1;
      if (slotNumParsed % 1 < targetedPlayer.getInventory().getContainerSize() && slotNumParsed % 1 > 0) {
       selectedSlotNum=modFloatResult.intValue();       
      } else {
       selectedSlotNum=rand.nextInt(targetedPlayer.getInventory().getContainerSize());
      }
    } else {
     selectedSlotNum=rand.nextInt(targetedPlayer.getInventory().getContainerSize());
    }
    
    tempStack=targetedPlayer.getInventory().getItem(selectedSlotNum).copy();
    targetedPlayer.getInventory().setItem(selectedSlotNum, ItemStack.EMPTY.copy());
   
    if (tempStack.isEmpty()) {
     ArchCommand.playerMsger(source, targetPlayers, 
       Component.literal("Oh! " + fromName + " tried to clear an inventory slot from ").withStyle(ChatFormatting.GOLD)
       .append(Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED))
       .append(Component.literal(" but it was already empty.").withStyle(ChatFormatting.GOLD)));
//     ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.GOLD + "Oh! " + fromName + " tried to clear an inventory slot from " + ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " but it was already empty."));
    } else {
     ArchCommand.playerMsger(source, targetPlayers, 
       Component.literal("Oh! " + fromName + " just took "  + tempStack.getCount() + " of ").withStyle(ChatFormatting.GOLD)
       .append(Component.literal(targetedPlayer.getName().getString() + "'s ").withStyle(ChatFormatting.RED))
       .append(Component.literal(tempStack.getHoverName().getString() + ".").withStyle(ChatFormatting.GOLD)));
//     ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.GOLD + "Oh! " + fromName + " just took "  + tempStack.getCount() + " of " + ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + "'s " + tempStack.getHoverName().getString()));
    }
   }
   return 0;
  }

}
