package com.direwolf20.buildinggadgets.client.proxy;

import com.direwolf20.buildinggadgets.client.KeyBindings;
import com.direwolf20.buildinggadgets.common.BuildingObjects;
import com.direwolf20.buildinggadgets.common.blocks.Models.BakedModelLoader;
import com.direwolf20.buildinggadgets.common.blocks.templatemanager.TemplateManagerContainer;
import com.direwolf20.buildinggadgets.common.entities.*;
import com.direwolf20.buildinggadgets.common.items.gadgets.*;
import com.direwolf20.buildinggadgets.common.tools.ToolRenders;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy {
    public static void clientSetup(final FMLClientSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            KeyBindings.init();
//            BuildingObjects.initColorHandlers();
        });
    }

    public static void registerModels(@SuppressWarnings("unused") ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(new BakedModelLoader());

        RenderingRegistry.registerEntityRenderingHandler(BlockBuildEntity.class, new BlockBuildEntityRender.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ConstructionBlockEntity.class, new ConstructionBlockEntityRender.Factory());

        // @todo: reimplement? @since 1.13.x
//        ModBlocks.effectBlock.initModel();
//        ModBlocks.templateManager.initModel();
//
//        BuildingObjects.gadgetBuilding.initModel();
//        BuildingObjects.gadgetExchanger.initModel();
//        BuildingObjects.gadgetCopyPaste.initModel();
//        BuildingObjects.template.initModel();
//
//        if (SyncedConfig.enableDestructionGadget) {
//            BuildingObjects.gadgetDestruction.initModel();
//        }
//
//        if (SyncedConfig.enablePaste) {
//            BuildingObjects.ConstructionPasteContainer.initModel();
//            BuildingObjects.constructionPaste.initModel();
//
//            ModBlocks.constructionBlock.initModel();
//            ModBlocks.constructionBlockPowder.initModel();
//
//        // REIMPLEMENT
////            ModelLoader.setCustomMeshDefinition(ModItems.constructionPasteContainer, new PasteContainerMeshDefinition());
////            ModelLoader.setCustomMeshDefinition(ModItems.constructionPasteContainert2, new PasteContainerMeshDefinition());
////            ModelLoader.setCustomMeshDefinition(ModItems.constructionPasteContainert3, new PasteContainerMeshDefinition());
//        }

        RenderingRegistry.registerEntityRenderingHandler(BlockBuildEntity.class, new BlockBuildEntityRender.Factory());
        RenderingRegistry.registerEntityRenderingHandler(ConstructionBlockEntity.class, new ConstructionBlockEntityRender.Factory());
    }

    public static void renderWorldLastEvent(RenderWorldLastEvent evt) {
        Minecraft mc = Minecraft.getInstance();
        EntityPlayer player = mc.player;
        ItemStack heldItem = GadgetGeneric.getGadget(player);
        if (heldItem.isEmpty())
            return;

        if (heldItem.getItem() instanceof GadgetBuilding) {
            ToolRenders.renderBuilderOverlay(evt, player, heldItem);
        } else if (heldItem.getItem() instanceof GadgetExchanger) {
            ToolRenders.renderExchangerOverlay(evt, player, heldItem);
        } else if (heldItem.getItem() instanceof GadgetCopyPaste) {
            ToolRenders.renderPasteOverlay(evt, player, heldItem);
        } else if (heldItem.getItem() instanceof GadgetDestruction) {
            ToolRenders.renderDestructionOverlay(evt, player, heldItem);
        }

    }

    public static void registerSprites(TextureStitchEvent.Pre event) {
        registerSprite(event, TemplateManagerContainer.TEXTURE_LOC_SLOT_TOOL);
        registerSprite(event, TemplateManagerContainer.TEXTURE_LOC_SLOT_TEMPLATE);
    }

    private static void registerSprite(TextureStitchEvent.Pre event, String loc) {
        event.getMap().registerSprite(Minecraft.getInstance().getResourceManager(), new ResourceLocation(loc));
    }
}