package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.mojang.brigadier.arguments.FloatArgumentType;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class InventorySlotClearer {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("inventoryslotclearer").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return inventorySlotClearerLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),null,"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return inventorySlotClearerLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null,"someone");
     }).then(Commands.argument("slotNum", StringArgumentType.string()).executes((cmd_2arg) -> {
      return inventorySlotClearerLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "slotNum"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_3arg) -> {
      return inventorySlotClearerLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "slotNum"),StringArgumentType.getString(cmd_3arg, "fromName"));
      })
     )));
 }
  
  private static int inventorySlotClearerLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String slotNum, String fromName) {
   Boolean sneakPressed=false;
   Integer selectedSlotNum=null;
   Float slotNumParsed=null;
   Integer modFloatResult=null;
   ItemStack tempStack=ItemStack.EMPTY;
   
   try {
    slotNumParsed=Float.parseFloat(slotNum);
   } catch (Exception e) {
    slotNumParsed=-1F;
   }
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    tempStack=ItemStack.EMPTY;
    
    if(slotNumParsed!=null && slotNumParsed >= 0 && slotNum !=null) {
     modFloatResult=Math.round((slotNumParsed % 1)*100);
//     if (slotNumParsed % 1 == 0 && slotNumParsed < targetedPlayer.inventory.getSizeInventory() && slotNumParsed >= 0) {
//      selectedSlotNum=slotNumParsed.intValue();
//      source.getServer().getPlayerList().sendMessage(new StringTextComponent("slot whole number" + slotNumParsed.toString() + " " + selectedSlotNum.toString()));
//     } else {
//      source.getServer().getPlayerList().sendMessage(new StringTextComponent("slot not whole number" + slotNumParsed.toString()));
      if (slotNumParsed % 1 < targetedPlayer.inventory.getSizeInventory() && slotNumParsed % 1 >= 0) {
       selectedSlotNum=modFloatResult.intValue();
       
      } else {
       selectedSlotNum=rand.nextInt(targetedPlayer.inventory.getSizeInventory());
       
      }
//     }
    } else {
     selectedSlotNum=rand.nextInt(targetedPlayer.inventory.getSizeInventory());
     
    }
    
    tempStack=targetedPlayer.inventory.getStackInSlot(selectedSlotNum).copy();
    targetedPlayer.inventory.setInventorySlotContents(selectedSlotNum, ItemStack.EMPTY.copy());
   
    if (tempStack.isEmpty()) {
     source.getServer().getPlayerList().sendMessage(new StringTextComponent(TextFormatting.GOLD + "Oh! " + fromName + " tried to clear an inventory slot from " + TextFormatting.RED + targetedPlayer.getName().getFormattedText() + TextFormatting.GOLD + " but it was already empty."));
    } else {
     source.getServer().getPlayerList().sendMessage(new StringTextComponent(TextFormatting.GOLD + "Oh! " + fromName + " just took "  + tempStack.getCount() + " of " + TextFormatting.RED + targetedPlayer.getName().getFormattedText() + TextFormatting.GOLD + "'s " + tempStack.getDisplayName().getFormattedText()));
    }
   }
   
   return 0;
  }

}