package com.github.mikn.better_seeds.init

import com.github.mikn.better_seeds.BetterSeeds
import com.github.mikn.better_seeds.blocks.MagicCropBlock
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
    }
}