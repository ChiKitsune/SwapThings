package chikitsune.swap_things.commands;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.mojang.brigadier.builder.ArgumentBuilder;

import chikitsune.swap_things.config.Configs;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.stats.Stats;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.common.UsernameCache;

public class DisplayDeathBoard {
 public static ArgumentBuilder<CommandSource, ?> register() { 
  return Commands.literal("displaydeathboard").requires((cmd_init) -> { return cmd_init.hasPermissionLevel(Configs.cmdSTPermissionsLevel); }).executes((cmd_0arg) -> {
   return displayDeathBoardLogic(cmd_0arg.getSource());
  });
 }
  
  
  private static int displayDeathBoardLogic(CommandSource source) {
   List<ServerPlayerEntity> plyList=new ArrayList<ServerPlayerEntity>(source.getServer().getPlayerList().getPlayers());
   PlayerList plyListForStats= source.getServer().getPlayerList();
   Map<String, Integer> playerDeaths = buildDeathBoardPlayerList(source.getServer());
   
   
   
//   plyList.sort((ServerPlayerEntity spe1, ServerPlayerEntity spe2) -> spe2.getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS))-spe1.getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS)));
   
   
   
   String placeStrPre = null,placeStrPost = null;
   source.getServer().getPlayerList().sendPacketToAllPlayers(new SChatPacket(new StringTextComponent(TextFormatting.GOLD + "** DEATH LEADERBOARD **"),ChatType.SYSTEM,plyList.stream().findFirst().get().getUniqueID()));
   
   if (playerDeaths.entrySet().size()>0) {
   int c=0;
   for(Map.Entry<String, Integer> entry:playerDeaths.entrySet()) {
    c=c+1;
    
    if (entry.getValue()!=0) {
     switch (c %10) {
      case 1: placeStrPre=c + "st: "; break;
      case 2: placeStrPre=c + "nd: "; break;
      case 3: placeStrPre=c + "rd: "; break;
      default: placeStrPre=c + "th: "; break;
     }
    
    
    if (Configs.displayDeathBoardPlacesTextList.size()>0) {
     int c_post=c-1;
     if (c_post>Configs.displayDeathBoardPlacesTextList.size()-1) c_post=Configs.displayDeathBoardPlacesTextList.size()-1;
     
     if (Configs.displayDeathBoardPlacesTextList.get(c_post) != null) {
     placeStrPost=Configs.displayDeathBoardPlacesTextList.get(c_post);
     } else {
      placeStrPost="";
     }
     } else {
      placeStrPost="";
     }
    }

    source.getServer().getPlayerList().sendPacketToAllPlayers(new SChatPacket(new StringTextComponent(
      TextFormatting.GOLD + placeStrPre + 
      TextFormatting.DARK_RED + entry.getValue() +
      TextFormatting.GOLD + " deaths: " + 
      TextFormatting.RED + entry.getKey() +
      TextFormatting.WHITE + placeStrPost
      ),ChatType.SYSTEM,plyList.stream().findFirst().get().getUniqueID()));
   }
   } else {
    source.getServer().getPlayerList().sendPacketToAllPlayers(new SChatPacket(new StringTextComponent(TextFormatting.GOLD + "No deaths ... for now!"),ChatType.SYSTEM,plyList.stream().findFirst().get().getUniqueID()));
   }
   source.getServer().getPlayerList().sendPacketToAllPlayers(new SChatPacket(new StringTextComponent(TextFormatting.GOLD + "*********************************"),ChatType.SYSTEM,plyList.stream().findFirst().get().getUniqueID()));
   return 0;
  }
  
  private static Map<String, Integer> buildDeathBoardPlayerList (MinecraftServer server) {
   Map<String, Integer> playerDeaths = new LinkedHashMap<>();
   List<ServerPlayerEntity> plyList=new ArrayList<ServerPlayerEntity>(server.getPlayerList().getPlayers());
   
   plyList.forEach((ServerPlayerEntity ply)->playerDeaths.put(ply.getName().getString(), ply.getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS))));

   try {
    File statsFolder = server.func_240776_a_(FolderName.STATS).toFile();
    if(statsFolder.isDirectory() && statsFolder.listFiles() != null && statsFolder.listFiles().length > 0) {
     for (File playerStatFile : statsFolder.listFiles()) {
      String uuid = FilenameUtils.removeExtension(playerStatFile.getName());
      String username = UsernameCache.getLastKnownUsername(UUID.fromString(uuid));
      if(!playerDeaths.containsKey(username)) {
       JsonParser parser = new JsonParser();
       JsonElement element = parser.parse(new FileReader(playerStatFile));
       JsonObject object = element.getAsJsonObject();
       JsonObject stats = object.getAsJsonObject("stats");
       JsonObject customStats = stats.getAsJsonObject(Stats.CUSTOM.getRegistryName().toString());
       JsonPrimitive deaths = customStats.getAsJsonPrimitive(Stats.DEATHS.toString());
//        JsonObject statsCustomObj = parser.parse(new FileReader(playerStatFile)).getAsJsonObject().getAsJsonObject("stats").getAsJsonObject(Stats.CUSTOM.getRegistryName().toString());
//        JsonPrimitive deaths = statsCustomObj.getAsJsonPrimitive(Stats.DEATHS.toString());
       playerDeaths.put(username, deaths.getAsInt());
       }
     }
    }
  } catch (Exception e) {
   e.printStackTrace();
  }
   
   Map<String, Integer> playerDeathsOut = playerDeaths.entrySet()
    .stream().filter((Map.Entry<String, Integer> entry)-> entry.getValue()!=0)
    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
   
//   playerDeaths.entrySet().stream().filter((Map.Entry<String, Integer> entry)-> entry.getValue()!=0).collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
//   playerDeaths.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed());
//   playerDeaths.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEachOrdered(null);
   
   return playerDeathsOut;
  }
}