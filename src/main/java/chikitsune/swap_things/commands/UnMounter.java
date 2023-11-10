package chikitsune.swap_things.commands;

import chikitsune.swap_things.config.Configs;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Collection;
import java.util.Collections;

public class UnMounter {
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("unmounter").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return unmounterLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return unmounterLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return unmounterLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int unmounterLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName) {
   ArchCommand.ReloadConfig();
   Component msg;
   
   for(ServerPlayer targetedPlayer : targetPlayers) {
    
    targetedPlayer.stopRiding();

    if (targetedPlayer.getPassengers().size()>0) {
     Entity tempPass=targetedPlayer.getPassengers().get(0);
     tempPass.stopRiding();
    }
    if (targetedPlayer.connection != null) {
     targetedPlayer.connection.send(new ClientboundSetPassengersPacket(targetedPlayer));
   }
     msg=Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED).append(Component.literal(" was separated from their mount " + fromName + ".").withStyle(ChatFormatting.GOLD));
    ArchCommand.playerMsger(source, targetPlayers, msg);
    //ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " was separated from their mount " + fromName + "."));
   }
   
   return 0;
  }

}
