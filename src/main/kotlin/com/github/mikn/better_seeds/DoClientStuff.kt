package com.github.mikn.better_seeds

import com.github.mikn.better_seeds.client.screen.SeedModifierScreen
import com.github.mikn.better_seeds.init.BlockInit
import com.github.mikn.better_seeds.init.ContainerInit
import net.minecraft.client.gui.screens.MenuScreens
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
            MenuScreens.register(ContainerInit.SEED_MODIFIER.get(), ::SeedModifierScreen);
        }
    }
}