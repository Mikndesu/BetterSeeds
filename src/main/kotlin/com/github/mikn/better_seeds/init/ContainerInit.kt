package com.github.mikn.better_seeds.init

import com.github.mikn.better_seeds.BetterSeeds
import com.github.mikn.better_seeds.container.SeedModifierContainer
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.MenuType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

class ContainerInit {
    companion object {
        @JvmStatic
        val CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, BetterSeeds.MOD_ID)
        @JvmStatic
        val SEED_MODIFIER = CONTAINERS.register("seed_modifier") {
            MenuType {id:Int, plauerInv: Inventory ->
                SeedModifierContainer(id, plauerInv)
            }
        }
    }
}