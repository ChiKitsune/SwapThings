package chikitsune.swap_things.commands;

import chikitsune.swap_things.config.Configs;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Collection;

public class SwapLocation {
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("swaplocation").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return swapLocationLogic(cmd_0arg.getSource(),cmd_0arg.getSource().getPlayerOrException(),cmd_0arg.getSource().getPlayerOrException());
  }).then(Commands.argument("targetedPlayerOne", EntityArgument.players()).executes((cmd_1arg) -> {
   return swapLocationLogic(cmd_1arg.getSource(),EntityArgument.getPlayer(cmd_1arg, "targetedPlayerOne"),cmd_1arg.getSource().getPlayerOrException());
  }).then(Commands.argument("targetedPlayerTwo", EntityArgument.players()).executes((cmd_2arg) -> {
   return swapLocationLogic(cmd_2arg.getSource(),EntityArgument.getPlayer(cmd_2arg, "targetedPlayerOne"),EntityArgument.getPlayer(cmd_2arg, "targetedPlayerTwo"));
  })));
 }
  
  private static int swapLocationLogic(CommandSourceStack source, ServerPlayer targetedPlayerOne, ServerPlayer targetedPlayerTwo) {
   ArchCommand.ReloadConfig();
   if (targetedPlayerOne.getName().getContents() == targetedPlayerTwo.getName().getContents()) targetedPlayerTwo=ArchCommand.getNewRandomSecondTarget(targetedPlayerOne, targetedPlayerTwo, source.getServer());
   
   Boolean isSameDim=(targetedPlayerOne.getCommandSenderWorld().dimensionType() == targetedPlayerTwo.getCommandSenderWorld().dimensionType());
   DimensionType playerOneDim=targetedPlayerOne.getCommandSenderWorld().dimensionType();
   DimensionType playerTwoDim=targetedPlayerTwo.getCommandSenderWorld().dimensionType();
   Vec3 playerOneVec=targetedPlayerOne.position();
   Float playerOneYaw=targetedPlayerOne.getYRot();
   Float playerOnePitch=targetedPlayerOne.getXRot();
   Vec3 playerTwoVec=targetedPlayerTwo.position();
   Float playerTwoYaw=targetedPlayerTwo.getYRot();
   Float playerTwoPitch=targetedPlayerTwo.getXRot();
   ServerLevel playerOnedimWorld=targetedPlayerOne.serverLevel();
   ServerLevel playerTwodimWorld=targetedPlayerTwo.serverLevel();
   
   targetedPlayerOne.teleportTo(playerTwodimWorld, Mth.floor(playerTwoVec.x()), Mth.floor(playerTwoVec.y()), Mth.floor(playerTwoVec.z()), playerTwoYaw, playerTwoPitch);
   targetedPlayerTwo.teleportTo(playerOnedimWorld, Mth.floor(playerOneVec.x()), Mth.floor(playerOneVec.y()), Mth.floor(playerOneVec.z()), playerOneYaw, playerOnePitch);
   
   Collection<ServerPlayer> targetPlayers=Arrays.asList(targetedPlayerOne);
    if (targetedPlayerOne.getName().getContents() == targetedPlayerTwo.getName().getContents()) {
    ArchCommand.playerMsger(source, targetPlayers,ArchCommand.getRainbowizedStr("Well I guess if you really want to swap locations with yourself you can go right ahead " + targetedPlayerOne.getName().getString() + ".")); 
  } else {
   targetPlayers=Arrays.asList(targetedPlayerOne,targetedPlayerTwo);
   ArchCommand.playerMsger(source, targetPlayers,ArchCommand.getRainbowizedStr("Wow what a trip. A fresh perspective is nice once a while wouldn't you agree " + targetedPlayerOne.getName().getString() + " and " + targetedPlayerTwo.getName().getString() + "?"));
   }
   return 0;
  }
}
