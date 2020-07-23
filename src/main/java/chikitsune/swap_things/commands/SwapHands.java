package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class SwapHands {
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("swaphands").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return swapHandsLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return swapHandsLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return swapHandsLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int swapHandsLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName) {
   ItemStack tempItem=ItemStack.EMPTY;
   Boolean sneakPressed=false;
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    tempItem=targetedPlayer.getHeldItem(Hand.MAIN_HAND).copy();
    targetedPlayer.setHeldItem(Hand.MAIN_HAND,targetedPlayer.getHeldItem(Hand.OFF_HAND));
    targetedPlayer.setHeldItem(Hand.OFF_HAND,tempItem);
       
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " let " + fromName + " switch what was in their hands."));
   }
   return 0;
  }

}
