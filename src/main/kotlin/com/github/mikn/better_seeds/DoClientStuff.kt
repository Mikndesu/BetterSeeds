package com.github.mikn.better_seeds

import com.github.mikn.better_seeds.init.BlockInit
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

@Mod.EventBusSubscriber(modid = BetterSeeds.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object DoClientStuff {
    @JvmStatic @SubscribeEvent
    fun clientSetup(evt: FMLClientSetupEvent) {
        evt.enqueueWork {
            ItemBlockRenderTypes.setRenderLayer(BlockInit.MAGIC_WHEAT.get(), RenderType.cutout())
        }
    }
}