package com.thebroman10.medievalprogression.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import net.minecraft.client.Minecraft;


public class ModConfig {

    private static final Gson GSON =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();


    private static final File FILE =
            new File(
                    Minecraft.getInstance()
                            .gameDirectory,
                    "config/medievalprogression.json"
            );



    public double mapDragSpeed = 0.8;


    public int minimapSize = 120;


    public double minimapZoom = 0.1;


    public String minimapSide = "right";


    public boolean minimapBorder = true;


    public int minimapBorderSize = 8;



    private static ModConfig INSTANCE;



    public static ModConfig get() {

        if (INSTANCE == null) {

            load();

        }

        return INSTANCE;

    }



    public static void load() {

        try {

            if (!FILE.exists()) {

                FILE.getParentFile().mkdirs();

                INSTANCE = new ModConfig();

                save();

                return;

            }



            FileReader reader =
                    new FileReader(FILE);



            INSTANCE =
                    GSON.fromJson(
                            reader,
                            ModConfig.class
                    );



            reader.close();



            if (INSTANCE == null) {

                INSTANCE = new ModConfig();

            }


        } catch (Exception e) {

            e.printStackTrace();

            INSTANCE = new ModConfig();

        }

    }



    public static void save() {

        try {

            FILE.getParentFile().mkdirs();


            FileWriter writer =
                    new FileWriter(FILE);



            GSON.toJson(
                    INSTANCE,
                    writer
            );


            writer.close();


        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}