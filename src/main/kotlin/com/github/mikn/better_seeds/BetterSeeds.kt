package com.github.mikn.better_seeds

import com.github.mikn.better_seeds.init.BlockEntityInit.Companion.BLOCK_ENTITIES
import com.github.mikn.better_seeds.init.BlockInit.Companion.BLOCKS
import com.github.mikn.better_seeds.init.ContainerInit.Companion.CONTAINERS
import com.github.mikn.better_seeds.init.ItemInit.Companion.ITEMS
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager


@Mod(BetterSeeds.MOD_ID)
class BetterSeeds {
    companion object {
        const val MOD_ID = "better_seeds"
        @JvmStatic
        val LOGGER = LogManager.getLogger("BetterSeeds/Main")
    }

    init {
        val bus = FMLJavaModLoadingContext.get().modEventBus
        CONTAINERS.register(bus)
        BLOCK_ENTITIES.register(bus)
        BLOCKS.register(bus)
        ITEMS.register(bus)
        MinecraftForge.EVENT_BUS.register(this)
    }
}