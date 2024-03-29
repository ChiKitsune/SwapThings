package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class RandomTeleportDirection {
public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  ArchCommand.ReloadConfig();
  return Commands.literal("randomteleportdirection").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return randomTeleportDirectionLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone", Configs.RTD_AMT.get());
  }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
   return randomTeleportDirectionLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone", Configs.RTD_AMT.get());
   }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
    return randomTeleportDirectionLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"), Configs.RTD_AMT.get());
   }).then(Commands.argument("distance",IntegerArgumentType.integer()).executes((cmd_3arg) -> {
    return randomTeleportDirectionLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "fromName"), IntegerArgumentType.getInteger(cmd_3arg, "distance"));
   })
   )));
 }
  
private static int randomTeleportDirectionLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName, Integer distance) {
   ArchCommand.ReloadConfig();

   int plyX,plyY,plyZ;
   int loopCnt=0,listLoopCnt=0;
   boolean attTele=false;
   List<Integer> yPossibleList;
   BlockPos blockpos;
   BlockState blockstate;
   Integer Y_Min=Configs.RTD_Y_MIN.get();
   Integer Y_Max=Configs.RTD_Y_MAX.get();
   double rdmDegree;
   double rdmAngle, rdmX, rdmY, rdmZ;
   double teleX=0,teleY=0,teleZ=0;
   Integer rdmDir;
   
   
   for(ServerPlayer targetedPlayer : targetPlayers) {
    rand= new Random();

    
    attTele=false;
    teleY=0;
    loopCnt=0;
    plyX= Mth.floor(targetedPlayer.blockPosition().getX());
    plyY= Mth.floor(targetedPlayer.blockPosition().getY());
    plyZ= Mth.floor(targetedPlayer.blockPosition().getZ());
    
    do {
     Integer rndYloopCnt=0;
     rdmAngle = Math.random()*Math.PI*2;
     teleX = Math.cos(rdmAngle)*distance;
     teleZ = Math.sin(rdmAngle)*distance;
//     do { teleY=rand.nextInt((255)+1); } while (!(((teleY+plyY)>=1) && ((plyY+teleY)<=255)));
     do { teleY=rand.nextInt((Y_Max*2)+1) - Y_Max; rndYloopCnt++; } while (!(Math.abs(teleY)>=Y_Min && Math.abs(teleY)<=Y_Max) && rndYloopCnt<=100);
     
     
     targetedPlayer.teleportTo(targetedPlayer.serverLevel(),(plyX + teleX), (plyY + teleY), (plyZ + teleZ), targetedPlayer.getYRot(), targetedPlayer.getXRot());
     attTele=targetedPlayer.randomTeleport((plyX + teleX), (plyY + teleY), (plyZ + teleZ), true);
     
     if (!attTele) {
      blockpos = new BlockPos((int) (plyX + teleX), (int) (plyY + teleY), (int) (plyZ + teleZ));
      
      blockstate=targetedPlayer.level().getBlockState(blockpos);
      
      yPossibleList=Lists.newArrayList();
      
      for (int i = (plyY-Y_Max); i <= (plyY+Y_Max); i++){
       if ((Math.abs(i)>=Y_Min)) yPossibleList.add(i);
      }
//      for (int i = 0; i <= 255; i++){
//       if ((i>=1) && (i<=255)) yPossibleList.add(i);
//       }
      Collections.shuffle(yPossibleList);
      listLoopCnt=0;
      do {
       attTele=targetedPlayer.randomTeleport((plyX + teleX), yPossibleList.get(listLoopCnt), (plyZ + teleZ), true);
       listLoopCnt++;
      } while (!attTele && listLoopCnt<yPossibleList.size() && listLoopCnt <=300);
     }
     
     loopCnt++;
    } while (!attTele && loopCnt<=20);
    
    if (attTele) {
     ArchCommand.playerMsger(source, targetPlayers, 
       Component.literal("Oh no! ").withStyle(ChatFormatting.GOLD)
       .append(Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED))
       .append(Component.literal(" was distracted by " + fromName + " and is now lost.").withStyle(ChatFormatting.GOLD)));
//     ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.GOLD + "Oh no! " + ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " was distracted by " + fromName + " and is now lost."));
     } else {
      targetedPlayer.teleportTo(targetedPlayer.serverLevel(),(plyX), (plyY), (plyZ), targetedPlayer.getYRot(), targetedPlayer.getXRot());
      ArchCommand.playerMsger(source, targetPlayers, 
        Component.literal("Oh no " + fromName + " tried to distract ").withStyle(ChatFormatting.GOLD)
        .append(Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED))
        .append(Component.literal(" but they were distracted instead.").withStyle(ChatFormatting.GOLD)));
//      ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.GOLD + "Oh no " + fromName + " tried to distract " + ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " but they were distracted instead."));
     }
   }  
   
   return 0;
  }

}
