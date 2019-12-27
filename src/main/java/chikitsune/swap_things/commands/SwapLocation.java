package chikitsune.swap_things.commands;

import java.util.Arrays;
import java.util.Collection;

import com.mojang.brigadier.builder.ArgumentBuilder;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;

public class SwapLocation {
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("swaplocation").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return swapLocationLogic(cmd_0arg.getSource(),cmd_0arg.getSource().asPlayer(),cmd_0arg.getSource().asPlayer());
  }).then(Commands.argument("targetedPlayerOne", EntityArgument.players()).executes((cmd_1arg) -> {
   return swapLocationLogic(cmd_1arg.getSource(),EntityArgument.getPlayer(cmd_1arg, "targetedPlayerOne"),cmd_1arg.getSource().asPlayer());
  }).then(Commands.argument("targetedPlayerTwo", EntityArgument.players()).executes((cmd_2arg) -> {
   return swapLocationLogic(cmd_2arg.getSource(),EntityArgument.getPlayer(cmd_2arg, "targetedPlayerOne"),EntityArgument.getPlayer(cmd_2arg, "targetedPlayerTwo"));
  })));
 }
  
  private static int swapLocationLogic(CommandSource source, ServerPlayerEntity targetedPlayerOne, ServerPlayerEntity targetedPlayerTwo) {
//   if (targetedPlayerOne.getName().getUnformattedComponentText() == targetedPlayerTwo.getName().getUnformattedComponentText()) targetedPlayerTwo=ArchCommand.getNewRandomSecondTarget(targetedPlayerOne, targetedPlayerTwo, source.getServer());
   
   Boolean isSameDim=(targetedPlayerOne.dimension == targetedPlayerTwo.dimension);
   DimensionType playerOneDim=targetedPlayerOne.dimension;
   DimensionType playerTwoDim=targetedPlayerTwo.dimension;
   Vec3d playerOneVec=targetedPlayerOne.getPositionVec();
   Float playerOneYaw=targetedPlayerOne.rotationYaw;
   Float playerOnePitch=targetedPlayerOne.rotationPitch;
   Vec3d playerTwoVec=targetedPlayerTwo.getPositionVec();
   Float playerTwoYaw=targetedPlayerTwo.rotationYaw;
   Float playerTwoPitch=targetedPlayerTwo.rotationPitch;
   ServerWorld playerOnedimWorld=targetedPlayerOne.getServerWorld();
   ServerWorld playerTwodimWorld=targetedPlayerTwo.getServerWorld();
   
   targetedPlayerOne.teleport(playerTwodimWorld, MathHelper.floor(playerTwoVec.getX()), MathHelper.floor(playerTwoVec.getY()), MathHelper.floor(playerTwoVec.getZ()), playerTwoYaw, playerTwoPitch);

   targetedPlayerTwo.teleport(playerOnedimWorld, MathHelper.floor(playerOneVec.getX()), MathHelper.floor(playerOneVec.getY()), MathHelper.floor(playerOneVec.getZ()), playerOneYaw, playerOnePitch);
//   } catch ( Exception e) {
//    
//   }
   
   source.getServer().getPlayerList().sendMessage(new StringTextComponent("after teleport"));
   
   Collection<ServerPlayerEntity> targetPlayers=Arrays.asList(targetedPlayerOne);
    if (targetedPlayerOne.getName().getUnformattedComponentText() == targetedPlayerTwo.getName().getUnformattedComponentText()) {
    ArchCommand.playerMsger(source, targetPlayers,ArchCommand.getRainbowizedStr("Well I guess if you really want to swap locations with yourself you can go right ahead " + targetedPlayerOne.getName().getFormattedText() + ".")); 
//    source.getServer().getPlayerList().sendMessage(ArchCommand.getRainbowizedStr("Well I guess if you really want to swap locations with yourself you can go right ahead " + targetedPlayerOne.getName().getFormattedText() + "."));
  } else {
   targetPlayers=Arrays.asList(targetedPlayerOne,targetedPlayerTwo);
   ArchCommand.playerMsger(source, targetPlayers,ArchCommand.getRainbowizedStr("Wow what a trip. A fresh perspective is nice once a while wouldn't you agree " + targetedPlayerOne.getName().getFormattedText() + " and " + targetedPlayerTwo.getName().getFormattedText() + "?"));
//   source.getServer().getPlayerList().sendMessage(ArchCommand.getRainbowizedStr("Wow what a trip. A fresh perspective is nice once a while wouldn't you agree " + targetedPlayerOne.getName().getFormattedText() + " and " + targetedPlayerTwo.getName().getFormattedText() + "?"));
   }
   return 0;
  }
}
