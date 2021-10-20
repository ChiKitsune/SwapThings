package chikitsune.swap_things.commands;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import chikitsune.swap_things.commands.arguments.RandomSingleArmorSlotArgument;
import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ReplaceArmorPiece {
 public static Random rand= new Random();
 public static List<String> realArmorList = Arrays.asList("MAINHAND", "OFFHAND","FEET","LEGS","CHEST","HEAD");
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("replacearmorpiece").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return replaceArmorPieceLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"RANDOM",null,"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return replaceArmorPieceLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"RANDOM",null,"someone");
   }).then(Commands.argument("armorType", RandomSingleArmorSlotArgument.allArmorSlots()).executes((cmd_2arg) -> {
    return replaceArmorPieceLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),RandomSingleArmorSlotArgument.getSingleRandomArmorSlot(cmd_2arg, "armorType"),null,"someone");
   }).then(Commands.argument("item", ItemArgument.item()).executes((cmd_3arg) -> {
    return replaceArmorPieceLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),RandomSingleArmorSlotArgument.getSingleRandomArmorSlot(cmd_3arg, "armorType"),ItemArgument.getItem(cmd_3arg, "item"),"someone");
    }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_4arg) -> {
      return replaceArmorPieceLogic(cmd_4arg.getSource(),EntityArgument.getPlayers(cmd_4arg, "targetedPlayer"),RandomSingleArmorSlotArgument.getSingleRandomArmorSlot(cmd_4arg, "armorType"),ItemArgument.getItem(cmd_4arg, "item"),StringArgumentType.getString(cmd_4arg, "fromName"));
      })
       ))));
 }
 
 private static int replaceArmorPieceLogic(CommandSourceStack source, Collection<ServerPlayer> targetPlayers, String armorType, ItemInput itemInput, String fromName) {
  ArchCommand.ReloadConfig();
  Boolean isRandomArmorSlot="RANDOM".equals(armorType.toUpperCase());
  String targetedArmorSlot="";
  ItemStack tempStack=ItemStack.EMPTY,defItemStack=ItemStack.EMPTY;
  ItemArgument iaStack=new ItemArgument();
  
  if (itemInput!=null) {
   try {
   defItemStack=itemInput.createItemStack(1, true);
   } catch (CommandSyntaxException e) {
    e.printStackTrace();
    defItemStack=new ItemStack(Items.DEAD_BUSH);
   }
  }
  
  for(ServerPlayer targetedPlayer : targetPlayers) {
   
   if (isRandomArmorSlot) {
    targetedArmorSlot=ArchCommand.getRandomArmorSlotTarget(targetedPlayer,armorType,isRandomArmorSlot);
    } else {
    targetedArmorSlot=armorType;
    }
   
   tempStack=targetedPlayer.getItemBySlot(EquipmentSlot.byName(targetedArmorSlot.toLowerCase()));
   
   if (targetedPlayer.getItemBySlot(EquipmentSlot.byName(targetedArmorSlot.toLowerCase())) != ItemStack.EMPTY) targetedPlayer.drop(targetedPlayer.getItemBySlot(EquipmentSlot.byName(targetedArmorSlot.toLowerCase())), false, true);
   
   targetedPlayer.setItemSlot(EquipmentSlot.byName(targetedArmorSlot.toLowerCase()), defItemStack);
   
   
   ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " got a brand new " + ArchCommand.getArmorSlotDescription(targetedArmorSlot) 
   ));
  }
  return 0;
 }

}
