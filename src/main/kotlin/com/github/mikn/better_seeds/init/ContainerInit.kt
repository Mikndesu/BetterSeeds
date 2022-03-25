package com.github.mikn.better_seeds.init

import com.github.mikn.better_seeds.BetterSeeds
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

class ContainerInit {
    companion object {
        @JvmStatic
        val CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BetterSeeds.MOD_ID)
    }
}