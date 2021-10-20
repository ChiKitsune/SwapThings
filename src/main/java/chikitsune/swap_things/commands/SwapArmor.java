package chikitsune.swap_things.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.commands.arguments.RandomArmorSlotArgument;
import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class SwapArmor {
 public static Random rand= new Random();
// public static List<String> realArmorList = Arrays.asList("MAINHAND", "OFFHAND","FEET","LEGS","CHEST","HEAD");
  
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("swaparmor").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return swapArmorLogic(cmd_0arg.getSource(),"RANDOM",cmd_0arg.getSource().getPlayerOrException(),cmd_0arg.getSource().getPlayerOrException());
  }).then(Commands.argument("armorType", RandomArmorSlotArgument.allArmorSlots()).executes((cmd_1arg) -> {
   return swapArmorLogic(cmd_1arg.getSource(),RandomArmorSlotArgument.getRandomArmorSlot(cmd_1arg, "armorType"),cmd_1arg.getSource().getPlayerOrException(),cmd_1arg.getSource().getPlayerOrException());
  }).then(Commands.argument("targetedPlayerOne", EntityArgument.players()).executes((cmd_2arg) -> {
   return swapArmorLogic(cmd_2arg.getSource(),RandomArmorSlotArgument.getRandomArmorSlot(cmd_2arg, "armorType"),EntityArgument.getPlayer(cmd_2arg, "targetedPlayerOne"),cmd_2arg.getSource().getPlayerOrException());
  }).then(Commands.argument("targetedPlayerTwo", EntityArgument.players()).executes((cmd_3arg) -> {
   return swapArmorLogic(cmd_3arg.getSource(),RandomArmorSlotArgument.getRandomArmorSlot(cmd_3arg, "armorType"),EntityArgument.getPlayer(cmd_3arg, "targetedPlayerOne"),EntityArgument.getPlayer(cmd_3arg, "targetedPlayerTwo"));
  }))));
 }
 
 private static int swapArmorLogic(CommandSourceStack source, String armorType, ServerPlayer targetedPlayerOne, ServerPlayer targetedPlayerTwo) {
  ArchCommand.ReloadConfig();
  if (targetedPlayerOne.getName().getContents() == targetedPlayerTwo.getName().getContents()) targetedPlayerTwo=ArchCommand.getNewRandomSecondTarget(targetedPlayerOne, targetedPlayerTwo, source.getServer());
  
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
  
  ServerPlayer targetedPlayerTemp;
  Collection<ServerPlayer> targetPlayers=Arrays.asList(targetedPlayerOne);
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
 
 public static TextComponent getMessageString(ServerPlayer targetedPlayerOne, ServerPlayer targetedPlayerTwo,String targetedArmorSlotOne, String targetedArmorSlotTwo) {
  TextComponent txtMsg=null,txtMsg2=null;
    ItemStack targetedArmorOne=targetedPlayerOne.getItemBySlot(EquipmentSlot.byName(targetedArmorSlotOne.toLowerCase()));
  ItemStack targetedArmorTwo=targetedPlayerTwo.getItemBySlot(EquipmentSlot.byName(targetedArmorSlotTwo.toLowerCase()));
  String targetedArmorSlotDescOne=ArchCommand.getArmorSlotDescription(targetedArmorSlotOne);
  String targetedArmorSlotDescTwo=ArchCommand.getArmorSlotDescription(targetedArmorSlotTwo);
  
  if (targetedPlayerOne.getName().getContents() == targetedPlayerTwo.getName().getContents()) {
   
   txtMsg=new TextComponent(ChatFormatting.RED + targetedPlayerOne.getName().getString() + ChatFormatting.GOLD + " you may want to rethink trying to swap " + targetedArmorSlotDescOne.toLowerCase() + " with yourself.");
  } else {
   if (targetedArmorTwo == ItemStack.EMPTY) {
  txtMsg=new TextComponent(ChatFormatting.GOLD + "Wow " + ChatFormatting.RED + targetedPlayerTwo.getName().getString() + ChatFormatting.GOLD + " tried trading their nonexistent " + targetedArmorSlotDescTwo.toLowerCase() + " for ");
 } else {
  txtMsg=new TextComponent(ChatFormatting.GOLD + "Wow " + ChatFormatting.RED + targetedPlayerTwo.getName().getString() + ChatFormatting.GOLD + " traded their " + targetedArmorTwo.getHoverName().getString().toLowerCase() + " for ");
 }
 if (targetedArmorOne == ItemStack.EMPTY) {
  txtMsg2=new TextComponent(ChatFormatting.RED + targetedPlayerOne.getName().getString() + ChatFormatting.GOLD + "'s nonexistent " + targetedArmorSlotDescOne.toLowerCase() + ".");
 } else {
  txtMsg2=new TextComponent(ChatFormatting.RED + targetedPlayerOne.getName().getString() + ChatFormatting.GOLD + "'s " + targetedArmorOne.getHoverName().getString().toLowerCase() + ".");
 }
  }
  if (txtMsg2!=null) txtMsg.append(txtMsg2);
  return txtMsg;
 }
}
