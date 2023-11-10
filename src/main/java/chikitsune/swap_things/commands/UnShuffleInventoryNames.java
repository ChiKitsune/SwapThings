package chikitsune.swap_things.commands;

import chikitsune.swap_things.config.Configs;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public class UnShuffleInventoryNames {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("unshuffleinventorynames").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return unShuffleInventoryNamesLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return unShuffleInventoryNamesLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null);
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return unShuffleInventoryNamesLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int unShuffleInventoryNamesLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName) {
   ArchCommand.ReloadConfig();
   ItemStack tempItem=ItemStack.EMPTY;
   Integer tempRandNum=0,tempFirstNonEmpty=0;
   Component tempItemName=null,prevItemName=null;
   String strMsgFromName="Someone";
   if (fromName!=null) {
    strMsgFromName=fromName;
   }
   
   for(ServerPlayer targetedPlayer : targetPlayers) {
    tempFirstNonEmpty=null;
    prevItemName=null;
    
    for (int i=0;i<targetedPlayer.getInventory().getContainerSize();i++) {
     tempItem=ItemStack.EMPTY;
     tempItem=targetedPlayer.getInventory().getItem(i);
     if (!tempItem.isEmpty()) {
      if (tempFirstNonEmpty==null) tempFirstNonEmpty=i;
      tempItem.setHoverName(null);
       prevItemName=tempItemName;
      }
     }
    if (tempFirstNonEmpty!=null) targetedPlayer.getInventory().getItem(tempFirstNonEmpty).setHoverName(prevItemName);
   
    ArchCommand.playerMsger(source, targetPlayers, 
      Component.literal(strMsgFromName + " decided that ").withStyle(ChatFormatting.GOLD)
      .append(Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED))
      .append(Component.literal(" had too many items with fancy names and changed them back.").withStyle(ChatFormatting.GOLD)));
//   ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.GOLD + strMsgFromName + " decided that " + ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " had too many items with fancy names and changed them back."));
   }
   return 0;
  }

}
