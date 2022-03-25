package com.github.mikn.better_seeds.init

import com.github.mikn.better_seeds.BetterSeeds
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

class BlockEntityInit {
    companion object {
        @JvmStatic
        val BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, BetterSeeds.MOD_ID)
    }
}