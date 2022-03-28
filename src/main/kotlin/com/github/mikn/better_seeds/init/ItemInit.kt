package com.github.mikn.better_seeds.init

import com.github.mikn.better_seeds.BetterSeeds
import com.github.mikn.better_seeds.items.CustomItemNameBlockItem
import com.github.mikn.better_seeds.items.MagicWheat
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

class ItemInit {
    companion object {
        @JvmStatic
        val ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BetterSeeds.MOD_ID)
        @JvmStatic
        val MAGIC_WHEAT_SEEDS = ITEMS.register("magic_wheat_seeds") {
            CustomItemNameBlockItem(
                BlockInit.MAGIC_WHEAT.get(),
                (Item.Properties()).tab(CreativeModeTab.TAB_FOOD)
            )
        }
        @JvmStatic
        val MAGIC_WHEAT = ITEMS.register("magic_wheat") {
            MagicWheat(Item.Properties().tab(CreativeModeTab.TAB_MATERIALS))
        }
    }
}