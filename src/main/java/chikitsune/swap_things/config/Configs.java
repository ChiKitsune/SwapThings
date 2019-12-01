package chikitsune.swap_things.config;

import java.nio.file.Path;
import java.util.List;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class Configs {
 
 public static final String CATEGORY_GENERAL = "general";
 public static final String CATEGORY_COMMANDS = "commands";
 public static final String SUBCATEGORY_CMD_PLAYERNUDGER = "playernudger command";
 public static final String SUBCATEGORY_CMD_QUICKHIDE = "quickhide command";

 private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
 private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

 public static ForgeConfigSpec COMMON_CONFIG;
 public static ForgeConfigSpec CLIENT_CONFIG;
 
 public static ForgeConfigSpec.IntValue PLAYERNUDGER_NORTH_CHANCE;
 public static ForgeConfigSpec.DoubleValue PLAYERNUDGER_NORTH_STRENGTH;
 public static ForgeConfigSpec.IntValue PLAYERNUDGER_NORTHEAST_CHANCE;
 public static ForgeConfigSpec.DoubleValue PLAYERNUDGER_NORTHEAST_STRENGTH;
 public static ForgeConfigSpec.IntValue PLAYERNUDGER_EAST_CHANCE;
 public static ForgeConfigSpec.DoubleValue PLAYERNUDGER_EAST_STRENGTH;
 public static ForgeConfigSpec.IntValue PLAYERNUDGER_SOUTHEAST_CHANCE;
 public static ForgeConfigSpec.DoubleValue PLAYERNUDGER_SOUTHEAST_STRENGTH;
 public static ForgeConfigSpec.IntValue PLAYERNUDGER_SOUTH_CHANCE;
 public static ForgeConfigSpec.DoubleValue PLAYERNUDGER_SOUTH_STRENGTH;
 public static ForgeConfigSpec.IntValue PLAYERNUDGER_SOUTHWEST_CHANCE;
 public static ForgeConfigSpec.DoubleValue PLAYERNUDGER_SOUTHWEST_STRENGTH;
 public static ForgeConfigSpec.IntValue PLAYERNUDGER_WEST_CHANCE;
 public static ForgeConfigSpec.DoubleValue PLAYERNUDGER_WEST_STRENGTH;
 public static ForgeConfigSpec.IntValue PLAYERNUDGER_NORTHWEST_CHANCE;
 public static ForgeConfigSpec.DoubleValue PLAYERNUDGER_NORTHWEST_STRENGTH;
 public static ForgeConfigSpec.IntValue PLAYERNUDGER_UP_CHANCE;
 public static ForgeConfigSpec.DoubleValue PLAYERNUDGER_UP_STRENGTH;
 public static ForgeConfigSpec.IntValue PLAYERNUDGER_DOWN_CHANCE;
 public static ForgeConfigSpec.DoubleValue PLAYERNUDGER_DOWN_STRENGTH;
 
 public static ForgeConfigSpec.IntValue PLAYERNUDGER_FORWARD_MULTIPLIER;
 public static ForgeConfigSpec.IntValue PLAYERNUDGER_BACKWARD_MULTIPLIER;
 public static ForgeConfigSpec.IntValue PLAYERNUDGER_LEFT_MULTIPLIER;
 public static ForgeConfigSpec.IntValue PLAYERNUDGER_RIGHT_MULTIPLIER;
 
 public static ForgeConfigSpec.ConfigValue<List<List<String>>> QUICKHIDE_LIST;
 
 static {
  COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
  COMMON_BUILDER.pop();
  COMMON_BUILDER.comment("Command settings").push(CATEGORY_COMMANDS);
  
  setupPlayerNudgerConfigs();
//  setupQuickHideConfigs();
  
  COMMON_BUILDER.pop();
  
  COMMON_CONFIG = COMMON_BUILDER.build();
  CLIENT_CONFIG = CLIENT_BUILDER.build();
 }

 private static void setupPlayerNudgerConfigs() {
  COMMON_BUILDER.comment("PlayerNudger Command settings").push(SUBCATEGORY_CMD_PLAYERNUDGER);
  
  PLAYERNUDGER_NORTH_CHANCE= COMMON_BUILDER.comment("Chance for playernudger command to nudge North").defineInRange("North chance",4,0,100);
  PLAYERNUDGER_NORTH_STRENGTH= COMMON_BUILDER.comment("Strength of playernudger command nudging North").defineInRange("North strength",.7,0,10);
  PLAYERNUDGER_NORTHEAST_CHANCE= COMMON_BUILDER.comment("Chance for playernudger command to nudge Northeast").defineInRange("Northeast chance",2,0,100);
  PLAYERNUDGER_NORTHEAST_STRENGTH= COMMON_BUILDER.comment("Strength for playernudger command nudging Northeast").defineInRange("Northeast strength",.7,0,10);
  PLAYERNUDGER_EAST_CHANCE= COMMON_BUILDER.comment("Chance for playernudger command to nudge East").defineInRange("East chance",4,0,100);
  PLAYERNUDGER_EAST_STRENGTH= COMMON_BUILDER.comment("Strength for playernudger command to nudge East").defineInRange("East strength",.7,0,10);
  PLAYERNUDGER_SOUTHEAST_CHANCE= COMMON_BUILDER.comment("Chance for playernudger command to nudge Southeast").defineInRange("Southeast chance",2,0,100);
  PLAYERNUDGER_SOUTHEAST_STRENGTH= COMMON_BUILDER.comment("Strength for playernudger command to nudge Southeast").defineInRange("Southeast strength",.7,0,10);
  PLAYERNUDGER_SOUTH_CHANCE= COMMON_BUILDER.comment("Chance for playernudger command to nudge South").defineInRange("South chance",4,0,100);
  PLAYERNUDGER_SOUTH_STRENGTH= COMMON_BUILDER.comment("Strength for playernudger command to nudge South").defineInRange("South strength",.7,0,10);
  PLAYERNUDGER_SOUTHWEST_CHANCE= COMMON_BUILDER.comment("Chance for playernudger command to nudge Southwest").defineInRange("Southwest chance",2,0,100);
  PLAYERNUDGER_SOUTHWEST_STRENGTH= COMMON_BUILDER.comment("Strength for playernudger command to nudge Southwest").defineInRange("Southwest strength",.7,0,10);
  PLAYERNUDGER_WEST_CHANCE= COMMON_BUILDER.comment("Chance for playernudger command to nudge West").defineInRange("West chance",4,0,100);
  PLAYERNUDGER_WEST_STRENGTH= COMMON_BUILDER.comment("Strength for playernudger command to nudge West").defineInRange("West strength",.7,0,10);
  PLAYERNUDGER_NORTHWEST_CHANCE= COMMON_BUILDER.comment("Chance for playernudger command to nudge Northwest").defineInRange("Northwest chance",2,0,100);
  PLAYERNUDGER_NORTHWEST_STRENGTH= COMMON_BUILDER.comment("Strength for playernudger command to nudge Northwest").defineInRange("Northwest strength",.7,0,10);
  PLAYERNUDGER_UP_CHANCE= COMMON_BUILDER.comment("Chance for playernudger command to nudge Up").defineInRange("Up chance",1,0,100);
  PLAYERNUDGER_UP_STRENGTH= COMMON_BUILDER.comment("Strength for playernudger command to nudge Up").defineInRange("Up strength",.7,0,10);
  PLAYERNUDGER_DOWN_CHANCE= COMMON_BUILDER.comment("Chance for playernudger command to nudge Down").defineInRange("Down chance",0,0,100);
  PLAYERNUDGER_DOWN_STRENGTH= COMMON_BUILDER.comment("Strength for playernudger command to nudge Down").defineInRange("Down strength",.7,0,10);
  
  PLAYERNUDGER_FORWARD_MULTIPLIER= COMMON_BUILDER.comment("Multiplier to increase the chance to nudge where the player is looking").defineInRange("Forward Multiplier",1,0,100);
  PLAYERNUDGER_BACKWARD_MULTIPLIER= COMMON_BUILDER.comment("Multiplier to increase the chance to nudge opposite where the player is looking").defineInRange("Backward Multiplier",1,0,100);
  PLAYERNUDGER_LEFT_MULTIPLIER= COMMON_BUILDER.comment("Multiplier to increase the chance to nudge the player to the right").defineInRange("Left Multiplier",1,0,100);
  PLAYERNUDGER_RIGHT_MULTIPLIER= COMMON_BUILDER.comment("Multiplier to increase the chance to nudge the player to the left").defineInRange("Right Multiplier",1,0,100);
  
  COMMON_BUILDER.pop();
 }
 
 private static void setupQuickHideConfigs() {
  COMMON_BUILDER.comment("QuickHide Command settings").push(SUBCATEGORY_CMD_QUICKHIDE);
  
  List<List<String>> quiHidList=Lists.newArrayList();
  quiHidList.add(Lists.newArrayList("minecraft:deadbush","quick hide in these bushes!"));
  quiHidList.add(Lists.newArrayList("minecraft:wheat","quick hide in the wheat field!"));
  quiHidList.add(Lists.newArrayList("minecraft:feather","quick act like a chicken!"));
  quiHidList.add(Lists.newArrayList("minecraft:painting","quick blend into the wall!"));
  
//  QUICKHIDE_LIST=COMMON_BUILDER.comment("List of random options that can be choosen when using quickhide command")
//    .defineList("Quick Hide Options", quiHidList, null);
  
  COMMON_BUILDER.pop();
 }
 
 public static void loadConfig(ForgeConfigSpec spec, Path path) {

  final CommentedFileConfig configData = CommentedFileConfig.builder(path)
          .sync()
          .autosave()
          .writingMode(WritingMode.REPLACE)
          .build();

  configData.load();
  spec.setConfig(configData);
}
 
 @SubscribeEvent
 public static void onLoad(final ModConfig.Loading configEvent) {

 }

 @SubscribeEvent
 public static void onReload(final ModConfig.ConfigReloading configEvent) {
 }
}
