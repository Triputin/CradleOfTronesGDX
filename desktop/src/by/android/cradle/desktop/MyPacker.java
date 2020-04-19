package by.android.cradle.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class MyPacker {
    public static void main (String[] args) throws Exception {
        TexturePacker.process("android/assets/solareffect", "android/assets/solareffect_atlas", "solareffectatlas");
    }
}