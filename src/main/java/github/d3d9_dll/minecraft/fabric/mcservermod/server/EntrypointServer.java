package github.d3d9_dll.minecraft.fabric.mcservermod.server;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
public class EntrypointServer implements DedicatedServerModInitializer {

	@Override
	public void onInitializeServer() {
		System.out.println("Hello server!");
	}

}
