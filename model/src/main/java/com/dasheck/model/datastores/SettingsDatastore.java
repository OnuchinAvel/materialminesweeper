package com.dasheck.model.datastores;

import com.dasheck.model.models.BackgroundMusic;
import com.dasheck.model.models.User;
import java.util.List;
import rx.Observable;

/**
 * @author Stefan Neidig
 */
public interface SettingsDatastore {

  Observable<Boolean> isVibrationEnabled();

  Observable<Void> enableVibration(boolean enabled);

  Observable<List<BackgroundMusic>> getAvailableBackgroundMusic();

  Observable<BackgroundMusic> getSelectedBackgroundMusic();

  Observable<Void> selectBackgroundMusic(String title);

  Observable<Boolean> isSoundEnabled();

  Observable<Void> enableSound(boolean enabled);

  Observable<Void> setVolume(float volume);

  Observable<Float> getVolume();

  Observable<Boolean> isFirstStart();

  Observable<Void> setFirstStart(boolean firstStart);

  Observable<User> getDeviceUser();

  Observable<Void> updateDeviceUser(User user);
}
