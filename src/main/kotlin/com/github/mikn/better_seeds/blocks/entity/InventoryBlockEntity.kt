package com.github.mikn.better_seeds.blocks.entity

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.Connection
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.util.LazyOptional
import net.minecraftforge.items.CapabilityItemHandler
import net.minecraftforge.items.ItemStackHandler

open class InventoryBlockEntity(type: BlockEntityType<*>?, pos: BlockPos?, state: BlockState?, var size: Int) :
    BlockEntity(type, pos, state) {
    private var timer = 0
    private var requiresUpdate = false
    val inventory: ItemStackHandler
    var handler: LazyOptional<ItemStackHandler>
        protected set

    init {
        if (size <= 0) {
            size = 1
        }
        inventory = createInventory()
        handler = LazyOptional.of { inventory }
    }

    fun extractItem(slot: Int): ItemStack {
        val count = getItemInSlot(slot).count
        requiresUpdate = true
        return handler.map { inv: ItemStackHandler ->
            inv.extractItem(
                slot,
                count,
                false
            )
        }.orElse(ItemStack.EMPTY)
    }

    override fun <T> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        return if (cap === CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) handler.cast() else super.getCapability(
            cap,
            side
        )
    }

    private fun getItemInSlot(slot: Int): ItemStack {
        return handler.map { inv: ItemStackHandler ->
            inv.getStackInSlot(
                slot
            )
        }.orElse(ItemStack.EMPTY)
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener>? {
        return ClientboundBlockEntityDataPacket.create(this)
    }

    override fun getUpdateTag(): CompoundTag {
        return serializeNBT()
    }

    override fun handleUpdateTag(tag: CompoundTag) {
        super.handleUpdateTag(tag)
        load(tag)
    }

    fun insertItem(slot: Int, stack: ItemStack): ItemStack {
        val copy = stack.copy()
        stack.shrink(copy.count)
        requiresUpdate = true
        return handler.map { inv: ItemStackHandler ->
            inv.insertItem(
                slot,
                copy,
                false
            )
        }.orElse(ItemStack.EMPTY)
    }

    override fun invalidateCaps() {
        super.invalidateCaps()
        handler.invalidate()
    }

    override fun load(tag: CompoundTag) {
        super.load(tag)
        inventory.deserializeNBT(tag.getCompound("Inventory"))
    }

    override fun onDataPacket(net: Connection, pkt: ClientboundBlockEntityDataPacket) {
        super.onDataPacket(net, pkt)
        handleUpdateTag(pkt.tag!!)
    }

    fun tick() {
        timer++
        if (requiresUpdate && level != null) {
            update()
            requiresUpdate = false
        }
    }

    fun update() {
        requestModelDataUpdate()
        setChanged()
        if (level != null) {
            level!!.setBlockAndUpdate(worldPosition, blockState)
        }
    }

    override fun saveAdditional(tag: CompoundTag) {
        super.saveAdditional(tag)
        tag.put("Inventory", inventory.serializeNBT())
    }

    private fun createInventory(): ItemStackHandler {
        return object : ItemStackHandler(size) {
            override fun extractItem(slot: Int, amount: Int, simulate: Boolean): ItemStack {
                update()
                return super.extractItem(slot, amount, simulate)
            }

            override fun insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack {
                update()
                return super.insertItem(slot, stack, simulate)
            }
        }
    }
}