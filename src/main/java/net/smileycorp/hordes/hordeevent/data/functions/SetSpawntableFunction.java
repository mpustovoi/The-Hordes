package net.smileycorp.hordes.hordeevent.data.functions;

import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.smileycorp.atlas.api.data.DataType;
import net.smileycorp.hordes.common.HordesLogger;
import net.smileycorp.hordes.common.data.conditions.BiomeCondition;
import net.smileycorp.hordes.common.data.values.ValueGetter;
import net.smileycorp.hordes.common.event.HordeBuildSpawntableEvent;
import net.smileycorp.hordes.hordeevent.data.HordeTableLoader;

public class SetSpawntableFunction implements HordeFunction<HordeBuildSpawntableEvent> {

    private final ValueGetter<String> getter;

    public SetSpawntableFunction(ValueGetter<String> getter) {
        this.getter = getter;
    }

    @Override
    public void apply(HordeBuildSpawntableEvent event) {
        event.setSpawnTable(HordeTableLoader.INSTANCE.getTable(
                new ResourceLocation(getter.get(event.getEntityWorld(),
                        event.getEntity(), event.getEntityWorld().random))));
    }

    public static SetSpawntableFunction deserialize(JsonElement json) {
        try {
            return new SetSpawntableFunction(ValueGetter.readValue(DataType.STRING, json));
        } catch(Exception e) {
            HordesLogger.logError("Incorrect parameters for function hordes:set_spawntable", e);
        }
        return null;
    }
}
