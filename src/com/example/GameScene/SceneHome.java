package com.example.GameScene;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
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
import org.andengine.util.modifier.IModifier;

import android.widget.Toast;

import com.example.GameObject.Pet;
import com.example.GameObject.PetInfo;
import com.example.GameObject.PetResource;
import com.example.hellopuppy_09.MainActivity;

public class SceneHome extends GameScene implements IScene {

	// *Background Resource
	private BitmapTextureAtlas bg_Space;
	private TextureRegion bg_TextureRegion, 
					callMenubar_TextureRegion;
	private TiledTextureRegion window_TextureRegion,
					door_TextureRegion;
	
	// *Menu Resource
	private MenuMain mMenuScene;
	private Sprite callMenubar;
	
	// *Pet Resource
	private PetResource petResource;
	private TiledTextureRegion mPetTexturRegion;
	private Pet mSprite;
	
	public static double random;
	
	public SceneHome(BaseGameActivity pActivity, SmoothCamera pCamera) {
		
		super(pActivity, pCamera);		
		Debug.e("SceneHome");
		
	}

	public void onCreateResources(){
		
		// * Background Resource
		bg_Space = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		bg_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space,
				this.mActivity, "03_home_bg.jpg", 0, 0);
		callMenubar_TextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space,
				this.mActivity, "03_callMenu.png", 800, 0);
		
		window_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "03_home_window.png", 0, 480, 1, 2);
		door_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "03_home_door.png", 800, 150, 1, 2);
		
		bg_Space.load();		

		// 可連線, 再來修改
		//initialLoad();
	}
	  
	public void onCreateScene(){	
		
		// * Background 
		Sprite home_bg = new Sprite(0, 0, bg_TextureRegion,
				this.mActivity.getVertexBufferObjectManager());
		this.attachChild(home_bg);
		
		// * Windows
		this.createWindow();
		
		// * Door
		this.createdoor();
		
		
		mMenuScene = new MenuMain(this.mActivity, this.mCamera);
		SceneHome.this.setChildScene(mMenuScene);
		
		// 縮到螢幕左邊
		final float _menuSceneX = -544;
		mMenuScene.setPosition(_menuSceneX , 0);
		
		// 隱形的button
		callMenubar = new Sprite(0, 0, callMenubar_TextureRegion, 
				this.mActivity.getVertexBufferObjectManager()){
			
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				Debug.e("Menubar touch!!");
				if(pSceneTouchEvent.getAction()== TouchEvent.ACTION_DOWN){
					mMenuScene.registerEntityModifier(new SequenceEntityModifier(
							new MoveXModifier(2.0f, -544, 0),
							new DelayModifier(3.0f),
							new MoveXModifier(2.0f, 0, -544)
							));
					
					this.registerEntityModifier(new SequenceEntityModifier(
							new MoveXModifier(2.0f, 0, 544),
							new DelayModifier(3.0f),
							new MoveXModifier(2.0f, 0, 0)
							));
				}				
				return true;
			}
		};
		
		this.attachChild(callMenubar);
		this.registerTouchArea(callMenubar);
		this.initialCreateScene();
       
	}
	
	public void registerUpdateHandler(){
		
	}
	
	private void createdoor(){
		ButtonSprite door = new ButtonSprite(295, 15.5f, door_TextureRegion, 
				this.mActivity.getVertexBufferObjectManager(),  new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				GameSceneManager mGM = new GameSceneManager(mActivity, mCamera);
				mGM.creatingScene(GameSceneType.STORY);
			}
		});
		this.attachChild(door);
		this.registerTouchArea(door);
	}
	
	private void createWindow(){
		TiledSprite window = new TiledSprite(636, -1, window_TextureRegion, 
				this.mActivity.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.getAction()== TouchEvent.ACTION_UP)
				{	
					if(this.getCurrentTileIndex()==0)
						this.setCurrentTileIndex(1);						
					
					else{
						this.setCurrentTileIndex(0);
						MainActivity.playSound(SoundType.PARK_BIRDS);
					}
				}
				return false;
			}
		};
		window.setCurrentTileIndex(1);
		this.attachChild(window);
		this.registerTouchArea(window);
	}
	/*
	// 載入使用者所選的寵物
	private void initialLoad(){
		
		// *load player dog texture
		mPuppy_Space = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 
				1024, 512, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		mPuppy_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mPuppy_Space, 
				this.mActivity, "dog1_walk.png", 0, 0, 4, 2);
		mPuppy_Space.load();
	}*/
	
	
	// 有檔案 再顯示
	private void initialCreateScene(){
				
		// PetInfo 沒寫得很好, 有時間再改!!!	
		if(PetInfo.loadPetInfo(mActivity) != false)
			this.settingPlayer();	
		else
			this.gameToast("你的狗狗還未創建喔!!!");

	}
	
	private void settingPlayer(){
		VertexBufferObjectManager VBOM = this.mActivity.getVertexBufferObjectManager();
		petResource = new PetResource(mActivity, mCamera);
		this.setPlayerTexture();	
		
		mSprite = new Pet(camera_W-mPetTexturRegion.getWidth(0), 250, 
				this.mPetTexturRegion, VBOM);
		mSprite.animate(new long[]{150,150,150,150}, 0, 3, true);
		mSprite.setTag(0);
		mSprite.showtest();
		this.attachChild(mSprite);
		
	}
	

	
	private void setPlayerTexture(){
		switch(PetInfo.petType){
			case 0:
				mPetTexturRegion = petResource.getWalkTextureRegion0();
				break;
			case 1:
				mPetTexturRegion = petResource.getWalkTextureRegion1();
				break;
			case 2:
				mPetTexturRegion = petResource.getWalkTextureRegion2();
				break;
			case 3:
				mPetTexturRegion = petResource.getWalkTextureRegion3();
				break;
		}
	}
	
	public void gameToast(final String msg) {
	    this.mActivity.runOnUiThread(new Runnable() {
	        public void run() {
	           Toast.makeText(SceneHome.this.mActivity, msg, Toast.LENGTH_LONG).show();
	        }
	    });
	}
	

	
}			

	
	
