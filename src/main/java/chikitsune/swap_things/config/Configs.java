package chikitsune.swap_things.config;

import chikitsune.swap_things.SwappingThings;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.Lists;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Path;
import java.util.List;

//@Mod.EventBusSubscriber
@Mod.EventBusSubscriber(modid = SwappingThings.MODID, bus = Bus.MOD)
public class Configs {

// public static final SwapThingsConfig STCONFIG;
 private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
 public static final ForgeConfigSpec STCONFIG_SPEC;
 
// static {
//  final Pair<SwapThingsConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(SwapThingsConfig::new);
//  STCONFIG_SPEC= specPair.getRight();
//  STCONFIG= specPair.getLeft();
// }
 private static List<String> quiHidList_def;
 private static List<String> sumMountINCLUDEClassList_def;
 private static List<String> sumMountEXLUDEList_def;
 private static List<String> sumRiderClassList_def;
 private static List<String> deathBoardList_def;
 private static List<String> randGiftList_def;
 private static List<String> randEnchantEXLUDEList_def;
 
 
 public static void init(Path file) {  
  final CommentedFileConfig configData = CommentedFileConfig.builder(file)
    .sync()
    .autosave()
    .writingMode(WritingMode.REPLACE)
    .autoreload()
    .preserveInsertionOrder()
    .build();
  configData.load();
  STCONFIG_SPEC.setConfig(configData);
 }
 

 public static final String CAT_CMDS = "commands";
 public static final String CAT_CMDS_CMT = "Command Settings";
 
 public static final String CAT_GEN = "general";
   public static ForgeConfigSpec.BooleanValue MSG_ALL_SERVER;
   public static boolean MSG_ALL_SERVER_DEF = false;
   private static final String MSG_ALL_SERVER_NAM = "msgAllPlayers";
   private static final String MSG_ALL_SERVER_CMT = "Command messages show to all players on server.";
   public static  ForgeConfigSpec.IntValue CMD_PERMISSION_LEVEL;
   public static int CMD_PERMISSION_LEVEL_DEF = 2;
   public static int CMD_PERMISSION_LEVEL_MIN = 0;
   public static int CMD_PERMISSION_LEVEL_MAX = 4;
   public static final String CMD_PERMISSION_LEVEL_NAM = "cmdPermissionsLevel";
   public static final String CMD_PERMISSION_LEVEL_CMT = "Sets all command to be this permission level.";
   public static ForgeConfigSpec.BooleanValue MSG_SHOW;
   public static boolean MSG_SHOW_DEF = true;
   private static final String MSG_SHOW_NAM = "msgShow";
   private static final String MSG_SHOW_CMT = "Commands show a message in chat.";

 public static final String CAT_IB = "InventoryBomb";
 public static final String CAT_IB_CMT = "InventoryBomb Command Settings";
   public static  ForgeConfigSpec.ConfigValue<String> IB_ITEM;
   public static final String IB_ITEM_DEF = ForgeRegistries.ITEMS.getKey(Items.DEAD_BUSH).toString();
   //public static final String IB_ITEM_DEF = Items.DEAD_BUSH.getRegistryName().toString();
   
   public static final String IB_ITEM_NAM = "inventoryBombItem";
   public static final String IB_ITEM_CMT = "Item that will replace all inventory slots with after items are dropped";

 public static final String CAT_DP = "DisconnectPlayer";
 public static final String CAT_DP_CMT = "DisconnectPlayer Command Settings";
   public static  ForgeConfigSpec.ConfigValue<String> DP_MSG;
   public static final String DP_MSG_DEF = " and had a Dark PANIC moment and some how left the server.";
   public static final String DP_MSG_NAM = "disconnectMsg";
   public static final String DP_MSG_CMT = "Message to show when disconnected"; 

 public static final String CAT_PN = "PlayerNudger";
 public static final String CAT_PN_CMT = "PlayerNudger Command Settings";
   public static final String CAT_PN_N = "North";
     public static  ForgeConfigSpec.IntValue PN_N_CHA;
     public static int PN_N_CHA_DEF = 4;
     public static int PN_N_CHA_MIN = 0;
     public static int PN_N_CHA_MAX = 100;
     public static final String PN_N_CHA_NAM = "North_Chance";
     public static final String PN_N_CHA_CMT = "Chance for playernudger command to nudge North";
     public static  ForgeConfigSpec.DoubleValue PN_N_STR;
     public static double PN_N_STR_DEF = .7;
     public static double PN_N_STR_MIN = 0;
     public static double PN_N_STR_MAX = 10;
     public static final String PN_N_STR_NAM = "North_Strength";
     public static final String PN_N_STR_CMT = "Strength of playernudger command nudging North";
   public static final String CAT_PN_NE = "NorthEast";
     public static ForgeConfigSpec.IntValue PN_NE_CHA;
     public static int PN_NE_CHA_DEF = 2;
     public static int PN_NE_CHA_MIN = 0;
     public static int PN_NE_CHA_MAX = 100;
     public static final String PN_NE_CHA_NAM = "Northeast_Chance";
     public static final String PN_NE_CHA_CMT = "Chance for playernudger command to nudge Northeast";
     public static ForgeConfigSpec.DoubleValue PN_NE_STR;
     public static double PN_NE_STR_DEF = .7;
     public static double PN_NE_STR_MIN = 0;
     public static double PN_NE_STR_MAX = 10;
     public static final String PN_NE_STR_NAM = "Northeast_Strength";
     public static final String PN_NE_STR_CMT = "Strength of playernudger command nudging Northeast";
   public static final String CAT_PN_E = "East";
     public static ForgeConfigSpec.IntValue PN_E_CHA;
     public static int PN_E_CHA_DEF = 4;
     public static int PN_E_CHA_MIN = 0;
     public static int PN_E_CHA_MAX = 100;
     public static final String PN_E_CHA_NAM = "East_Chance";
     public static final String PN_E_CHA_CMT = "Chance for playernudger command to nudge East";
     public static ForgeConfigSpec.DoubleValue PN_E_STR;
     public static double PN_E_STR_DEF = .7;
     public static double PN_E_STR_MIN = 0;
     public static double PN_E_STR_MAX = 10;
     public static final String PN_E_STR_NAM = "East_Strength";
     public static final String PN_E_STR_CMT = "Strength of playernudger command nudging East";
   public static final String CAT_PN_SE = "SouthEast";
     public static ForgeConfigSpec.IntValue PN_SE_CHA;
     public static int PN_SE_CHA_DEF = 2;
     public static int PN_SE_CHA_MIN = 0;
     public static int PN_SE_CHA_MAX = 100;
     public static final String PN_SE_CHA_NAM = "SouthEast_Chance";
     public static final String PN_SE_CHA_CMT = "Chance for playernudger command to nudge Southeast";
     public static ForgeConfigSpec.DoubleValue PN_SE_STR;
     public static double PN_SE_STR_DEF = .7;
     public static double PN_SE_STR_MIN = 0;
     public static double PN_SE_STR_MAX = 10;
     public static final String PN_SE_STR_NAM = "SouthEast_Strength";
     public static final String PN_SE_STR_CMT = "Strength of playernudger command nudging Southeast";
   public static String CAT_PN_S = "South";
     public static ForgeConfigSpec.IntValue PN_S_CHA;
     public static int PN_S_CHA_DEF = 4;
     public static int PN_S_CHA_MIN = 0;
     public static int PN_S_CHA_MAX = 100;
     public static final String PN_S_CHA_NAM = "South_Chance";
     public static final String PN_S_CHA_CMT = "Chance for playernudger command to nudge South";
     public static ForgeConfigSpec.DoubleValue PN_S_STR;
     public static double PN_S_STR_DEF = .7;
     public static double PN_S_STR_MIN = 0;
     public static double PN_S_STR_MAX = 10;
     public static final String PN_S_STR_NAM = "South_Strength";
     public static final String PN_S_STR_CMT = "Strength of playernudger command nudging South";
   public static final String CAT_PN_SW = "SouthWest";
     public static ForgeConfigSpec.IntValue PN_SW_CHA;
     public static int PN_SW_CHA_DEF = 2;
     public static int PN_SW_CHA_MIN = 0;
     public static int PN_SW_CHA_MAX = 100;
     public static final String PN_SW_CHA_NAM = "SouthWest_Chance";
     public static final String PN_SW_CHA_CMT = "Chance for playernudger command to nudge Southwest";
     public static ForgeConfigSpec.DoubleValue PN_SW_STR;
     public static double PN_SW_STR_DEF = .7;
     public static double PN_SW_STR_MIN = 0;
     public static double PN_SW_STR_MAX = 10;
     public static final String PN_SW_STR_NAM = "SouthWest_Strength";
     public static final String PN_SW_STR_CMT = "Strength of playernudger command nudging Southwest";
   public static final String CAT_PN_W = "West";
     public static ForgeConfigSpec.IntValue PN_W_CHA;
     public static int PN_W_CHA_DEF = 4;
     public static int PN_W_CHA_MIN = 0;
     public static int PN_W_CHA_MAX = 100;
     public static final String PN_W_CHA_NAM = "West_Chance";
     public static final String PN_W_CHA_CMT = "Chance for playernudger command to nudge West";
     public static ForgeConfigSpec.DoubleValue PN_W_STR;
     public static double PN_W_STR_DEF = .7;
     public static double PN_W_STR_MIN = 0;
     public static double PN_W_STR_MAX = 10;
     public static final String PN_W_STR_NAM = "West_Strength";
     public static final String PN_W_STR_CMT = "Strength of playernudger command nudging West";
   public static final String CAT_PN_NW = "NorthWest";
     public static ForgeConfigSpec.IntValue PN_NW_CHA;
     public static int PN_NW_CHA_DEF = 2;
     public static int PN_NW_CHA_MIN = 0;
     public static int PN_NW_CHA_MAX = 100;
     public static final String PN_NW_CHA_NAM = "NorthWest_Chance";
     public static final String PN_NW_CHA_CMT = "Chance for playernudger command to nudge Northwest";
     public static ForgeConfigSpec.DoubleValue PN_NW_STR;
     public static double PN_NW_STR_DEF = .7;
     public static double PN_NW_STR_MIN = 0;
     public static double PN_NW_STR_MAX = 10;
     public static final String PN_NW_STR_NAM = "NorthWest_Strength";
     public static final String PN_NW_STR_CMT = "Strength of playernudger command nudging Northwest";
   public static final String CAT_PN_U = "Up";
     public static ForgeConfigSpec.IntValue PN_U_CHA;
     public static int PN_U_CHA_DEF = 1;
     public static int PN_U_CHA_MIN = 0;
     public static int PN_U_CHA_MAX = 100;
     public static final String PN_U_CHA_NAM = "Up_Chance";
     public static final String PN_U_CHA_CMT = "Chance for playernudger command to nudge Up";
     public static ForgeConfigSpec.DoubleValue PN_U_STR;
     public static double PN_U_STR_DEF = .7;
     public static double PN_U_STR_MIN = 0;
     public static double PN_U_STR_MAX = 10;
     public static final String PN_U_STR_NAM = "Up_Strength";
     public static final String PN_U_STR_CMT = "Strength of playernudger command nudging Up";
   public static final String CAT_PN_D = "Down";
     public static ForgeConfigSpec.IntValue PN_D_CHA;
     public static int PN_D_CHA_DEF = 0;
     public static int PN_D_CHA_MIN = 0;
     public static int PN_D_CHA_MAX = 100;
     public static final String PN_D_CHA_NAM = "Down_Chance";
     public static final String PN_D_CHA_CMT = "Chance for playernudger command to nudge Down";
     public static ForgeConfigSpec.DoubleValue PN_D_STR;
     public static double PN_D_STR_DEF = .7;
     public static double PN_D_STR_MIN = 0;
     public static double PN_D_STR_MAX = 10;
     public static final String PN_D_STR_NAM = "Down_Strength";
     public static final String PN_D_STR_CMT = "Strength of playernudger command nudging Down";
   public static final String CAT_PN_PFD = "PlayerFacingDirections";
     public static ForgeConfigSpec.IntValue PN_PFD_FOR;
     public static int PN_PFD_FOR_DEF = 1;
     public static int PN_PFD_FOR_MIN = 0;
     public static int PN_PFD_FOR_MAX = 100;
     public static final String PN_PFD_FOR_NAM = "Forward_Multiplier";
     public static final String PN_PFD_FOR_CMT = "Multiplier to increase the chance to nudge where the player is looking";
     public static ForgeConfigSpec.IntValue PN_PFD_BAC;
     public static int PN_PFD_BAC_DEF = 1;
     public static int PN_PFD_BAC_MIN = 0;
     public static int PN_PFD_BAC_MAX = 100;
     public static final String PN_PFD_BAC_NAM = "Backward_Multiplier";
     public static final String PN_PFD_BAC_CMT = "Multiplier to increase the chance to nudge opposite where the player is looking";
     public static ForgeConfigSpec.IntValue PN_PFD_LEF;
     public static int PN_PFD_LEF_DEF = 1;
     public static int PN_PFD_LEF_MIN = 0;
     public static int PN_PFD_LEF_MAX = 100;
     public static final String PN_PFD_LEF_NAM = "Left_Multiplier";
     public static final String PN_PFD_LEF_CMT = "Multiplier to increase the chance to nudge the player to the left";
     public static ForgeConfigSpec.IntValue PN_PFD_RIG;
     public static int PN_PFD_RIG_DEF = 1;
     public static int PN_PFD_RIG_MIN = 0;
     public static int PN_PFD_RIG_MAX = 100;
     public static final String PN_PFD_RIG_NAM = "Right_Multiplier";
     public static final String PN_PFD_RIG_CMT = "Multiplier to increase the chance to nudge the player to the right";

 public static final String CAT_RT = "RandomTeleport";
 public static final String CAT_RT_CMT = "RandomTeleport Command Settings";
   public static final String CAT_RT_X = "X Axis";
     public static ForgeConfigSpec.IntValue RT_X_MIN;
     public static int RT_X_MIN_DEF = 100;
     public static int RT_X_MIN_MIN = 0;
     public static int RT_X_MIN_MAX = 100000;
     public static final String RT_X_MIN_NAM = "randomTeleportXMin";
     public static final String RT_X_MIN_CMT = "Miniumum X offset from original X level. (i.e. 10 means when teleported will be at least 10 blocks along the X axis from original position)";
     public static ForgeConfigSpec.IntValue RT_X_MAX;
     public static int RT_X_MAX_DEF = 1000;
     public static int RT_X_MAX_MIN = 0;
     public static int RT_X_MAX_MAX = 100000;
     public static final String RT_X_MAX_NAM = "randomTeleportXMax";
     public static final String RT_X_MAX_CMT = "Maximum X offset from original X level. (i.e. 100 means when teleported will be less than 100 blocks away on the X axis from original position)";
   public static final String CAT_RT_Y = "Y Axis";
     public static ForgeConfigSpec.IntValue RT_Y_MIN;
     public static int RT_Y_MIN_DEF = 0;
     public static int RT_Y_MIN_MIN = 0;
     public static int RT_Y_MIN_MAX = 30000000;
     public static final String RT_Y_MIN_NAM = "randomTeleportYMin";
     public static final String RT_Y_MIN_CMT = "Miniumum Y offset from original Y level. (i.e. 10 means when teleported will be at least 10 blocks higher or lower than original position)";
     public static ForgeConfigSpec.IntValue RT_Y_MAX;
     public static int RT_Y_MAX_DEF = 320;
     public static int RT_Y_MAX_MIN = 0;
     public static int RT_Y_MAX_MAX = 30000000;
     public static final String RT_Y_MAX_NAM = "randomTeleportYMax";
     public static final String RT_Y_MAX_CMT = "Maximum Y offset from original Y level. (i.e. 100 means when teleported will be less than 100 blocks higher or lower than original position)";
//     "Max Block +/- range on the Y Axis that could teleport along";
   public static final String CAT_RT_Z = "Z Axis";
     public static ForgeConfigSpec.IntValue RT_Z_MIN;
     public static int RT_Z_MIN_DEF = 100;
     public static int RT_Z_MIN_MIN = 0;
     public static int RT_Z_MIN_MAX = 100000;
     public static final String RT_Z_MIN_NAM = "randomTeleportZMin";
     public static final String RT_Z_MIN_CMT = "Miniumum Z offset from original X level. (i.e. 10 means when teleported will be at least 10 blocks along the Z axis from original position)";
     public static ForgeConfigSpec.IntValue RT_Z_MAX;
     public static int RT_Z_MAX_DEF = 1000;
     public static int RT_Z_MAX_MIN = 0;
     public static int RT_Z_MAX_MAX = 100000;
     public static final String RT_Z_MAX_NAM = "randomTeleportZMax";
     public static final String RT_Z_MAX_CMT = "Maximum Z offset from original X level. (i.e. 100 means when teleported will be less than 100 blocks away on the Z axis from original position)";
       
   public static final String CAT_QH = "QuickHide";
   public static final String CAT_QH_CMT = "QuickHide Command Settings";
     public static ConfigValue<List<? extends String>> QH_LIST;
     public static List<String> QH_LIST_DEF = quiHidList_def; 
     public static final String QH_LIST_NAM = "QuickHideList";
     public static final String QH_LIST_CMT = "List of random options that can be chosen when using quickhide command";
 
   public static final String CAT_SM = "SummonMount";
   public static final String CAT_SM_CMT = "SummonMount Command Settings";
     public static ConfigValue<List<? extends String>> SM_IN_LIST = null;
     public static List<String> SM_IN_LIST_DEF = sumMountINCLUDEClassList_def; 
     public static final String SM_IN_LIST_NAM = "SummonMountClass";
     public static final String SM_IN_LIST_CMT = "List of Classifications for SummonMount command to INCLUDE from possible entities. Use \"ANY\" for all.";
     public static ConfigValue<List<? extends String>> SM_EX_LIST = null;
     public static List<String> SM_EX_LIST_DEF = sumMountEXLUDEList_def; 
     public static final String SM_EX_LIST_NAM = "SummonMount";
     public static final String SM_EX_LIST_CMT = "List of entities for SummonMount command to EXCLUDE from possible entities";
     public static ForgeConfigSpec.BooleanValue SM_CUS_NAME = null;
     public static boolean SM_CUS_NAME_DEF = true;
     private static String SM_CUS_NAME_NAM = "summonMountCustomName";
     private static final String SM_CUS_NAME_CMT = "Custom name for SummonMount entities.";
     public static ForgeConfigSpec.BooleanValue SM_CUS_TAME = null;
     public static boolean SM_CUS_TAME_DEF = true;
     private static final String SM_CUS_TAME_NAM = "summonMountTamed";
     private static final String SM_CUS_TAME_CMT = "Try to summon entity already tamed.";
 
   public static final String CAT_SR = "SummonRider";
   public static final String CAT_SR_CMT = "SummonRider Command Settings";
     public static ConfigValue<List<? extends String>> SR_IN_LIST = null;
     public static List<String> SR_IN_LIST_DEF = sumRiderClassList_def; 
     public static final String SR_IN_LIST_NAM = "SummonRiderClass";
     public static final String SR_IN_LIST_CMT = "List of Classifications for SummonRide command to INCLUDE from possible entities. Use \"ANY\" for all.";
     
   public static final String CAT_DB = "DisplayDeathBoard";
   public static final String CAT_DB_CMT = "DisplayDeathBoard Command Settings";
     public static ConfigValue<List<? extends String>> DB_LIST = null;
     public static List<String> DB_LIST_DEF = deathBoardList_def;
     public static final String DB_LIST_NAM = "DisplayDeathBoard";
     public static final String DB_LIST_CMT = "List of strings to put at the end based on position. Ranks without a separate value will use whatever the last value is";
     
   public static final String CAT_RG = "RandomGift";
   public static final String CAT_RG_CMT = "RandomGift Command Settings";
     public static ConfigValue<List<? extends String>> RG_LIST = null;
     public static List<String> RG_LIST_DEF = randGiftList_def; 
     public static final String RG_LIST_NAM = "RandomGift";
     public static final String RG_LIST_CMT = "List of items (resource location,amount,weighted chance) to be chosen for RandomGift command to choose from";
 
   public static final String CAT_RTD = "RandomTeleportDirection";
   public static final String CAT_RTD_CMT = "RandomTeleportDirection Command Settings";
     public static ForgeConfigSpec.IntValue RTD_AMT;
     public static int RTD_AMT_DEF = 1000;
     public static int RTD_AMT_MIN = 0;
     public static int RTD_AMT_MAX = 100000;
     public static final String RTD_AMT_NAM = "RandomTeleportDirection";
     public static final String RTD_AMT_CMT = "Teleports user in a random direction with the given amount as distance from their original spot";
     public static ForgeConfigSpec.IntValue RTD_Y_MIN;
     public static int RTD_Y_MIN_DEF = 0;
     public static int RTD_Y_MIN_MIN = 0;
     public static int RTD_Y_MIN_MAX = 30000000;
     public static final String RTD_Y_MIN_NAM = "randomTeleportdDirectionYMin";
     public static final String RTD_Y_MIN_CMT = "Miniumum Y offset from original Y level. (i.e. 10 means when teleported will be at least 10 blocks higher or lower than original position";
     public static ForgeConfigSpec.IntValue RTD_Y_MAX;
     public static int RTD_Y_MAX_DEF = 320;
     public static int RTD_Y_MAX_MIN = 0;
     public static int RTD_Y_MAX_MAX = 30000000;
     public static final String RTD_Y_MAX_NAM = "randomTeleportYDirectionMax";
     public static final String RTD_Y_MAX_CMT = "Maximum Y offset from original Y level. (i.e. 100 means when teleported will be less than 100 blocks higher or lower than original position";
     
   public static final String CAT_RE = "RandomEnchanting";
   public static final String CAT_RE_CMT = "RandomEnchanting Command Settings";
     public static ConfigValue<List<? extends String>> RE_EX_LIST = null;
     public static List<String> RE_EX_LIST_DEF = randEnchantEXLUDEList_def; 
     public static final String RE_EX_LIST_NAM = "RandomEnchantingExclude";
     public static final String RE_EX_LIST_CMT = "List of enchantment(s) for RandomEnchanting command to EXCLUDE from possible enchantments (i.e. minecraft:vanishing_curse)";     
     public static ForgeConfigSpec.BooleanValue RE_FORCE_ENCHANT = null;
     public static boolean RE_FORCE_ENCHANT_DEF = false;
     private static final String RE_FORCE_ENCHANT_NAM = "randomEnchantForce";
     private static final String RE_FORCE_ENCHANT_CMT = "Force random enchantment on even if normally cannot go on item.";
     
     
 static {
  PopLists();
  
  //*
  BUILDER.comment(CAT_CMDS_CMT).push(CAT_CMDS);
  
  BUILDER.push(CAT_GEN);
  MSG_ALL_SERVER=BUILDER.comment(MSG_ALL_SERVER_CMT).define(MSG_ALL_SERVER_NAM, MSG_ALL_SERVER_DEF);
  CMD_PERMISSION_LEVEL=BUILDER.comment(CMD_PERMISSION_LEVEL_CMT).defineInRange(CMD_PERMISSION_LEVEL_NAM, CMD_PERMISSION_LEVEL_DEF, CMD_PERMISSION_LEVEL_MIN, CMD_PERMISSION_LEVEL_MAX);
  MSG_SHOW=BUILDER.comment(MSG_SHOW_CMT).define(MSG_SHOW_NAM, MSG_SHOW_DEF);
  BUILDER.pop();
  
  BUILDER.comment(CAT_IB_CMT).push(CAT_IB);
  IB_ITEM=BUILDER.comment(IB_ITEM_CMT).define(IB_ITEM_NAM, IB_ITEM_DEF);
  BUILDER.pop();
  
  BUILDER.comment(CAT_DP_CMT).push(CAT_DP);
  DP_MSG=BUILDER.comment(DP_MSG_CMT).define(DP_MSG_NAM, DP_MSG_DEF);
  BUILDER.pop();
  
  BUILDER.comment(CAT_PN_CMT).push(CAT_PN);
  BUILDER.push(CAT_PN_N);
  PN_N_CHA=BUILDER.comment(PN_N_CHA_CMT).defineInRange(PN_N_CHA_NAM, PN_N_CHA_DEF, PN_N_CHA_MIN, PN_N_CHA_MAX);
  PN_N_STR=BUILDER.comment(PN_N_STR_CMT).defineInRange(PN_N_STR_NAM, PN_N_STR_DEF, PN_N_STR_MIN, PN_N_STR_MAX);
  BUILDER.pop().push(CAT_PN_NE);
  PN_NE_CHA=BUILDER.comment(PN_NE_CHA_CMT).defineInRange(PN_NE_CHA_NAM, PN_NE_CHA_DEF, PN_NE_CHA_MIN, PN_NE_CHA_MAX);
  PN_NE_STR=BUILDER.comment(PN_NE_STR_CMT).defineInRange(PN_NE_STR_NAM, PN_NE_STR_DEF, PN_NE_STR_MIN, PN_NE_STR_MAX);
  BUILDER.pop().push(CAT_PN_E);
  PN_E_CHA=BUILDER.comment(PN_E_CHA_CMT).defineInRange(PN_E_CHA_NAM, PN_E_CHA_DEF, PN_E_CHA_MIN, PN_E_CHA_MAX);
  PN_E_STR=BUILDER.comment(PN_E_STR_CMT).defineInRange(PN_E_STR_NAM, PN_E_STR_DEF, PN_E_STR_MIN, PN_E_STR_MAX);
  BUILDER.pop().push(CAT_PN_SE);
  PN_SE_CHA=BUILDER.comment(PN_SE_CHA_CMT).defineInRange(PN_SE_CHA_NAM, PN_SE_CHA_DEF, PN_SE_CHA_MIN, PN_SE_CHA_MAX);
  PN_SE_STR=BUILDER.comment(PN_SE_STR_CMT).defineInRange(PN_SE_STR_NAM, PN_SE_STR_DEF, PN_SE_STR_MIN, PN_SE_STR_MAX);
  BUILDER.pop().push(CAT_PN_S);
  PN_S_CHA=BUILDER.comment(PN_S_CHA_CMT).defineInRange(PN_S_CHA_NAM, PN_S_CHA_DEF, PN_S_CHA_MIN, PN_S_CHA_MAX);
  PN_S_STR=BUILDER.comment(PN_S_STR_CMT).defineInRange(PN_S_STR_NAM, PN_S_STR_DEF, PN_S_STR_MIN, PN_S_STR_MAX);
  BUILDER.pop().push(CAT_PN_SW);
  PN_SW_CHA=BUILDER.comment(PN_SW_CHA_CMT).defineInRange(PN_SW_CHA_NAM, PN_SW_CHA_DEF, PN_SW_CHA_MIN, PN_SW_CHA_MAX);
  PN_SW_STR=BUILDER.comment(PN_SW_STR_CMT).defineInRange(PN_SW_STR_NAM, PN_SW_STR_DEF, PN_SW_STR_MIN, PN_SW_STR_MAX);
  BUILDER.pop().push(CAT_PN_W);
  PN_W_CHA=BUILDER.comment(PN_W_CHA_CMT).defineInRange(PN_W_CHA_NAM, PN_W_CHA_DEF, PN_W_CHA_MIN, PN_W_CHA_MAX);
  PN_W_STR=BUILDER.comment(PN_W_STR_CMT).defineInRange(PN_W_STR_NAM, PN_W_STR_DEF, PN_W_STR_MIN, PN_W_STR_MAX);
  BUILDER.pop().push(CAT_PN_NW);
  PN_NW_CHA=BUILDER.comment(PN_NW_CHA_CMT).defineInRange(PN_NW_CHA_NAM, PN_NW_CHA_DEF, PN_NW_CHA_MIN, PN_NW_CHA_MAX);
  PN_NW_STR=BUILDER.comment(PN_NW_STR_CMT).defineInRange(PN_NW_STR_NAM, PN_NW_STR_DEF, PN_NW_STR_MIN, PN_NW_STR_MAX);
  BUILDER.pop().push(CAT_PN_U);
  PN_U_CHA=BUILDER.comment(PN_U_CHA_CMT).defineInRange(PN_U_CHA_NAM, PN_U_CHA_DEF, PN_U_CHA_MIN, PN_U_CHA_MAX);
  PN_U_STR=BUILDER.comment(PN_U_STR_CMT).defineInRange(PN_U_STR_NAM, PN_U_STR_DEF, PN_U_STR_MIN, PN_U_STR_MAX);
  BUILDER.pop().push(CAT_PN_D);
  PN_D_CHA=BUILDER.comment(PN_D_CHA_CMT).defineInRange(PN_D_CHA_NAM, PN_D_CHA_DEF, PN_D_CHA_MIN, PN_D_CHA_MAX);
  PN_D_STR=BUILDER.comment(PN_D_STR_CMT).defineInRange(PN_D_STR_NAM, PN_D_STR_DEF, PN_D_STR_MIN, PN_D_STR_MAX);
  BUILDER.pop().push(CAT_PN_PFD);
  PN_PFD_FOR=BUILDER.comment(PN_PFD_FOR_CMT).defineInRange(PN_PFD_FOR_NAM, PN_PFD_FOR_DEF, PN_PFD_FOR_MIN, PN_PFD_FOR_MAX);
  PN_PFD_BAC=BUILDER.comment(PN_PFD_BAC_CMT).defineInRange(PN_PFD_BAC_NAM, PN_PFD_BAC_DEF, PN_PFD_BAC_MIN, PN_PFD_BAC_MAX);
  PN_PFD_LEF=BUILDER.comment(PN_PFD_LEF_CMT).defineInRange(PN_PFD_LEF_NAM, PN_PFD_LEF_DEF, PN_PFD_LEF_MIN, PN_PFD_LEF_MAX);
  PN_PFD_RIG=BUILDER.comment(PN_PFD_RIG_CMT).defineInRange(PN_PFD_RIG_NAM, PN_PFD_RIG_DEF, PN_PFD_RIG_MIN, PN_PFD_RIG_MAX);
  BUILDER.pop(2);
  
  BUILDER.comment(CAT_RT_CMT).push(CAT_RT);
  BUILDER.push(CAT_RT_X);
  RT_X_MIN=BUILDER.comment(RT_X_MIN_CMT).defineInRange(RT_X_MIN_NAM, RT_X_MIN_DEF, RT_X_MIN_MIN, RT_X_MIN_MAX);
  RT_X_MAX=BUILDER.comment(RT_X_MAX_CMT).defineInRange(RT_X_MAX_NAM, RT_X_MAX_DEF, RT_X_MAX_MIN, RT_X_MAX_MAX);
  BUILDER.pop().push(CAT_RT_Y);
  RT_Y_MIN=BUILDER.comment(RT_Y_MIN_CMT).defineInRange(RT_Y_MIN_NAM, RT_Y_MIN_DEF, RT_Y_MIN_MIN, RT_Y_MIN_MAX);
  RT_Y_MAX=BUILDER.comment(RT_Y_MAX_CMT).defineInRange(RT_Y_MAX_NAM, RT_Y_MAX_DEF, RT_Y_MAX_MIN, RT_Y_MAX_MAX);
  BUILDER.pop().push(CAT_RT_Z);
  RT_Z_MIN=BUILDER.comment(RT_Z_MIN_CMT).defineInRange(RT_Z_MIN_NAM, RT_Z_MIN_DEF, RT_Z_MIN_MIN, RT_Z_MIN_MAX);
  RT_Z_MAX=BUILDER.comment(RT_Z_MAX_CMT).defineInRange(RT_Z_MAX_NAM, RT_Z_MAX_DEF, RT_Z_MAX_MIN, RT_Z_MAX_MAX);
  BUILDER.pop(2);
  
  BUILDER.comment(CAT_QH_CMT).push(CAT_QH);
  QH_LIST=BUILDER.comment(QH_LIST_CMT).defineList(QH_LIST_NAM, QH_LIST_DEF, s -> s instanceof String);
  BUILDER.pop();

  
  //*
  BUILDER.comment(CAT_SM_CMT).push(CAT_SM);
  SM_IN_LIST=BUILDER.comment(SM_IN_LIST_CMT).defineList(SM_IN_LIST_NAM, SM_IN_LIST_DEF, s -> s instanceof String);
  SM_EX_LIST=BUILDER.comment(SM_EX_LIST_CMT).defineList(SM_EX_LIST_NAM, SM_EX_LIST_DEF, s -> s instanceof String);
  SM_CUS_NAME=BUILDER.comment(SM_CUS_NAME_CMT).define(SM_CUS_NAME_NAM, SM_CUS_NAME_DEF);
  SM_CUS_TAME=BUILDER.comment(SM_CUS_TAME_CMT).define(SM_CUS_TAME_NAM, SM_CUS_TAME_DEF);
  BUILDER.pop();
  
  BUILDER.comment(CAT_SR_CMT).push(CAT_SR);
  SR_IN_LIST=BUILDER.comment(SR_IN_LIST_CMT).defineList(SR_IN_LIST_NAM, SR_IN_LIST_DEF, s -> s instanceof String);
  BUILDER.pop();
  
  BUILDER.comment(CAT_DB_CMT).push(CAT_DB);
  DB_LIST=BUILDER.comment(DB_LIST_CMT).defineList(DB_LIST_NAM, DB_LIST_DEF, s -> s instanceof String);
  BUILDER.pop();
  
  BUILDER.comment(CAT_RG_CMT).push(CAT_RG);
  RG_LIST=BUILDER.comment(RG_LIST_CMT).defineList(RG_LIST_NAM, RG_LIST_DEF, s -> s instanceof String);
  BUILDER.pop();
  //*/
  
  BUILDER.comment(CAT_RTD_CMT).push(CAT_RTD);
  RTD_AMT=BUILDER.comment(RTD_AMT_CMT).defineInRange(RTD_AMT_NAM, RTD_AMT_DEF, RTD_AMT_MIN, RTD_AMT_MAX);
  RTD_Y_MIN=BUILDER.comment(RTD_Y_MIN_CMT).defineInRange(RTD_Y_MIN_NAM, RTD_Y_MIN_DEF, RTD_Y_MIN_MIN, RTD_Y_MIN_MAX);
  RTD_Y_MAX=BUILDER.comment(RTD_Y_MAX_CMT).defineInRange(RTD_Y_MAX_NAM, RTD_Y_MAX_DEF, RTD_Y_MAX_MIN, RTD_Y_MAX_MAX);
  BUILDER.pop();  
  
  BUILDER.comment(CAT_RE_CMT).push(CAT_RE);
  RE_EX_LIST=BUILDER.comment(RE_EX_LIST_CMT).defineList(RE_EX_LIST_NAM, RE_EX_LIST_DEF, s -> s instanceof String);
  RE_FORCE_ENCHANT=BUILDER.comment(RE_FORCE_ENCHANT_CMT).define(RE_FORCE_ENCHANT_NAM, RE_FORCE_ENCHANT_DEF);
  BUILDER.pop();

  
  BUILDER.pop();
  
  STCONFIG_SPEC = BUILDER.build();
 }
 
 private static void PopLists() {
  quiHidList_def=Lists.newArrayList();
  quiHidList_def.add(ForgeRegistries.ITEMS.getKey(Items.DEAD_BUSH).toString()+",quick hide in these bushes!");
  quiHidList_def.add(ForgeRegistries.ITEMS.getKey(Items.WHEAT).toString()+",quick hide in the wheat field!");
  quiHidList_def.add(ForgeRegistries.ITEMS.getKey(Items.FEATHER).toString()+",quick act like a chicken!");
  quiHidList_def.add(ForgeRegistries.ITEMS.getKey(Items.PAINTING).toString()+",quick blend into the wall!");
  QH_LIST_DEF=quiHidList_def;
  
  sumMountINCLUDEClassList_def=Lists.newArrayList();
  sumMountINCLUDEClassList_def.add(MobCategory.AMBIENT.getName());
  sumMountINCLUDEClassList_def.add(MobCategory.CREATURE.getName());
  sumMountINCLUDEClassList_def.add(MobCategory.WATER_CREATURE.getName());
  sumMountINCLUDEClassList_def.add(MobCategory.WATER_AMBIENT.getName());
  sumMountINCLUDEClassList_def.add(MobCategory.MONSTER.getName());
  SM_IN_LIST_DEF = sumMountINCLUDEClassList_def; 
  
  sumMountEXLUDEList_def=Lists.newArrayList();
  sumMountEXLUDEList_def.add(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.WITHER).toString());
  sumMountEXLUDEList_def.add(ForgeRegistries.ENTITY_TYPES.getKey(EntityType.ENDER_DRAGON).toString());
  SM_EX_LIST_DEF = sumMountEXLUDEList_def;
  
  sumRiderClassList_def=Lists.newArrayList();
  sumRiderClassList_def.add(MobCategory.AMBIENT.getName());
  sumRiderClassList_def.add(MobCategory.CREATURE.getName()); 
  sumRiderClassList_def.add(MobCategory.WATER_CREATURE.getName());
  sumRiderClassList_def.add(MobCategory.WATER_AMBIENT.getName());
  SR_IN_LIST_DEF = sumRiderClassList_def;
  
  deathBoardList_def=Lists.newArrayList();
  deathBoardList_def.add(" rip");
  DB_LIST_DEF = deathBoardList_def;
  
  randGiftList_def=Lists.newArrayList();
  randGiftList_def.add(ForgeRegistries.ITEMS.getKey(Items.DIAMOND).toString()+",1,1");
  randGiftList_def.add(ForgeRegistries.ITEMS.getKey(Items.FEATHER).toString()+",64,99");
  RG_LIST_DEF = randGiftList_def;
  
  randEnchantEXLUDEList_def=Lists.newArrayList();
//  randEnchantEXLUDEList_def.add(Enchantments.VANISHING_CURSE.getRegistryName().toString());
  RE_EX_LIST_DEF=randEnchantEXLUDEList_def;
 }
}