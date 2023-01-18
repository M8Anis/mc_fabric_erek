package github.d3d9_dll.minecraft.fabric.erek;

import github.d3d9_dll.minecraft.fabric.erek.item.*;
import github.d3d9_dll.minecraft.fabric.erek.util.Logs;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;

import java.io.File;

public class Entrypoint implements ModInitializer {

    public final static Logs LOGGER = new Logs(LogManager.getLogger(Logs.LOG_PREFIX));

    public static final Identifier PACKET_SLOTMACHINE_SPIN =
            new Identifier("d3d9_dllerek", "packet_slotmachine_spin");
    public static final Identifier PACKET_SLOTMACHINE_BALANCE =
            new Identifier("d3d9_dllerek", "packet_slotmachine_balance");
    public static final Identifier PACKET_VERSION_SYNC =
            new Identifier("d3d9_dllerek", "packet_version_synchronize");

    public static final ItemGroup CASINO_ITEM_GROUP = FabricItemGroupBuilder.create(
                    new Identifier("d3d9_dllerek", "casino"))
            .icon(() -> new ItemStack(SlotMachineBlockItem.ITEM))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(SlotMachineBottomCaseItem.ITEM));
                stacks.add(new ItemStack(SlotMachineBlockItem.ITEM));
                stacks.add(new ItemStack(SlotMachineInfoPanelItem.ITEM));
            })
            .build();

    public static final ItemGroup BANK_ITEM_GROUP = FabricItemGroupBuilder.create(
                    new Identifier("d3d9_dllerek", "bank"))
            .icon(() -> new ItemStack(AtmBlockItem.ITEM))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(AtmBlockItem.ITEM));
                stacks.add(new ItemStack(AtmBottomCaseItem.ITEM));
            })
            .build();

    public static final File MOD_DATA_DIRECTORY =
            new File(FabricLoader.getInstance().getConfigDir().toFile(), "d3d9_dllerek");

    @Override
    public void onInitialize() {
        LOGGER.debug("Common-side initialization");

        if (!MOD_DATA_DIRECTORY.exists()) //noinspection ResultOfMethodCallIgnored
            MOD_DATA_DIRECTORY.mkdirs();
    }

}
