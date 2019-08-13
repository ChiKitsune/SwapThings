package chikitsune.swap_things.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.commands.arguments.RandomArmorSlotArgument;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.ComponentArgument;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class SwapArmor {
 public static Random rand= new Random();
// public static List<String> armorList = Arrays.asList("HELM","CHEST","LEGS","BOOTS","OFFHAND","RANDOM","SET","ALL");
 public static List<String> realArmorList = Arrays.asList("MAINHAND", "OFFHAND","FEET","LEGS","CHEST","HEAD");
  
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("swaparmor").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return swapArmorLogic(cmd_0arg.getSource(),"RANDOM",cmd_0arg.getSource().asPlayer(),cmd_0arg.getSource().asPlayer());
  }).then(Commands.argument("armorType", RandomArmorSlotArgument.allArmorSlots()).executes((cmd_1arg) -> {
   return swapArmorLogic(cmd_1arg.getSource(),RandomArmorSlotArgument.getRandomArmorSlot(cmd_1arg, "armorType"),cmd_1arg.getSource().asPlayer(),cmd_1arg.getSource().asPlayer());
  }).then(Commands.argument("targetedPlayerOne", EntityArgument.players()).executes((cmd_2arg) -> {
   return swapArmorLogic(cmd_2arg.getSource(),RandomArmorSlotArgument.getRandomArmorSlot(cmd_2arg, "armorType"),EntityArgument.getPlayer(cmd_2arg, "targetedPlayerOne"),cmd_2arg.getSource().asPlayer());
  }).then(Commands.argument("targetedPlayerTwo", EntityArgument.players()).executes((cmd_3arg) -> {
   return swapArmorLogic(cmd_3arg.getSource(),RandomArmorSlotArgument.getRandomArmorSlot(cmd_3arg, "armorType"),EntityArgument.getPlayer(cmd_3arg, "targetedPlayerOne"),EntityArgument.getPlayer(cmd_3arg, "targetedPlayerTwo"));
  }))));
 }
 
 private static int swapArmorLogic(CommandSource source, String armorType, ServerPlayerEntity targetedPlayerOne, ServerPlayerEntity targetedPlayerTwo) {
  if (targetedPlayerOne.getName().getUnformattedComponentText() == targetedPlayerTwo.getName().getUnformattedComponentText()) targetedPlayerTwo=ArchCommand.getNewRandomSecondTarget(targetedPlayerOne, targetedPlayerTwo, source.getServer());
  
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("armorType= "+ armorType + "-."));
  
  Boolean isRandomArmorSlot="RANDOM".equals(armorType.toUpperCase());
  String targetedArmorSlotOne="";
  String targetedArmorSlotTwo="";
  
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("After init"));
  
  if (isRandomArmorSlot) {
//   targetedArmorSlotOne=realArmorList.get(rand.nextInt(realArmorList.size()));
//   targetedArmorSlotTwo=realArmorList.get(rand.nextInt(realArmorList.size()));
   targetedArmorSlotOne=getRandomArmorSlotTarget(targetedPlayerOne,armorType,isRandomArmorSlot);
   targetedArmorSlotTwo=getRandomArmorSlotTarget(targetedPlayerTwo,armorType,isRandomArmorSlot);
   } else {
   targetedArmorSlotOne=armorType;
   targetedArmorSlotTwo=armorType;
   }
  
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("After slots = " + armorType));
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("targetedArmorSlotOne = " + targetedArmorSlotOne));
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("targetedArmorSlotTwo = " + targetedArmorSlotTwo));
  
  String targetedArmorNameOne="", targetedArmorNameTwo="";
  
  if ((!"ALL".equals(targetedArmorSlotOne.toUpperCase()) && !"SET".equals(targetedArmorSlotOne.toUpperCase())) && targetedPlayerOne.getItemStackFromSlot(EquipmentSlotType.fromString(targetedArmorSlotOne.toLowerCase())) != ItemStack.EMPTY) {
   targetedArmorNameOne=targetedPlayerOne.getItemStackFromSlot(EquipmentSlotType.fromString(targetedArmorSlotOne.toLowerCase())).getDisplayName().getFormattedText();
  }
//  if (targetedPlayerTwo.getItemStackFromSlot(EquipmentSlotType.fromString(targetedArmorSlotTwo.toLowerCase())) != ItemStack.EMPTY) {
  if ((!"ALL".equals(targetedArmorSlotTwo.toUpperCase()) && !"SET".equals(targetedArmorSlotTwo.toUpperCase())) && targetedPlayerTwo.getItemStackFromSlot(EquipmentSlotType.fromString(targetedArmorSlotTwo.toLowerCase())) != ItemStack.EMPTY) {
   targetedArmorNameTwo=targetedPlayerTwo.getItemStackFromSlot(EquipmentSlotType.fromString(targetedArmorSlotTwo.toLowerCase())).getDisplayName().getFormattedText();
  }
  
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("After get stack"));
  
  String targetedArmorDescOne = "",targetedArmorDescTwo = "";
  String targetedArmorSlotDescOne = "",targetedArmorSlotDescTwo = "";
  
  switch (armorType.toUpperCase()) {
   case "ALL":
//    source.getServer().getPlayerList().sendMessage(new StringTextComponent("ALL case"));
    ArchCommand.swapArmorItems(targetedPlayerOne, targetedPlayerTwo, "OFFHAND");
    ArchCommand.swapArmorItems(targetedPlayerOne, targetedPlayerTwo, "MAINHAND");
    targetedArmorDescOne="equipment";
    targetedArmorDescTwo="equipment";
   case "SET":
//    source.getServer().getPlayerList().sendMessage(new StringTextComponent("SET case"));
    ArchCommand.swapArmorItems(targetedPlayerOne, targetedPlayerTwo, "HEAD");
    ArchCommand.swapArmorItems(targetedPlayerOne, targetedPlayerTwo, "CHEST");
    ArchCommand.swapArmorItems(targetedPlayerOne, targetedPlayerTwo, "LEGS");
    ArchCommand.swapArmorItems(targetedPlayerOne, targetedPlayerTwo, "FEET");
    if ("SET".equals(armorType.toUpperCase())) {
     targetedArmorDescOne="armor";
     targetedArmorDescTwo="armor";
    }
    break;
   default:
//    source.getServer().getPlayerList().sendMessage(new StringTextComponent("default case"));
    targetedArmorDescOne=targetedArmorNameOne;
    targetedArmorSlotDescOne=getArmorSlotDescription(targetedArmorSlotOne);
    targetedArmorDescTwo=targetedArmorNameTwo;
    targetedArmorSlotDescTwo=getArmorSlotDescription(targetedArmorSlotTwo);    
    ArchCommand.swapArmorItems(targetedPlayerOne, targetedPlayerTwo, targetedArmorSlotOne, targetedArmorSlotTwo);
    break;
  }
  
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("targetedArmorSlotOne = " + targetedArmorSlotOne));
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("targetedArmorDescOne = " + targetedArmorDescOne));
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("targetedArmorSlotDescOne = " + targetedArmorSlotDescOne));
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("targetedArmorSlotTwo = " + targetedArmorSlotTwo));
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("targetedArmorDescTwo = " + targetedArmorDescTwo));
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("targetedArmorSlotDescTwo = " + targetedArmorSlotDescTwo));
  
  StringTextComponent txtMsg=null,txtMsg2 = null;
  
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("p1= "+targetedPlayerOne.getName().getUnformattedComponentText()));
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("p2= "+targetedPlayerTwo.getName().getUnformattedComponentText()));
  
  if (targetedPlayerOne.getName().getUnformattedComponentText() == targetedPlayerTwo.getName().getUnformattedComponentText()) {
//   source.getServer().getPlayerList().sendMessage(new StringTextComponent("Same"));
   txtMsg=new StringTextComponent(TextFormatting.RED + targetedPlayerOne.getName().getFormattedText() + TextFormatting.GOLD + " you may want to rethink trying to swap " + armorType.toLowerCase() + " with yourself.");
  } else {
//   source.getServer().getPlayerList().sendMessage(new StringTextComponent("diffName"));
   if (!"ALL".equals(armorType.toUpperCase()) && !"SET".equals(armorType.toUpperCase())) {
//    source.getServer().getPlayerList().sendMessage(new StringTextComponent("Not ALL and not SET"));
   if (targetedArmorNameTwo == "") {
//    source.getServer().getPlayerList().sendMessage(new StringTextComponent("EmptyItem1"));
    txtMsg=new StringTextComponent(TextFormatting.GOLD + "Wow " + TextFormatting.RED + targetedPlayerTwo.getName().getFormattedText() + TextFormatting.GOLD + " tried trading their nonexistent " + targetedArmorSlotDescTwo.toLowerCase() + " for ");
   } else {
//    source.getServer().getPlayerList().sendMessage(new StringTextComponent("NonEmptyItem1"));
    txtMsg=new StringTextComponent(TextFormatting.GOLD + "Wow " + TextFormatting.RED + targetedPlayerTwo.getName().getFormattedText() + TextFormatting.GOLD + " traded their " + targetedArmorDescTwo.toLowerCase() + " for ");
   }
   if (targetedArmorNameOne == "") {
//    source.getServer().getPlayerList().sendMessage(new StringTextComponent("EmptyItem2"));
    txtMsg2=new StringTextComponent(TextFormatting.RED + targetedPlayerOne.getName().getFormattedText() + TextFormatting.GOLD + "'s nonexistent " + targetedArmorSlotDescOne.toLowerCase() + ".");
   } else {
//    source.getServer().getPlayerList().sendMessage(new StringTextComponent("NonEmptyItem2"));
    txtMsg2=new StringTextComponent(TextFormatting.RED + targetedPlayerOne.getName().getFormattedText() + TextFormatting.GOLD + "'s " + targetedArmorDescOne.toLowerCase() + ".");
   }
  } else {
//   source.getServer().getPlayerList().sendMessage(new StringTextComponent("ALL or SET"));
   txtMsg=new StringTextComponent(TextFormatting.RED + targetedPlayerTwo.getName().getFormattedText() + TextFormatting.GOLD + " traded all of their " + targetedArmorDescTwo + " for all of " + TextFormatting.RED + targetedPlayerOne.getName().getFormattedText() + TextFormatting.GOLD + "'s.");
  }
  }
//  source.getServer().getPlayerList().sendMessage(new StringTextComponent("Before append"));
  if (txtMsg2!=null) txtMsg.appendSibling(txtMsg2);
//   if (txtMsg2!=null) txtMsg.appendText(""+txtMsg2);
//   source.getServer().getPlayerList().sendMessage(new StringTextComponent("After append"));
  
  source.getServer().getPlayerList().sendMessage(txtMsg);
  
  return 0;  
 }
 
 public static String getRandomArmorSlotTarget(ServerPlayerEntity targetedPlayerOne,String targetedArmorSlot,Boolean isRandomArmorSlot) {
  ItemStack tempItem=ItemStack.EMPTY;
  Integer iCnt=0;
  Integer tempRand=0;
  String newEquipmentSlotTarget="";
  
  if (targetedArmorSlot == "RANDOM") {
   targetedArmorSlot=realArmorList.get(rand.nextInt(realArmorList.size()));
   isRandomArmorSlot=true;
   }
  tempItem=targetedPlayerOne.getItemStackFromSlot(EquipmentSlotType.fromString(targetedArmorSlot));
  
  if (tempItem == ItemStack.EMPTY && isRandomArmorSlot) {
   do {
    tempRand=rand.nextInt(6);
    switch (tempRand) {
     case 0: targetedArmorSlot="HEAD"; break;
     case 1: targetedArmorSlot="CHEST"; break;
     case 2: targetedArmorSlot="LEGS"; break;
     case 3: targetedArmorSlot="FEET"; break;
     case 4: targetedArmorSlot="OFFHAND"; break;
     case 5: targetedArmorSlot="MAINHAND"; break;
     default: break;
    }
    tempItem=targetedPlayerOne.getItemStackFromSlot(EquipmentSlotType.fromString(targetedArmorSlot));
    iCnt++;
   } while (tempItem == ItemStack.EMPTY && iCnt<=25);
  }
  return newEquipmentSlotTarget;
 }
 
 public static String getArmorSlotDescription(String targetedArmorSlot) {
  String armorSlotDesc="";
  
  switch (targetedArmorSlot) {
   case "HEAD": armorSlotDesc="HELM"; break;
   case "CHEST": armorSlotDesc="CHESTPLATE"; break;
   case "LEGS": armorSlotDesc="LEGGINGS"; break;
   case "FEET": armorSlotDesc="BOOTS"; break;
   case "OFFHAND": armorSlotDesc="OFFHAND"; break;
   case "MAINHAND": armorSlotDesc="HELD ITEM"; break;
   case "ALL": armorSlotDesc="EQUIPMENT"; break;
   case "SET": armorSlotDesc="ARMOR"; break;
   default: break;
  }
  
  return armorSlotDesc;
 }
}
