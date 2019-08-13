package chikitsune.swap_things;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import chikitsune.swap_things.proxies.ClientProxy;
import chikitsune.swap_things.proxies.IProxy;
import chikitsune.swap_things.proxies.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SwappingThings.MODID)
@Mod.EventBusSubscriber(modid = SwappingThings.MODID, bus = Bus.MOD)
public class SwappingThings {
 
 public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
 // Above is double lambda
 // Below is cleaner version but is slightly different and keep as above?
 // public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
 private static final Logger LOGGER = LogManager.getLogger();
 
 public static final String MODID = "swap_things";
 public static final String NAME = "Swapping Things";
 
 
 public static List<List<String>> quiHidList;
 
 public SwappingThings() {
//Register the setup method for modloading
 FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
 // Register the enqueueIMC method for modloading
 FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
 // Register the processIMC method for modloading
 FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
 // Register the doClientStuff method for modloading
 FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

 // Register ourselves for server and other game events we are interested in
 MinecraftForge.EVENT_BUS.register(this);
 }
 
 private void setup(final FMLCommonSetupEvent event) {
//  LOGGER.info("HELLO FROM PREINIT");
  
 }
 
 private void doClientStuff(final FMLClientSetupEvent event) {
  // do something that can only be done on the client
//  LOGGER.info("Got game settings {}",event.getMinecraftSupplier().get().gameSettings);
 }

 private void enqueueIMC(final InterModEnqueueEvent event) {
  // some example code to dispatch IMC to another mod
//  InterModComms.sendTo("examplemod", "helloworld", () -> {
//   LOGGER.info("Hello world from the MDK");
//   return "Hello world";
//  });
 }

 private void processIMC(final InterModProcessEvent event) {
  // some example code to receive and process InterModComms from other mods
//  LOGGER.info("Got IMC {}", event.getIMCStream().map(m -> m.getMessageSupplier().get()).collect(Collectors.toList()));
 }
 
 // You can use SubscribeEvent and let the Event Bus discover methods to call
 @SubscribeEvent
 public void onServerStarting(FMLServerStartingEvent event) {
  // do something when the server starts
//  LOGGER.info("HELLO from server starting");
  SwappingThings.quiHidList=Lists.newArrayList();
  SwappingThings.quiHidList.add(Lists.newArrayList("minecraft:deadbush","quick hide in these bushes!"));
  SwappingThings.quiHidList.add(Lists.newArrayList("minecraft:wheat","quick hide in the wheat field!"));
  SwappingThings.quiHidList.add(Lists.newArrayList("minecraft:feather","quick act like a chicken!"));
  SwappingThings.quiHidList.add(Lists.newArrayList("minecraft:painting","quick blend into the wall!"));
 }

 // You can use EventBusSubscriber to automatically subscribe events on the
 // contained class (this is subscribing to the MOD
 // Event bus for receiving Registry Events)
 @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
 public static class RegistryEvents {
//  @SubscribeEvent
//  public static void onBlocksRegistry(
//    final RegistryEvent.Register<Block> blockRegistryEvent) {
//   // register a new block here
////   LOGGER.info("HELLO from Register Block");
//  }
 }
}
