package com.github.ItzSwirlz.PretendoSMPMusic;

import com.github.ItzSwirlz.PretendoSMPMusic.soundinstance.SpawnSoundInstance;
import com.github.ItzSwirlz.PretendoSMPMusic.soundinstance.WaraWaraPlazaSoundInstance;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.dimension.DimensionTypes;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.entity.event.api.client.ClientEntityTickCallback;

public class PretendoSMPMusicMod implements ModInitializer {
	public static final WaraWaraPlazaSoundInstance WARAWARAPLAZA_SOUND_INSTANCE = new WaraWaraPlazaSoundInstance();
	public static final SpawnSoundInstance SPAWN_SOUND_INSTANCE = new SpawnSoundInstance();
	@Override
	public void onInitialize(ModContainer mod) {
		ClientEntityTickCallback.EVENT.register((entity, isPassengerTick) -> {
			if(isPassengerTick) return;
			if(entity.isPlayer() && MinecraftClient.getInstance().getCurrentServerEntry() != null && MinecraftClient.getInstance().getCurrentServerEntry().address.equals("survival.pretendo.network")) {
                /* We can actually inject the music into the sound manager.
                   The music manager is an easier way but this will let the
                   music play, even if the music slider is disabled.
                   This will only not play if Ambient/Environment sounds are disabled.

                   The obvious alternative is to just play it via the client world, but
                   that is mostly done for item usage sounds (like tools breaking, sharing,
                   cows; not music that is not to be used with the game's main music but
                   only for certain scenarios). Thus, those sounds don't have a SoundInstance
                   and don't require a need to have the sound manager queued to stop the sound
                   However, we do.

                   In order to do this, each sound gets its own Instance, where then game
                   can play the audio in its sound manager.

                   In terms of fading in and out, this will probably require
                   progressively lowering the volume of the audio.
                */
				if(entity.world.getDimensionKey().equals(DimensionTypes.OVERWORLD)) {
					if((-200.0 <= entity.getX() && entity.getX() <= -15.0) && (-115.0 <= entity.getZ() && entity.getZ() <= 150.0) && entity.getY() >= 70) {
						if(!MinecraftClient.getInstance().getSoundManager().isPlaying(WARAWARAPLAZA_SOUND_INSTANCE)) {
							MinecraftClient.getInstance().getSoundManager().play(WARAWARAPLAZA_SOUND_INSTANCE);
						}
					} else {
						if (MinecraftClient.getInstance().getSoundManager().isPlaying(WARAWARAPLAZA_SOUND_INSTANCE)) {
							MinecraftClient.getInstance().getSoundManager().stop(WARAWARAPLAZA_SOUND_INSTANCE);
						}
					}

					if(entity.squaredDistanceTo(0, 96, 0) <= 15.0 && entity.getY() >= 90.0) {
						if (!MinecraftClient.getInstance().getSoundManager().isPlaying(SPAWN_SOUND_INSTANCE)) {
							MinecraftClient.getInstance().getSoundManager().play(SPAWN_SOUND_INSTANCE);
						}
					} else {
						if(MinecraftClient.getInstance().getSoundManager().isPlaying(SPAWN_SOUND_INSTANCE)) {
							MinecraftClient.getInstance().getSoundManager().stop(SPAWN_SOUND_INSTANCE);
						}
					}
				}
			}
		});
	}
}
