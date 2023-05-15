package com.github.ItzSwirlz.PretendoSMPMusic.soundinstance;

import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

// Based on BiomeEffectSoundPlayer's MusicLoop
public class SpawnSoundInstance extends MovingSoundInstance {
    public SpawnSoundInstance() {
        // Initialize sounds here
        super(SoundEvent.createVariableRangeEvent(new Identifier("pretendosmpmusic:music.spawn")), SoundCategory.AMBIENT, SoundInstance.createRandom());
    }

    @Override
    public void tick() {
        // nothing needs to be done here, game will do what it needs to
        // maybe for a fade out/fade in mechanism this might be required
        // but the main loop in the main mod class can probably find some
        // way to progressively set the volume lower
    }
}
