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

public class ShuffleInventory {
 public static Random rand= new Random();

 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("shuffleinventory").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return shuffleInventoryLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return shuffleInventoryLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
   }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
    return shuffleInventoryLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
   })));
 }

 private static int shuffleInventoryLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers,String fromName) {
  ItemStack tempItem=ItemStack.EMPTY;
  Integer tempRandNum=0;
  
  for(ServerPlayerEntity targetedPlayer : targetPlayers) {
   for (int i=0;i<targetedPlayer.inventory.getSizeInventory();i++) {
    tempItem=ItemStack.EMPTY;
    tempRandNum=i;
    
    while (tempRandNum==i) {
     tempRandNum=rand.nextInt(targetedPlayer.inventory.getSizeInventory());
    }
    
    tempItem=targetedPlayer.inventory.getStackInSlot(i).copy();
    targetedPlayer.inventory.setInventorySlotContents(i, targetedPlayer.inventory.getStackInSlot(tempRandNum).copy());
    targetedPlayer.inventory.setInventorySlotContents(tempRandNum,tempItem);
//    source.getServer().getPlayerList().sendMessage(new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getFormattedText() + TextFormatting.GOLD + " " + i + " -> " + tempRandNum));
   }
   source.getServer().getPlayerList().sendMessage(new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getFormattedText() + TextFormatting.GOLD + " let " + fromName + " re-sort their inventory."));
   
  }
  
  return 0;
 }
}