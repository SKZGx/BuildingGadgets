package com.direwolf20.buildinggadgets.common.registry.objects;

import com.direwolf20.buildinggadgets.client.gui.blocks.ChargingStationGUI;
import com.direwolf20.buildinggadgets.client.gui.blocks.TemplateManagerGUI;
import com.direwolf20.buildinggadgets.common.containers.ChargingStationContainer;
import com.direwolf20.buildinggadgets.common.containers.TemplateManagerContainer;
import com.direwolf20.buildinggadgets.common.registry.container.ScreenContainerBuilder;
import com.direwolf20.buildinggadgets.common.registry.container.ScreenContainerObjectBuilder;
import com.direwolf20.buildinggadgets.common.registry.container.ScreenContainerRegistryContainer;
import com.direwolf20.buildinggadgets.common.util.ref.Reference;
import com.direwolf20.buildinggadgets.common.util.ref.Reference.ContainerReference;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Reference.MODID)
@EventBusSubscriber(modid = Reference.MODID, bus = Bus.MOD)
public final class BGContainers {
    private static final ScreenContainerRegistryContainer container = new ScreenContainerRegistryContainer();

    private BGContainers() {}

    @ObjectHolder(ContainerReference.TEMPLATE_MANAGER_CONTAINER)
    public static ContainerType<TemplateManagerContainer> TEMPLATE_MANAGER_CONTAINER;
    @ObjectHolder(ContainerReference.CHARGING_STATION_CONTAINER)
    public static ContainerType<ChargingStationContainer> CHARGING_STATION_CONTAINER;

    static void init() {
        //Warning: The generics here cannot be converted to diamonds - even though IntelliJ thinks that these are redundant, Java cannot resolve that
        container.add(
                new ScreenContainerObjectBuilder(ContainerReference.TEMPLATE_MANAGER_CONTAINER_RL)
                        .builder(new ScreenContainerBuilder<TemplateManagerContainer, TemplateManagerGUI>(
                                TemplateManagerContainer::new, TemplateManagerGUI::new))
        );
        container.add(
                new ScreenContainerObjectBuilder(ContainerReference.CHARGING_STATION_CONTAINER_RL)
                        .builder(new ScreenContainerBuilder<ChargingStationContainer, ChargingStationGUI>(
                                ChargingStationContainer::new,
                                ChargingStationGUI::new))
        );
    }

    static void cleanup() {
        container.clear();
    }

    static void clientSetup() {
        container.clientSetup();
    }

    @SubscribeEvent
    public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
        container.register(event);
    }
}
