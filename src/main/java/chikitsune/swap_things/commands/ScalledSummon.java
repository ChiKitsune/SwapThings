package chikitsune.swap_things.commands;

import java.util.Collection;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ScalledSummon {
 
 
 
 private static int scalledSummonLogic(CommandSourceStack source,Collection<ServerPlayer> targetPlayers, ResourceLocation targetMountRL, CompoundTag nbt) throws CommandSyntaxException {
  //TODO
  // Thoughts about making a summon automatically be more difficult based on conditions but couldn't decide exactly how.
  // Options were Local Chunk Level, Player Level, Game day, Number arg from command, current hearts, total hearts, armor, and/or whatever made sense
  
  return 0;
 }

}
