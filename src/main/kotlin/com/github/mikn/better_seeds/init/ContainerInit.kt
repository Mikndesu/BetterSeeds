package com.github.mikn.better_seeds.init

import com.github.mikn.better_seeds.BetterSeeds
import com.github.mikn.better_seeds.container.SeedModifierBlockContainer
import net.minecraftforge.common.extensions.IForgeMenuType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

class ContainerInit {
    companion object {
        @JvmStatic
        val CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BetterSeeds.MOD_ID)
        @JvmStatic
        val SEED_MODIFIER_CONTAINER = CONTAINERS.register("seed_modifier") {
            IForgeMenuType.create { windowId, inv, _ -> SeedModifierBlockContainer(windowId, inv) }
        }
    }
}