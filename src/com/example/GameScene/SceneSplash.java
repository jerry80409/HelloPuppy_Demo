package com.example.GameScene;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

public class SceneSplash extends GameScene implements IScene {
	
	// Splash Icon Resource
	private BitmapTextureAtlas splash_Space;
	private TextureRegion splash_TextureRegion;
	private Sprite splash; 
	
	// Constructor	
	public SceneSplash(BaseGameActivity pActivity, SmoothCamera pCamera) {
				
		super(pActivity, pCamera);
		Debug.e("SceneSplash");
	}
	
	
	public void onCreateResources() {
	
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splash_Space = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 256, 256, 
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
    	splash_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(splash_Space, 
    			this.mActivity, "01_splash.png", 0, 0);		    			
    	splash_Space.load();
	}


	public void onCreateScene() {
		
		final float cX = (camera_W - splash_TextureRegion.getWidth()) *0.5f;
		final float cY = (camera_H - splash_TextureRegion.getHeight()) *0.5f;
		splash = new Sprite(cX, cY, splash_TextureRegion, this.mActivity.getVertexBufferObjectManager());
		this.attachChild(splash);
	}


	public void registerUpdateHandler() {
	
		this.registerUpdateHandler(new TimerHandler(3.0f, true, new ITimerCallback(){

			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				SceneSplash.this.unregisterUpdateHandler(pTimerHandler);
								
				// ³õ´º¤Á´« Title Scene
				GameSceneManager gm = new GameSceneManager(mActivity, mCamera);
				gm.creatingScene(GameSceneType.TITLE);
			}
			
		}
		));
	}
	




}
