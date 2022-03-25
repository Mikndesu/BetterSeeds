package com.github.mikn.better_seeds.init

import com.github.mikn.better_seeds.BetterSeeds
import com.github.mikn.better_seeds.blocks.entity.SeedModifierBlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

class BlockEntityInit {
    companion object {
        @JvmStatic
        val BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, BetterSeeds.MOD_ID)
        @JvmStatic
        val SEED_MODIFIER = BLOCK_ENTITIES.register("seed_modifier") {
            BlockEntityType.Builder.of(::SeedModifierBlockEntity, BlockInit.SEED_MODIFIER.get()).build(null)
        }
    }
}