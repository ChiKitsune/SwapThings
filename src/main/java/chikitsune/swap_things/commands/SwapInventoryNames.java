package chikitsune.swap_things.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
import net.minecraftforge.registries.ForgeRegistries;

public class SwapInventoryNames {
public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("swapinventorynames").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return swapInventoryNamesLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return swapInventoryNamesLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null);
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return swapInventoryNamesLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int swapInventoryNamesLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName) {
   ArchCommand.ReloadConfig();
   ItemStack tempItem=ItemStack.EMPTY;
   int tempRandNum=0;
   TextComponent tempItemName=null,prevItemName=null;
   String strPref="",strMsgFromName="Someone";
   List<ItemStack> tempInventory=null,tempBUInventory;
   ServerPlayer targetedPlayerTwo;
   boolean isSamePerson=true;
   
//   tempInventory=ForgeRegistries.ITEMS.getValues().stream().map(ItemStack::new).collect(Collectors.toList());
//   ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent("1"+tempInventory.get(1).getDisplayName().getString()));
   
   if (fromName!=null) {
    strPref=fromName + "'s ";
    strMsgFromName=fromName;
   }
   
   for(ServerPlayer targetedPlayer : targetPlayers) {
    targetedPlayerTwo=ArchCommand.getNewRandomSecondTarget(targetedPlayer, targetedPlayer, source.getServer());
    isSamePerson=(targetedPlayer.getName().getContents() == targetedPlayerTwo.getName().getContents());
    prevItemName=null;
    
    if (!isSamePerson) {
     tempInventory=new ArrayList<ItemStack>();
     for (int i=0;i<targetedPlayerTwo.getInventory().getContainerSize();i++) {
      tempItem=ItemStack.EMPTY;
      
      if (!tempItem.isEmpty()) tempInventory.add(targetedPlayerTwo.getInventory().getItem(i));
      }
    }
    if (isSamePerson || tempInventory.size()<1 || tempInventory == null) tempInventory=ForgeRegistries.ITEMS.getValues().stream().map(ItemStack::new).collect(Collectors.toList());    
    tempBUInventory=tempInventory;
    
    for (int i=0;i<targetedPlayer.getInventory().getContainerSize();i++) {
     tempItem=ItemStack.EMPTY;
     tempItem=targetedPlayer.getInventory().getItem(i);
     if (!tempItem.isEmpty()) {
      if (tempInventory.size()<1) tempInventory=tempBUInventory;
      tempRandNum=rand.nextInt(tempInventory.size());
      tempItemName=new TextComponent(strPref + tempInventory.get(tempRandNum).getHoverName().getString());
      tempItem.setHoverName(tempItemName);
      tempInventory.remove(tempRandNum);
     }
    }
    
    ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " let " + strMsgFromName + " pick better names for their items."));
   }   
   return 0;
  }
}
