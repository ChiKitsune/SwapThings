package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import chikitsune.swap_things.config.Configs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class RandomGift {
public static Random rand= new Random();
 
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("randomgift").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return randomGiftLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return randomGiftLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return randomGiftLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int randomGiftLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName) {
   ArchCommand.ReloadConfig();
   ItemStack tempStack = ItemStack.EMPTY;
   String curMsg="where is the gift?";
   ItemArgument iaStack=new ItemArgument();
   Integer weightedChance=0, weightedMax=1 ,randTemp,curWeight,curLoop,stackAmount=64;
   
   weightedMax=Configs.RG_LIST.get().stream().map(str->Integer.parseInt(str.split(",")[2])).reduce(0, Integer::sum);

   for(ServerPlayer targetedPlayer : targetPlayers) {
    randTemp=rand.nextInt(weightedMax)+1;
    curWeight=0;
    curLoop=0;
    
    for(String str:Configs.RG_LIST.get()) {
     curWeight+=Integer.parseInt(str.split(",")[2]);
     if(curWeight>=randTemp) {
      try {
       stackAmount=Integer.parseInt(str.split(",")[1]);
      tempStack=iaStack.parse(new StringReader(str.split(",")[0])).createItemStack(stackAmount, false);
      } catch (CommandSyntaxException e) {
       e.printStackTrace();
       stackAmount=64;
       tempStack=new ItemStack(Items.DEAD_BUSH,stackAmount);       
      }
      break;
     }
     curLoop+=1;
    }
    curMsg=ChatFormatting.GOLD + "Oh! " + fromName + " was nice and gave " + ChatFormatting.RED + targetedPlayer.getName().getString() + " " + ChatFormatting.GOLD + tempStack.getCount() + " " + ChatFormatting.AQUA + tempStack.getHoverName().getString();
    
    targetedPlayer.addItem(tempStack);
    
    ArchCommand.playerMsger(source, targetPlayers, new TextComponent(curMsg));
   }
   
   return 0;
  }

}
