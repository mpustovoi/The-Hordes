package net.smileycorp.hordes.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.smileycorp.hordes.common.mixinutils.ChatName;
import net.smileycorp.hordes.common.mixinutils.CustomTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity implements CustomTexture {

    private static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.STRING);

    public MixinLivingEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Override
    public void setTexture(ResourceLocation texture) {
        entityData.set(TEXTURE, texture.toString());
    }

    @Override
    public ResourceLocation getTexture() {
        return ResourceLocation.tryParse(entityData.get(TEXTURE));
    }

    @Override
    public boolean hasCustomTexture() {
        return !entityData.get(TEXTURE).isEmpty();
    }

    @Inject(at=@At("HEAD"), method = "defineSynchedData")
    public void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo callback){
        builder.define(TEXTURE, "");
    }

    @Inject(at=@At("HEAD"), method = "addAdditionalSaveData")
    public void addAdditionalSaveData(CompoundTag tag, CallbackInfo callback) {
        if (hasCustomTexture()) tag.putString("texture", entityData.get(TEXTURE));
        if (((ChatName)this).hasChatName()) tag.putString("chat_name", ((ChatName)this).getChatName());
    }

    @Inject(at=@At("HEAD"), method = "readAdditionalSaveData")
    public void readAdditionalSaveData(CompoundTag tag, CallbackInfo callback) {
        if (tag.contains("texture")) {
            String texture = tag.getString("texture");
            if (ResourceLocation.tryParse(texture) != null) entityData.set(TEXTURE, texture);
        }
        if (tag.contains("chat_name")) ((ChatName)this).setChatName(tag.getString("chat_name"));
    }

}
