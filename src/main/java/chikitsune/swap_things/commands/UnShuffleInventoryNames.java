package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class UnShuffleInventoryNames {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("unshuffleinventorynames").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return unShuffleInventoryNamesLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return unShuffleInventoryNamesLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null);
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return unShuffleInventoryNamesLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int unShuffleInventoryNamesLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName) {
   ItemStack tempItem=ItemStack.EMPTY;
   Integer tempRandNum=0,tempFirstNonEmpty=0;
   StringTextComponent tempItemName=null,prevItemName=null;
   String strMsgFromName="Someone";
   if (fromName!=null) {
    strMsgFromName=fromName;
   }
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    tempFirstNonEmpty=null;
    prevItemName=null;
    
    for (int i=0;i<targetedPlayer.inventory.getSizeInventory();i++) {
     tempItem=ItemStack.EMPTY;
     tempItem=targetedPlayer.inventory.getStackInSlot(i);
     if (!tempItem.isEmpty()) {
      if (tempFirstNonEmpty==null) tempFirstNonEmpty=i;
      tempItem.setDisplayName(null);
       prevItemName=tempItemName;
      }
     }
    if (tempFirstNonEmpty!=null) targetedPlayer.inventory.getStackInSlot(tempFirstNonEmpty).setDisplayName(prevItemName);
   
   ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + strMsgFromName + " decided that " + TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " had too many items with fancy names and changed them back."));
   }
   return 0;
  }

}
