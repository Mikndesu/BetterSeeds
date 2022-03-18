package com.github.mikn.better_seeds.blocks

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
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
import net.minecraft.world.level.block.BonemealableBlock
import net.minecraft.world.level.block.BushBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties.AGE_7
import net.minecraft.world.level.block.state.properties.IntegerProperty
import net.minecraft.world.phys.shapes.CollisionContext
import net.minecraft.world.phys.shapes.VoxelShape
import net.minecraftforge.common.IPlantable
import net.minecraftforge.event.ForgeEventFactory
import java.util.*

class MagicCropBlock(property: Properties) : BushBlock(property), BonemealableBlock {

    private val MAX_AGE = 7

    init {
        registerDefaultState(stateDefinition.any().setValue(AGE_7, Integer.valueOf(0)))
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
        fun getGrowthSpeed(block: Block, getter: BlockGetter, blockPos: BlockPos) : Float {
            var f = 1.0F
            val blockPos = blockPos.below()
            for(i in -1 .. 1) {
                for(j in -1 .. 1) {
                    var f1 = 0.0F
                    val blockState = getter.getBlockState(blockPos.offset(i, 0, j))
                    if(blockState.canSustainPlant(getter, blockPos.offset(i, 0, j), Direction.UP, block as IPlantable)) {
                        var f1 = 1.0F
                        if(blockState.isFertile(getter, blockPos.offset(i, 0, j))) {
                            f1 = 3.0F
                        }
                    }
                    if(i != 0 || j != 0) {
                        f1 /= 4.0F
                    }
                    f += f1
                }
            }
            val blockPos1: BlockPos = blockPos.north()
            val blockPos2: BlockPos = blockPos.south()
            val blockPos3: BlockPos = blockPos.west()
            val blockPos4: BlockPos = blockPos.east()
            val flag = getter.getBlockState(blockPos3).`is`(block) || getter.getBlockState(blockPos4).`is`(block)
            val flag1 = getter.getBlockState(blockPos1).`is`(block) || getter.getBlockState(blockPos2).`is`(block)
            if(flag && flag1) {
                f /= 2.0F
            } else {
                val flag2 = getter.getBlockState(blockPos3.north()).`is`(block) || getter.getBlockState(blockPos4.north()).`is`(block) || getter.getBlockState(blockPos4.south()).`is`(block) || getter.getBlockState(blockPos3.south()).`is`(block)
                if(flag2) {
                    f /= 2.0F
                }
            }
            return f
        }
    }

    override fun getShape(blockState: BlockState, getter: BlockGetter, blockPos: BlockPos, context:CollisionContext) : VoxelShape {
        return SHAPE_BY_AGE[blockState.getValue(this.getAgeProperty())]
    }

    override fun mayPlaceOn(blockState: BlockState, getter: BlockGetter, blockPos: BlockPos) : Boolean {
        return blockState.`is`(Blocks.FARMLAND)
    }

    private fun getAgeProperty() : IntegerProperty {
        return AGE_7
    }

    private fun getAge(blockState: BlockState) : Int {
        return blockState.getValue(this.getAgeProperty())
    }

    private fun getStateForAge(int:Int) : BlockState {
        return this.defaultBlockState().setValue(this.getAgeProperty(), Integer.valueOf(int))
    }

    private fun isMaxAge(state: BlockState) : Boolean {
        return state.getValue(getAgeProperty()) >= this.MAX_AGE
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

    fun growCrops(level: Level, blockPos: BlockPos, blockState: BlockState) {
        var i = this.getAge(blockState) + this.getBonemealAgeIncrease(level)
        val j = this.MAX_AGE
        if(i > j) {
            i = j
        }
        level.setBlock(blockPos, this.getStateForAge(i), 2)
    }

    private fun getBonemealAgeIncrease(level: Level) : Int {
        return Mth.nextInt(level.random, 2, 5)
    }

    override fun canSurvive(blockState: BlockState, levelReader: LevelReader, blockPos: BlockPos) : Boolean {
        return (levelReader.getRawBrightness(blockPos, 0) >= 8 || levelReader.canSeeSky(blockPos)) && super.canSurvive(blockState, levelReader, blockPos)
    }

    override fun entityInside(blockState: BlockState, level: Level, blockPos: BlockPos, entity: Entity) {
        if (entity is Ravager && ForgeEventFactory.getMobGriefingEvent(level, entity)) {
            level.destroyBlock(blockPos, true, entity)
        }
        super.entityInside(blockState, level, blockPos, entity)
    }

    private fun getBaseSeedId(): Item {
        return Items.WHEAT_SEEDS
    }

    override fun getCloneItemStack(getter: BlockGetter, blockPos: BlockPos, blockState: BlockState) : ItemStack {
        return ItemStack(this.getBaseSeedId())
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
        builder.add(AGE_7)
    }
}