package com.example.GameScene;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import com.example.GameObject.Pet;
import com.example.GameObject.PetInfo;
import com.example.GameObject.PetResource;
import com.example.hellopuppy_09.MainActivity;

public class MenuMain extends Scene implements IScene{

	private BaseGameActivity mActivity;
	private SmoothCamera mCamera;
	
	private BitmapTextureAtlas bg_Space;
	
	private TextureRegion menuBg_TextureRegion;
	
	private TiledTextureRegion menu_NewTextureRegion, 
							menu_FoodTextureRegion, 
							menu_TrainingTextureRegion, 
							menu_outdoorTextureRegion;
	
	public MenuMain(BaseGameActivity pActivity, SmoothCamera pCamera){		
		this.mActivity = pActivity;
		this.mCamera = pCamera;
		Debug.e("MainMenu");
		
		this.onCreateResources();
		this.onCreateScene();
		this.registerUpdateHandler();
	}
	
	@Override
	public void onCreateResources() {
		bg_Space = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 512,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		
		// *Menubar
		menuBg_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space,
				this.mActivity, "03_menubar.png", 0, 0);
		
		// *Menu Items	
		menu_NewTextureRegion = this.setMenuButtonRegion(0, 145, "03_menu_new.png");
		menu_FoodTextureRegion = this.setMenuButtonRegion(89, 145, "03_menu_food.png");
		menu_TrainingTextureRegion = this.setMenuButtonRegion(178, 145, "03_menu_teach.png");
		menu_outdoorTextureRegion = this.setMenuButtonRegion(267, 145, "03_menu_walk.png");
		bg_Space.load();
	}

	@Override
	public void onCreateScene() {
	
		this.setBackgroundEnabled(false);
		Sprite menu_bg = new Sprite(0, 0, menuBg_TextureRegion, this.mActivity.getVertexBufferObjectManager());
		menu_bg.setAlpha(0.8f);
		
		ButtonSprite new_MenuItem = new ButtonSprite(413, 52, 
				menu_NewTextureRegion, this.mActivity.getVertexBufferObjectManager(), 
				new MenuItemListener(0));		
		ButtonSprite food_MenuItem = new ButtonSprite(290, 53, 
				menu_FoodTextureRegion, this.mActivity.getVertexBufferObjectManager(), 
				new MenuItemListener (1));
		ButtonSprite training_MenuItem = new ButtonSprite(167, 52, 
				menu_TrainingTextureRegion, this.mActivity.getVertexBufferObjectManager(), 
				new MenuItemListener(2));
		ButtonSprite outdoor_MenuItem = new ButtonSprite(44, 52, 
				menu_outdoorTextureRegion, this.mActivity.getVertexBufferObjectManager(), 
				new MenuItemListener(3));
		
		new_MenuItem.setTag(0);
		food_MenuItem.setTag(1);
		training_MenuItem.setTag(2);
		outdoor_MenuItem.setTag(3);
		
		this.registerTouchArea(new_MenuItem);
		this.registerTouchArea(food_MenuItem);
		this.registerTouchArea(training_MenuItem);
		this.registerTouchArea(outdoor_MenuItem);
		
		this.attachChild(menu_bg);
		this.attachChild(new_MenuItem);
		this.attachChild(food_MenuItem);
		this.attachChild(training_MenuItem);
		this.attachChild(outdoor_MenuItem);
	}

	@Override
	public void registerUpdateHandler() {
	
		
	}

	// setting Tiled Texture Region Atlas, don't touch
	private TiledTextureRegion setMenuButtonRegion(int _x, int _y, String filePath)
	{		
		return BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
	    			this.mActivity, filePath, _x, _y, 1, 1);		
	}
	
	
// ===========================================================
// Inner and Anonymous Classes
// ===========================================================

class MenuItemListener implements OnClickListener{

	private int menuID;
	private GameSceneManager mGM;
	private Scene currentScene; 
	private PetResource mPetResource;
	private TiledTextureRegion mTextureRegion;
	private AnimatedSprite mSprite;
	
	public MenuItemListener(int pMenuID){
		this.menuID = pMenuID;
		this.mGM = new GameSceneManager(mActivity, mCamera);
		mPetResource = new PetResource(mActivity, mCamera);
	}
	
	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		
		VertexBufferObjectManager VBOM = mActivity.getVertexBufferObjectManager();
		MainActivity.playSound(SoundType.CLICKING);
		
		switch(menuID) {
		
		case 0:
			Debug.e("Creating Pet");
			mGM.creatingScene(GameSceneType.CREATING_PET);
			break;
			
			
		case 1:
			Debug.e("HOME_MENU_FOOD");
			this.setSpriteFoodTextureRegion();
			mSprite = new AnimatedSprite(200, 240, mTextureRegion, VBOM);
			mSprite.animate(new long[]{700,700,700,700,700,700}, 0, 5, 2, new PetAnimationListener(mSprite));
			this.setSpritOnCurrentScene(mSprite);
			break;
			
			
		case 2:
			Debug.e("HOME_MENU_TRAINING");
			this.setSpriteTeachTextureRegion();
			mSprite = new AnimatedSprite(200, 240, mTextureRegion, VBOM);
			mSprite.animate(new long[]{300,300,300,300}, 0, 3, 3, new PetAnimationListener(mSprite));
			this.setSpritOnCurrentScene(mSprite);
			break;
			
			
		case 3:
			Debug.e("HOME_MENU_OUTDOOR");
			mGM.creatingScene(GameSceneType.STORY);
			
		}
	
	}
	
	private void setSpriteFoodTextureRegion(){
		
		switch(PetInfo.petType){
			case 0:
				mTextureRegion = mPetResource.getHungryTextureRegion0();
				break;
			case 1:
				mTextureRegion = mPetResource.getHungryTextureRegion1();
				break;
			case 2:
				mTextureRegion = mPetResource.getHungryTextureRegion2();
				break;
			case 3:
				mTextureRegion = mPetResource.getHungryTextureRegion3();
				break;
		}
	}
	
	private void setSpriteTeachTextureRegion(){
		switch(PetInfo.petType){
		case 0:
			mTextureRegion = mPetResource.getTeachTextureRegion0();
			break;
		case 1:
			mTextureRegion = mPetResource.getTeachTextureRegion1();
			break;
		case 2:
			mTextureRegion = mPetResource.getTeachTextureRegion2();
			break;
		case 3:
			mTextureRegion = mPetResource.getTeachTextureRegion3();
			break;
		}
	}
	
	private void setSpritOnCurrentScene(AnimatedSprite pSprite){
		currentScene = mGM.getApplyScene();
		currentScene.attachChild(pSprite);
	}
	
	
}
		
class PetAnimationListener implements IAnimationListener{

	private AnimatedSprite mSprite, saveSprite;
	private GameSceneManager mGM;
	private Scene currentScene;
	public PetAnimationListener(AnimatedSprite pSprite){
		this.mSprite = pSprite;
		this.mGM = new GameSceneManager(mActivity, mCamera);
	}
	
	@Override
	public void onAnimationStarted(AnimatedSprite pAnimatedSprite, int pInitialLoopCount) {
		// 儲存正在場景中的sprite
		this.saveCurrentSprite();
		// 移除正在場景中的sprite
		this.detachCurrentSprite();
	}

	@Override
	public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite, int pOldFrameIndex, int pNewFrameIndex) {
		
	}

	@Override
	public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite, int pRemainingLoopCount, int pInitialLoopCount) {
		
	}

	@Override
	public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
		// 播放結束, 移除事件偵聽的sprite
		this.deatchSelf();
		
		// 放回原本的sprite
		this.currentScene = this.mGM.getApplyScene();
		saveSprite.animate(new long[]{150,150,150,150}, 0, 3, true);
		this.currentScene.attachChild(saveSprite);
	}
	
	private void deatchSelf(){
		this.currentScene = this.mGM.getApplyScene();
		this.currentScene.detachChild(mSprite);
	}
	
	private void detachCurrentSprite(){
		this.currentScene = this.mGM.getApplyScene();
		this.currentScene.detachChild(0);
		
	}
	
	private void saveCurrentSprite(){
		this.currentScene = this.mGM.getApplyScene();
		saveSprite = (Pet) this.currentScene.getChildByTag(0);
	}
	
	
}	


}
