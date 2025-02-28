package net.coderbot.iris.mixin.texture;

import java.nio.IntBuffer;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;

import net.coderbot.iris.texture.TextureInfoCache;
import net.coderbot.iris.texture.TextureTracker;
import net.coderbot.iris.texture.pbr.PBRTextureManager;

@Mixin(GlStateManager.class)
public class MixinGlStateManager {
	@Inject(method = "_bindTexture(I)V", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glBindTexture(II)V", shift = Shift.AFTER, remap = false))
	private static void iris$onBindTexture(int id, CallbackInfo ci) {
		TextureTracker.INSTANCE.onBindTexture(id);
	}

	@Inject(method = "_texImage2D(IIIIIIIILjava/nio/IntBuffer;)V", at = @At("TAIL"))
	private static void iris$onTexImage2D(int target, int level, int internalformat, int width, int height, int border,
										  int format, int type, @Nullable IntBuffer pixels, CallbackInfo ci) {
		TextureInfoCache.INSTANCE.onTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
	}

	@Inject(method = "_deleteTexture(I)V", at = @At("TAIL"))
	private static void iris$onDeleteTexture(int id, CallbackInfo ci) {
		iris$onDeleteTexture(id);
	}

	@Inject(method = "_deleteTextures([I)V", at = @At("TAIL"))
	private static void iris$onDeleteTextures(int[] ids, CallbackInfo ci) {
		for (int id : ids) {
			iris$onDeleteTexture(id);
		}
	}

	@Unique
	private static void iris$onDeleteTexture(int id) {
		TextureTracker.INSTANCE.onDeleteTexture(id);
		TextureInfoCache.INSTANCE.onDeleteTexture(id);
		PBRTextureManager.INSTANCE.onDeleteTexture(id);
	}
}
