package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

public class InventoryEqualizer {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("inventoryequalizer").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return inventoryEqualizerLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),null,null,"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
    return inventoryEqualizerLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),null,null,"someone");
    }).then(Commands.argument("item", ItemArgument.item()).executes((cmd_2arg) -> {
     return inventoryEqualizerLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),ItemArgument.getItem(cmd_2arg, "item"),null,"someone");
     }).then(Commands.argument("stackAmt",IntegerArgumentType.integer()).executes((cmd_3arg) -> {
      return inventoryEqualizerLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),ItemArgument.getItem(cmd_3arg, "item"),IntegerArgumentType.getInteger(cmd_3arg, "stackAmt"),"someone");
      }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_4arg) -> {
       return inventoryEqualizerLogic(cmd_4arg.getSource(),EntityArgument.getPlayers(cmd_4arg, "targetedPlayer"),ItemArgument.getItem(cmd_4arg, "item"),IntegerArgumentType.getInteger(cmd_4arg, "stackAmt"),StringArgumentType.getString(cmd_4arg, "fromName"));
      })))));
 }
 
 
 private static int inventoryEqualizerLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, ItemInput itemInput, Integer stackAmt, String fromName) {
  ItemStack defItemStack=ItemStack.EMPTY;
  ItemStack tempItemStack=ItemStack.EMPTY;
  Integer tempStackAmt=1;
  if (stackAmt!=null) tempStackAmt=stackAmt;
  if (itemInput!=null) {
   try {
   defItemStack=itemInput.createStack(1, true);
   } catch (CommandSyntaxException e) {
    e.printStackTrace();
    defItemStack=new ItemStack(Items.DEAD_BUSH);
   }
  }
  
//  defItemStack=new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(Configs.INVENTORY_BOMB_ITEM.get())));
//  if (defItemStack.isEmpty()) defItemStack=new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("minecraft:dead_bush")));
  defItemStack.setCount(tempStackAmt);
  
  List<NonNullList<ItemStack>> allInventories;
  for(ServerPlayerEntity targetedPlayer : targetPlayers) {
   allInventories = ImmutableList.of(targetedPlayer.inventory.mainInventory, targetedPlayer.inventory.armorInventory, targetedPlayer.inventory.offHandInventory);
   for(List<ItemStack> list : allInventories) {
    for(int i = 0; i < list.size(); ++i) {
     tempItemStack = list.get(i);
       if (!tempItemStack.isEmpty()) {
        ibDropItem(tempItemStack,targetedPlayer);
       }
       list.set(i, defItemStack.copy());
    }
 }
   
   ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " just found out what their inventory would look like if it only had " + TextFormatting.DARK_GREEN + defItemStack.getDisplayName().getString() + TextFormatting.GOLD + " in it."));
  }
  
  return 0;
 }
 
 @Nullable
 private static ItemEntity ibDropItem(ItemStack droppedItem, ServerPlayerEntity targetedPlayer) {
  if (droppedItem.isEmpty()) {
   return null;
  } else {
   double d0 = targetedPlayer.getPosY() - (double)0.3F + (double)targetedPlayer.getEyeHeight();
   ItemEntity itementity = new ItemEntity(targetedPlayer.world, targetedPlayer.getPosX(), d0, targetedPlayer.getPosZ(), droppedItem);
   itementity.setPickupDelay(20);
   itementity.setThrowerId(targetedPlayer.getUniqueID());
   float f = rand.nextFloat() * 0.5F;
   float f1 = rand.nextFloat() * ((float)Math.PI * 2F);
   itementity.setMotion((double)(-MathHelper.sin(f1) * f), (double)0.2F, (double)(MathHelper.cos(f1) * f));
   
   if (itementity.captureDrops() != null) itementity.captureDrops().add(itementity);
   else
    itementity.world.addEntity(itementity);
   return itementity;
  }
 }

}
