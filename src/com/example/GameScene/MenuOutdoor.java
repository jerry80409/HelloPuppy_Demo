package com.example.GameScene;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.scene.Scene;
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

import com.example.hellopuppy_09.MainActivity;

public class MenuOutdoor extends Scene implements IScene{

	private BaseGameActivity mActivity;
	private SmoothCamera mCamera;
	
	public static int target;
	
	private TiledSprite targetPet;
	private TiledSprite _menuPhoto;
	
	private BitmapTextureAtlas bg_Space;
	private TextureRegion menuBg_TextureRegion;
	private TiledTextureRegion esc_btn_TextureRegion, 
								menuItem_TextureRegion, 
								menuPhoto_TextureRegion, 
								menuStick_TextureRegion,
								dotList_TextureRegion;
	private ButtonSprite esc_btn;
	
	
	public MenuOutdoor(BaseGameActivity pActivity, SmoothCamera pCamera) {
		this.mActivity = pActivity;
		this.mCamera = pCamera;

		Debug.e("OutdoorMenu");
		
		this.onCreateResources();
		this.onCreateScene();
		this.registerUpdateHandler();
	}

	@Override
	public void onCreateResources() {
		
		bg_Space = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);		
		
		menuBg_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space,
				this.mActivity, "07_outMenu_bg.png", 0, 0);
		menuPhoto_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "07_outMenu_photo.jpg", 0, 372, 2, 2);
		dotList_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "04_dogList.png", 466, 372, 2, 2);
		menuStick_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "07_outMenu_stick.png", 609, 0, 2, 2);
		menuItem_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "07_outMenu_item.png", 609, 162, 2, 2);
		esc_btn_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "07_outMenu_esc.png", 609, 264, 1, 2);
		bg_Space.load();
	}

	@Override
	public void onCreateScene() {
		this.setBackgroundEnabled(false);
		
		VertexBufferObjectManager VBOM = this.mActivity.getVertexBufferObjectManager();
		
		float _bgX = (this.mCamera.getWidth() - menuBg_TextureRegion.getWidth()) *0.5f;
		float _bgY = (this.mCamera.getHeight() - menuBg_TextureRegion.getHeight()) *0.5f;
		
		Sprite menu_bg = new Sprite(_bgX, _bgY, menuBg_TextureRegion, VBOM);
		this.attachChild(menu_bg);
		
		float _escX = _bgX + menuBg_TextureRegion.getWidth() - esc_btn_TextureRegion.getWidth();
		esc_btn = new ButtonSprite(_escX, _bgY, esc_btn_TextureRegion, VBOM, new OnClickListener(){

			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				// 
				GameSceneManager mGM = new GameSceneManager(mActivity, mCamera);
				mGM.creatingScene(GameSceneType.OUTDOOR);
			}
			
		});
		this.attachChild(esc_btn);
		this.registerTouchArea(esc_btn);

// Target Pet
		targetPet = new TiledSprite(180, 80, 
				dotList_TextureRegion, this.mActivity.getVertexBufferObjectManager());
		targetPet.setScale(0.6f);
		this.attachChild(targetPet);
		
// Menu Photo
		_menuPhoto = new TiledSprite(169, 87, menuPhoto_TextureRegion, VBOM);
		_menuPhoto.setVisible(false);
		this.attachChild(_menuPhoto);
		
// Menu Item		
		TiledSprite menuItem0 = this.createMenuItem(0, VBOM);
		TiledSprite menuItem1 = this.createMenuItem(1, VBOM);
		TiledSprite menuItem2 = this.createMenuItem(2, VBOM);
		TiledSprite menuItem3 = this.createMenuItem(3, VBOM);
		
// Stick fake
		TiledSprite stick0 = this.creatStickItem(0, VBOM);
		TiledSprite stick1 = this.creatStickItem(1, VBOM);
		TiledSprite stick2 = this.creatStickItem(2, VBOM);
		stick0.setVisible(this.randomVisible());
		stick1.setVisible(this.randomVisible());
		stick2.setVisible(this.randomVisible());
		
	}

	@Override
	public void registerUpdateHandler() {
	
		
	}

	public void setTarget(int pTarget){
		this.target = pTarget;
		targetPet.setCurrentTileIndex(pTarget);
	}
	
	private TiledSprite creatStickItem(final int index, VertexBufferObjectManager VBOM){
		
		TiledSprite stick = new TiledSprite(150+(85*index), 60, menuStick_TextureRegion, VBOM);
		stick.setCurrentTileIndex(index);		
		this.attachChild(stick);
		return stick;
	}
	
	private boolean randomVisible(){
		int mRandom = (int) (Math.random()*4);
		Debug.e("mRandom:" + mRandom);
		if(mRandom %2 == 0){
			Debug.e("true");
			return true;
		}
		else{
			Debug.e("false");
			return false;
		}
	}
	
	private TiledSprite createMenuItem(final int index, VertexBufferObjectManager VBOM){
		
		TiledSprite menuItem = new TiledSprite(169+(63*index), 335, menuItem_TextureRegion, VBOM){
			 @Override
             public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
             {
                 MainActivity.playSound(SoundType.CLICKING);
                 targetPet.setVisible(false);
                 _menuPhoto.setVisible(true);
                 switch(index){
                 	case 0:
                 		_menuPhoto.setCurrentTileIndex(2);
                 		break;
                 	case 1:
                 		_menuPhoto.setCurrentTileIndex(3);
                 		break;
                 	case 2:
                 		_menuPhoto.setCurrentTileIndex(1);
                 		break;
                 	case 3:
                 		_menuPhoto.setCurrentTileIndex(0);
                 		break;
                 }
                 GameSceneManager mGM = new GameSceneManager(mActivity, mCamera);
                 mGM.getApplyScene().clearTouchAreas();
                 return false;
             }
		};
		
		menuItem.setCurrentTileIndex(index);		
		this.attachChild(menuItem);
		this.registerTouchArea(menuItem);
		return menuItem;
	}
	
	

	
}
