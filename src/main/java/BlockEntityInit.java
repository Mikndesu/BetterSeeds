import com.github.mikn.better_seeds.BetterSeeds;
import com.github.mikn.better_seeds.init.BlockInit;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BlockEntityInit {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(ForgeRegistries.BLOCK_ENTITIES, BetterSeeds.MOD_ID);

    public static final RegistryObject<BlockEntityType<MyChestBlockEntity>> EXAMPLE_CHEST = BLOCK_ENTITIES
            .register("example_chest", () -> BlockEntityType.Builder
                    .of(MyChestBlockEntity::new, BlockInit.getEXAMPLE_CHEST().get()).build(null));

    private BlockEntityInit() {
    }
}

