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
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.EntitySummonArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundSetPassengersPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraftforge.registries.ForgeRegistries;

public class SummonRider {
 public static Random rand= new Random();

 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("summonrider").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return summonRiderLogic(cmd_0arg.getSource(),Collections.singleton(cmd_0arg.getSource().getPlayerOrException()),"someone",null);
   }).then(Commands.argument("targetedPlayer", EntityArgument.players()).executes((cmd_1arg) -> {
     return summonRiderLogic(cmd_1arg.getSource(),EntityArgument.getPlayers(cmd_1arg, "targetedPlayer"),"someone",null);
     }).then(Commands.argument("fromName", StringArgumentType.string()).executes((cmd_2arg) -> {
      return summonRiderLogic(cmd_2arg.getSource(),EntityArgument.getPlayers(cmd_2arg, "targetedPlayer"),StringArgumentType.getString(cmd_2arg, "fromName"),null);
      }).then(Commands.argument("mount", EntitySummonArgument.id()).executes((cmd_3arg) -> {
       return summonRiderLogic(cmd_3arg.getSource(),EntityArgument.getPlayers(cmd_3arg, "targetedPlayer"),StringArgumentType.getString(cmd_3arg, "fromName"),EntitySummonArgument.getSummonableEntity(cmd_3arg, "mount"));
       })
     )));
 }
  
  private static int summonRiderLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, String fromName, ResourceLocation targetMountRL) throws CommandSyntaxException {
   ArchCommand.ReloadConfig();
   Entity newMount;
   EntityType tempEnt = null;
   ServerLevel serverworld = source.getLevel();
   
//   Boolean boolCreature=true;
//   List<EntityClassification> ttt= new ArrayList<>();
//   ttt.add(EntityClassification.getClassificationByName("creature"));
   
   List<EntityType<?>> lstSummEnt = ForgeRegistries.ENTITIES.getValues().stream()
     .filter((EntityType eT) -> !ArchCommand.GetSM_EXT_LIST().contains(eT.getRegistryName().toString()))
     .filter(EntityType::canSummon)
     .filter((EntityType eT) -> ArchCommand.GetSR_INC_LIST().contains(eT.getCategory()))
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
      throw new SimpleCommandExceptionType(new TranslatableComponent("commands.summon.failed")).create();
     }
    }
     
     newMount=tempEnt.spawn(source.getLevel(),new CompoundTag(),null,null,targetedPlayer.blockPosition(),MobSpawnType.COMMAND,true,true);
     if (newMount instanceof TamableAnimal && Configs.SM_CUS_TAME.get()) {
      ((TamableAnimal) newMount).tame(targetedPlayer);
      ((TamableAnimal) newMount).setTame(true);
     } else if (newMount instanceof AbstractHorse && Configs.SM_CUS_TAME.get()) {
      ((AbstractHorse) newMount).setTamed(true);
     }
     newMount.setCustomName(ArchCommand.getRainbowizedStr(fromName));
     newMount.setCustomNameVisible(true);
     
     boolean isRidingNow=false;
     if (targetedPlayer.getPassengers().size()>0) {
      Entity tempPass=targetedPlayer.getPassengers().get(0);
      tempPass.stopRiding();
      tempPass.startRiding(newMount, true);
     }
      isRidingNow=newMount.startRiding(targetedPlayer,true);
     if (isRidingNow && targetedPlayer.connection != null) {
      targetedPlayer.connection.send(new ClientboundSetPassengersPacket(targetedPlayer));
    }
     
    ArchCommand.playerMsger(source, targetPlayers, new TextComponent(ChatFormatting.RED + targetedPlayer.getName().getString() + "'s " + ChatFormatting.GOLD + "head feels about a " + ChatFormatting.AQUA + tempEnt.getDescription().getString() + "'s " + ChatFormatting.GOLD + "weight worth heavier. " + fromName + " you wouldn't know why would you?"));
   }
   return 0;
  }

}
