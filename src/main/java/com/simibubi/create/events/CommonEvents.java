package com.simibubi.create.events;

import com.simibubi.create.foundation.utility.WorldAttached;

import net.minecraft.world.IWorld;

@EventBusSubscriber
public class CommonEvents {

	@SubscribeEvent
	public static void onUnloadWorld(WorldEvent.Unload event) {
		IWorld world = event.getWorld();
		WorldAttached.invalidateWorld(world);
	}

}
