package com.example.GameScene;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.ease.EaseLinear;

import com.example.hellopuppy_09.MainActivity;

import android.widget.Toast;

public class SceneOutdoor extends GameScene implements IScene {

	// *Background Resource 
	private BitmapTextureAtlas bg_Space;
	private TextureRegion bg_TextureRegion;
	
	private TiledTextureRegion playerPOI_TextureRegion;
	private TextureRegion radar_TextureRegion;

	// talk & social button
	private TiledTextureRegion talk_TextureRegion, 
				email_TextureRegion, fb_TextureRegion, twitter_TextureRegion;
	
	// *Menu Resource
	private MenuOutdoor mMenuScene;	    
	
	public SceneOutdoor(BaseGameActivity pActivity, SmoothCamera pCamera) {
		super(pActivity, pCamera);
		Debug.e("SceneOutdoor");
	}

	public void onCreateResources(){
				
		// background
		bg_Space = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024, 
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		bg_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space, 
				this.mActivity, "05_outdoor_bg.jpg", 0, 0);
		
		// player POI
		playerPOI_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "05_player_poi.png", 800, 0, 2, 2);
		// radar
		radar_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space, 
				this.mActivity, "05_radar.png", 0, 480);
		
		talk_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "05_talk_btn.png", 240, 480, 1, 2);
		
		email_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "05_mail_btn.png", 851, 116, 1, 2);
		
		fb_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "05_fb_btn.png", 902, 116, 1, 2);
		
		twitter_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "05_twitter_btn.png", 953, 116, 1, 2);
		bg_Space.load();
	}
	
	public void onCreateScene(){
		
		VertexBufferObjectManager mBufferObjectManager = this.mActivity.getVertexBufferObjectManager();
		
		// 建立Background
		Sprite outdoor_bg = new Sprite(0, 0, bg_TextureRegion, mBufferObjectManager);
		this.attachChild(outdoor_bg);
		
		// * Button
		this.createButton(mBufferObjectManager);
		
		// radar
		float _radarX = 575 - radar_TextureRegion.getWidth();
		float _radarY = 240 - radar_TextureRegion.getHeight();
		
		Sprite radar = new Sprite(_radarX, _radarY, radar_TextureRegion, mBufferObjectManager);
		radar.setColor(0.2f, 0.6f, 0.8f, 0.8f);
		radar.setRotationCenter(radar.getWidth(), radar.getHeight());		
		radar.registerEntityModifier(new LoopEntityModifier(new RotationModifier(3, 0, 360, EaseLinear.getInstance())));
		this.attachChild(radar);

		// Menu Scene
		mMenuScene = new MenuOutdoor(mActivity, mCamera);
	
		// Map Tag
		this.initialMapPOI(mBufferObjectManager);
		
	}
	
	public void registerUpdateHandler(){
		
	}

	private void LocationMessage(){
		this.gameToast("目前的緯度: " + MainActivity.pLatitude +"\n目前經度: " +MainActivity.pLongitude);
	}
	
	private void createButton(VertexBufferObjectManager mBufferObjectManager){
		
		final GameSceneManager mGM = new GameSceneManager(mActivity, mCamera);
		ButtonSprite email = new ButtonSprite(78, 259, email_TextureRegion, mBufferObjectManager, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				SceneWebPage.webTag = 0;
				mGM.creatingScene(GameSceneType.WEBPAGE);
			}
		});
		ButtonSprite fb = new ButtonSprite(164, 259, fb_TextureRegion, mBufferObjectManager, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				SceneWebPage.webTag = 1;
				mGM.creatingScene(GameSceneType.WEBPAGE);
			}
		});
		ButtonSprite twitter = new ButtonSprite(250, 259, twitter_TextureRegion, mBufferObjectManager, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				SceneWebPage.webTag = 2;
				mGM.creatingScene(GameSceneType.WEBPAGE);
			}
		});
		
		ButtonSprite talk = new ButtonSprite(64, 338, talk_TextureRegion, mBufferObjectManager, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				mGM.creatingScene(GameSceneType.TALKPAGE);
			}
			
		});
		
		this.attachChild(email);
		this.attachChild(fb);
		this.attachChild(twitter);
		this.attachChild(talk);
		this.registerTouchArea(email);
		this.registerTouchArea(fb);
		this.registerTouchArea(twitter);
		this.registerTouchArea(talk);
	}
	
	private void initialMapPOI(VertexBufferObjectManager mBufferObjectManager){
		
		// player tag
		float _playerPOIX = 580 - (playerPOI_TextureRegion.getWidth(0) *0.5f);
		float _playerPOIY = 240 - playerPOI_TextureRegion.getHeight(0);
		TiledSprite player = new TiledSprite(_playerPOIX, _playerPOIY, playerPOI_TextureRegion, mBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				// location
				SceneOutdoor.this.LocationMessage();
				return true;
			}
		};
		player.setCurrentTileIndex(1);
		this.registerTouchArea(player);
		this.attachChild(player);
		
		// there is fake
		TiledSprite player1 = setOthersPOI(653, 192, 0, mBufferObjectManager, 2);
		TiledSprite player2 = setOthersPOI(560, 328, 2, mBufferObjectManager, 1);
		TiledSprite player3 = setOthersPOI(437, 142, 3, mBufferObjectManager, 3);
		
	}
	
	private TiledSprite setOthersPOI(float _x, float _y, int index, VertexBufferObjectManager mBufferObjectManager, final int pTarget){
	
		TiledSprite somebody = new TiledSprite(_x, _y, playerPOI_TextureRegion, mBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{				
				SceneOutdoor.this.setChildScene(mMenuScene);
				mMenuScene.setTarget(pTarget);
				
				return true;
			}
		};
		somebody.setCurrentTileIndex(index);
		this.attachChild(somebody);
		this.registerTouchArea(somebody);
		return somebody;
	}
	
	public void gameToast(final String msg) {
		this.mActivity.runOnUiThread(new Runnable() {
	        public void run() {
	           Toast.makeText(SceneOutdoor.this.mActivity, msg, Toast.LENGTH_LONG).show();
	        }
	    });
	}
}
