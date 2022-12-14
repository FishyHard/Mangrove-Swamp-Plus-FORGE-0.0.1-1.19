package net.fishyhard.mangroveplus.item;

import net.fishyhard.mangroveplus.MangroveSwampPlus;
import net.fishyhard.mangroveplus.entities.ModEntities;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static software.bernie.example.registry.ItemRegistry.ITEMS;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MangroveSwampPlus.MOD_ID);

    public static final RegistryObject<Item> KING_FROG_SPAWN_EGG = ITEMS.register("king_frog_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.KING_FROG,0x0f62ba, 0xebb931,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<Item> BEAVER_SPAWN_EGG = ITEMS.register("beaver_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BEAVER,0x8a7350, 0x5c4b36,
                    new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
