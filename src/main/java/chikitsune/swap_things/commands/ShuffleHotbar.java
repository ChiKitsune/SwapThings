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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ShuffleHotbar {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("shufflehotbar").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return shuffleHotbarLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return shuffleHotbarLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
    }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
     return shuffleHotbarLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
    })));
 }
 
 private static int shuffleHotbarLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers,String fromName) {
  ArchCommand.ReloadConfig();
  ItemStack tempItem=ItemStack.EMPTY;
  Integer tempRandNum=0;
  
  for(ServerPlayer targetedPlayer : targetPlayers) {
   for (int i=0;i<targetedPlayer.getInventory().getSelectionSize();i++) {
    tempItem=ItemStack.EMPTY;
    tempRandNum=i;
    
    while (tempRandNum==i) {
     tempRandNum=rand.nextInt(targetedPlayer.getInventory().getSelectionSize());
    }
    
    tempItem=targetedPlayer.getInventory().getItem(i).copy();
    targetedPlayer.getInventory().setItem(i, targetedPlayer.getInventory().getItem(tempRandNum).copy());
    targetedPlayer.getInventory().setItem(tempRandNum,tempItem);
   }
   ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " let " + fromName + " re-sort their hotbar."));   
  }
  return 0;
 }
}
