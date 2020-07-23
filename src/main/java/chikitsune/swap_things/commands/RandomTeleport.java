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

public class RandomTeleport {
public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("randomteleport").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return randomTeleportLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return randomTeleportLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return randomTeleportLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int randomTeleportLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName) {
   Integer rngXMax=Configs.randomTeleportXMax;
   Integer rngYMax=Configs.randomTeleportYMax;
   Integer rngZMax=Configs.randomTeleportZMax;
   Integer rngXMin=Configs.randomTeleportXMin;
   Integer rngYMin=Configs.randomTeleportYMin;
   Integer rngZMin=Configs.randomTeleportZMin;
   Integer teleX=0,teleY=0,teleZ=0;
   Integer plyX,plyY,plyZ;
   boolean attTele=false,tempChk=false;
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {    
    plyX=targetedPlayer.func_233580_cy_().getX();
    plyY=targetedPlayer.func_233580_cy_().getY();
    plyZ=targetedPlayer.func_233580_cy_().getZ();
    
    do {
     do { teleX=rand.nextInt((rngXMax*2)+1) - rngXMax; } while (!(Math.abs(teleX)>=rngXMin && Math.abs(teleX)<=rngXMax));
     do { teleY=rand.nextInt((rngYMax*2)+1) - rngYMax; } while (!(Math.abs(teleX)>=rngXMin && Math.abs(teleX)<=rngXMax && ((teleY+plyY)>=1) && ((plyY+teleY)<=255)));
     do { teleZ=rand.nextInt((rngZMax*2)+1) - rngZMax; } while (!(Math.abs(teleZ)>=rngZMin && Math.abs(teleZ)<=rngZMax));
     attTele=targetedPlayer.attemptTeleport(plyX + teleX, plyY + teleY, plyZ + teleZ, true);
    } while (!attTele);
    
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + "Oh no! " + TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " was distracted by " + fromName + " and is now lost."));
   }  
   
   return 0;
  }

}
