package com.github.mikn.better_seeds

import com.github.mikn.better_seeds.init.BlockInit
import com.github.mikn.better_seeds.init.BlockInit.Companion.BLOCKS
import com.github.mikn.better_seeds.init.ItemInit.Companion.ITEMS
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
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
        MinecraftForge.EVENT_BUS.register(this)
        BLOCKS.register(bus)
        ITEMS.register(bus)
    }

    private fun setRenderLayer(block: Block, type: RenderType) {
        ItemBlockRenderTypes.setRenderLayer(block, type)
    }

    @SubscribeEvent
    fun commonSetup(evt: FMLClientSetupEvent) {
        setRenderLayer(BlockInit.MAGIC_WHEAT.get(), RenderType.cutout())
    }
}