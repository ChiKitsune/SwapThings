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
   float playYaw,rndYaw;
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    playYaw=targetedPlayer.getPitchYaw().x;
    rndYaw=rand.nextInt(360)-180;
//    targetedPlayer.setRotationYawHead((playYaw+rndYaw));
//    targetedPlayer.setRenderYawOffset((playYaw+rndYaw));
//    targetedPlayer.setHeadRotation((playYaw+rndYaw), (int) targetedPlayer.getPitchYaw().y);
    
    targetedPlayer.setLocationAndAngles(targetedPlayer.func_233580_cy_().getX(), targetedPlayer.func_233580_cy_().getY(), targetedPlayer.func_233580_cy_().getZ(), (playYaw+rndYaw)%360F, targetedPlayer.getPitchYaw().y);
    targetedPlayer.getServerWorld().getChunkProvider().updatePlayerPosition(targetedPlayer);
    
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent("org:"+playYaw+" rnd:"+rndYaw+" new:"+((playYaw+rndYaw)%360)));
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + fromName + " spun " + TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " right round."));
   }
   return 0;
  }

}
