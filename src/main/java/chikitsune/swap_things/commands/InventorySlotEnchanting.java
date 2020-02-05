package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EnchantmentArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ForgeRegistries;

public class InventorySlotEnchanting {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("inventoryslotenchanting").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return inventorySlotEnchantingLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),null,"someone",null,null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return inventorySlotEnchantingLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null,"someone",null,null);
   }).then(Commands.argument("slotNum", StringArgumentType.string()).executes((cmd_2arg) -> {
    return inventorySlotEnchantingLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "slotNum"),"someone",null,null);
   }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_3arg) -> {
    return inventorySlotEnchantingLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "slotNum"),StringArgumentType.getString(cmd_3arg, "fromName"),null,null);
    }).then(Commands.argument("enchantment",EnchantmentArgument.enchantment()).executes((cmd_4arg) -> {
     return inventorySlotEnchantingLogic(cmd_4arg.getSource(),EntityArgument.getPlayers(cmd_4arg, "targetedPlayer"),StringArgumentType.getString(cmd_4arg, "slotNum"),StringArgumentType.getString(cmd_4arg, "fromName"),EnchantmentArgument.getEnchantment(cmd_4arg, "enchantment"),null);
    }).then(Commands.argument("enchantment_level",IntegerArgumentType.integer()).executes((cmd_5arg) -> {
     return inventorySlotEnchantingLogic(cmd_5arg.getSource(),EntityArgument.getPlayers(cmd_5arg, "targetedPlayer"),StringArgumentType.getString(cmd_5arg, "slotNum"),StringArgumentType.getString(cmd_5arg, "fromName"),EnchantmentArgument.getEnchantment(cmd_5arg, "enchantment"),IntegerArgumentType.getInteger(cmd_5arg, "enchantment_level"));
    })
     )))));
 }
 
 private static int inventorySlotEnchantingLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String slotNum, String fromName, Enchantment enchInput, Integer enchLevel) {
  ItemStack tempStack=ItemStack.EMPTY;
  Enchantment tempEnch;
  Integer iCnt=0,tempEnchLevel=0;
  Integer selectedSlotNum=null;
  Float slotNumParsed=null;
  Integer modFloatResult=null;
  
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
      } while (targetedPlayer.inventory.getStackInSlot(selectedSlotNum).isEmpty() && iCnt<50);
     }
   } else {
    iCnt=0;
    do {
    selectedSlotNum=rand.nextInt(targetedPlayer.inventory.getSizeInventory());
    iCnt++;
    } while (targetedPlayer.inventory.getStackInSlot(selectedSlotNum).isEmpty() && iCnt<50);
   }
   
   tempStack=targetedPlayer.inventory.getStackInSlot(selectedSlotNum);
   
   if (tempStack.isEmpty()) {
    source.getServer().getPlayerList().sendMessage(new StringTextComponent(TextFormatting.GOLD + "Well " + fromName + " wanted to be nice to " + TextFormatting.RED + targetedPlayer.getName().getFormattedText() + TextFormatting.GOLD + " and enchant one of their items but it was empty."));
    continue;
   }
   
   if (enchInput!=null) {
    tempEnch=enchInput;
   } else {
    iCnt=0;
    do {
     tempEnch=(Enchantment)ForgeRegistries.ENCHANTMENTS.getValues().toArray()[rand.nextInt(ForgeRegistries.ENCHANTMENTS.getValues().size())];
    iCnt++;
    } while (!tempEnch.canApply(tempStack) && iCnt<50);
   }
   
   if (enchLevel==null) {
    tempEnchLevel=rand.nextInt(tempEnch.getMaxLevel()+1);
   } else {
    tempEnchLevel=enchLevel;
   }
   if (tempEnchLevel<0) tempEnchLevel=0;
   
   Map<Enchantment, Integer> itemEnchants=EnchantmentHelper.getEnchantments(tempStack);
   Map<Enchantment, Integer> itemEnchantsNew=Maps.<Enchantment, Integer>newLinkedHashMap();
   
   for (Map.Entry<Enchantment, Integer> curMapEnch : itemEnchants.entrySet()) {
    if (curMapEnch.getKey().getRegistryName()!=tempEnch.getRegistryName()) {
     itemEnchantsNew.put(curMapEnch.getKey(), curMapEnch.getValue());
    }
   }
   
   if (itemEnchants.get(tempEnch)==null) {
    if (tempEnchLevel>0) {
     tempStack.addEnchantment(tempEnch, tempEnchLevel);
     ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getFormattedText() + "'s " + TextFormatting.AQUA + tempStack.getDisplayName().getFormattedText() +  TextFormatting.GOLD + " has gained a new power: " + TextFormatting.DARK_GREEN + tempEnch.getDisplayName(tempEnchLevel).getFormattedText() + TextFormatting.GOLD + ". Blame " + fromName));
    } else {
     ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getFormattedText() + TextFormatting.GOLD + " was convinced by " + fromName + " that they didn't need " + TextFormatting.DARK_GREEN + tempEnch.getDisplayName(1).getFormattedText() + TextFormatting.GOLD + " on their " +  TextFormatting.AQUA + tempStack.getDisplayName().getFormattedText() +  TextFormatting.GOLD + " anymore but then remembered they didn't have it anyways."));
    }
   } else {
    if (tempEnchLevel==0) {
     //remove enchantment
     EnchantmentHelper.setEnchantments(itemEnchantsNew,tempStack);
     ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getFormattedText() + TextFormatting.GOLD + " was convinced by " + fromName + " that they didn't need " + TextFormatting.DARK_GREEN + tempEnch.getDisplayName(itemEnchants.get(tempEnch)).getFormattedText() + TextFormatting.GOLD + " on their " +  TextFormatting.AQUA + tempStack.getDisplayName().getFormattedText() +  TextFormatting.GOLD + " anymore."));
    } else if (tempEnchLevel==itemEnchants.get(tempEnch)) {
     //same level so nothing is done
     ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getFormattedText() + TextFormatting.GOLD + " wasn't convinced by " + fromName + " to get rid of " + TextFormatting.DARK_GREEN + tempEnch.getDisplayName(itemEnchants.get(tempEnch)).getFormattedText() + TextFormatting.GOLD + " on their " +  TextFormatting.AQUA + tempStack.getDisplayName().getFormattedText() +  TextFormatting.GOLD + "."));
     
    } else {
     itemEnchantsNew.put(tempEnch, tempEnchLevel);
     EnchantmentHelper.setEnchantments(itemEnchantsNew,tempStack);
     if (tempEnchLevel>itemEnchants.get(tempEnch)) {
      ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getFormattedText() + "'s " + TextFormatting.AQUA + tempStack.getDisplayName().getFormattedText() +  TextFormatting.GOLD + " has increased to " + TextFormatting.DARK_GREEN + tempEnch.getDisplayName(tempEnchLevel).getFormattedText() + TextFormatting.GOLD + ". Jeez thanks " + fromName));
      } else {
       ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getFormattedText() + "'s " + TextFormatting.AQUA + tempStack.getDisplayName().getFormattedText() +  TextFormatting.GOLD + " has decreased to " + TextFormatting.DARK_GREEN + tempEnch.getDisplayName(tempEnchLevel).getFormattedText() + TextFormatting.GOLD + ". Woot! Thanks " + fromName + "!")); 
      }
    }
   }
  }
  
  return 0;
 }

}
