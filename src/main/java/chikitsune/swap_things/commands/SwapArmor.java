package chikitsune.swap_things.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.commands.arguments.RandomArmorSlotArgument;
import chikitsune.swap_things.config.Configs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class SwapArmor {
 public static Random rand= new Random();
// public static List<String> realArmorList = Arrays.asList("MAINHAND", "OFFHAND","FEET","LEGS","CHEST","HEAD");
  
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("swaparmor").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
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
  
  Boolean isRandomArmorSlot="RANDOM".equals(armorType.toUpperCase());
  String targetedArmorSlotOne="";
  String targetedArmorSlotTwo="";
  
  if (isRandomArmorSlot) {
   targetedArmorSlotOne=ArchCommand.getRandomArmorSlotTarget(targetedPlayerOne,armorType,isRandomArmorSlot);
   targetedArmorSlotTwo=ArchCommand.getRandomArmorSlotTarget(targetedPlayerTwo,armorType,isRandomArmorSlot);
   } else {
   targetedArmorSlotOne=armorType;
   targetedArmorSlotTwo=armorType;
   }
  
  ServerPlayerEntity targetedPlayerTemp;
  Collection<ServerPlayerEntity> targetPlayers=Arrays.asList(targetedPlayerOne);
  if (targetedPlayerOne.getName() != targetedPlayerTwo.getName()) targetPlayers=Arrays.asList(targetedPlayerOne,targetedPlayerTwo);
  
  switch (armorType.toUpperCase()) {
   case "ALL":
    targetedPlayerTemp=ArchCommand.getNewRandomSecondTarget(targetedPlayerOne, targetedPlayerTwo, source.getServer());
    ArchCommand.playerMsger(source, targetPlayers, getMessageString(targetedPlayerOne, targetedPlayerTemp, "OFFHAND", "OFFHAND"));
    ArchCommand.swapArmorItems(targetedPlayerOne, targetedPlayerTwo, "OFFHAND");
    
    targetedPlayerTemp=ArchCommand.getNewRandomSecondTarget(targetedPlayerOne, targetedPlayerTwo, source.getServer());
    ArchCommand.playerMsger(source, targetPlayers, getMessageString(targetedPlayerOne, targetedPlayerTemp, "MAINHAND", "MAINHAND"));
    ArchCommand.swapArmorItems(targetedPlayerOne, targetedPlayerTwo, "MAINHAND");
   case "SET":
    targetedPlayerTemp=ArchCommand.getNewRandomSecondTarget(targetedPlayerOne, targetedPlayerTwo, source.getServer());
    ArchCommand.playerMsger(source, targetPlayers, getMessageString(targetedPlayerOne, targetedPlayerTemp, "HEAD", "HEAD"));
    ArchCommand.swapArmorItems(targetedPlayerOne, targetedPlayerTemp, "HEAD");

    targetedPlayerTemp=ArchCommand.getNewRandomSecondTarget(targetedPlayerOne, targetedPlayerTwo, source.getServer());
    ArchCommand.playerMsger(source, targetPlayers, getMessageString(targetedPlayerOne, targetedPlayerTemp, "CHEST", "CHEST"));
    ArchCommand.swapArmorItems(targetedPlayerOne, targetedPlayerTemp, "CHEST");
    
    targetedPlayerTemp=ArchCommand.getNewRandomSecondTarget(targetedPlayerOne, targetedPlayerTwo, source.getServer());
    ArchCommand.playerMsger(source, targetPlayers, getMessageString(targetedPlayerOne, targetedPlayerTemp, "LEGS", "LEGS"));
    ArchCommand.swapArmorItems(targetedPlayerOne, targetedPlayerTemp, "LEGS");
    
    targetedPlayerTemp=ArchCommand.getNewRandomSecondTarget(targetedPlayerOne, targetedPlayerTwo, source.getServer());
    ArchCommand.playerMsger(source, targetPlayers, getMessageString(targetedPlayerOne, targetedPlayerTemp, "FEET", "FEET"));
    ArchCommand.swapArmorItems(targetedPlayerOne, targetedPlayerTemp, "FEET");
    break;
   default:
    ArchCommand.playerMsger(source, targetPlayers, getMessageString(targetedPlayerOne, targetedPlayerTwo, targetedArmorSlotOne, targetedArmorSlotTwo));
    ArchCommand.swapArmorItems(targetedPlayerOne, targetedPlayerTwo, targetedArmorSlotOne, targetedArmorSlotTwo);
    break;
  }
  return 0;  
 }
 
 public static StringTextComponent getMessageString(ServerPlayerEntity targetedPlayerOne, ServerPlayerEntity targetedPlayerTwo,String targetedArmorSlotOne, String targetedArmorSlotTwo) {
  StringTextComponent txtMsg=null,txtMsg2=null;
    ItemStack targetedArmorOne=targetedPlayerOne.getItemStackFromSlot(EquipmentSlotType.fromString(targetedArmorSlotOne.toLowerCase()));
  ItemStack targetedArmorTwo=targetedPlayerTwo.getItemStackFromSlot(EquipmentSlotType.fromString(targetedArmorSlotTwo.toLowerCase()));
  String targetedArmorSlotDescOne=ArchCommand.getArmorSlotDescription(targetedArmorSlotOne);
  String targetedArmorSlotDescTwo=ArchCommand.getArmorSlotDescription(targetedArmorSlotTwo);
  
  if (targetedPlayerOne.getName().getUnformattedComponentText() == targetedPlayerTwo.getName().getUnformattedComponentText()) {
   
   txtMsg=new StringTextComponent(TextFormatting.RED + targetedPlayerOne.getName().getString() + TextFormatting.GOLD + " you may want to rethink trying to swap " + targetedArmorSlotDescOne.toLowerCase() + " with yourself.");
  } else {
   if (targetedArmorTwo == ItemStack.EMPTY) {
  txtMsg=new StringTextComponent(TextFormatting.GOLD + "Wow " + TextFormatting.RED + targetedPlayerTwo.getName().getString() + TextFormatting.GOLD + " tried trading their nonexistent " + targetedArmorSlotDescTwo.toLowerCase() + " for ");
 } else {
  txtMsg=new StringTextComponent(TextFormatting.GOLD + "Wow " + TextFormatting.RED + targetedPlayerTwo.getName().getString() + TextFormatting.GOLD + " traded their " + targetedArmorTwo.getDisplayName().getString().toLowerCase() + " for ");
 }
 if (targetedArmorOne == ItemStack.EMPTY) {
  txtMsg2=new StringTextComponent(TextFormatting.RED + targetedPlayerOne.getName().getString() + TextFormatting.GOLD + "'s nonexistent " + targetedArmorSlotDescOne.toLowerCase() + ".");
 } else {
  txtMsg2=new StringTextComponent(TextFormatting.RED + targetedPlayerOne.getName().getString() + TextFormatting.GOLD + "'s " + targetedArmorOne.getDisplayName().getString().toLowerCase() + ".");
 }
  }
  if (txtMsg2!=null) txtMsg.appendSibling(txtMsg2);
  return txtMsg;
 }
}
