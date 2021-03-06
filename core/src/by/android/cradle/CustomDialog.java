package by.android.cradle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

public class CustomDialog extends Dialog {

    private float dialog_width;
    private float dialog_height;

    public CustomDialog(String title, Skin skin, float width, float height) {
        super(title, skin);
        dialog_width = width;
        dialog_height = height;
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();

        if (w>1000) {
            getButtonTable().defaults().height(height * 0.3f);
        } else
        {
            getButtonTable().defaults().height(height * 0.5f);
        }

        getButtonTable().defaults().width(width * 0.4f);

        getContentTable().defaults().width(dialog_width);
        //setBackground(new Image(new Texture( Gdx.files.internal("marble.jpg") )).getDrawable());
    }

    @Override
    public float getPrefWidth() {
        return dialog_width;
    }

    @Override
    public float getPrefHeight() {
        return dialog_height;
    }

    @Override
    public CustomDialog text(String text) {
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        /*
        float fontScale = 1.0f;
        if (w>1000){
            fontScale = 1.5f;
        } else {
            fontScale = 0.6f;
        }
        */

        Label dialogLabel = new Label(text, BaseGame.labelStyle_SuperSmall);
        dialogLabel.setWrap(true);
        dialogLabel.setAlignment( Align.center );
        dialogLabel.setWidth( dialog_width);
        //dialogLabel.setFontScale(fontScale);
        dialogLabel.setColor(Color.GOLD);
        text(dialogLabel);
        return this;
    }
}
