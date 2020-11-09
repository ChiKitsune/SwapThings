package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
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
   Configs.bakeConfig();
   Integer rngXMax=Configs.randomTeleportXMax;
   Integer rngYMax=Configs.randomTeleportYMax;
   Integer rngZMax=Configs.randomTeleportZMax;
   Integer rngXMin=Configs.randomTeleportXMin;
   Integer rngYMin=Configs.randomTeleportYMin;
   Integer rngZMin=Configs.randomTeleportZMin;
   Integer teleX=0,teleY=0,teleZ=0;
   Integer plyX,plyY,plyZ;
   Integer loopCnt=0,listLoopCnt=0;
   boolean attTele=false,tempChk=false;
   List<Integer> yPossibleList;
   BlockPos blockpos;
   BlockState blockstate;
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    attTele=false;
    teleX=0;
    teleY=0;
    teleZ=0;
    loopCnt=0;
    plyX= MathHelper.floor(targetedPlayer.getPosition().getX());
    plyY= MathHelper.floor(targetedPlayer.getPosition().getY());
    plyZ= MathHelper.floor(targetedPlayer.getPosition().getZ());

    
    do {
     do { teleX=rand.nextInt((rngXMax*2)+1) - rngXMax; } while (!(Math.abs(teleX)>=rngXMin && Math.abs(teleX)<=rngXMax));
     do { teleY=rand.nextInt((rngYMax*2)+1) - rngYMax; } while (!(Math.abs(teleY)>=rngYMin && Math.abs(teleY)<=rngYMax && ((teleY+plyY)>=1) && ((plyY+teleY)<=255)));
     do { teleZ=rand.nextInt((rngZMax*2)+1) - rngZMax; } while (!(Math.abs(teleZ)>=rngZMin && Math.abs(teleZ)<=rngZMax));     
     
     targetedPlayer.teleport(targetedPlayer.getServerWorld(),(plyX + teleX), (plyY + teleY), (plyZ + teleZ), targetedPlayer.rotationYaw, targetedPlayer.rotationPitch);
     attTele=targetedPlayer.attemptTeleport((plyX + teleX), (plyY + teleY), (plyZ + teleZ), true);
     
     if (!attTele) {
      blockpos = new BlockPos((plyX + teleX), (plyY + teleY), (plyZ + teleZ));
      
      blockstate=targetedPlayer.world.getBlockState(blockpos);
      
      yPossibleList=Lists.newArrayList();
      
      for (int i = (plyY-rngYMax); i <= (plyY+rngYMax); i++){
//       System.out.println("i="+i);
       if ((Math.abs(i)>=rngYMin) && (i>=1) && (i<=255)) yPossibleList.add(i);
       }
//      System.out.println("ul="+yPossibleList.toString());
      Collections.shuffle(yPossibleList);
//      System.out.println("ol="+yPossibleList.toString());
      listLoopCnt=0;
      do {
//       System.out.println("yPossibleList.get(listLoopCnt)="+yPossibleList.get(listLoopCnt));
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
