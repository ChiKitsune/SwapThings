package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class ToggleRun {
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("togglerun").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return toggleRunLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return toggleRunLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return toggleRunLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int toggleRunLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName) {
   Boolean sneakPressed=false;
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
   sneakPressed=Minecraft.getInstance().gameSettings.keyBindSprint.isKeyDown();
   KeyBinding.setKeyBindState(Minecraft.getInstance().gameSettings.keyBindSprint.getKey(),!sneakPressed);
   
   source.getServer().getPlayerList().sendMessage(new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getFormattedText() + TextFormatting.GOLD + " let " + fromName + " decide if they should be running or not."));
   }
   
   return 0;
  }

}