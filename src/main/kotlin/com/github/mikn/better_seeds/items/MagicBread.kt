package com.github.mikn.better_seeds.items

import com.github.mikn.better_seeds.util.EffectEnum
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class MagicBread(properties: Properties): Item(properties) {
    override fun appendHoverText(
        itemStack: ItemStack,
        level: Level?,
        list: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        val tag = this.getShareTag(itemStack)
        if(tag != null) {
            val id = tag.getInt("effect_id")
            list.add(TextComponent(EffectEnum.getEffectById(id).toString()))
        }
    }
}