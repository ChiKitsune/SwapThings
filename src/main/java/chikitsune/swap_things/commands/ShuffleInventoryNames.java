package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ShuffleInventoryNames {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("shuffleinventorynames").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return shuffleInventoryNamesLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return shuffleInventoryNamesLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null);
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return shuffleInventoryNamesLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int shuffleInventoryNamesLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName) {
   ArchCommand.ReloadConfig();
   ItemStack tempItem=ItemStack.EMPTY;
   Integer tempRandNum=0,tempFirstNonEmpty=0;
   TextComponent tempItemName=null,prevItemName=null;
   String strPref="",strMsgFromName="Someone";
   if (fromName!=null) {
    strPref=fromName + "'s ";
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
       //tempItemName=new StringTextComponent(strPref + tempItem.getDisplayName().getUnformattedComponentText());
      tempItemName=new TextComponent(strPref + tempItem.getHoverName().getString());
       tempItem.setHoverName(prevItemName);
       prevItemName=tempItemName;
      }
     }
    if (tempFirstNonEmpty!=null) targetedPlayer.getInventory().getItem(tempFirstNonEmpty).setHoverName(prevItemName);
   
   ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " let " + strMsgFromName + " pick better names for their items."));
   }
   return 0;
  }

}
