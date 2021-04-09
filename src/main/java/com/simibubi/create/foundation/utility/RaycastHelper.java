package com.simibubi.create.foundation.utility;

import java.util.function.Predicate;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class RaycastHelper {

	public static BlockRayTraceResult rayTraceRange(World worldIn, PlayerEntity playerIn, double range) {
		Vector3d origin = getTraceOrigin(playerIn);
		Vector3d target = getTraceTarget(playerIn, range, origin);
		RayTraceContext context = new RayTraceContext(origin, target, BlockMode.COLLIDER, FluidMode.NONE, playerIn);
		return worldIn.rayTraceBlocks(context);
	}

	public static PredicateTraceResult rayTraceUntil(PlayerEntity playerIn, double range,
		Predicate<BlockPos> predicate) {
		Vector3d origin = getTraceOrigin(playerIn);
		Vector3d target = getTraceTarget(playerIn, range, origin);
		return rayTraceUntil(origin, target, predicate);
	}

	public static Vector3d getTraceTarget(PlayerEntity playerIn, double range, Vector3d origin) {
		float f = playerIn.rotationPitch;
		float f1 = playerIn.rotationYaw;
		float f2 = MathHelper.cos(-f1 * 0.017453292F - (float) Math.PI);
		float f3 = MathHelper.sin(-f1 * 0.017453292F - (float) Math.PI);
		float f4 = -MathHelper.cos(-f * 0.017453292F);
		float f5 = MathHelper.sin(-f * 0.017453292F);
		float f6 = f3 * f4;
		float f7 = f2 * f4;
		double d3 = range;
		Vector3d Vector3d1 = origin.add((double) f6 * d3, (double) f5 * d3, (double) f7 * d3);
		return Vector3d1;
	}

	public static Vector3d getTraceOrigin(PlayerEntity playerIn) {
		double d0 = playerIn.getX();
		double d1 = playerIn.getY() + (double) playerIn.getEyeHeight();
		double d2 = playerIn.getZ();
		Vector3d Vector3d = new Vector3d(d0, d1, d2);
		return Vector3d;
	}

	public static class PredicateTraceResult {
		private BlockPos pos;
		private Direction facing;

		private PredicateTraceResult(BlockPos pos, Direction facing) {
			this.pos = pos;
			this.facing = facing;
		}

		private PredicateTraceResult() {
			// missed, no result
		}

		public Direction getFacing() {
			return facing;
		}

		public BlockPos getPos() {
			return pos;
		}

		public boolean missed() {
			return this.pos == null;
		}
	}

}
