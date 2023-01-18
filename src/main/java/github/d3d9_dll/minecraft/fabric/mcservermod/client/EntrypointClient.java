package github.d3d9_dll.minecraft.fabric.mcservermod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class EntrypointClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		System.out.println("Hello client!");
	}

}
