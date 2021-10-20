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

public class RandomTeleport {
public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  Configs.bakeConfig();
  return Commands.literal("randomteleport").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return randomTeleportLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone",Configs.randomTeleportXMin, Configs.randomTeleportXMax, Configs.randomTeleportYMin, Configs.randomTeleportYMax, Configs.randomTeleportZMin, Configs.randomTeleportZMax);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return randomTeleportLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone",Configs.randomTeleportXMin, Configs.randomTeleportXMax, Configs.randomTeleportYMin, Configs.randomTeleportYMax, Configs.randomTeleportZMin, Configs.randomTeleportZMax);
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return randomTeleportLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"),Configs.randomTeleportXMin, Configs.randomTeleportXMax, Configs.randomTeleportYMin, Configs.randomTeleportYMax, Configs.randomTeleportZMin, Configs.randomTeleportZMax);
      }).then(Commands.argument("X_Min", IntegerArgumentType.integer())
        .then(Commands.argument("X_Max", IntegerArgumentType.integer())
          .then(Commands.argument("Y_Min", IntegerArgumentType.integer())
            .then(Commands.argument("Y_Max", IntegerArgumentType.integer())
              .then(Commands.argument("Z_Min", IntegerArgumentType.integer())
                .then(Commands.argument("Z_Max", IntegerArgumentType.integer())        
        .executes((cmd_8arg) -> {
       return randomTeleportLogic(cmd_8arg.getSource(),EntityArgument.getPlayers(cmd_8arg, "targetedPlayer"),StringArgumentType.getString(cmd_8arg, "fromName"),
         IntegerArgumentType.getInteger(cmd_8arg, "X_Min"), IntegerArgumentType.getInteger(cmd_8arg, "X_Max"),
         IntegerArgumentType.getInteger(cmd_8arg, "Y_Min"), IntegerArgumentType.getInteger(cmd_8arg, "Y_Max"),
         IntegerArgumentType.getInteger(cmd_8arg, "Z_Min"), IntegerArgumentType.getInteger(cmd_8arg, "Z_Max"));
       })
     ))))))));
 }
  
  private static int randomTeleportLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName,
        Integer X_Min, Integer X_Max, Integer Y_Min, Integer Y_Max, Integer Z_Min, Integer Z_Max) {
   Configs.bakeConfig();
//   Integer X_Max=Configs.randomTeleportXMax;
//   Integer Y_Max=Configs.randomTeleportYMax;
//   Integer Z_Max=Configs.randomTeleportZMax;
//   Integer X_Min=Configs.randomTeleportXMin;
//   Integer Y_Min=Configs.randomTeleportYMin;
//   Integer Z_Min=Configs.randomTeleportZMin;
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
     do { teleX=rand.nextInt((X_Max*2)+1) - X_Max; } while (!(Math.abs(teleX)>=X_Min && Math.abs(teleX)<=X_Max));
     do { teleY=rand.nextInt((Y_Max*2)+1) - Y_Max; } while (!(Math.abs(teleY)>=Y_Min && Math.abs(teleY)<=Y_Max && ((teleY+plyY)>=1) && ((plyY+teleY)<=255)));
     do { teleZ=rand.nextInt((Z_Max*2)+1) - Z_Max; } while (!(Math.abs(teleZ)>=Z_Min && Math.abs(teleZ)<=Z_Max));     
     
     targetedPlayer.teleport(targetedPlayer.getServerWorld(),(plyX + teleX), (plyY + teleY), (plyZ + teleZ), targetedPlayer.rotationYaw, targetedPlayer.rotationPitch);
     attTele=targetedPlayer.attemptTeleport((plyX + teleX), (plyY + teleY), (plyZ + teleZ), true);
     
     if (!attTele) {
      blockpos = new BlockPos((plyX + teleX), (plyY + teleY), (plyZ + teleZ));
      
      blockstate=targetedPlayer.world.getBlockState(blockpos);
      
      yPossibleList=Lists.newArrayList();
      
      for (int i = (plyY-Y_Max); i <= (plyY+Y_Max); i++){
//       System.out.println("i="+i);
       if ((Math.abs(i)>=Y_Min) && (i>=1) && (i<=255)) yPossibleList.add(i);
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
