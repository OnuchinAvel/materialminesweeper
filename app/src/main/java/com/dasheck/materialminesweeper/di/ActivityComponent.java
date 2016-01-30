package com.dasheck.materialminesweeper.di;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import com.dasheck.materialminesweeper.activities.BaseActivity;
import com.dasheck.materialminesweeper.activities.Navigator;
import com.dasheck.materialminesweeper.annotations.PerActivity;
import com.dasheck.model.controllers.GameTimeController;
import com.dasheck.model.datastores.FieldDatastore;
import com.dasheck.model.datastores.GameModeDatastore;
import dagger.Component;

/**
 * Created by s.neidig on 17/01/16.
 */
@PerActivity @Component(modules = {
    AcitivityModule.class, TransformerModule.class, DatastoresModule.class, ControllerModule.class
}, dependencies = ApplicationComponent.class) public interface ActivityComponent {

  BaseActivity baseActivity();

  Context context();

  Navigator navigator();

  FragmentManager supportFragmentManager();

  /* Transformer */

  /* Datastores */

  FieldDatastore fieldDatastore();

  GameModeDatastore gameModeDatastore();

  /* Controllers */

  GameTimeController gameTimeController();
}
