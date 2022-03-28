package com.github.mikn.better_seeds.items

import com.github.mikn.better_seeds.util.EffectEnum
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.item.ItemNameBlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block

class CustomItemNameBlockItem(block: Block, properties: Properties) : ItemNameBlockItem(block, properties) {
    // this means that the item has a visual effect like enchanted item
    override fun isFoil(p_41453_: ItemStack): Boolean {
        return true
    }
    override fun appendHoverText(
        itemStack: ItemStack,
        level: Level?,
        list: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        val tag = this.getShareTag(itemStack)
        if(tag != null) {
            val id = tag.getInt("id")
            list.add(TextComponent(EffectEnum.getEffectById(id).toString()))
        }
    }
}