package com.github.mikn.better_seeds.blocks

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.Mth
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.monster.Ravager
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.CropBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import net.minecraftforge.event.ForgeEventFactory
import java.util.*

class MagicCropBlock(property: Properties) : CropBlock(property) {

    private val MAX_AGE = 7

    init {
        registerDefaultState(stateDefinition.any().setValue(EFFECT_ID, Integer.valueOf(0)))
        registerDefaultState(stateDefinition.any().setValue(AGE, Integer.valueOf(0)))
    }

    companion object {
        private val SHAPE_BY_AGE = arrayOf(
            box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
            box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
        )
        @JvmStatic
        val EFFECT_ID = IntegerProperty.create("effect_id", 0, 31)
        @JvmStatic
        val AGE = IntegerProperty.create("age", 0, 7)
    }

    override fun getShape(blockState: BlockState, getter: BlockGetter, blockPos: BlockPos, context:CollisionContext) : VoxelShape {
        return SHAPE_BY_AGE[blockState.getValue(this.ageProperty)]
    }

    override fun mayPlaceOn(blockState: BlockState, getter: BlockGetter, blockPos: BlockPos) : Boolean {
        return blockState.`is`(Blocks.END_STONE)
    }

    override fun getAgeProperty() : IntegerProperty {
        return AGE
    }

    override fun getAge(blockState: BlockState) : Int {
        return blockState.getValue(this.ageProperty)
    }

    override fun getStateForAge(int:Int) : BlockState {
        return this.defaultBlockState().setValue(this.ageProperty, Integer.valueOf(int))
    }

    override fun isMaxAge(state: BlockState) : Boolean {
        return state.getValue(ageProperty) >= this.MAX_AGE
    }

    override fun isRandomlyTicking(blockState: BlockState): Boolean {
        return !this.isMaxAge(blockState)
    }

    override fun randomTick(blockState: BlockState, serverLevel: ServerLevel, blockPos: BlockPos, random:Random) {
        if(!serverLevel.isAreaLoaded(blockPos, 1)) return
        if(serverLevel.getRawBrightness(blockPos, 0) >= 9) {
            val i = this.getAge(blockState)
            if(i < this.MAX_AGE) {
                val f:Float = getGrowthSpeed(this, serverLevel, blockPos)
                if(net.minecraftforge.common.ForgeHooks.onCropsGrowPre(serverLevel, blockPos, blockState, random.nextInt((25.0F / f).toInt() + 1) == 0)) {
                    serverLevel.setBlock(blockPos, this.getStateForAge(i+1), 2)
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(serverLevel, blockPos, blockState)
                }
            }
        }
    }

    override fun growCrops(level: Level, blockPos: BlockPos, blockState: BlockState) {
        var i = this.getAge(blockState) + this.getBonemealAgeIncrease(level)
        val j = this.MAX_AGE
        if(i > j) {
            i = j
        }
        level.setBlock(blockPos, this.getStateForAge(i), 2)
    }

    override fun getBonemealAgeIncrease(level: Level) : Int {
        return Mth.nextInt(level.random, 2, 5)
    }

    override fun canSurvive(blockstate: BlockState?, worldIn: LevelReader, pos: BlockPos): Boolean {
        val blockpos = pos.below()
        val groundState = worldIn.getBlockState(blockpos)
        return this.mayPlaceOn(groundState, worldIn, blockpos)
    }

    override fun entityInside(blockState: BlockState, level: Level, blockPos: BlockPos, entity: Entity) {
        if (entity is Ravager && ForgeEventFactory.getMobGriefingEvent(level, entity)) {
            level.destroyBlock(blockPos, true, entity)
        }
        super.entityInside(blockState, level, blockPos, entity)
    }

    override fun getBaseSeedId(): Item {
        return Items.WHEAT_SEEDS
    }

    override fun getCloneItemStack(getter: BlockGetter, blockPos: BlockPos, blockState: BlockState) : ItemStack {
        return ItemStack(this.baseSeedId)
    }

    override fun isValidBonemealTarget(
        getter: BlockGetter,
        blockPos: BlockPos,
        blockState: BlockState,
        boolean: Boolean
    ): Boolean {
        return !this.isMaxAge(blockState)
    }

    override fun isBonemealSuccess(
        level: Level,
        random: Random,
        blockPos: BlockPos,
        blockState: BlockState
    ): Boolean {
        return true
    }

    override fun performBonemeal(serverLevel: ServerLevel, random: Random, blockPos: BlockPos, blockState: BlockState) {
        this.growCrops(level = serverLevel, blockPos, blockState)
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(AGE)
        builder.add(EFFECT_ID)
    }
}