package chikitsune.swap_things.commands;

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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

public class SummonMount {
 public static Random rand= new Random();

 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("summonmount").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(0); }).executes((cmd_0arg) -> {
   return summonMountLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone");
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return summonMountLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone");
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return summonMountLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"));
      })
     ));
 }
  
  private static int summonMountLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName) {
   Entity newMount;
   EntityType tempEnt;
   ServerWorld serverworld = source.getWorld();
   List<EntityType<?>> lstSummEnt = ForgeRegistries.ENTITIES.getValues().stream().filter(EntityType::isSummonable).collect(Collectors.toList());   
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {    
     do {
      tempEnt=lstSummEnt.get(rand.nextInt(lstSummEnt.size()));
//     } while (SwappingThings.sumMountList.contains(tempEnt.getRegistryName().toString()));
     } while (Configs.summonMountExcludeList.contains(tempEnt.getRegistryName().toString()));
//     newMount=tempEnt.spawn(source.getWorld(),new CompoundNBT(),null,null,targetedPlayer.getPosition(),SpawnReason.COMMAND,true,true);
     newMount=tempEnt.spawn(source.getWorld(),new CompoundNBT(),null,null,targetedPlayer.func_233580_cy_(),SpawnReason.COMMAND,true,true);
     if (newMount instanceof TameableEntity) {
      ((TameableEntity) newMount).setTamed(true);
      ((TameableEntity) newMount).setTamedBy(targetedPlayer);
     }
    targetedPlayer.getLowestRidingEntity().startRiding(newMount, true);
    
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + "Nice ride! " + TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " got a new " + TextFormatting.AQUA + tempEnt.getName().getString() + TextFormatting.GOLD + " mount by " + fromName + "."));
   }
   return 0;
  }
  
}