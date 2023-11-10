package chikitsune.swap_things.commands;

import chikitsune.swap_things.config.Configs;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import java.util.*;

public class PlayerNudger {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("playernudger").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return playerNudgerLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return playerNudgerLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return playerNudgerLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int playerNudgerLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName) {
   ArchCommand.ReloadConfig();   
   double tempX,tempY,tempZ,nudgeStrength=.7,tempXLook,tempZLook;
   Integer randTemp;
   String directionStr,lookDirStr;
   Integer dirNorth,dirNorthEast,dirEast,dirSouthEast,dirSouth,dirSouthWest,dirWest,dirNorthWest,dirUp,dirDown;
   float playYaw, playPitch;
   
   for(ServerPlayer targetedPlayer : targetPlayers) {
    tempX=0;
    tempY=0;
    tempZ=0;
    dirNorth=Configs.PN_N_CHA.get();
    dirNorthEast=Configs.PN_NE_CHA.get();
    dirEast=Configs.PN_E_CHA.get();
    dirSouthEast=Configs.PN_SE_CHA.get();
    dirSouth=Configs.PN_S_CHA.get();
    dirSouthWest=Configs.PN_SW_CHA.get();
    dirWest=Configs.PN_W_CHA.get();
    dirNorthWest=Configs.PN_NW_CHA.get();
    dirUp=Configs.PN_U_CHA.get();
    dirDown=Configs.PN_D_CHA.get();
    tempXLook=Math.round(targetedPlayer.getLookAngle().x()*4);
    tempZLook=Math.round(targetedPlayer.getLookAngle().z()*4);
    lookDirStr="";
//ZZZZZZZZZZZZZZ Axis
//  0 - 1 = 0-.25 = W/E
//  1 - 3 = .25-.75 = SW/SE
//  3 - 4 = .75-1 = S
//  -1- 0 = -.25-0 = W/E
//  -3--1 = -.75--.25 = NW/NE
//  -4--3 = -1--.75 = N
//XXXXXXXXXXXXXXX Axis
//0 - 1 = 0-.25 = N/S
//1 - 3 = .25-.75 = NE/SE
//3 - 4 = .75-1 = E
//-1- 0 = -.25-0 = N/S
//-3--1 = -.75--.25 = NW/SW
//-4--3 = -1--.75 = W
  if (tempZLook >=1) {
   lookDirStr=lookDirStr + "SOUTH";
  } else if (tempZLook <=-1) {
   lookDirStr=lookDirStr + "NORTH";
  }
  if (tempXLook >=1) {
   lookDirStr=lookDirStr + "EAST";
  } else if (tempXLook <=-1) {
   lookDirStr=lookDirStr + "WEST";
  }
  switch(lookDirStr) {
   case "NORTH": 
    dirNorth*=Configs.PN_PFD_FOR.get(); dirSouth*=Configs.PN_PFD_BAC.get();
    dirWest*=Configs.PN_PFD_LEF.get(); dirEast*=Configs.PN_PFD_RIG.get();
    break;
   case "NORTHEAST":
    dirNorthEast*=Configs.PN_PFD_FOR.get(); dirSouthWest*=Configs.PN_PFD_BAC.get();
    dirNorthWest*=Configs.PN_PFD_LEF.get(); dirSouthEast*=Configs.PN_PFD_RIG.get();
    break;
   case "EAST":
    dirEast*=Configs.PN_PFD_FOR.get(); dirWest*=Configs.PN_PFD_BAC.get();
    dirNorth*=Configs.PN_PFD_LEF.get(); dirSouth*=Configs.PN_PFD_RIG.get();
    break;
   case "SOUTHEAST":
    dirSouthEast*=Configs.PN_PFD_FOR.get(); dirNorthWest*=Configs.PN_PFD_BAC.get();
    dirNorthEast*=Configs.PN_PFD_LEF.get(); dirSouthWest*=Configs.PN_PFD_RIG.get();
    break;
   case "SOUTH":
    dirSouth*=Configs.PN_PFD_FOR.get(); dirNorth*=Configs.PN_PFD_BAC.get();
    dirEast*=Configs.PN_PFD_LEF.get(); dirWest*=Configs.PN_PFD_RIG.get();
    break;
   case "SOUTHWEST":
    dirSouthWest*=Configs.PN_PFD_FOR.get(); dirNorthEast*=Configs.PN_PFD_BAC.get();
    dirSouthEast*=Configs.PN_PFD_LEF.get(); dirNorthWest*=Configs.PN_PFD_RIG.get();
    break;
   case "WEST":
    dirWest*=Configs.PN_PFD_FOR.get(); dirEast*=Configs.PN_PFD_BAC.get();
    dirSouth*=Configs.PN_PFD_LEF.get(); dirNorth*=Configs.PN_PFD_RIG.get();
    break;
   case "NORTHWEST":
    dirNorthWest*=Configs.PN_PFD_FOR.get(); dirSouthEast*=Configs.PN_PFD_BAC.get();
    dirSouthWest*=Configs.PN_PFD_LEF.get(); dirNorthEast*=Configs.PN_PFD_RIG.get();
    break;
    default:
    break;
  }
    
    randTemp=rand.nextInt(dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest+dirUp+dirDown);
    playYaw=targetedPlayer.getRotationVector().x;
    playPitch=targetedPlayer.getRotationVector().y;
    
    if (0 <= randTemp && randTemp <dirNorth) {
     tempX=0;tempZ=-Configs.PN_N_STR.get(); directionStr="north"; playYaw=-180;
    } else if (dirNorth <= randTemp && randTemp <dirNorth+dirNorthEast) {
     tempX=Configs.PN_NE_STR.get(); tempZ=-Configs.PN_NE_STR.get(); directionStr="northeast"; playYaw=-135;
    } else if (dirNorth+dirNorthEast <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast) {
     tempX=Configs.PN_E_STR.get(); tempZ=0; directionStr="east"; playYaw=-90;
    } else if (dirNorth+dirNorthEast+dirEast <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast) {
     tempX=Configs.PN_SE_STR.get();tempZ=Configs.PN_SE_STR.get(); directionStr="southeast"; playYaw=-45;
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth) {
     tempX=0;tempZ=Configs.PN_S_STR.get(); directionStr="south"; playYaw=0;
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest) {
     tempX=-Configs.PN_SW_STR.get(); tempZ=Configs.PN_SW_STR.get(); directionStr="southwest"; playYaw=45;
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest) {
     tempX=-Configs.PN_W_STR.get(); tempZ=0; directionStr="west"; playYaw=90;
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest) {
     tempX=-Configs.PN_NW_STR.get(); tempZ=-Configs.PN_NW_STR.get(); directionStr="northwest"; playYaw=135;
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest+dirUp) {
     tempY=Configs.PN_U_STR.get(); directionStr ="up"; playPitch=-90;
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest+dirUp <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest+dirUp+dirDown) {
     tempY=-Configs.PN_D_STR.get()-.4; directionStr ="down"; playPitch=90;
    } else {
     directionStr ="nowhere";
    }
   
    if (targetedPlayer.getVehicle() !=null) {
     Entity ridingUpon=targetedPlayer.getRootVehicle();
     ridingUpon.setDeltaMovement(tempX, tempY + .4, tempZ);
     ridingUpon.moveTo(ridingUpon.position().x(), ridingUpon.position().y(), ridingUpon.position().z(), playYaw, playPitch);
     ridingUpon.hurtMarked=true;
    } else {
     targetedPlayer.stopRiding();
     if (targetedPlayer.isSleeping()) targetedPlayer.stopSleepInBed(true, false);
//     if (targetedPlayer.isSleeping()) targetedPlayer.wakeUpPlayer(true, false, false);
     targetedPlayer.setDeltaMovement(tempX, tempY + .4, tempZ);
     targetedPlayer.moveTo(targetedPlayer.position().x(), targetedPlayer.position().y(), targetedPlayer.position().z(), playYaw, playPitch);
     targetedPlayer.hurtMarked=true;
    }
    ArchCommand.playerMsger(source, targetPlayers, 
      Component.literal("Oh no! " ).withStyle(ChatFormatting.GOLD)
      .append(Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED))
      .append(Component.literal(" was pushed " + directionStr + " by " + fromName + ".").withStyle(ChatFormatting.GOLD)));
//    ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.GOLD + "Oh no! " + ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " was pushed " + directionStr + " by " + fromName + "."));
   }
   return 0;
  }
}
