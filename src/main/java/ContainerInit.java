import com.github.mikn.better_seeds.BetterSeeds;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ContainerInit {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS,
            BetterSeeds.MOD_ID);

    public static final RegistryObject<MenuType<MyChestContainer>> EXAMPLE_CHEST = CONTAINERS
            .register("example_chest", () -> new MenuType<>(MyChestContainer::new));

    private ContainerInit() {
    }
}