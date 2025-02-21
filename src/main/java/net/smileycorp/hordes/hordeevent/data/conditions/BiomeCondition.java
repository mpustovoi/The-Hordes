package net.smileycorp.hordes.hordeevent.data.conditions;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.smileycorp.hordes.common.HordesLogger;
import net.smileycorp.hordes.common.event.HordePlayerEvent;

import java.util.List;

public class BiomeCondition implements Condition {
	
	protected final List<Either<TagKey<Biome>, ResourceLocation>> biomes;
	
	public BiomeCondition(List<Either<TagKey<Biome>, ResourceLocation>> biomes) {
		this.biomes = biomes;
	}

	@Override
	public boolean apply(HordePlayerEvent event) {
		Holder<Biome> biome = event.getLevel().getBiomeManager().getBiome(event.getEntity().blockPosition());
		for (Either<TagKey<Biome>, ResourceLocation> either : biomes) if (either.map(biome::is, biome::is)) return true;
		return false;
	}

	public static BiomeCondition deserialize(JsonElement json) {
		try {
			if (json.isJsonArray()) {
				List<Either<TagKey<Biome>, ResourceLocation>> biomes = Lists.newArrayList();
				for (JsonElement element : json.getAsJsonArray()) biomes.add(either(element.getAsString()));
				return new BiomeCondition(biomes);
			}
			return new BiomeCondition(Lists.newArrayList(either(json.getAsString())));
		} catch(Exception e) {
			HordesLogger.logError("Incorrect parameters for condition hordes:biome", e);
		}
		return null;
	}
	
	private static Either<TagKey<Biome>, ResourceLocation> either(String string) {
		return string.contains("#") ? Either.left(TagKey.create(Registries.BIOME, ResourceLocation.tryParse(string.replace("#", ""))))
				: Either.right(ResourceLocation.tryParse(string));
	}
	
}
