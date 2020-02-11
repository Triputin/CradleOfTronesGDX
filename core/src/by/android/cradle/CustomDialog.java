package by.android.cradle;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CustomDialog extends Dialog {

    private float dialog_width;
    private float dialog_height;

    public CustomDialog(String title, Skin skin, float width, float height) {
        super(title, skin);
        dialog_width = width;
        dialog_height = height;


    }
    @Override
    public float getPrefWidth() {
        return dialog_width;
    }

    @Override
    public float getPrefHeight() {
        return dialog_height;
    }
}
