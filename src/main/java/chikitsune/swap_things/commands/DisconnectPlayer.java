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

public class DisconnectPlayer {
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("disconnectPlayer").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return disconnectPlayerLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return disconnectPlayerLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return disconnectPlayerLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int disconnectPlayerLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName) {
   ArchCommand.ReloadConfig();
   String disMsg=Configs.DP_MSG.get();
   Component comDisMsg=Component.literal("");
   
   for(ServerPlayer targetedPlayer : targetPlayers) {
    comDisMsg=Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED)
      .append(Component.literal(" was surprised by " + fromName + disMsg).withStyle(ChatFormatting.GOLD));
    
    ArchCommand.playerMsger(source, targetPlayers,comDisMsg);
//    ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().m_6111_() + ChatFormatting.GOLD + " was surprised by " + fromName + disMsg));;
    targetedPlayer.connection.disconnect(comDisMsg);
//    targetedPlayer.connection.disconnect(new TextComponent(ChatFormatting.RED + targetedPlayer.getName().m_6111_() + ChatFormatting.GOLD + " was surprised by " + fromName + disMsg));
   }
   return 0;
  }

}
