package by.android.cradle.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class MyPacker {
    public static void main (String[] args) throws Exception {
        //TexturePacker.process("android/assets/solareffect", "android/assets/solareffect_atlas", "solareffectatlas");
        TexturePacker.process("android/assets/flag_red01", "android/assets/flags_atlas", "flag_redatlas");

    }
}