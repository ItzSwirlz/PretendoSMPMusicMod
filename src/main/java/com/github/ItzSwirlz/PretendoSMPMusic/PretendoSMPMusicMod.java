package com.github.ItzSwirlz.PretendoSMPMusic;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod(PretendoSMPMusicMod.MODID)
public class PretendoSMPMusicMod
{
    public static final String MODID = "pretendosmpmusic";

    private static final DeferredRegister<SoundEvent> SOUNDEVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);
    public static final RegistryObject<SoundEvent> WARAWARAPLAZA = SOUNDEVENTS.register("ambient.warawaraplaza", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "ambient.warawaraplaza")));

    public PretendoSMPMusicMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        SOUNDEVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void commonSetup(final FMLCommonSetupEvent event)
    {
        SOUNDEVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientStart(FMLClientSetupEvent event) {
            SOUNDEVENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        }
        @SubscribeEvent
        public static void onPlayerEvent(TickEvent.PlayerTickEvent event) {
            if(Minecraft.getInstance().getCurrentServer() != null && Minecraft.getInstance().getCurrentServer().ip.equals("survival.pretendo.network")) {
                /* We can actually inject the music into the music manager.
                   The 'true' statement means this *will replace currently playing music*
                   This also means the sounds can be adjusted.
                   Additionally, if the sound volume changes in settings, so will the music.
                   Ideally I would make this ambient but I don't feel like digging through the SoundInstance

                   One more thing - the Music can't be initialized as its own variable, for some reason MC crashes
                   unless the Music is being registered when trying to also start playing music. Probably because
                   of the main menu music, the game trying to play a sound that may not have been registered.
                */

                /*  ------------------------------------
                              Wara Wara Plaza
                    ------------------------------------ */
                if((-200.0 <= event.player.getX() && event.player.getX() <= -15.0) && (-115.0 <= event.player.getZ() && event.player.getZ() <= 150.0) && event.player.getY() >= 70) {
                    if (!Minecraft.getInstance().getMusicManager().isPlayingMusic(new Music(WARAWARAPLAZA.getHolder().get(), 0, 0, true))) {
                        Minecraft.getInstance().getMusicManager().startPlaying(new Music(WARAWARAPLAZA.getHolder().get(), 0, 0, true));
                    }
                } else {
                    if (Minecraft.getInstance().getMusicManager().isPlayingMusic(new Music(WARAWARAPLAZA.getHolder().get(), 0, 0, true))) {
                        Minecraft.getInstance().getMusicManager().stopPlaying();
                    }
                }
            }
        }
    }
}
