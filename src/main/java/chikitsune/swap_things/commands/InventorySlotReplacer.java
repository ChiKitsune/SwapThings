package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import chikitsune.swap_things.config.Configs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class InventorySlotReplacer {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("inventoryslotreplacer").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return inventorySlotReplacerLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),null,null,"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return inventorySlotReplacerLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null,null,"someone");
     }).then(Commands.argument("item", ItemArgument.item()).executes((cmd_2arg) -> {
      return inventorySlotReplacerLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),ItemArgument.getItem(cmd_2arg, "item"),null,"someone");
      }).then(Commands.argument("slotNum", StringArgumentType.string()).executes((cmd_3arg) -> {
       return inventorySlotReplacerLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),ItemArgument.getItem(cmd_3arg, "item"),StringArgumentType.getString(cmd_3arg, "slotNum"),"someone");
       }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_4arg) -> {
        return inventorySlotReplacerLogic(cmd_4arg.getSource(),EntityArgument.getPlayers(cmd_4arg, "targetedPlayer"),ItemArgument.getItem(cmd_4arg, "item"),StringArgumentType.getString(cmd_4arg, "slotNum"),StringArgumentType.getString(cmd_4arg, "fromName"));
        })
     ))));
 }
  
  private static int inventorySlotReplacerLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, ItemInput itemInput, String slotNum, String fromName) {
   Integer selectedSlotNum=null;
   Float slotNumParsed=null;
   Integer modFloatResult=null;
   ItemStack tempStack=ItemStack.EMPTY;
   ItemStack rndStack = ItemStack.EMPTY;
   
   try {
    slotNumParsed=Float.parseFloat(slotNum);
   } catch (Exception e) {
    slotNumParsed=-1F;
   }
   try {
    if (itemInput==null) {
//     rndStack
    } else {
     rndStack=itemInput.createStack(1, false);
    }
   } catch (CommandSyntaxException e) {
    e.printStackTrace();
    rndStack=ItemStack.EMPTY;
   }  
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    tempStack=ItemStack.EMPTY;
    
    if(slotNumParsed!=null && (slotNumParsed == 0 || (slotNumParsed % 1 == 0) ) && slotNum !=null) {
     selectedSlotNum=targetedPlayer.inventory.currentItem;
    } else if(slotNumParsed!=null && slotNumParsed > 0 && slotNum !=null) {
     modFloatResult=Math.round((slotNumParsed % 1)*100) -1;
      if (slotNumParsed % 1 < targetedPlayer.inventory.getSizeInventory() && slotNumParsed % 1 > 0) {
       selectedSlotNum=modFloatResult.intValue();       
      } else {
       selectedSlotNum=rand.nextInt(targetedPlayer.inventory.getSizeInventory());
      }
    } else {
     selectedSlotNum=rand.nextInt(targetedPlayer.inventory.getSizeInventory());
    }
    
    tempStack=targetedPlayer.inventory.getStackInSlot(selectedSlotNum).copy();
    if (!tempStack.isEmpty()) {
     targetedPlayer.dropItem(targetedPlayer.inventory.getStackInSlot(selectedSlotNum),false,true);
     targetedPlayer.inventory.setInventorySlotContents(selectedSlotNum, rndStack.copy());
    }
    
   
    if (tempStack.isEmpty()) {
     ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + "Oh! " + fromName + " tried to drop one of " + TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " but it was already empty."));
    } else {
     ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + "Oh! " + fromName + " just dropped "  + tempStack.getCount() + " of " + TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + "'s " + tempStack.getDisplayName().getString()));
    }
   }
   return 0;
  }

}
