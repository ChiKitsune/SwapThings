package chikitsune.swap_things.proxies;

import net.minecraft.world.level.Level;

public class ServerProxy implements IProxy {
 @Override
 public Level getClientWorld() {
  throw new IllegalStateException("Only run this on the client!");
 }
}
