package com.xkings.pokemontd;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.xkings.core.utils.ParamHolder;

import java.io.File;

import static com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;
import static com.badlogic.gdx.tools.imagepacker.TexturePacker2.process;

public class Main {
    public static void main(String[] args) {
        Settings texturePackerSettings = new Settings();
        texturePackerSettings.edgePadding = false;
        texturePackerSettings.combineSubdirectories = true;

        String source = new File(".").getAbsoluteFile().getParentFile().getParentFile().getParentFile().toString() +
                "/unprocessed/textures/";
        String destination = new File(".").toString() + "/data/textures/";
        process(texturePackerSettings, source, destination, "packed");

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "PokemonTD";
        cfg.useGL20 = true;
        cfg.width = 800;
        cfg.height = 640;
        if (new ParamHolder(args).getParam("-fullscreen") != null) {
            cfg.fullscreen = true;
            cfg.width = 2560;
            cfg.height = 1440;
            //cfg.fullscreen = true;
        }

        new LwjglApplication(new App(args), cfg);
    }
}
