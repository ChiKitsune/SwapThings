package chikitsune.swap_things.commands;

import chikitsune.swap_things.config.Configs;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class SummonAmbush {
 public static Random rand= new Random();
 
 // swapthings summonambush [playername] [courtesy of] [entitySpawnDistance] [entityCNT] [entity] [entityNBT] 
 
 
 private static int summonAmbushLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName, Integer entitySpawnDis, Integer entityCNT, ResourceLocation ambushRL, CompoundTag ambushNBT) throws CommandSyntaxException {
  ArchCommand.ReloadConfig();

  Integer plyX,plyY,plyZ;
  Integer loopCnt=0,listLoopCnt=0;
  boolean attTele=false;
  List<Integer> yPossibleList;
  BlockPos blockpos;
  BlockState blockstate;
  
  // These will need to be their own configs
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
   
   
   //TODO: Loop through each count and do the math below
   
   do {
    Integer rndYloopCnt=0;
    rdmAngle = Math.random()*Math.PI*2;
    teleX = Math.cos(rdmAngle)*entitySpawnDis;
    teleZ = Math.sin(rdmAngle)*entitySpawnDis;
//    do { teleY=rand.nextInt((255)+1); } while (!(((teleY+plyY)>=1) && ((plyY+teleY)<=255)));
    do { teleY=rand.nextInt((Y_Max*2)+1) - Y_Max; rndYloopCnt++; } while (!(Math.abs(teleY)>=Y_Min && Math.abs(teleY)<=Y_Max) && rndYloopCnt<=100);
    
    
   }while (!attTele && loopCnt<=20);
   
   
  }
  
  return 0;
 }

}
