package com.simibubi.create.foundation.render.backend.core;

import com.simibubi.create.foundation.render.backend.gl.attrib.VertexFormat;
import com.simibubi.create.foundation.render.backend.instancing.InstancedModel;
import com.simibubi.create.foundation.render.backend.instancing.InstancedTileRenderer;

import net.minecraft.client.renderer.BufferBuilder;

public class TransformedModel extends InstancedModel<ModelData> {

	public TransformedModel(InstancedTileRenderer<?> renderer, BufferBuilder buf) {
        super(buf);
    }

    @Override
    protected ModelData newInstance() {
        return new ModelData(this);
    }

    @Override
    protected VertexFormat getInstanceFormat() {
        return INSTANCE_FORMAT;
    }
}
