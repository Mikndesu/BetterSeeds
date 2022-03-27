package com.github.mikn.better_seeds.blocks

import com.github.mikn.better_seeds.blocks.entity.SeedModifierBlockEntity
import com.github.mikn.better_seeds.container.SeedModifierBlockContainer
import net.minecraft.core.BlockPos
import net.minecraft.world.*
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.ContainerLevelAccess
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.PathComputationType
import net.minecraft.world.phys.BlockHitResult


class SeedModifierBlock(properties: Properties) : Block(properties), EntityBlock {

    override fun use(blockState: BlockState, level: Level, blockPos: BlockPos, player: Player, hand: InteractionHand, hit: BlockHitResult): InteractionResult {
        return if(level.isClientSide) {
            InteractionResult.SUCCESS
        } else {
            player.openMenu(blockState.getMenuProvider(level, blockPos))
            InteractionResult.CONSUME
        }
    }

    override fun getMenuProvider(blockState: BlockState, level: Level, blockPos: BlockPos): MenuProvider? {
        val blockEntity = level.getBlockEntity(blockPos)
        return if(blockEntity is SeedModifierBlockEntity) {
            val name = (blockEntity as Nameable).displayName
            SimpleMenuProvider({ id: Int, inventory: Inventory?, _: Player? ->
                SeedModifierBlockContainer(
                    id,
                    inventory!!,
                    ContainerLevelAccess.create(level, blockPos)
                )
            }, name)
        } else {
            null
        }
    }

    override fun setPlacedBy(
        level: Level,
        blockPos: BlockPos,
        blockState: BlockState,
        livingEntity: LivingEntity?,
        itemStack: ItemStack
    ) {
        if(itemStack.hasCustomHoverName()) {
            val blockEntity = level.getBlockEntity(blockPos)
            if(blockEntity is SeedModifierBlockEntity) {
                blockEntity.customName = itemStack.hoverName
            }
        }
    }

    override fun isPathfindable(
        p_60475_: BlockState,
        p_60476_: BlockGetter,
        p_60477_: BlockPos,
        p_60478_: PathComputationType
    ): Boolean {
        return false
    }

    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity {
        return SeedModifierBlockEntity(blockPos, blockState)
    }
}