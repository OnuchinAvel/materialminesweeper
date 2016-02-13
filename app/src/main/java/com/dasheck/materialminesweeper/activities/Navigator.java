package com.dasheck.materialminesweeper.activities;

import android.support.v4.app.Fragment;
import com.dasheck.materialminesweeper.fragments.game.DaggerGameComponent;
import com.dasheck.materialminesweeper.fragments.game.GameComponent;
import com.dasheck.materialminesweeper.fragments.game.GameFragment;
import com.dasheck.materialminesweeper.fragments.game.GameModule;
import com.dasheck.materialminesweeper.fragments.game.GamePresenterImpl;
import com.dasheck.materialminesweeper.fragments.gamemenu_item.DaggerGameMenuItemComponent;
import com.dasheck.materialminesweeper.fragments.gamemenu_item.GameMenuItemComponent;
import com.dasheck.materialminesweeper.fragments.gamemenu_item.GameMenuItemFragment;
import com.dasheck.materialminesweeper.fragments.gamemenu_item.GameMenuItemModule;
import com.dasheck.materialminesweeper.fragments.gamemenu_item.GameMenuItemPresenterImpl;
import com.dasheck.materialminesweeper.fragments.help.HelpFragment;
import com.dasheck.materialminesweeper.fragments.history.DaggerHistoryComponent;
import com.dasheck.materialminesweeper.fragments.history.HistoryComponent;
import com.dasheck.materialminesweeper.fragments.history.HistoryFragment;
import com.dasheck.materialminesweeper.fragments.history.HistoryModule;
import com.dasheck.materialminesweeper.fragments.history.HistoryPresenterImpl;
import com.dasheck.materialminesweeper.fragments.menu.DaggerMenuComponent;
import com.dasheck.materialminesweeper.fragments.menu.MenuComponent;
import com.dasheck.materialminesweeper.fragments.menu.MenuFragment;
import com.dasheck.materialminesweeper.fragments.menu.MenuModule;
import com.dasheck.materialminesweeper.fragments.menu.MenuPresenterImpl;
import com.dasheck.materialminesweeper.fragments.settings.DaggerSettingsComponent;
import com.dasheck.materialminesweeper.fragments.settings.SettingsComponent;
import com.dasheck.materialminesweeper.fragments.settings.SettingsFragment;
import com.dasheck.materialminesweeper.fragments.settings.SettingsModule;
import com.dasheck.materialminesweeper.fragments.settings.SettingsPresenterImpl;
import com.dasheck.model.models.Configuration;
import com.dasheck.model.models.GameMode;
import timber.log.Timber;

/**
 * Created by s.neidig on 17/01/16.
 */
public class Navigator {

  private BaseActivity baseActivity;

  public Navigator(BaseActivity baseActivity) {
    this.baseActivity = baseActivity;
  }

  public void showMenu() {
    MenuFragment fragment = new MenuFragment();
    MenuPresenterImpl presenter = new MenuPresenterImpl();

    MenuComponent component = DaggerMenuComponent.builder()
        .activityComponent(baseActivity.getActivityComponent())
        .menuModule(new MenuModule(fragment, presenter))
        .build();

    component.inject(fragment);
    component.inject(presenter);

    transist(fragment);
  }

  public void showGame(Configuration configuration) {
    GameFragment fragment = new GameFragment();
    GamePresenterImpl presenter = new GamePresenterImpl();
    presenter.setConfiguration(configuration);

    GameComponent component = DaggerGameComponent.builder()
        .activityComponent(baseActivity.getActivityComponent())
        .gameModule(new GameModule(fragment, presenter))
        .build();

    component.inject(fragment);
    component.inject(presenter);

    transist(fragment);
  }

  public GameMenuItemFragment createGameMenuItem(GameMode gameMode) {
    GameMenuItemFragment fragment = new GameMenuItemFragment();
    GameMenuItemPresenterImpl presenter = new GameMenuItemPresenterImpl();
    presenter.setGameMode(gameMode);

    GameMenuItemComponent component = DaggerGameMenuItemComponent.builder()
        .activityComponent(baseActivity.getActivityComponent())
        .gameMenuItemModule(new GameMenuItemModule(fragment, presenter))
        .build();

    component.inject(fragment);
    component.inject(presenter);

    return fragment;
  }

  public void showSettings() {
    SettingsFragment fragment = new SettingsFragment();
    SettingsPresenterImpl presenter = new SettingsPresenterImpl();

    SettingsComponent component = DaggerSettingsComponent.builder()
        .activityComponent(baseActivity.getActivityComponent())
        .settingsModule(new SettingsModule(fragment, presenter))
        .build();

    component.inject(fragment);
    component.inject(presenter);

    transist(fragment);
  }

  public void showHistory() {
    HistoryFragment fragment = new HistoryFragment();
    HistoryPresenterImpl presenter = new HistoryPresenterImpl();

    HistoryComponent component = DaggerHistoryComponent.builder()
        .activityComponent(baseActivity.getActivityComponent())
        .historyModule(new HistoryModule(fragment, presenter))
        .build();

    component.inject(fragment);
    component.inject(presenter);

    transist(fragment);
  }

  // TODO: 26/01/16 Write an awesome transition controller supporting shared element transition

  private void transist(Fragment fragment) {
    baseActivity.getSupportFragmentManager()
        .beginTransaction()
        .replace(baseActivity.getFragmentContainerId(), fragment, fragment.getClass().getName())
        .addToBackStack(fragment.getClass().getName())
        .commit();
  }

  public void showHelp() {
    HelpFragment fragment = new HelpFragment();
    fragment.setNavigator(this);
    transist(fragment);
  }
}
