package by.android.cradle;

import by.android.cradle.BaseGame;

public class CradleGame extends BaseGame
{
    public void create()
    {
        super.create();

        setActiveScreen( new ScreenGamePlay() );
    }
}
