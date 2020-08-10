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
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ForgeRegistries;

public class SwapInventoryNames {
public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("swapinventorynames").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return swapInventoryNamesLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return swapInventoryNamesLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null);
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return swapInventoryNamesLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int swapInventoryNamesLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName) {
   ItemStack tempItem=ItemStack.EMPTY;
   int tempRandNum=0;
   StringTextComponent tempItemName=null,prevItemName=null;
   String strPref="",strMsgFromName="Someone";
   List<ItemStack> tempInventory=null,tempBUInventory;
   ServerPlayerEntity targetedPlayerTwo;
   boolean isSamePerson=true;
   
//   tempInventory=ForgeRegistries.ITEMS.getValues().stream().map(ItemStack::new).collect(Collectors.toList());
//   ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent("1"+tempInventory.get(1).getDisplayName().getString()));
   
   if (fromName!=null) {
    strPref=fromName + "'s ";
    strMsgFromName=fromName;
   }
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    targetedPlayerTwo=ArchCommand.getNewRandomSecondTarget(targetedPlayer, targetedPlayer, source.getServer());
    isSamePerson=(targetedPlayer.getName().getUnformattedComponentText() == targetedPlayerTwo.getName().getUnformattedComponentText());
    prevItemName=null;
    
    if (!isSamePerson) {
     tempInventory=new ArrayList<ItemStack>();
     for (int i=0;i<targetedPlayerTwo.inventory.getSizeInventory();i++) {
      tempItem=ItemStack.EMPTY;
      
      if (!tempItem.isEmpty()) tempInventory.add(targetedPlayerTwo.inventory.getStackInSlot(i));
      }
    }
    if (isSamePerson || tempInventory.size()<1 || tempInventory == null) tempInventory=ForgeRegistries.ITEMS.getValues().stream().map(ItemStack::new).collect(Collectors.toList());    
    tempBUInventory=tempInventory;
    
    for (int i=0;i<targetedPlayer.inventory.getSizeInventory();i++) {
     tempItem=ItemStack.EMPTY;
     tempItem=targetedPlayer.inventory.getStackInSlot(i);
     if (!tempItem.isEmpty()) {
      if (tempInventory.size()<1) tempInventory=tempBUInventory;
      tempRandNum=rand.nextInt(tempInventory.size());
      tempItemName=new StringTextComponent(strPref + tempInventory.get(tempRandNum).getDisplayName().getString());
      tempItem.setDisplayName(tempItemName);
      tempInventory.remove(tempRandNum);
     }
    }
    
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " let " + strMsgFromName + " pick better names for their items."));
   }   
   return 0;
  }
}
