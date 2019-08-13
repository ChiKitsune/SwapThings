package chikitsune.swap_things.commands;

import java.rmi.registry.Registry;
import java.util.Random;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import chikitsune.swap_things.SwappingThings;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.command.arguments.ItemParser;
import net.minecraft.command.arguments.ItemPredicateArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.TextComponentMessageFormatHandler;
import net.minecraftforge.items.ItemStackHandler;

public class QuickHide {
 public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("quickhide").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return quickhideLogic(cmd_0arg.getSource(),cmd_0arg.getSource().asPlayer(),null,null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return quickhideLogic(cmd_1arg.getSource(),EntityArgument.getPlayer(cmd_1arg, "targetedPlayer"),null,null);
    }).then(Commands.argument("item", ItemArgument.item()).executes((cmd_2arg) -> {
      return quickhideLogic(cmd_2arg.getSource(),EntityArgument.getPlayer(cmd_2arg, "targetedPlayer"),ItemArgument.getItem(cmd_2arg, "item"),null);
     }).then(Commands.argument("message", StringArgumentType.string()).executes((cmd_3arg) -> {
      return quickhideLogic(cmd_3arg.getSource(),EntityArgument.getPlayer(cmd_3arg, "targetedPlayer"),ItemArgument.getItem(cmd_3arg, "item"),StringArgumentType.getString(cmd_3arg, "message"));
      })
     )));
 }
  
 private static int quickhideLogic(CommandSource source,ServerPlayerEntity targetedPlayer, ItemInput itemInput, String message) {
  ItemStack rndStack = ItemStack.EMPTY;
  String curMsg="quick use these to hide!";
  ItemArgument iaStack=new ItemArgument();

  try {
   if (itemInput==null && message == null) {
    Integer newRanNum=rand.nextInt(SwappingThings.quiHidList.size());
    rndStack=iaStack.parse(new StringReader(SwappingThings.quiHidList.get(newRanNum).get(0))).createStack(1, false);
    curMsg=SwappingThings.quiHidList.get(newRanNum).get(1);
   } else {
    rndStack=itemInput.createStack(1, false);
    curMsg=message;
   }
   if (curMsg == null) curMsg="quick use these to hide!";   
  } catch (CommandSyntaxException e) {
   e.printStackTrace();
   rndStack=new ItemStack(Items.DEAD_BUSH);
  }
  
  if (targetedPlayer.getItemStackFromSlot(EquipmentSlotType.HEAD) != ItemStack.EMPTY) targetedPlayer.dropItem(targetedPlayer.getItemStackFromSlot(EquipmentSlotType.HEAD), false, true);
  if (targetedPlayer.getItemStackFromSlot(EquipmentSlotType.CHEST) != ItemStack.EMPTY) targetedPlayer.dropItem(targetedPlayer.getItemStackFromSlot(EquipmentSlotType.CHEST), false, true);
  if (targetedPlayer.getItemStackFromSlot(EquipmentSlotType.FEET) != ItemStack.EMPTY) targetedPlayer.dropItem(targetedPlayer.getItemStackFromSlot(EquipmentSlotType.FEET), false, true);
  if (targetedPlayer.getItemStackFromSlot(EquipmentSlotType.LEGS) != ItemStack.EMPTY) targetedPlayer.dropItem(targetedPlayer.getItemStackFromSlot(EquipmentSlotType.LEGS), false, true);
  if (targetedPlayer.getItemStackFromSlot(EquipmentSlotType.OFFHAND) != ItemStack.EMPTY) targetedPlayer.dropItem(targetedPlayer.getItemStackFromSlot(EquipmentSlotType.OFFHAND), false, true);
  if (targetedPlayer.getItemStackFromSlot(EquipmentSlotType.MAINHAND) != ItemStack.EMPTY) targetedPlayer.dropItem(targetedPlayer.getItemStackFromSlot(EquipmentSlotType.MAINHAND), false, true);
    
  targetedPlayer.setItemStackToSlot(EquipmentSlotType.HEAD, rndStack.copy());
  targetedPlayer.setItemStackToSlot(EquipmentSlotType.CHEST, rndStack.copy());
  targetedPlayer.setItemStackToSlot(EquipmentSlotType.FEET, rndStack.copy());
  targetedPlayer.setItemStackToSlot(EquipmentSlotType.LEGS, rndStack.copy());
  targetedPlayer.setItemStackToSlot(EquipmentSlotType.OFFHAND, rndStack.copy());
  targetedPlayer.setItemStackToSlot(EquipmentSlotType.MAINHAND, rndStack.copy());
  
  source.getServer().getPlayerList().sendMessage(new StringTextComponent(TextFormatting.RED + targetedPlayer.getName().getFormattedText() + TextFormatting.GOLD + " " + curMsg));
  
  return 0;
 }
}
