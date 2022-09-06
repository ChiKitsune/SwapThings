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

public class InventorySlotRenamer {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("inventoryslotrenamer").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return inventorySlotRenamerLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),null,null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return inventorySlotRenamerLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null,null);
   }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
    return inventorySlotRenamerLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"),null);
   }).then(Commands.argument("slotNum", StringArgumentType.string()).executes((cmd_3arg) -> {
    return inventorySlotRenamerLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "fromName"),StringArgumentType.getString(cmd_3arg, "slotNum"));
   }))));
 }
 
 private static int inventorySlotRenamerLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName, String slotNum) {
  ArchCommand.ReloadConfig();
  ItemStack tempStack=ItemStack.EMPTY;
  Integer iCnt=0;
  Integer selectedSlotNum=null;
  Float slotNumParsed=null;
  Integer modFloatResult=null;
  String prevItemName=null;
  String strPref="Someone's ",strMsgFromName="Someone";
  if (fromName!=null) {
   strPref=fromName + "'s ";
   strMsgFromName=fromName;
  }
  
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
      iCnt=0;
      do {
      selectedSlotNum=rand.nextInt(targetedPlayer.getInventory().getContainerSize());
      iCnt++;
      } while (targetedPlayer.getInventory().getItem(selectedSlotNum).isEmpty() && iCnt<100);
     }
   } else {
    iCnt=0;
    do {
    selectedSlotNum=rand.nextInt(targetedPlayer.getInventory().getContainerSize());
    iCnt++;
    } while (targetedPlayer.getInventory().getItem(selectedSlotNum).isEmpty() && iCnt<100);
   }
   
   tempStack=targetedPlayer.getInventory().getItem(selectedSlotNum);
   if (!tempStack.isEmpty()) {
    prevItemName=targetedPlayer.getInventory().getItem(selectedSlotNum).getHoverName().getString();
    targetedPlayer.getInventory().getItem(selectedSlotNum).setHoverName(Component.literal(strPref + tempStack.getHoverName().getString()));
//    prevItemName=targetedPlayer.inventory.getStackInSlot(selectedSlotNum).getDisplayName().getUnformattedComponentText();
//    targetedPlayer.inventory.getStackInSlot(selectedSlotNum).setDisplayName(new StringTextComponent(strPref + tempStack.getDisplayName().getUnformattedComponentText()));
   
    ArchCommand.playerMsger(source, targetPlayers, 
      Component.literal("Oh! " + strMsgFromName + " thought ").withStyle(ChatFormatting.GOLD)
      .append(Component.literal(targetedPlayer.getName().getString() + "'s ").withStyle(ChatFormatting.RED))
      .append(Component.literal(prevItemName + "should be theirs.").withStyle(ChatFormatting.GOLD)));
//   ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.GOLD + "Oh! " + strMsgFromName + " thought " + ChatFormatting.RED + targetedPlayer.getName().getString() + "'s " + ChatFormatting.GOLD + prevItemName + " should be theirs."));
   }
   else {
    ArchCommand.playerMsger(source, targetPlayers, 
      Component.literal("Oh! " + strMsgFromName + " couldn't find an item they wanted in ").withStyle(ChatFormatting.GOLD)
      .append(Component.literal(targetedPlayer.getName().getString() + "'s ").withStyle(ChatFormatting.RED))
      .append(Component.literal("inventory that they liked and gave up.").withStyle(ChatFormatting.GOLD)));
//    ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.GOLD + "Oh. " + strMsgFromName + " couldn't find an item they wanted in " + ChatFormatting.RED + targetedPlayer.getName().getString() + "'s inventory that they liked and gave up."));
   }
  }
  
  return 0;
 }

}
