package com.simibubi.create.foundation.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IWorldReader;

/**
 * Stolen from EntityRendererManager
 */
public class ShadowRenderHelper {

	public static void renderShadow(MatrixStack p_229096_0_, IRenderTypeBuffer p_229096_1_, Vector3d pos,
		float p_229096_3_, float p_229096_6_) {
		float f = p_229096_6_;

		double d2 = pos.getX();
		double d0 = pos.getY();
		double d1 = pos.getZ();
		int i = MathHelper.floor(d2 - (double) f);
		int j = MathHelper.floor(d2 + (double) f);
		int k = MathHelper.floor(d0 - (double) f);
		int l = MathHelper.floor(d0);
		int i1 = MathHelper.floor(d1 - (double) f);
		int j1 = MathHelper.floor(d1 + (double) f);
		MatrixStack.Entry matrixstack$entry = p_229096_0_.peek();
		IVertexBuilder ivertexbuilder = p_229096_1_.getBuffer(SHADOW_LAYER);

		for (BlockPos blockpos : BlockPos.getAllInBoxMutable(new BlockPos(i, k, i1), new BlockPos(j, l, j1))) {
			renderShadowPart(matrixstack$entry, ivertexbuilder, Minecraft.getInstance().world, blockpos, d2, d0, d1, f,
				p_229096_3_);
		}

	}

	private static void shadowVertex(MatrixStack.Entry p_229091_0_, IVertexBuilder p_229091_1_, float p_229091_2_,
		float p_229091_3_, float p_229091_4_, float p_229091_5_, float p_229091_6_, float p_229091_7_) {
		p_229091_1_.vertex(p_229091_0_.getModel(), p_229091_3_, p_229091_4_, p_229091_5_)
			.color(1.0F, 1.0F, 1.0F, p_229091_2_)
			.texture(p_229091_6_, p_229091_7_)
			.overlay(OverlayTexture.DEFAULT_UV)
			.light(15728880)
			.normal(p_229091_0_.getNormal(), 0.0F, 1.0F, 0.0F)
			.endVertex();
	}
}
