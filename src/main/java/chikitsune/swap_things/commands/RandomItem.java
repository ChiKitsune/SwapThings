package chikitsune.swap_things.commands;

import chikitsune.swap_things.commands.arguments.RandomEffectTypeArgument;
import chikitsune.swap_things.config.Configs;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.*;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

public class RandomItem {
public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext cmdBuildContext) { 
  return Commands.literal("randomitem").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return randomItemLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone","ANY",null,null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return randomItemLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone","ANY",null,null);
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return randomItemLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"),"ANY",null,null);
     }).then(Commands.argument("randomEffectType", RandomEffectTypeArgument.randomEffectTypeArgument()).executes((cmd_3arg) -> {
      return randomItemLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "fromName"),RandomEffectTypeArgument.getRandomEffectType(cmd_3arg,"randomEffectType"),null,null); 
      }).then(Commands.argument("item", ItemArgument.item(cmdBuildContext)).executes((cmd_4arg) -> {
       return randomItemLogic(cmd_4arg.getSource(),EntityArgument.getPlayers(cmd_4arg, "targetedPlayer"),StringArgumentType.getString(cmd_4arg, "fromName"),RandomEffectTypeArgument.getRandomEffectType(cmd_4arg,"randomEffectType"),ItemArgument.getItem(cmd_4arg, "item"),null);
      }).then(Commands.argument("stackAmt",IntegerArgumentType.integer()).executes((cmd_5arg) -> {
       return randomItemLogic(cmd_5arg.getSource(),EntityArgument.getPlayers(cmd_5arg, "targetedPlayer"),StringArgumentType.getString(cmd_5arg, "fromName"),RandomEffectTypeArgument.getRandomEffectType(cmd_5arg,"randomEffectType"),ItemArgument.getItem(cmd_5arg, "item"),IntegerArgumentType.getInteger(cmd_5arg, "stackAmt"));
      })
     )))));
 }
 
 private static int randomItemLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName, String randomEffectType, ItemInput itemInput, Integer stackAmt) {
  ArchCommand.ReloadConfig();
  ItemStack tempStack=ItemStack.EMPTY;
  double bias=1.0F;
  Integer tempRandNum=0;
  
  if (itemInput!=null) {
   try {
    tempStack=itemInput.createItemStack(1, true);
   } catch (CommandSyntaxException e) {
    e.printStackTrace();
    tempStack=new ItemStack(Items.DEAD_BUSH);
   }
  }
  if(tempStack.isEmpty()) {
   
  }
  
  switch (randomEffectType.toUpperCase()) {
   case "ANY": bias=1.0F;
    break;
   case "POSITIVE": bias=.5F;
    break;
   case "NEGATIVE":bias=2.0F;
   break;
   case "RANDOM":
   default:bias=rand.nextDouble()*2;
    break;
  }
  
  tempRandNum = (int)(100 * Math.pow(rand.nextDouble(), bias));
  
  // TODO
  return 0;
 }

}
