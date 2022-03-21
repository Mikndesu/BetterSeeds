package com.github.mikn.better_seeds.init

import com.github.mikn.better_seeds.BetterSeeds
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemNameBlockItem
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

class ItemInit {
    companion object {
        @JvmStatic
        val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterSeeds.MOD_ID)
        @JvmStatic
        val MAGIC_WHEAT_SEEDS = ITEMS.register("magic_wheat_seeds") {
            ItemNameBlockItem(
                BlockInit.MAGIC_WHEAT.get(),
                (Item.Properties()).tab(CreativeModeTab.TAB_FOOD)
            )
        }
        @JvmStatic
        val MAGIC_WHEAT = ITEMS.register("magic_wheat") {
            Item(Item.Properties().tab(CreativeModeTab.TAB_MATERIALS))
        }
    }
}