import com.github.mikn.better_seeds.BetterSeeds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;

public class MyChestBlockEntity extends InventoryBlockEntity {
    public static final Component TITLE = new TranslatableComponent(
            "container." + BetterSeeds.MOD_ID + ".example_chest");

    public MyChestBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.EXAMPLE_CHEST.get(), pos, state, 27);
    }
}