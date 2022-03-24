package com.github.mikn.better_seeds.blocks

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.MenuProvider
import net.minecraft.world.SimpleMenuProvider
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.inventory.CraftingMenu
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.CraftingTableBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class SeedModifierBlock(properties: Properties): Block(properties) {
    override fun use(blockState: BlockState, level: Level, blockPos: BlockPos, player: Player, interactionHand: InteractionHand, blockHitResult: BlockHitResult) : InteractionResult {
        return if(level.isClientSide) {
            InteractionResult.SUCCESS
        } else {
            player.openMenu(blockState.getMenuProvider(level, blockPos))
            InteractionResult.CONSUME
        }
    }

    override fun getMenuProvider(blockState: BlockState, level: Level, blockPos: BlockPos): MenuProvider? {
        return SimpleMenuProvider({ p_52229_: Int, p_52230_: Inventory?, _: Player? ->
            CraftingMenu(
                p_52229_,
                p_52230_,
                ContainerLevelAccess.create(level, blockPos)
            )
        }, TextComponent("aaa"))
    }
}