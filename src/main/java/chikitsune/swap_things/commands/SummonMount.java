package chikitsune.swap_things.commands;

import chikitsune.swap_things.config.Configs;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.*;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.stream.Collectors;

public class SummonMount {
 public static Random rand= new Random();

 public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext cmdBuildContext) {
  return Commands.literal("summonmount").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return summonMountLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone",null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return summonMountLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone",null);
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return summonMountLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"),null);
      }).then(Commands.argument("mount", ResourceArgument.resource(cmdBuildContext, Registries.ENTITY_TYPE)).suggests(SuggestionProviders.SUMMONABLE_ENTITIES).executes((cmd_3arg) -> {
       return summonMountLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "fromName"),ResourceArgument.getSummonableEntityType(cmd_3arg, "mount").key().registry());
       })
     )));
 }
  
  private static int summonMountLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName, ResourceLocation targetMountRL) throws CommandSyntaxException {
   ArchCommand.ReloadConfig();
   Entity newMount;
   EntityType tempEnt = null;
   ServerLevel serverworld = source.getLevel();
   
//   System.out.println("ArchCommand.GetSM_EXT_LIST(): "+ArchCommand.GetSM_EXT_LIST());
//   System.out.println("ArchCommand.GetSM_INC_LIST(): "+ArchCommand.GetSM_INC_LIST());
   
   List<EntityType<?>> lstSummEnt = ForgeRegistries.ENTITY_TYPES.getValues().stream()
     .filter((EntityType eT) -> !ArchCommand.GetSM_EXT_LIST().contains(ForgeRegistries.ENTITY_TYPES.getKey(eT).toString()))
     .filter(EntityType::canSummon)
     .filter((EntityType eT) -> ArchCommand.GetSM_INC_LIST().contains(eT.getCategory()))
     .collect(Collectors.toList());
   CompoundTag compoundnbt = new CompoundTag();
   Boolean randMount=true;
   if(targetMountRL!=null) {
    randMount=false;
   compoundnbt.putString("id", targetMountRL.toString());
   }
   
   
   for(ServerPlayer targetedPlayer : targetPlayers) {    
    if (randMount) {
      tempEnt=lstSummEnt.get(rand.nextInt(lstSummEnt.size()));
    } else {
     if(EntityType.by(compoundnbt).isPresent()) {
      tempEnt=EntityType.by(compoundnbt).get();
     } else {
      throw new SimpleCommandExceptionType(Component.translatable("commands.summon.failed")).create();
     }
    }
     
     newMount=tempEnt.spawn(source.getLevel(),new CompoundTag(),null,targetedPlayer.blockPosition(),MobSpawnType.COMMAND,true,true);
     if (newMount instanceof TamableAnimal && Configs.SM_CUS_TAME.get()) {
      ((TamableAnimal) newMount).tame(targetedPlayer);
      ((TamableAnimal) newMount).setTame(true);
     } else if (newMount instanceof AbstractHorse && Configs.SM_CUS_TAME.get()) {
      ((AbstractHorse) newMount).setTamed(true);
     }
     
     if (Configs.SM_CUS_NAME.get()) {
     newMount.setCustomName(ArchCommand.getRainbowizedStr(fromName));
     newMount.setCustomNameVisible(true);
     }
    targetedPlayer.getRootVehicle().startRiding(newMount, true);
    
    ArchCommand.playerMsger(source, targetPlayers, 
      Component.literal("Nice ride! ").withStyle(ChatFormatting.GOLD)
      .append(Component.literal(targetedPlayer.getName().getString()).withStyle(ChatFormatting.RED))
      .append(Component.literal(" got a new ").withStyle(ChatFormatting.GOLD))
      .append(Component.literal(tempEnt.getDescription().getString()).withStyle(ChatFormatting.AQUA))
      .append(Component.literal(" mount by " + fromName + ".").withStyle(ChatFormatting.GOLD)));
//    ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.GOLD + "Nice ride! " + ChatFormatting.RED + targetedPlayer.getName().getString() + ChatFormatting.GOLD + " got a new " + ChatFormatting.AQUA + tempEnt.getDescription().getString() + ChatFormatting.GOLD + " mount by " + fromName + "."));
   }
   return 0;
  }
  
}