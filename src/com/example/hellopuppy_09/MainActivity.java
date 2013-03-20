package com.example.hellopuppy_09;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.input.sensor.location.ILocationListener;
import org.andengine.input.sensor.location.LocationProviderStatus;
import org.andengine.input.sensor.location.LocationSensorOptions;
import org.andengine.input.sensor.orientation.IOrientationListener;
import org.andengine.input.sensor.orientation.OrientationData;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.GameScene.GameSceneManager;
import com.example.GameScene.GameSceneType;
import com.example.GameScene.SoundType;

public class MainActivity extends BaseGameActivity implements IOrientationListener, ILocationListener{

	// Constants
	public static final int CAMERA_WIDTH = 800;
    public static final int CAMERA_HEIGHT = 480;
    public static double pLatitude;
    public static double pLongitude;
    
    private SmoothCamera mCamera;
    private GameSceneManager mGameManager;

    private Location mLocation;				// 註: 要去manifest 開啟網路跟定位權限
    private double mLatitude;
    private double mLongitude;
    
    private static Music mMusic;
    public static Sound barkSound1, barkSound2, clickSound, 
    		carDriveSound, parkBirds;
    
	@Override
	public EngineOptions onCreateEngineOptions() {
		
		Debug.e("=== onCreateEngineOptions()===");		
    	this.mCamera = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, 500, 500, 5);
    	 
        /* EngineOptions()
         * full screen = true
         * Screen Orientation = LandScape 
         * Ratio Resolution Policy
         * */
        EngineOptions engineOptions = new EngineOptions(
        		true,													 
        		ScreenOrientation.LANDSCAPE_FIXED, 
        		new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), 
        		this.mCamera);
        engineOptions.getAudioOptions().setNeedsMusic(true);
        engineOptions.getAudioOptions().setNeedsSound(true);
        this.initialLocation();
        return engineOptions;
	}

	@Override
	public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		
		Debug.e("=== onCreateResources()===");
		// 載入背景音樂 與音效
	    try{
		      mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this,
		    		  "mfx/backgroundMusic.ogg");
		      mMusic.setLooping(true);
		      barkSound1 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), 
						this, "mfx/bark.ogg");
		      barkSound2 = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), 
						this, "mfx/bark2.ogg");
		      clickSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), 
						this, "mfx/clicking.ogg");
		      carDriveSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), 
						this, "mfx/carDrive.ogg");
		      parkBirds = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), 
						this, "mfx/parkBird.ogg");
		    }catch (IOException e){
		      e.printStackTrace();
		}	    
	    
		// 載入完成, finished
    	pOnCreateResourcesCallback.onCreateResourcesFinished();
		
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		
		Debug.e("=== onCreateScene()===");		
		
		// initial bg Music
		this.playbgMusic();
		
		mGameManager = new GameSceneManager(this, mCamera);
		mGameManager.creatingScene(GameSceneType.SPLASH);
			
		// finished 
        pOnCreateSceneCallback.onCreateSceneFinished(mGameManager.getCurrentScene());
	}

	@Override
	public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
	
		Debug.e("=== onPopulateScene()===");
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	
	// 遊戲暫停
	@Override
	public synchronized void onPauseGame() {
		
		super.onPauseGame();
		Debug.e("=== onPauseGame()===");
		this.mEngine.disableLocationSensor(this);
		this.mEngine.disableOrientationSensor(this);
		this.finish();
		System.exit(0);
	}
	
	@Override
	public synchronized void onResumeGame() {
	
		super.onResumeGame();
		// 啟動 Orientation Sensor
		this.enableOrientationSensor(this);

		final LocationSensorOptions locationSensorOptions = new LocationSensorOptions();
		locationSensorOptions.setAccuracy(Criteria.ACCURACY_COARSE);
		locationSensorOptions.setMinimumTriggerTime(0);
		locationSensorOptions.setMinimumTriggerDistance(0);
		
		// 啟動 Location Sensor
		this.enableLocationSensor(this, locationSensorOptions);
	}
	
	// 自訂義方法
	private void initialLocation()
	{
		// 取得GPS System 服務
		this.mLocation = new Location(LocationManager.GPS_PROVIDER);
		mLatitude = this.mLocation.getLatitude();
		mLongitude = this.mLocation.getLongitude();
		Debug.e("(mLatitude, mLongitude)=" + mLatitude + "," + mLongitude);
	}
		
	// 按下Back 鍵, 跳回上一個Scene
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){  	
		
		// 如果KeyCode == Back 鍵, 並且被按下
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{	
			switch (GameSceneManager.mSceneType){
		    	
				case TITLE:
					// 離開遊戲
					this.mMusic.stop();
					this.finish();
					System.exit(0);					
					break;
					
				case HOME:
					// 返回TITLE 頁面
					mGameManager.creatingScene(GameSceneType.TITLE);
					break;		
					
				case CREATING_PET:
					mGameManager.creatingScene(GameSceneType.HOME);
					break;
					
				case STORY:
					mGameManager.creatingScene(GameSceneType.HOME);			
					break;
					
				case OUTDOOR:
					mGameManager.creatingScene(GameSceneType.HOME);	
					break;
				
				case WEBPAGE:
					mGameManager.creatingScene(GameSceneType.OUTDOOR);
					break;
			}
			this.mCamera.getHUD().setVisible(true);
		}
		return false; 
	}


	public static void stopbgMusic(){
		mMusic.pause();
	}
	
	public static void playbgMusic(){
		mMusic.play();
	}
	
	public static void playSound(SoundType pSoundType){
		
		switch(pSoundType){	
		case BARK_START:
			barkSound1.play();
			break;
			
		case BARK_STORY:
			barkSound2.play();
			break;
			
		case CAR_DRIVE:
			carDriveSound.play();
			break;
			
		case PARK_BIRDS:
			parkBirds.play();
			break;
			
		case CLICKING:
			clickSound.play();
			break;
		
		}
	}

	public void onLocationProviderEnabled() {
		// TODO Auto-generated method stub
		
	}

	public void onLocationChanged(Location pLocation) {
		this.mLocation = pLocation;
		mLatitude = this.mLocation.getLatitude();
		mLongitude = this.mLocation.getLongitude();
		this.pLatitude = this.mLatitude;
		this.pLongitude = this.mLongitude;
		Debug.e("LocationChanged(mLatitude, mLongitude)=" + mLatitude + "," + mLongitude);
	}

	public void onLocationLost() {
	}

	public void onLocationProviderDisabled() {
	}

	public void onLocationProviderStatusChanged(
			LocationProviderStatus pLocationProviderStatus, Bundle pBundle) {
	}

	public void onOrientationAccuracyChanged(OrientationData pOrientationData) {
	}

	public void onOrientationChanged(OrientationData pOrientationData) {
	}
}
