package com.example.GameScene;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.AnimatedSprite;
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
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import com.example.hellopuppy_09.MainActivity;

public class SceneTitle extends GameScene implements IScene {
	
	// *Background Resource 
	private BitmapTextureAtlas bg_Space;
	private TextureRegion bg_TextureRegion;
	
	// *Button Resource
	private BitmapTextureAtlas btn_Space;
	private TiledTextureRegion btn_TextureRegion, 
							eye_TextureRegion;
	
	// *Option Resource
	private TiledTextureRegion soundBtn_TextureRegion,
								option_TextureRegion, 
								home_TextureRegion;
	private TiledSprite sound_btn, home_btn;
	
	// *Hud
	private HUD mHud;
	
	// Constructor
	public SceneTitle(BaseGameActivity pActivity, SmoothCamera pCamera) {

		super(pActivity, pCamera);
		Debug.e("SceneTitle");
	}

	@Override
	public void onCreateResources() {
		
		bg_Space = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 512, 
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		
		// createFormAsset(Texture, Activity Context, source, image_x(圖像在source 的座標), image_y(圖像在source 的座標));
    	bg_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space, 
    			this.mActivity, "02_title_bg.jpg", 0, 0);
    	soundBtn_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "00_music_btn.png", 800, 0, 1, 2);
    	option_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
    			this.mActivity, "00_option.png", 890, 0, 1, 1); 
    	home_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
    			this.mActivity, "00_home_btn.png", 800, 140, 1, 1);
       	bg_Space.load();
    	
		btn_Space = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 256, 512, 
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		btn_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btn_Space, 
				this.mActivity, "02_title_btn.png", 0, 0, 1, 2);
		eye_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(btn_Space, 
				this.mActivity, "02_title_eye.png", 0, 400, 1, 2);
		btn_Space.load();	
		
	}


	public void onCreateScene() {
	
		// 建立Background
		Sprite title_bg = new Sprite(0, 0, bg_TextureRegion, 
					this.mActivity.getVertexBufferObjectManager());
		this.attachChild(title_bg);
		
		// 建立eye
		AnimatedSprite eye = new AnimatedSprite(376, 447, eye_TextureRegion,
				this.mActivity.getVertexBufferObjectManager());
		eye.animate(new long[]{2000,300});		// 第一個影格2000ms, 第二個影格300ms
		this.attachChild(eye);
		
		// 建立Start Button
		ButtonSprite start_btn = new ButtonSprite(435, 212, btn_TextureRegion, 
						this.mActivity.getVertexBufferObjectManager(), new OnClickListener(){
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{ 
				// 場景轉換 Home Scene
				MainActivity.playSound(SoundType.BARK_START);
				GameSceneManager mGM = new GameSceneManager(mActivity, mCamera);
				mGM.creatingScene(GameSceneType.HOME);			
			}
			
		});
		this.attachChild(start_btn);
		this.registerTouchArea(start_btn);
		
		// setting HUD
		this.setHUD();

	}

	public void registerUpdateHandler() {

	}
	
	private void setHUD(){
		
		this.mHud = new HUD();
		this.mCamera.setHUD(mHud);
		float _optionX = this.mCamera.getWidth() - option_TextureRegion.getWidth();
		float _optionY = 0;
		final float _soundBtnX = this.mCamera.getWidth() - soundBtn_TextureRegion.getWidth();
		final float _soundBtnY = _optionY - soundBtn_TextureRegion.getHeight();
		
		sound_btn = new TiledSprite(_soundBtnX, _soundBtnY, soundBtn_TextureRegion, 
				this.mActivity.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.getAction()== TouchEvent.ACTION_UP){
					if(this.getCurrentTileIndex() == 1){
						// 關掉背景音樂
						this.setCurrentTileIndex(0);
						MainActivity.stopbgMusic();
					}
					else{
						// 開啟背景音樂
						this.setCurrentTileIndex(1);
						MainActivity.playbgMusic();
					}
				}	
				return true;
			}
		};
		sound_btn.setVisible(false);
		sound_btn.setCurrentTileIndex(1);
		
		TiledSprite option = new TiledSprite(_optionX -10, _optionY, option_TextureRegion,
				this.mActivity.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{	
				sound_btn.setVisible(true);			
				if(pSceneTouchEvent.getAction()== TouchEvent.ACTION_UP)
				{
					sound_btn.registerEntityModifier(new SequenceEntityModifier(
							new MoveYModifier(0.3f, _soundBtnY, _soundBtnY + soundBtn_TextureRegion.getHeight() + option_TextureRegion.getHeight()),
							new DelayModifier(5.0f),
							new MoveYModifier(0.3f, sound_btn.getY(), _soundBtnY)
					));
				}
				return true;
			}
		};
		
		float _homeX = _optionX - home_TextureRegion.getWidth();
		TiledSprite home = new TiledSprite(_homeX-20, 0, home_TextureRegion,
				this.mActivity.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{						
				if(pSceneTouchEvent.getAction()== TouchEvent.ACTION_UP)
				{
					GameSceneManager mGM = new GameSceneManager(mActivity, mCamera);
					mGM.creatingScene(GameSceneType.HOME);
				}
				return true;
			}
		};
				
		mHud.attachChild(home);
		mHud.registerTouchArea(home);
		mHud.attachChild(option);
		mHud.attachChild(sound_btn);
		mHud.registerTouchArea(option);
		mHud.registerTouchArea(sound_btn);
		
	}
}
