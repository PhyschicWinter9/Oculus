package net.coderbot.iris.texture.mipmap;

import org.jetbrains.annotations.Nullable;

import com.mojang.blaze3d.platform.NativeImage;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public interface CustomMipmapGenerator {
	NativeImage[] generateMipLevels(NativeImage image, int mipLevel);

	public interface Provider {
		@Nullable
		CustomMipmapGenerator getMipmapGenerator(TextureAtlasSprite.Info info, int atlasWidth, int atlasHeight);
	}
}
