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

public class RandomTeleport {
public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  ArchCommand.ReloadConfig();
  return Commands.literal("randomteleport").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return randomTeleportLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone", Configs.RT_X_MIN.get(), Configs.RT_X_MAX.get(), Configs.RT_Y_MIN.get(), Configs.RT_Y_MAX.get(), Configs.RT_Z_MIN.get(), Configs.RT_Z_MAX.get());
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return randomTeleportLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone", Configs.RT_X_MIN.get(), Configs.RT_X_MAX.get(), Configs.RT_Y_MIN.get(), Configs.RT_Y_MAX.get(), Configs.RT_Z_MIN.get(), Configs.RT_Z_MAX.get());
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return randomTeleportLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"), Configs.RT_X_MIN.get(), Configs.RT_X_MAX.get(), Configs.RT_Y_MIN.get(), Configs.RT_Y_MAX.get(), Configs.RT_Z_MIN.get(), Configs.RT_Z_MAX.get());
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
  
//  private static int randomTeleportLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName) {
 private static int randomTeleportLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName,
       Integer X_Min, Integer X_Max, Integer Y_Min, Integer Y_Max, Integer Z_Min, Integer Z_Max) {
   ArchCommand.ReloadConfig();
   Integer rndXloopCnt=0,rndYloopCnt=0,rndZloopCnt=0;
   Integer teleX=0,teleY=0,teleZ=0;
   Integer plyX,plyY,plyZ;
   Integer loopCnt=0,listLoopCnt=0;
   boolean attTele=false,tempChk=false;
   List<Integer> yPossibleList;
   BlockPos blockpos;
   BlockState blockstate;
   
   if(X_Max<0) X_Max=0;
   if(X_Min<0) X_Min=0;
   if(Y_Max<0) Y_Max=0;
   if(Y_Min<0) Y_Min=0;
   if(Z_Max<0) Z_Max=0;
   if(Z_Min<0) Z_Min=0;
   
   
   for(ServerPlayer targetedPlayer : targetPlayers) {
    rand= new Random();
    attTele=false;
    teleX=0;
    teleY=0;
    teleZ=0;
    loopCnt=0;
    plyX= Mth.floor(targetedPlayer.blockPosition().getX());
    plyY= Mth.floor(targetedPlayer.blockPosition().getY());
    plyZ= Mth.floor(targetedPlayer.blockPosition().getZ());

    
    do {
     rndXloopCnt=0;
     rndYloopCnt=0;
     rndZloopCnt=0;
     do { teleX=rand.nextInt((X_Max*2)+1) - X_Max; rndXloopCnt++;} while (!(Math.abs(teleX)>=X_Min && Math.abs(teleX)<=X_Max) && rndXloopCnt<=100);
     do { teleY=rand.nextInt((Y_Max*2)+1) - Y_Max; rndYloopCnt++; } while (!(Math.abs(teleY)>=Y_Min && Math.abs(teleY)<=Y_Max) && rndYloopCnt<=100);
     do { teleZ=rand.nextInt((Z_Max*2)+1) - Z_Max; rndZloopCnt++; } while (!(Math.abs(teleZ)>=Z_Min && Math.abs(teleZ)<=Z_Max) && rndZloopCnt<=100);     
     
     targetedPlayer.teleportTo(targetedPlayer.getLevel(),(plyX + teleX), (plyY + teleY), (plyZ + teleZ), targetedPlayer.getYRot(), targetedPlayer.getXRot());
     attTele=targetedPlayer.randomTeleport((plyX + teleX), (plyY + teleY), (plyZ + teleZ), true);
     
     if (!attTele) {
      blockpos = new BlockPos((plyX + teleX), (plyY + teleY), (plyZ + teleZ));
      
      blockstate=targetedPlayer.level.getBlockState(blockpos);
      
      yPossibleList=Lists.newArrayList();
      
      
      
      for (int i = (plyY-Y_Max); i <= (plyY+Y_Max); i++){
//       System.out.println("i="+i);
       if ((Math.abs(i)>=Y_Min)) yPossibleList.add(i);
       }
//      System.out.println("ul="+yPossibleList.toString());
      Collections.shuffle(yPossibleList);
//      System.out.println("ol="+yPossibleList.toString());
      listLoopCnt=0;
      do {
//       System.out.println("yPossibleList.get(listLoopCnt)="+yPossibleList.get(listLoopCnt));
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
      targetedPlayer.teleportTo(targetedPlayer.getLevel(),(plyX), (plyY), (plyZ), targetedPlayer.getYRot(), targetedPlayer.getXRot());
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
