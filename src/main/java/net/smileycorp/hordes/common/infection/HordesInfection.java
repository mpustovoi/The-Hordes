package net.smileycorp.hordes.common.infection;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.smileycorp.hordes.common.Constants;
import net.smileycorp.hordes.common.entities.DrownedPlayer;
import net.smileycorp.hordes.common.entities.ZombiePlayer;

public class HordesInfection {

	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Constants.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Constants.MODID);

	public static final TagKey<EntityType<?>> INFECTION_ENTITIES_TAG = TagKey.create(Registries.f_256939_, Constants.loc("infection_entities"));
	public static final TagKey<Item> INFECTION_CURES_TAG = TagKey.create(Registries.f_256913_, Constants.loc("infection_cures"));

	public static final RegistryObject<MobEffect> INFECTED = EFFECTS.register("infected", () -> new InfectedEffect());

	public static final RegistryObject<EntityType<ZombiePlayer>> ZOMBIE_PLAYER = ENTITIES.register("zombie_player", () ->
	EntityType.Builder.<ZombiePlayer>of(ZombiePlayer::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8).build("zombie_player"));
	public static final RegistryObject<EntityType<DrownedPlayer>> DROWNED_PLAYER = ENTITIES.register("drowned_player", () ->
	EntityType.Builder.<DrownedPlayer>of(DrownedPlayer::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(8).build("drowned_player"));

	public static final ResourceKey<DamageType> INFECTION_DAMAGE = ResourceKey.create(Registries.f_268580_, Constants.loc("infection"));

	public static DamageSource getInfectionDamage(Entity entity) {
		return new DamageSource(entity.m_269291_().f_268645_.m_246971_(INFECTION_DAMAGE));
	}


}
