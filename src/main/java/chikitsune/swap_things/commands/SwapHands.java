package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class SwapHands {
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("swaphands").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return swapHandsLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return swapHandsLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return swapHandsLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int swapHandsLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName) {
   ArchCommand.ReloadConfig();
   ItemStack tempItem=ItemStack.EMPTY;
   Boolean sneakPressed=false;
   for(ServerPlayer targetedPlayer : targetPlayers) {
    tempItem=targetedPlayer.getItemInHand(InteractionHand.MAIN_HAND).copy();
    targetedPlayer.setItemInHand(InteractionHand.MAIN_HAND,targetedPlayer.getItemInHand(InteractionHand.OFF_HAND));
    targetedPlayer.setItemInHand(InteractionHand.OFF_HAND,tempItem);
       
    ArchCommand.playerMsger(source, targetPlayers, 
      Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED)
    .append(Component.literal(" let " + fromName + " switch what was in their hands.").withStyle(ChatFormatting.GOLD)));
//    ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " let " + fromName + " switch what was in their hands."));
   }
   return 0;
  }

}
