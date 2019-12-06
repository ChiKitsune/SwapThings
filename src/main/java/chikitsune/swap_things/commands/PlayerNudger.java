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

public class PlayerNudger {
 public static Random rand= new Random();
// public static Integer dirNorth=Configs.PLAYERNUDGER_NORTH_CHANCE.get();
// public static Integer dirNorthEast=Configs.PLAYERNUDGER_NORTHEAST_CHANCE.get();
// public static Integer dirEast=Configs.PLAYERNUDGER_EAST_CHANCE.get();
// public static Integer dirSouthEast=Configs.PLAYERNUDGER_SOUTHEAST_CHANCE.get();
// public static Integer dirSouth=Configs.PLAYERNUDGER_SOUTH_CHANCE.get();
// public static Integer dirSouthWest=Configs.PLAYERNUDGER_SOUTHWEST_CHANCE.get();
// public static Integer dirWest=Configs.PLAYERNUDGER_WEST_CHANCE.get();
// public static Integer dirNorthWest=Configs.PLAYERNUDGER_NORTHWEST_CHANCE.get();
// public static Integer dirUp=Configs.PLAYERNUDGER_UP_CHANCE.get();
// public static Integer dirDown=Configs.PLAYERNUDGER_DOWN_CHANCE.get();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("playernudger").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return toggleRunLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return toggleRunLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return toggleRunLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int toggleRunLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName) {
   double tempX,tempY,tempZ,nudgeStrength=.7,tempXLook,tempZLook;
   Integer randTemp;
   String directionStr,lookDirStr;
   Integer dirNorth,dirNorthEast,dirEast,dirSouthEast,dirSouth,dirSouthWest,dirWest,dirNorthWest,dirUp,dirDown;
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    tempX=0;
    tempY=0;
    tempZ=0;
    dirNorth=Configs.PLAYERNUDGER_NORTH_CHANCE.get();
    dirNorthEast=Configs.PLAYERNUDGER_NORTHEAST_CHANCE.get();
    dirEast=Configs.PLAYERNUDGER_EAST_CHANCE.get();
    dirSouthEast=Configs.PLAYERNUDGER_SOUTHEAST_CHANCE.get();
    dirSouth=Configs.PLAYERNUDGER_SOUTH_CHANCE.get();
    dirSouthWest=Configs.PLAYERNUDGER_SOUTHWEST_CHANCE.get();
    dirWest=Configs.PLAYERNUDGER_WEST_CHANCE.get();
    dirNorthWest=Configs.PLAYERNUDGER_NORTHWEST_CHANCE.get();
    dirUp=Configs.PLAYERNUDGER_UP_CHANCE.get();
    dirDown=Configs.PLAYERNUDGER_DOWN_CHANCE.get();
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
    dirNorth*=Configs.PLAYERNUDGER_FORWARD_MULTIPLIER.get(); dirSouth*=Configs.PLAYERNUDGER_BACKWARD_MULTIPLIER.get();
    dirWest*=Configs.PLAYERNUDGER_LEFT_MULTIPLIER.get(); dirEast*=Configs.PLAYERNUDGER_RIGHT_MULTIPLIER.get();
    break;
   case "NORTHEAST":
    dirNorthEast*=Configs.PLAYERNUDGER_FORWARD_MULTIPLIER.get(); dirSouthWest*=Configs.PLAYERNUDGER_BACKWARD_MULTIPLIER.get();
    dirNorthWest*=Configs.PLAYERNUDGER_LEFT_MULTIPLIER.get(); dirSouthEast*=Configs.PLAYERNUDGER_RIGHT_MULTIPLIER.get();
    break;
   case "EAST":
    dirEast*=Configs.PLAYERNUDGER_FORWARD_MULTIPLIER.get(); dirWest*=Configs.PLAYERNUDGER_BACKWARD_MULTIPLIER.get();
    dirNorth*=Configs.PLAYERNUDGER_LEFT_MULTIPLIER.get(); dirSouth*=Configs.PLAYERNUDGER_RIGHT_MULTIPLIER.get();
    break;
   case "SOUTHEAST":
    dirSouthEast*=Configs.PLAYERNUDGER_FORWARD_MULTIPLIER.get(); dirNorthWest*=Configs.PLAYERNUDGER_BACKWARD_MULTIPLIER.get();
    dirNorthEast*=Configs.PLAYERNUDGER_LEFT_MULTIPLIER.get(); dirSouthWest*=Configs.PLAYERNUDGER_RIGHT_MULTIPLIER.get();
    break;
   case "SOUTH":
    dirSouth*=Configs.PLAYERNUDGER_FORWARD_MULTIPLIER.get(); dirNorth*=Configs.PLAYERNUDGER_BACKWARD_MULTIPLIER.get();
    dirEast*=Configs.PLAYERNUDGER_LEFT_MULTIPLIER.get(); dirWest*=Configs.PLAYERNUDGER_RIGHT_MULTIPLIER.get();
    break;
   case "SOUTHWEST":
    dirSouthWest*=Configs.PLAYERNUDGER_FORWARD_MULTIPLIER.get(); dirNorthEast*=Configs.PLAYERNUDGER_BACKWARD_MULTIPLIER.get();
    dirSouthEast*=Configs.PLAYERNUDGER_LEFT_MULTIPLIER.get(); dirNorthWest*=Configs.PLAYERNUDGER_RIGHT_MULTIPLIER.get();
    break;
   case "WEST":
    dirWest*=Configs.PLAYERNUDGER_FORWARD_MULTIPLIER.get(); dirEast*=Configs.PLAYERNUDGER_BACKWARD_MULTIPLIER.get();
    dirSouth*=Configs.PLAYERNUDGER_LEFT_MULTIPLIER.get(); dirNorth*=Configs.PLAYERNUDGER_RIGHT_MULTIPLIER.get();
    break;
   case "NORTHWEST":
    dirNorthWest*=Configs.PLAYERNUDGER_FORWARD_MULTIPLIER.get(); dirSouthEast*=Configs.PLAYERNUDGER_BACKWARD_MULTIPLIER.get();
    dirSouthWest*=Configs.PLAYERNUDGER_LEFT_MULTIPLIER.get(); dirNorthEast*=Configs.PLAYERNUDGER_RIGHT_MULTIPLIER.get();
    break;
    default:
    break;
  }
    
    randTemp=rand.nextInt(dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest+dirUp+dirDown);
    
    if (0 <= randTemp && randTemp <dirNorth) {
     tempX=0;tempZ=-Configs.PLAYERNUDGER_NORTH_STRENGTH.get(); directionStr="north";
    } else if (dirNorth <= randTemp && randTemp <dirNorth+dirNorthEast) {
     tempX=Configs.PLAYERNUDGER_NORTHEAST_STRENGTH.get();tempZ=-Configs.PLAYERNUDGER_NORTHEAST_STRENGTH.get(); directionStr="northeast";
    } else if (dirNorth+dirNorthEast <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast) {
     tempX=Configs.PLAYERNUDGER_EAST_STRENGTH.get();tempZ=0; directionStr="east";
    } else if (dirNorth+dirNorthEast+dirEast <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast) {
     tempX=Configs.PLAYERNUDGER_SOUTHEAST_STRENGTH.get();tempZ=Configs.PLAYERNUDGER_SOUTHEAST_STRENGTH.get(); directionStr="southeast";
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth) {
     tempX=0;tempZ=Configs.PLAYERNUDGER_SOUTH_STRENGTH.get(); directionStr="south";
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest) {
     tempX=-Configs.PLAYERNUDGER_SOUTHWEST_STRENGTH.get();tempZ=Configs.PLAYERNUDGER_SOUTHWEST_STRENGTH.get(); directionStr="southwest";
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest) {
     tempX=-Configs.PLAYERNUDGER_WEST_STRENGTH.get();tempZ=0; directionStr="west";
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest) {
     tempX=-Configs.PLAYERNUDGER_NORTHWEST_STRENGTH.get();tempZ=-Configs.PLAYERNUDGER_NORTHWEST_STRENGTH.get(); directionStr="northwest";
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest+dirUp) {
     tempY=Configs.PLAYERNUDGER_UP_STRENGTH.get(); directionStr ="up";
    } else if (dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest+dirUp <= randTemp && randTemp <dirNorth+dirNorthEast+dirEast+dirSouthEast+dirSouth+dirSouthWest+dirWest+dirNorthWest+dirUp+dirDown) {
     tempY=-Configs.PLAYERNUDGER_DOWN_STRENGTH.get()-.4; directionStr ="down";
    } else {
     directionStr ="nowhere";
    }

//   KeyBinding.setKeyBindState(Minecraft.getInstance().gameSettings.keyBindSneak.getKey(),false);
   
   targetedPlayer.setMotion(tempX, tempY + .4, tempZ);
   
   targetedPlayer.velocityChanged=true;
   
//   source.getServer().getPlayerList().sendMessage(new StringTextComponent(lookDirStr));
   source.getServer().getPlayerList().sendMessage(new StringTextComponent(TextFormatting.GOLD + "Oh no! " + TextFormatting.RED + targetedPlayer.getName().getFormattedText() + TextFormatting.GOLD + " was pushed " + directionStr + " by " + fromName + "."));
   }
   
   return 0;
  }

}
