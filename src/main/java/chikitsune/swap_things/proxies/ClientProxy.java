package chikitsune.swap_things.proxies;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;

public class ClientProxy implements IProxy {
 @Override
 public Level getClientWorld() {
  return Minecraft.getInstance().level;
 }
}