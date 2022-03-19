package com.github.mikn.better_seeds

import com.github.mikn.better_seeds.init.BlockInit.Companion.BLOCKS
import com.github.mikn.better_seeds.init.ItemInit.Companion.ITEMS
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import java.util.logging.Logger


@Mod(BetterSeeds.MOD_ID)
class BetterSeeds {
    companion object {
        const val MOD_ID = "better_seeds"
        val LOGGER = Logger.getLogger("BetterSeeds/Main")
    }

    init {
        val bus = FMLJavaModLoadingContext.get().modEventBus
        BLOCKS.register(bus)
        ITEMS.register(bus)
        MinecraftForge.EVENT_BUS.register(this)
    }
}