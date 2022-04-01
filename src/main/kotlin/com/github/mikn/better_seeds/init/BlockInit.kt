package com.github.mikn.better_seeds.init

import com.github.mikn.better_seeds.BetterSeeds
import com.github.mikn.better_seeds.blocks.MagicBreadStockBlock
import com.github.mikn.better_seeds.blocks.MagicCropBlock
import com.github.mikn.better_seeds.blocks.SeedModifierBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries

class BlockInit {
    companion object {
        @JvmStatic
        val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BetterSeeds.MOD_ID)
        @JvmStatic
        val MAGIC_WHEAT = BLOCKS.register("magic_wheat") {
            MagicCropBlock(
                BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(
                    SoundType.CROP
                )
            )
        }
        @JvmStatic
        val SEED_MODIFIER = BLOCKS.register("seed_modifier") {
            SeedModifierBlock(BlockBehaviour.Properties.of(Material.WOOD))
        }
        @JvmStatic
        val MAGIC_BREAD_STOCK = BLOCKS.register("magic_bread_stock") {
            MagicBreadStockBlock(BlockBehaviour.Properties.of(Material.WOOD).noOcclusion())
        }
    }
}