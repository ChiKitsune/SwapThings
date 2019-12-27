package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.registries.ForgeRegistries;

public class InventoryBomb {
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("inventorybomb").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return inventoryBombLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return inventoryBombLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return inventoryBombLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
 
 private static int inventoryBombLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName) {
  ItemStack defItemStack=ItemStack.EMPTY;
  ItemStack tempItemStack=ItemStack.EMPTY;

  defItemStack=new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Configs.INVENTORY_BOMB_ITEM.get())));
  if (defItemStack.isEmpty()) defItemStack=new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:dead_bush")));
  defItemStack.setCount(defItemStack.getMaxStackSize());
  
  for(ServerPlayerEntity targetedPlayer : targetPlayers) {
   for (int i=0;i<targetedPlayer.inventory.getSizeInventory();i++) {
    tempItemStack=ItemStack.EMPTY;
    
    tempItemStack=targetedPlayer.inventory.getStackInSlot(i);
    if (!tempItemStack.isEmpty()) {
     targetedPlayer.dropItem(tempItemStack, true, true);
    }
    targetedPlayer.replaceItemInInventory(i, defItemStack.copy());
   }
   targetedPlayer.setItemStackToSlot(EquipmentSlotType.HEAD, defItemStack.copy());
   targetedPlayer.setItemStackToSlot(EquipmentSlotType.CHEST, defItemStack.copy());
   targetedPlayer.setItemStackToSlot(EquipmentSlotType.FEET, defItemStack.copy());
   targetedPlayer.setItemStackToSlot(EquipmentSlotType.LEGS, defItemStack.copy());
   targetedPlayer.setItemStackToSlot(EquipmentSlotType.OFFHAND, defItemStack.copy());
   targetedPlayer.setItemStackToSlot(EquipmentSlotType.MAINHAND, defItemStack.copy());
   
   ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getFormattedText() + TextFormatting.GOLD + " just found out what their inventory would look like if it only had " + defItemStack.getDisplayName().getFormattedText() + " in it."));
  }
  
  return 0;
 }

}
