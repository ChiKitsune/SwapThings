package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class PlayerRotate {
public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("playerrotate").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return playerRotateLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return playerRotateLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return playerRotateLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int playerRotateLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName) {
   float playYaw,rndYaw,rndPitch;
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    playYaw=targetedPlayer.getPitchYaw().y;
    rndYaw=rand.nextInt(360)-180;
    rndPitch=rand.nextInt(180)-90;

    if (targetedPlayer.connection != null) {
    targetedPlayer.connection.sendPacket(new SPlayerPositionLookPacket(targetedPlayer.getPosX(),targetedPlayer.getPosY(),targetedPlayer.getPosZ(),rndYaw,rndPitch, Collections.emptySet(),0));
    }
    
//    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent("Pitch org:"+targetedPlayer.getPitchYaw().x+" rnd:"+rndPitch));
//    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent("Yaw org:"+targetedPlayer.getPitchYaw().y+" rnd:"+rndYaw));
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + fromName + " spun " + TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " right round."));
   }
   return 0;
  }

}
