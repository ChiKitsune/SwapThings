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
import net.minecraft.block.BlockState;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class RandomTeleportDirection {
public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  Configs.bakeConfig();
  return Commands.literal("randomteleportdirection").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return randomTeleportDirectionLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone", Configs.randomTeleportDirectionDistance);
  }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
   return randomTeleportDirectionLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone", Configs.randomTeleportDirectionDistance);
   }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
    return randomTeleportDirectionLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"), Configs.randomTeleportDirectionDistance);
   }).then(Commands.argument("distance",IntegerArgumentType.integer()).executes((cmd_3arg) -> {
    return randomTeleportDirectionLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "fromName"), IntegerArgumentType.getInteger(cmd_3arg, "distance"));
   })
   )));
 }
  
private static int randomTeleportDirectionLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName, Integer distance) {
 Configs.bakeConfig();

   Integer plyX,plyY,plyZ;
   Integer loopCnt=0,listLoopCnt=0;
   boolean attTele=false;
   List<Integer> yPossibleList;
   BlockPos blockpos;
   BlockState blockstate;
   
   
   
   double rdmDegree;
   double rdmAngle, rdmX, rdmY, rdmZ;
   double teleX=0,teleY=0,teleZ=0;
   Integer rdmDir;
   
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    
    rdmAngle = Math.random()*Math.PI*2;
    teleX = Math.cos(rdmAngle)*distance;
    teleZ = Math.sin(rdmAngle)*distance;
    
    attTele=false;
    teleY=0;
    loopCnt=0;
    plyX= MathHelper.floor(targetedPlayer.getPosition().getX());
    plyY= MathHelper.floor(targetedPlayer.getPosition().getY());
    plyZ= MathHelper.floor(targetedPlayer.getPosition().getZ());
    
    do {
     do { teleY=rand.nextInt((255)+1); } while (!(((teleY+plyY)>=1) && ((plyY+teleY)<=255)));
     
     targetedPlayer.teleport(targetedPlayer.getServerWorld(),(plyX + teleX), (plyY + teleY), (plyZ + teleZ), targetedPlayer.rotationYaw, targetedPlayer.rotationPitch);
     attTele=targetedPlayer.attemptTeleport((plyX + teleX), (plyY + teleY), (plyZ + teleZ), true);
     
     if (!attTele) {
      blockpos = new BlockPos((plyX + teleX), (plyY + teleY), (plyZ + teleZ));
      
      blockstate=targetedPlayer.world.getBlockState(blockpos);
      
      yPossibleList=Lists.newArrayList();
      
      for (int i = 0; i <= 255; i++){
       if ((i>=1) && (i<=255)) yPossibleList.add(i);
       }
      Collections.shuffle(yPossibleList);
      listLoopCnt=0;
      do {
       attTele=targetedPlayer.attemptTeleport((plyX + teleX), yPossibleList.get(listLoopCnt), (plyZ + teleZ), true);
       listLoopCnt++;
      } while (!attTele && listLoopCnt<yPossibleList.size());
     }
     
     loopCnt++;
    } while (!attTele && loopCnt<=20);
    
    if (attTele) {
     ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + "Oh no! " + TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " was distracted by " + fromName + " and is now lost."));
     } else {
      targetedPlayer.teleport(targetedPlayer.getServerWorld(),(plyX), (plyY), (plyZ), targetedPlayer.rotationYaw, targetedPlayer.rotationPitch);
      ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + "Oh no " + fromName + " tried to distract " + TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " but they were distracted instead."));
     }
   }  
   
   return 0;
  }

}
