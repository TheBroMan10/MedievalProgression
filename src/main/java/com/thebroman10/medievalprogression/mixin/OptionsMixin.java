package com.thebroman10.medievalprogression.mixin;

import com.thebroman10.medievalprogression.client.ModKeybinds;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Options.class)
public class OptionsMixin {


    @Shadow
    @Final
    public KeyMapping[] keyMappings;


    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void addModKeybind(CallbackInfo ci) {

        KeyMapping[] old = this.keyMappings;

        KeyMapping[] updated =
                new KeyMapping[old.length + 1];


        System.arraycopy(
                old,
                0,
                updated,
                0,
                old.length
        );


        updated[old.length] =
                ModKeybinds.OPEN_MAP;


        this.keyMappings = updated;
    }
}