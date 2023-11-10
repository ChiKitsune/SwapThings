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
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.stats.Stats;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.common.UsernameCache;

public class DisplayDeathBoard {
 public static ArgumentBuilder<CommandSourceStack, ?> register() { 
  return Commands.literal("displaydeathboard").requires((cmd_init) -> { return cmd_init.hasPermission(Configs.CMD_PERMISSION_LEVEL.get()); }).executes((cmd_0arg) -> {
   return displayDeathBoardLogic(cmd_0arg.getSource());
  });
 }
  
  
  private static int displayDeathBoardLogic(CommandSourceStack source) {
   ArchCommand.ReloadConfig();
   List<ServerPlayer> plyList=new ArrayList<ServerPlayer>(source.getServer().getPlayerList().getPlayers());
   PlayerList plyListForStats= source.getServer().getPlayerList();
   Map<String, Integer> playerDeaths = buildDeathBoardPlayerList(source.getServer());
   
   Component leaderHeader=Component.literal("** DEATH LEADERBOARD **").withStyle(ChatFormatting.GOLD);
   
//   plyList.sort((ServerPlayerEntity spe1, ServerPlayerEntity spe2) -> spe2.getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS))-spe1.getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS)));
   
   
   
   String placeStrPre = null,placeStrPost = null;
//   source.getServer().getPlayerList().broadcastAll(new ClientboundChatPacket(new TextComponent(ChatFormatting.GOLD + "** DEATH LEADERBOARD **"),ChatType.SYSTEM,plyList.stream().findFirst().get().m_142081_()));
   
//   for(ServerPlayer allTargetedPlayer : source.getServer().getPlayerList().getPlayers()) {
//    allTargetedPlayer.sendSystemMessage(leaderHeader);
//    }
   ArchCommand.sendAllPlayMsg(source,leaderHeader);
   
   if (!playerDeaths.entrySet().isEmpty()) {
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
    
    
    if (!Configs.DB_LIST.get().isEmpty()) {
     int c_post=c-1;
     if (c_post>Configs.DB_LIST.get().size()-1) c_post=Configs.DB_LIST.get().size()-1;
     
     if (Configs.DB_LIST.get().get(c_post) != null) {
     placeStrPost=Configs.DB_LIST.get().get(c_post);
     } else {
      placeStrPost="";
     }
     } else {
      placeStrPost="";
     }
    }

//    source.getServer().getPlayerList().broadcastAll(new ClientboundChatPacket(new TextComponent(
//      ChatFormatting.GOLD + placeStrPre + 
//      ChatFormatting.DARK_RED + entry.getValue() +
//      ChatFormatting.GOLD + " deaths: " + 
//      ChatFormatting.RED + entry.getKey() +
//      ChatFormatting.WHITE + placeStrPost
//      ),ChatType.SYSTEM,plyList.stream().findFirst().get().m_142081_()));
    
    ArchCommand.sendAllPlayMsg(source,
      Component.literal(placeStrPre).withStyle(ChatFormatting.GOLD)
      .append(Component.literal(entry.getValue().toString()).withStyle(ChatFormatting.DARK_RED))
      .append(Component.literal(" deaths: ").withStyle(ChatFormatting.GOLD))
      .append(Component.literal(entry.getKey()).withStyle(ChatFormatting.RED))
      .append(Component.literal(placeStrPost).withStyle(ChatFormatting.WHITE))
      );
   }
   } else {
    ArchCommand.sendAllPlayMsg(source, Component.literal("No deaths ... for now!").withStyle(ChatFormatting.GOLD));
//    source.getServer().getPlayerList().broadcastAll(new ClientboundChatPacket(new TextComponent(ChatFormatting.GOLD + "No deaths ... for now!"),ChatType.SYSTEM,plyList.stream().findFirst().get().m_142081_()));
   }
   ArchCommand.sendAllPlayMsg(source, Component.literal("*********************************").withStyle(ChatFormatting.GOLD));
//   source.getServer().getPlayerList().broadcastAll(new ClientboundChatPacket(new TextComponent(ChatFormatting.GOLD + "*********************************"),ChatType.SYSTEM,plyList.stream().findFirst().get().m_142081_()));
   return 0;
  }
  
  private static Map<String, Integer> buildDeathBoardPlayerList (MinecraftServer server) {
   Map<String, Integer> playerDeaths = new LinkedHashMap<>();
   List<ServerPlayer> plyList=new ArrayList<ServerPlayer>(server.getPlayerList().getPlayers());
   
   plyList.forEach((ServerPlayer ply)->playerDeaths.put(ply.getName().getString(), ply.getStats().getValue(Stats.CUSTOM.get(Stats.DEATHS))));

   try {
    File statsFolder = server.getWorldPath(LevelResource.PLAYER_STATS_DIR).toFile();
    if(statsFolder.isDirectory() && statsFolder.listFiles() != null && statsFolder.listFiles().length > 0) {
     for (File playerStatFile : statsFolder.listFiles()) {
      String uuid = FilenameUtils.removeExtension(playerStatFile.getName());
      String username = UsernameCache.getLastKnownUsername(UUID.fromString(uuid));
      if(!playerDeaths.containsKey(username)) {
       JsonParser parser = new JsonParser();
       JsonElement element = parser.parse(new FileReader(playerStatFile));
       JsonObject object = element.getAsJsonObject();
       JsonObject stats = object.getAsJsonObject("stats");
       JsonObject customStats = stats.getAsJsonObject(Stats.CUSTOM.getRegistry().toString());
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