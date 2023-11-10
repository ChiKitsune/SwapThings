package chikitsune.swap_things.commands;

import chikitsune.swap_things.config.Configs;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.server.level.ServerPlayer;

import java.util.*;

public class PlayerRotate {
public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("playerrotate").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return playerRotateLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return playerRotateLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return playerRotateLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int playerRotateLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName) {
   ArchCommand.ReloadConfig();
   float playYaw,rndYaw,rndPitch;
   
   for(ServerPlayer targetedPlayer : targetPlayers) {
    playYaw=targetedPlayer.getRotationVector().y;
    rndYaw=rand.nextInt(360)-180;
    rndPitch=rand.nextInt(180)-90;

    if (targetedPlayer.connection != null) {
    targetedPlayer.connection.send(new ClientboundPlayerPositionPacket(targetedPlayer.getX(),targetedPlayer.getY(),targetedPlayer.getZ(),rndYaw,rndPitch, Collections.emptySet(),0));
    }
    
//    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent("Pitch org:"+targetedPlayer.getPitchYaw().x+" rnd:"+rndPitch));
//    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent("Yaw org:"+targetedPlayer.getPitchYaw().y+" rnd:"+rndYaw));
    ArchCommand.playerMsger(source, targetPlayers,
      Component.literal(fromName + " spun " ).withStyle(ChatFormatting.GOLD)
      .append(Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED))
      .append(Component.literal(" right round.").withStyle(ChatFormatting.GOLD)));
//    ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.GOLD + fromName + " spun " + ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " right round."));
   }
   return 0;
  }

}
