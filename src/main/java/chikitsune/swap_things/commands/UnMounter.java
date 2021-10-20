package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SSetPassengersPacket;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class UnMounter {
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("unmounter").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return unmounterLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return unmounterLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return unmounterLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int unmounterLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName) {
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    
    targetedPlayer.stopRiding();

    if (targetedPlayer.getPassengers().size()>0) {
     Entity tempPass=targetedPlayer.getPassengers().get(0);
     tempPass.stopRiding();
    }
    if (targetedPlayer.connection != null) {
     targetedPlayer.connection.sendPacket(new SSetPassengersPacket(targetedPlayer));
   }
    
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " was separated from their mount " + fromName + "."));
   }
   
   return 0;
  }

}
