package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class ShuffleHotbar {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("shufflehotbar").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return shuffleHotbarLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return shuffleHotbarLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
    }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
     return shuffleHotbarLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
    })));
 }
 
 private static int shuffleHotbarLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers,String fromName) {
  ItemStack tempItem=ItemStack.EMPTY;
  Integer tempRandNum=0;
  
  for(ServerPlayerEntity targetedPlayer : targetPlayers) {
   for (int i=0;i<targetedPlayer.inventory.getHotbarSize();i++) {
    tempItem=ItemStack.EMPTY;
    tempRandNum=i;
    
    while (tempRandNum==i) {
     tempRandNum=rand.nextInt(targetedPlayer.inventory.getHotbarSize());
    }
    
    tempItem=targetedPlayer.inventory.getStackInSlot(i).copy();
    targetedPlayer.inventory.setInventorySlotContents(i, targetedPlayer.inventory.getStackInSlot(tempRandNum).copy());
    targetedPlayer.inventory.setInventorySlotContents(tempRandNum,tempItem);
   }
   ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " let " + fromName + " re-sort their hotbar."));   
  }
  return 0;
 }
}
