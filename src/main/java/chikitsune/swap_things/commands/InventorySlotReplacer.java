package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;


public class InventorySlotReplacer {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext cmdBuildContext) { 
  return Commands.literal("inventoryslotreplacer").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return inventorySlotReplacerLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),null,null,"someone",cmdBuildContext);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return inventorySlotReplacerLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null,null,"someone",cmdBuildContext);
     }).then(Commands.argument("item", ItemArgument.item(cmdBuildContext)).executes((cmd_2arg) -> {
      return inventorySlotReplacerLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),ItemArgument.getItem(cmd_2arg, "item"),null,"someone",cmdBuildContext);
      }).then(Commands.argument("slotNum", StringArgumentType.string()).executes((cmd_3arg) -> {
       return inventorySlotReplacerLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),ItemArgument.getItem(cmd_3arg, "item"),StringArgumentType.getString(cmd_3arg, "slotNum"),"someone",cmdBuildContext);
       }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_4arg) -> {
        return inventorySlotReplacerLogic(cmd_4arg.getSource(),EntityArgument.getPlayers(cmd_4arg, "targetedPlayer"),ItemArgument.getItem(cmd_4arg, "item"),StringArgumentType.getString(cmd_4arg, "slotNum"),StringArgumentType.getString(cmd_4arg, "fromName"),cmdBuildContext);
        })
     ))));
 }
  
  private static int inventorySlotReplacerLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, ItemInput itemInput, String slotNum, String fromName,CommandBuildContext cmdBuildContext) {
   Integer selectedSlotNum=null;
   Integer slotNumParsed=null;
   Integer modFloatResult=null;
   ItemStack tempStack=ItemStack.EMPTY;
   ItemStack rndStack = ItemStack.EMPTY;
   boolean randomSlot=true;
   Integer iCnt=0,maxPlayerInvSize;
   
   try {
    slotNumParsed=Integer.parseInt(slotNum);
   } catch (Exception e) {
    slotNumParsed=-1;
   }
   if (slotNumParsed>=0) randomSlot=false;
   
   try {
    if (itemInput!=null) rndStack=itemInput.createItemStack(1, false);
   } catch (CommandSyntaxException e) {
    rndStack=ItemStack.EMPTY;
   } 
   
   for(ServerPlayer targetedPlayer : targetPlayers) {
    tempStack=ItemStack.EMPTY;
    maxPlayerInvSize=targetedPlayer.getInventory().getContainerSize();
    
    if (randomSlot) {
     iCnt=0;
     do {
     selectedSlotNum=rand.nextInt(maxPlayerInvSize);
     iCnt++;
     } while (targetedPlayer.getInventory().getItem(selectedSlotNum).isEmpty() && iCnt<50);
    } else {
     if (slotNumParsed == 0) {
      selectedSlotNum=targetedPlayer.getInventory().selected;
     } else if (slotNumParsed > 0) {
//      System.out.println("slotNumParsed: "+slotNumParsed+" maxPlayerInvSize: "+maxPlayerInvSize+" mod: "+(slotNumParsed % maxPlayerInvSize));
      selectedSlotNum=Math.round(slotNumParsed % maxPlayerInvSize)-1;
      if (selectedSlotNum<0) selectedSlotNum=0;
      
     } else {
      iCnt=0;
      do {
      selectedSlotNum=rand.nextInt(maxPlayerInvSize);
      iCnt++;
      } while (targetedPlayer.getInventory().getItem(selectedSlotNum).isEmpty() && iCnt<50);
     }
    }
    
    tempStack=targetedPlayer.getInventory().getItem(selectedSlotNum).copy();
    if (!tempStack.isEmpty()) {
     targetedPlayer.drop(targetedPlayer.getInventory().getItem(selectedSlotNum),false,true);
     targetedPlayer.getInventory().setItem(selectedSlotNum, rndStack.copy());
    }
    
   
    if (tempStack.isEmpty()) {
     ArchCommand.playerMsger(source, targetPlayers, 
       Component.literal("Oh! " + fromName + " tried to drop one of ").withStyle(ChatFormatting.GOLD)
       .append(Component.literal(targetedPlayer.getName().getString() + "'s ").withStyle(ChatFormatting.RED))
       .append(Component.literal("items but it was already empty.").withStyle(ChatFormatting.GOLD)));
//     ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.GOLD + "Oh! " + fromName + " tried to drop one of " + ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " but it was already empty."));
    } else {
     ArchCommand.playerMsger(source, targetPlayers, 
       Component.literal("Oh! " + fromName + " just dropped "  + tempStack.getCount() + " of ").withStyle(ChatFormatting.GOLD)
       .append(Component.literal(targetedPlayer.getName().getString() + "'s ").withStyle(ChatFormatting.RED))
       .append(Component.literal(tempStack.getDisplayName().getString()).withStyle(ChatFormatting.GOLD)));
//     ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.GOLD + "Oh! " + fromName + " just dropped "  + tempStack.getCount() + " of " + ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + "'s " + tempStack.getDisplayName().getString()));
    }
   }
   return 0;
  }

}
