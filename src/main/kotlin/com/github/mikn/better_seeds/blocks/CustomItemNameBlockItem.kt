package com.github.mikn.better_seeds.blocks

import net.minecraft.world.item.ItemNameBlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.Block

class CustomItemNameBlockItem(block: Block, properties: Properties) : ItemNameBlockItem(block, properties) {
    // this means that the item has a visual effect like enchanted item
    override fun isFoil(p_41453_: ItemStack): Boolean {
        return true
    }
}