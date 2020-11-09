package chikitsune.swap_things.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import chikitsune.swap_things.config.Configs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.EntitySummonArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.registries.ForgeRegistries;

public class SummonMount {
 public static Random rand= new Random();

 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("summonmount").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return summonMountLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().asPlayer()),"someone",null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return summonMountLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone",null);
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return summonMountLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"),null);
      }).then(Commands.argument("mount", EntitySummonArgument.entitySummon()).executes((cmd_3arg) -> {
       return summonMountLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "fromName"),EntitySummonArgument.getEntityId(cmd_3arg, "mount"));
       })
     )));
 }
  
  private static int summonMountLogic(CommandSource source,Collection<ServerPlayerEntity> targetPlayers, String fromName, ResourceLocation targetMountRL) throws CommandSyntaxException {
   Configs.bakeConfig();
   Entity newMount;
   EntityType tempEnt = null;
   ServerWorld serverworld = source.getWorld();
   List<EntityType<?>> lstSummEnt = ForgeRegistries.ENTITIES.getValues().stream().filter((EntityType eT) -> !Configs.summonMountExcludeList.contains(eT.getRegistryName().toString())).filter(EntityType::isSummonable).collect(Collectors.toList());
   CompoundNBT compoundnbt = new CompoundNBT();
   Boolean randMount=true;
   if(targetMountRL!=null) {
    randMount=false;   
   
   compoundnbt.putString("id", targetMountRL.toString());
   }
   
   
   for(ServerPlayerEntity targetedPlayer : targetPlayers) {
    if (randMount) {
//     do {
      tempEnt=lstSummEnt.get(rand.nextInt(lstSummEnt.size()));
//     } while (SwappingThings.sumMountList.contains(tempEnt.getRegistryName().toString()));
//     } while (!(tempEnt.create(null) instanceof LivingEntity));
    } else {
     if(EntityType.readEntityType(compoundnbt).isPresent()) {
      tempEnt=EntityType.readEntityType(compoundnbt).get();
     } else {
      throw new SimpleCommandExceptionType(new TranslationTextComponent("commands.summon.failed")).create();
     }
    }
     
     
//     newMount=tempEnt.spawn(source.getWorld(),new CompoundNBT(),null,null,targetedPlayer.getPosition(),SpawnReason.COMMAND,true,true);
     newMount=tempEnt.spawn(source.getWorld(),new CompoundNBT(),null,null,targetedPlayer.getPosition(),SpawnReason.COMMAND,true,true);
     if (newMount instanceof TameableEntity) {
      ((TameableEntity) newMount).setTamedBy(targetedPlayer);
      ((TameableEntity) newMount).setTamed(true);
     }
     newMount.setCustomName(ArchCommand.getRainbowizedStr(fromName));
     newMount.setCustomNameVisible(true);
    targetedPlayer.getLowestRidingEntity().startRiding(newMount, true);
    
    ArchCommand.playerMsger(source, targetPlayers, new StringTextComponent(TextFormatting.GOLD + "Nice ride! " + TextFormatting.RED + targetedPlayer.getName().getString() + TextFormatting.GOLD + " got a new " + TextFormatting.AQUA + tempEnt.getName().getString() + TextFormatting.GOLD + " mount by " + fromName + "."));
   }
   return 0;
  }
  
}