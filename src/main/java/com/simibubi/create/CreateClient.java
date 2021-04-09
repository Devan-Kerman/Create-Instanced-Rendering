package com.simibubi.create;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.simibubi.create.foundation.ResourceReloadHandler;
import com.simibubi.create.foundation.render.AllProgramSpecs;
import com.simibubi.create.foundation.render.KineticRenderer;
import com.simibubi.create.foundation.render.SuperByteBufferCache;
import com.simibubi.create.foundation.render.backend.Backend;
import com.simibubi.create.foundation.render.backend.OptifineHandler;
import com.simibubi.create.foundation.render.backend.instancing.InstancedTileRenderer;
import com.simibubi.create.foundation.utility.WorldAttached;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.settings.GraphicsFanciness;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.Item;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.IWorld;

public class CreateClient {

	public static SuperByteBufferCache bufferCache;
	public static WorldAttached<KineticRenderer> kineticRenderer;
	public static void clientInit(FMLClientSetupEvent event) {
		AllProgramSpecs.init();
		kineticRenderer = new WorldAttached<>(KineticRenderer::new);
		bufferCache = new SuperByteBufferCache();
		bufferCache.registerCompartment(KineticTileEntityRenderer.KINETIC_TILE);
		bufferCache.registerCompartment(ContraptionRenderDispatcher.CONTRAPTION, 20);
		IResourceManager resourceManager = Minecraft.getInstance()
			.getResourceManager();
		if (resourceManager instanceof IReloadableResourceManager)
			((IReloadableResourceManager) resourceManager).addReloadListener(new ResourceReloadHandler());
	}

	public static void invalidateRenderers() {
		invalidateRenderers(null);
	}

	public static void invalidateRenderers(@Nullable IWorld world) {
		bufferCache.invalidate();

		if (world != null) {
			kineticRenderer.get(world)
				.invalidate();
		} else {
			kineticRenderer.forEach(InstancedTileRenderer::invalidate);
		}

	}

	public static void checkGraphicsFanciness() {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null)
			return;

		if (mc.gameSettings.graphicsMode != GraphicsFanciness.FABULOUS)
			return;

		IFormattableTextComponent text = TextComponentUtils.bracketed(new StringTextComponent("WARN"))
			.formatted(TextFormatting.GOLD)
			.append(new StringTextComponent(
				" Some of Create's visual features will not be available while Fabulous graphics are enabled!"))
			.styled(style -> style
				.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/create dismissFabulousWarning"))
				.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
					new StringTextComponent("Click here to disable this warning"))));

		mc.ingameGUI.addChatMessage(ChatType.CHAT, text, mc.player.getUniqueID());
	}
}
