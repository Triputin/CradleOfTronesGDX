package by.android.cradle;

import by.android.cradle.BaseGame;

public class CradleGame extends BaseGame
{
    private MenuScreen menuScreen;
    private GameMapScreen gameMapScreen;
    private ScreenGamePlay screenGamePlay;
    private GameRes gameRes;

    public void create()
    {
        super.create();
        gameRes = new GameRes();
        GameRes.Bread=100;
        GameRes.Wood=100;
        GameRes.Gold=50;

        menuScreen = new MenuScreen(this);
        screenGamePlay = new ScreenGamePlay(this);
        gameMapScreen = new GameMapScreen(this);
        setActiveScreen( menuScreen );
    }


    public GameRes getGameRes() {
        return gameRes;
    }

    public void setGameRes(GameRes gameRes) {
        this.gameRes = gameRes;
    }
    public MenuScreen getMenuScreen() {
        return menuScreen;
    }

    public void setMenuScreen(MenuScreen menuScreen) {
        this.menuScreen = menuScreen;
    }

    public GameMapScreen getGameMapScreen() {
        return gameMapScreen;
    }

    public void setGameMapScreen(GameMapScreen gameMapScreen) {
        this.gameMapScreen = gameMapScreen;
    }

    public ScreenGamePlay getScreenGamePlay() {
        return screenGamePlay;
    }

    public void setScreenGamePlay(ScreenGamePlay screenGamePlay) {
        this.screenGamePlay = screenGamePlay;
    }

    public static void setActiveScreen(BaseScreen s)
    {
        game.setScreen(s);
    }


    public  void setActiveMenuScreen()
    {
        System.out.println("setActiveMenuScreen()");
        game.setScreen(menuScreen);
        gameMapScreen.PauseMusic();
        menuScreen.PlayMusic();
    }

    public  void setActiveGameMapScreen()
    {
        System.out.println("setActiveGameMapScreen");
        game.setScreen(gameMapScreen);
        gameMapScreen.UpdateRes();
        screenGamePlay.setPaused(true);
        menuScreen.PauseMusic();
        gameMapScreen.PlayMusic();
        gameMapScreen.SetMessageActorVisibility(false);
    }

    public  void setActivescreenGamePlay()
    {
        System.out.println("setActivescreenGamePlay()");
        game.setScreen(screenGamePlay);
        screenGamePlay.StartNewLevel(1);
        menuScreen.PauseMusic();
        gameMapScreen.PauseMusic();

    }
}
