package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class InventorySlotRenamer {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("inventoryslotrenamer").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return inventorySlotRenamerLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),null,null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return inventorySlotRenamerLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null,null);
   }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
    return inventorySlotRenamerLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"),null);
   }).then(Commands.argument("slotNum", StringArgumentType.string()).executes((cmd_3arg) -> {
    return inventorySlotRenamerLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "fromName"),StringArgumentType.getString(cmd_3arg, "slotNum"));
   }))));
 }
 
 private static int inventorySlotRenamerLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName, String slotNum) {
  ItemStack tempStack=ItemStack.EMPTY;
  Enchantment tempEnch;
  Integer iCnt=0,tempEnchLevel=0;
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
   if (!tempStack.isEmpty()) {
    prevItemName=targetedPlayer.inventory.getStackInSlot(selectedSlotNum).getDisplayName().getUnformattedComponentText();
   targetedPlayer.inventory.getStackInSlot(selectedSlotNum).setDisplayName(new StringTextComponent(strPref + tempStack.getDisplayName().getUnformattedComponentText()));
   
   ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + "Oh! " + strMsgFromName + " thought " + TextFormatting.RED + targetedPlayer.getName().getString() + "'s " + TextFormatting.GOLD + prevItemName + " should be theirs."));
   }
   else {
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + "Oh. " + strMsgFromName + " couldn't find an item they wanted in " + TextFormatting.RED + targetedPlayer.getName().getString() + "'s inventory that they liked and gave up."));
   }
  }
  
  return 0;
 }

}
