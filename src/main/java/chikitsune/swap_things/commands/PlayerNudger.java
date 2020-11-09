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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class PlayerNudger {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("playernudger").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return playerNudgerLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return playerNudgerLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return playerNudgerLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int playerNudgerLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName) {
   Configs.bakeConfig();
   double tempX,tempY,tempZ,nudgeStrength=.7,tempXLook,tempZLook;
   Integer randTemp;
   String directionStr,lookDirStr;
   Integer dirNorth,dirNorthEast,dirEast,dirSouthEast,dirSouth,dirSouthWest,dirWest,dirNorthWest,dirUp,dirDown;
   float playYaw, playPitch;
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    tempX=0;
    tempY=0;
    tempZ=0;
    dirNorth=Configs.playerNudgerNorthChance;
    dirNorthEast=Configs.playerNudgerNorthEastChance;
    dirEast=Configs.playerNudgerEastChance;
    dirSouthEast=Configs.playerNudgerSouthEastChance;
    dirSouth=Configs.playerNudgerSouthChance;
    dirSouthWest=Configs.playerNudgerSouthWestChance;
    dirWest=Configs.playerNudgerWestChance;
    dirNorthWest=Configs.playerNudgerNorthWestChance;
    dirUp=Configs.playerNudgerUpChance;
    dirDown=Configs.playerNudgerDownChance;
    tempXLook=Math.round(targetedPlayer.getLookVec().getX()*4);
    tempZLook=Math.round(targetedPlayer.getLookVec().getZ()*4);
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
    dirNorth*=Configs.playerNudgerForwardMultiplier; dirSouth*=Configs.playerNudgerBackwardMultiplier;
    dirWest*=Configs.playerNudgerLeftMultiplier; dirEast*=Configs.playerNudgerRightMultiplier;
    break;
   case "NORTHEAST":
    dirNorthEast*=Configs.playerNudgerForwardMultiplier; dirSouthWest*=Configs.playerNudgerBackwardMultiplier;
    dirNorthWest*=Configs.playerNudgerLeftMultiplier; dirSouthEast*=Configs.playerNudgerRightMultiplier;
    break;
   case "EAST":
    dirEast*=Configs.playerNudgerForwardMultiplier; dirWest*=Configs.playerNudgerBackwardMultiplier;
    dirNorth*=Configs.playerNudgerLeftMultiplier; dirSouth*=Configs.playerNudgerRightMultiplier;
    break;
   case "SOUTHEAST":
    dirSouthEast*=Configs.playerNudgerForwardMultiplier; dirNorthWest*=Configs.playerNudgerBackwardMultiplier;
    dirNorthEast*=Configs.playerNudgerLeftMultiplier; dirSouthWest*=Configs.playerNudgerRightMultiplier;
    break;
   case "SOUTH":
    dirSouth*=Configs.playerNudgerForwardMultiplier; dirNorth*=Configs.playerNudgerBackwardMultiplier;
    dirEast*=Configs.playerNudgerLeftMultiplier; dirWest*=Configs.playerNudgerRightMultiplier;
    break;
   case "SOUTHWEST":
    dirSouthWest*=Configs.playerNudgerForwardMultiplier; dirNorthEast*=Configs.playerNudgerBackwardMultiplier;
    dirSouthEast*=Configs.playerNudgerLeftMultiplier; dirNorthWest*=Configs.playerNudgerRightMultiplier;
    break;
   case "WEST":
    dirWest*=Configs.playerNudgerForwardMultiplier; dirEast*=Configs.playerNudgerBackwardMultiplier;
    dirSouth*=Configs.playerNudgerLeftMultiplier; dirNorth*=Configs.playerNudgerRightMultiplier;
    break;
   case "NORTHWEST":
    dirNorthWest*=Configs.playerNudgerForwardMultiplier; dirSouthEast*=Configs.playerNudgerBackwardMultiplier;
    dirSouthWest*=Configs.playerNudgerLeftMultiplier; dirNorthEast*=Configs.playerNudgerRightMultiplier;
    break;
    default:
    break;
  }
    
    randTemp=rand.nextInt(dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest+dirUp+dirDown);
    playYaw=targetedPlayer.getPitchYaw().x;
    playPitch=targetedPlayer.getPitchYaw().y;
    
    if (0 <= randTemp && randTemp <dirNorth) {
     tempX=0;tempZ=-Configs.playerNudgerNorthStrength; directionStr="north"; playYaw=-180;
    } else if (dirNorth <= randTemp && randTemp <dirNorth+dirNorthEast) {
     tempX=Configs.playerNudgerNorthEastStrength; tempZ=-Configs.playerNudgerNorthEastStrength; directionStr="northeast"; playYaw=-135;
    } else if (dirNorth+dirNorthEast <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast) {
     tempX=Configs.playerNudgerEastStrength; tempZ=0; directionStr="east"; playYaw=-90;
    } else if (dirNorth+dirNorthEast+dirEast <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast) {
     tempX=Configs.playerNudgerSouthEastStrength;tempZ=Configs.playerNudgerSouthEastStrength; directionStr="southeast"; playYaw=-45;
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth) {
     tempX=0;tempZ=Configs.playerNudgerSouthStrength; directionStr="south"; playYaw=0;
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest) {
     tempX=-Configs.playerNudgerSouthWestStrength; tempZ=Configs.playerNudgerSouthWestStrength; directionStr="southwest"; playYaw=45;
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest) {
     tempX=-Configs.playerNudgerWestStrength; tempZ=0; directionStr="west"; playYaw=90;
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest) {
     tempX=-Configs.playerNudgerNorthWestStrength; tempZ=-Configs.playerNudgerNorthWestStrength; directionStr="northwest"; playYaw=135;
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest+dirUp) {
     tempY=Configs.playerNudgerUpStrength; directionStr ="up"; playPitch=-90;
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest+dirUp <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest+dirUp+dirDown) {
     tempY=-Configs.playerNudgerDownStrength-.4; directionStr ="down"; playPitch=90;
    } else {
     directionStr ="nowhere";
    }
   
    if (targetedPlayer.getRidingEntity() !=null) {
     Entity ridingUpon=targetedPlayer.getLowestRidingEntity();
     ridingUpon.setMotion(tempX, tempY + .4, tempZ);
     ridingUpon.setLocationAndAngles(ridingUpon.getPositionVec().getX(), ridingUpon.getPositionVec().getY(), ridingUpon.getPositionVec().getZ(), playYaw, playPitch);
     ridingUpon.velocityChanged=true;
    } else {
     targetedPlayer.stopRiding();
     if (targetedPlayer.isSleeping()) targetedPlayer.stopSleepInBed(true, false);
//     if (targetedPlayer.isSleeping()) targetedPlayer.wakeUpPlayer(true, false, false);
     targetedPlayer.setMotion(tempX, tempY + .4, tempZ);
     targetedPlayer.setLocationAndAngles(targetedPlayer.getPositionVec().getX(), targetedPlayer.getPositionVec().getY(), targetedPlayer.getPositionVec().getZ(), playYaw, playPitch);
     targetedPlayer.velocityChanged=true;
    }
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + "Oh no! " + TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " was pushed " + directionStr + " by " + fromName + "."));
   }
   return 0;
  }
}
