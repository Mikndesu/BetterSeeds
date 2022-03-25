package com.github.mikn.better_seeds.blocks.entity

import com.github.mikn.better_seeds.BetterSeeds
import com.github.mikn.better_seeds.init.BlockEntityInit
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.TranslatableComponent
import net.minecraft.world.level.block.state.BlockState

class SeedModifierBlockEntity(pos: BlockPos, state: BlockState) :
    InventoryBlockEntity(BlockEntityInit.SEED_MODIFIER.get(), pos, state, 27) {
    companion object {
        @JvmStatic
        val TITLE = TranslatableComponent("container.${BetterSeeds.MOD_ID}.seed_modifier")
    }
}