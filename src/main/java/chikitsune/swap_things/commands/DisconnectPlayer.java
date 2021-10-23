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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class DisconnectPlayer {
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("disconnectPlayer").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return disconnectPlayerLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return disconnectPlayerLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return disconnectPlayerLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int disconnectPlayerLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName) {
   String disMsg=Configs.disconnectMsg;
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getUnformattedComponentText() + TextFormatting.GOLD + " was surprised by " + fromName + disMsg));
    targetedPlayer.connection.disconnect(new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getUnformattedComponentText() + TextFormatting.GOLD + " was surprised by " + fromName + disMsg));
   }
   return 0;
  }

}
