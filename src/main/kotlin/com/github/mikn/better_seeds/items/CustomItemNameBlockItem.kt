package com.github.mikn.better_seeds.items

import com.github.mikn.better_seeds.BetterSeeds
import com.github.mikn.better_seeds.blocks.MagicCropBlock
import com.github.mikn.better_seeds.init.BlockInit
import com.github.mikn.better_seeds.util.EffectEnum
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.ItemNameBlockItem
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks

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

    override fun useOn(context: UseOnContext): InteractionResult {
        val result = super.useOn(context)
        val tag = this.getShareTag(context.itemInHand)
        var id = 0
        if(tag != null) {
            id = tag.getInt("id")
        }
        context.level.setBlock(context.clickedPos.above(), context.level.getBlockState(context.clickedPos.above()).setValue(MagicCropBlock.EFFECT_ID, id), 1)
        return result
    }
}