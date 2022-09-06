package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class QuickHide {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext cmdBuildContext) { 
  return Commands.literal("quickhide").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return quickhideLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),null,null,cmdBuildContext);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return quickhideLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null,null,cmdBuildContext);
    }).then(Commands.argument("item", ItemArgument.item(cmdBuildContext)).executes((cmd_2arg) -> {
      return quickhideLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),ItemArgument.getItem(cmd_2arg, "item"),null,cmdBuildContext);
     }).then(Commands.argument("message", StringArgumentType.string()).executes((cmd_3arg) -> {
      return quickhideLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),ItemArgument.getItem(cmd_3arg, "item"),StringArgumentType.getString(cmd_3arg, "message"),cmdBuildContext);
      })
     )));
 }
  
 private static int quickhideLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, ItemInput itemInput, String message,CommandBuildContext cmdBuildContext) {
  ArchCommand.ReloadConfig();
  ItemStack rndStack = ItemStack.EMPTY;
  String curMsg="quick use these to hide!";
  ItemArgument iaStack = ItemArgument.item(cmdBuildContext);
  Integer newRanNum;

  for(ServerPlayer targetedPlayer : targetPlayers) {  
   newRanNum=0;
  try {
   if (itemInput==null && message == null) {
    newRanNum=rand.nextInt(Configs.QH_LIST.get().size());
    rndStack=iaStack.parse(new StringReader(Configs.QH_LIST.get().get(newRanNum).split(",")[0])).createItemStack(1, false);
    curMsg=Configs.QH_LIST.get().get(newRanNum).split(",")[1];
   } else {
    rndStack=itemInput.createItemStack(1, false);
    curMsg=message;
   }
   if (curMsg == null) curMsg="quick use these to hide!";   
  } catch (CommandSyntaxException e) {
   e.printStackTrace();
   rndStack=new ItemStack(Items.DEAD_BUSH);
  }  
  
  if (targetedPlayer.getItemBySlot(EquipmentSlot.HEAD) != ItemStack.EMPTY) targetedPlayer.drop(targetedPlayer.getItemBySlot(EquipmentSlot.HEAD), false, true);
  if (targetedPlayer.getItemBySlot(EquipmentSlot.CHEST) != ItemStack.EMPTY) targetedPlayer.drop(targetedPlayer.getItemBySlot(EquipmentSlot.CHEST), false, true);
  if (targetedPlayer.getItemBySlot(EquipmentSlot.FEET) != ItemStack.EMPTY) targetedPlayer.drop(targetedPlayer.getItemBySlot(EquipmentSlot.FEET), false, true);
  if (targetedPlayer.getItemBySlot(EquipmentSlot.LEGS) != ItemStack.EMPTY) targetedPlayer.drop(targetedPlayer.getItemBySlot(EquipmentSlot.LEGS), false, true);
  if (targetedPlayer.getItemBySlot(EquipmentSlot.OFFHAND) != ItemStack.EMPTY) targetedPlayer.drop(targetedPlayer.getItemBySlot(EquipmentSlot.OFFHAND), false, true);
  if (targetedPlayer.getItemBySlot(EquipmentSlot.MAINHAND) != ItemStack.EMPTY) targetedPlayer.drop(targetedPlayer.getItemBySlot(EquipmentSlot.MAINHAND), false, true);
    
  targetedPlayer.setItemSlot(EquipmentSlot.HEAD, rndStack.copy());
  targetedPlayer.setItemSlot(EquipmentSlot.CHEST, rndStack.copy());
  targetedPlayer.setItemSlot(EquipmentSlot.FEET, rndStack.copy());
  targetedPlayer.setItemSlot(EquipmentSlot.LEGS, rndStack.copy());
  targetedPlayer.setItemSlot(EquipmentSlot.OFFHAND, rndStack.copy());
  targetedPlayer.setItemSlot(EquipmentSlot.MAINHAND, rndStack.copy());
  
  ArchCommand.playerMsger(source, targetPlayers, 
    Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED)
  .append(Component.literal(" " + curMsg).withStyle(ChatFormatting.GOLD)));
//  ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " " + curMsg));
  }
  return 0;
 }
}
