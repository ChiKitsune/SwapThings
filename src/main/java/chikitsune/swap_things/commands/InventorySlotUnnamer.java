package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class InventorySlotUnnamer {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("inventoryslotunnamer").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return inventorySlotUnnamerLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),null,null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return inventorySlotUnnamerLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null,null);
   }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
    return inventorySlotUnnamerLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"),null);
   }).then(Commands.argument("slotNum", StringArgumentType.string()).executes((cmd_3arg) -> {
    return inventorySlotUnnamerLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "fromName"),StringArgumentType.getString(cmd_3arg, "slotNum"));
   }))));
 }
 
 private static int inventorySlotUnnamerLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName, String slotNum) {
  ItemStack tempStack=ItemStack.EMPTY;
  Integer iCnt=0;
  Integer selectedSlotNum=null;
  Float slotNumParsed=null;
  Integer modFloatResult=null;
  String prevItemName=null;
  String strMsgFromName="Someone";
  if (fromName!=null) {
   strMsgFromName=fromName;
  }
  
  try {
   slotNumParsed=Float.parseFloat(slotNum);
  } catch (Exception e) {
   slotNumParsed=-1F;
  }
  
  for(ServerPlayerEntity targetedPlayer : targetPlayers) {
   tempStack=ItemStack.EMPTY;
   
   if(slotNumParsed!=null && (slotNumParsed == 0 || (slotNumParsed % 1 == 0) ) && slotNum !=null) {
    selectedSlotNum=targetedPlayer.inventory.currentItem;
   } else if(slotNumParsed!=null && slotNumParsed > 0 && slotNum !=null) {
    modFloatResult=Math.round((slotNumParsed % 1)*100) -1;
     if (slotNumParsed % 1 < targetedPlayer.inventory.getSizeInventory() && slotNumParsed % 1 > 0) {
      selectedSlotNum=modFloatResult.intValue();    
     } else {
      iCnt=0;
      do {
      selectedSlotNum=rand.nextInt(targetedPlayer.inventory.getSizeInventory());
      iCnt++;
      } while (targetedPlayer.inventory.getStackInSlot(selectedSlotNum).isEmpty() && iCnt<100);
     }
   } else {
    iCnt=0;
    do {
    selectedSlotNum=rand.nextInt(targetedPlayer.inventory.getSizeInventory());
    iCnt++;
    } while (targetedPlayer.inventory.getStackInSlot(selectedSlotNum).isEmpty() && iCnt<100);
   }
   
   tempStack=targetedPlayer.inventory.getStackInSlot(selectedSlotNum);
   if (!tempStack.isEmpty() && tempStack.hasDisplayName()) {
    targetedPlayer.inventory.getStackInSlot(selectedSlotNum).setDisplayName(null);
   
   ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + "Oh! " + strMsgFromName + " thought " + TextFormatting.RED + targetedPlayer.getName().getString() + "'s " + TextFormatting.GOLD + prevItemName + " had too fancy of a name and made it plain."));
   }
   else if (!tempStack.isEmpty() && !tempStack.hasDisplayName()) {
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + "Oh! " + strMsgFromName + " thought " + TextFormatting.RED + targetedPlayer.getName().getString() + "'s " + TextFormatting.GOLD + prevItemName + " had too fancy of a name but it wasn't."));
   }
   else {
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + "Oh. " + strMsgFromName + " couldn't find an item that had a fancy name in " + TextFormatting.RED + targetedPlayer.getName().getString() + "'s inventory."));
   }
  }
  
  return 0;
 }

}
