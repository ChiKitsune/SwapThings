package chikitsune.swap_things.commands;

import chikitsune.swap_things.config.Configs;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.Collections;

public class Panicing {
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("panicing").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return panicingLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return panicingLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return panicingLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int panicingLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName) {
   ArchCommand.ReloadConfig();
   
   // TODO
   // Pretty sure this was for something to happen based on the block the player was standing on.
   // Ideally wanted a way in the configs or with a json file to set which blocks or types of blocks (off of block tags) and do what is stated (effect or command)  
   
   for(ServerPlayer targetedPlayer : targetPlayers) {
    
    
    
    
    
    targetedPlayer.updateOptions(null );
    if (targetedPlayer.connection != null) {

   }
    
   }
   
   return 0;
  }

}
