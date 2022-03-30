package com.github.mikn.better_seeds.items

import com.github.mikn.better_seeds.util.EffectEnum
import com.github.mikn.better_seeds.util.EffectIDManager
import com.github.mikn.better_seeds.util.LevelEnum
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.level.Level

class MagicWheat(properties: Properties): Item(properties) {
    override fun appendHoverText(
        itemStack: ItemStack,
        level: Level?,
        list: MutableList<Component>,
        tooltipFlag: TooltipFlag
    ) {
        val manager = EffectIDManager(itemStack)
        manager.listTag.forEach {
            val tag = it as CompoundTag
            val id = tag.getInt("effect_id")
            val level = tag.getInt("effect_level")
            list.add(TextComponent("${EffectEnum.getEffectById(id)} ${LevelEnum.getLevelEnumByNumber(level)}"))
        }
    }
}