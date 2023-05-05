package com.github.ItzSwirlz.PretendoSMPMusic;

import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Holder;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.entity.event.api.client.ClientEntityTickCallback;

public class PretendoSMPMusicMod implements ModInitializer {
	public static final SoundEvent WARAWARAPLAZA = SoundEvent.createVariableRangeEvent(new Identifier("pretendosmpmusic:music.warawaraplaza"));
	public static final Holder<SoundEvent> WARAWARAPLAZA_MUSIC = Holder.createDirect(WARAWARAPLAZA);
	@Override
	public void onInitialize(ModContainer mod) {
		ClientEntityTickCallback.EVENT.register((entity, isPassengerTick) -> {
			if(isPassengerTick) return;
			if(entity.isPlayer() && MinecraftClient.getInstance().getCurrentServerEntry() != null && MinecraftClient.getInstance().getCurrentServerEntry().address.equals("survival.pretendo.network")) {
                /* We can actually inject the music into the music manager.
                   The 'true' statement means this *will replace currently playing music*
                   This also means the sounds can be adjusted.
                   Additionally, if the sound volume changes in settings, so will the music.
                   Ideally I would make this music but I don't feel like digging through the SoundInstance

                   Something weird is that when detecting the music, I can't use isPlayingType() on the
                   MusicSound if its registered before hand. It's fine with the handler, but having a predefined
                   MusicSound does nothing and the music tracker doesn't recognize it. Maybe its different in memory?
                */

                /*  ------------------------------------
                              Wara Wara Plaza
                    ------------------------------------ */
				if(entity.isPlayer() && (-200.0 <= entity.getX() && entity.getX() <= -15.0) && (-115.0 <= entity.getZ() && entity.getZ() <= 150.0) && entity.getY() >= 70) {
					if (!MinecraftClient.getInstance().getMusicTracker().isPlayingType(new MusicSound(WARAWARAPLAZA_MUSIC, 0, 0, true))) {
						MinecraftClient.getInstance().getMusicTracker().play(new MusicSound(WARAWARAPLAZA_MUSIC, 0, 0, true));
					}
				} else {
					if (MinecraftClient.getInstance().getMusicTracker().isPlayingType(new MusicSound(WARAWARAPLAZA_MUSIC, 0, 0, true))) {
						MinecraftClient.getInstance().getMusicTracker().stop();
					}
				}
			}
		});
	}
}
