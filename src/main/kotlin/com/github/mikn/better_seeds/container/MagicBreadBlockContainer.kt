package com.github.mikn.better_seeds.container

import com.github.mikn.better_seeds.blocks.entity.MagicBreadStockBlockEntity
import com.github.mikn.better_seeds.init.BlockInit
import com.github.mikn.better_seeds.init.ContainerInit
import com.github.mikn.better_seeds.init.ItemInit
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.entity.player.Player
import net.minecraft.world.inventory.*
import net.minecraft.world.item.ItemStack
import net.minecraftforge.items.IItemHandler
import net.minecraftforge.items.ItemStackHandler
import net.minecraftforge.items.SlotItemHandler


class MagicBreadBlockContainer constructor(
    id: Int,
    playerInv: Inventory,
    slots: IItemHandler? = ItemStackHandler(27),
    pos: BlockPos? = BlockPos.ZERO
) :
    AbstractContainerMenu(ContainerInit.MAGIC_BREAD_STOCK_CONTAINER.get(), id) {
    private val containerAccess: ContainerLevelAccess

    init {
        containerAccess = ContainerLevelAccess.create(playerInv.player.level, pos)
        for (k in 0..2) {
            for (l in 0..8) {
                addSlot(object: SlotItemHandler(slots, l + k * 9, 8 + l * 18, 18 + k * 18) {
                    override fun mayPlace(stack: ItemStack): Boolean {
                        return ItemInit.MAGIC_BREAD.get() == stack.item
                    }
                })
            }
        }
        for (i1 in 0..2) {
            for (k1 in 0..8) {
                addSlot(Slot(playerInv, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18))
            }
        }
        for (j1 in 0..8) {
            addSlot(Slot(playerInv, j1, 8 + j1 * 18, 142))
        }
    }

    override fun quickMoveStack(player: Player, index: Int): ItemStack {
        var retStack = ItemStack.EMPTY
        val slot = getSlot(index)
        if (slot.hasItem()) {
            val item = slot.item
            retStack = item.copy()
            if (index < 27) {
                if (!moveItemStackTo(item, 27, slots.size, true)) return ItemStack.EMPTY
            } else if (!moveItemStackTo(item, 0, 27, false)) return ItemStack.EMPTY
            if (item.isEmpty) {
                slot.set(ItemStack.EMPTY)
            } else {
                slot.setChanged()
            }
        }
        return retStack
    }

    override fun stillValid(player: Player): Boolean {
        return stillValid(containerAccess, player, BlockInit.MAGIC_BREAD_STOCK.get())
    }

    companion object {
        fun getServerContainer(chest: MagicBreadStockBlockEntity, pos: BlockPos?): MenuConstructor {
            return MenuConstructor { id: Int, playerInv: Inventory, _: Player? ->
                MagicBreadBlockContainer(
                    id,
                    playerInv,
                    chest.inventory,
                    pos
                )
            }
        }
    }
}