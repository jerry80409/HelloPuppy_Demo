package com.example.GameScene;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
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

import com.example.hellopuppy_09.MainActivity;

public class SceneStory extends GameScene implements IScene{

	// Story
	private Sprite mStory;
	private BitmapTextureAtlas bg_Space;
	private TextureRegion story1_TextureRegion, 
							story2_TextureRegion,
							puppy_TextureRegion;
	private int storyTag = 0;
	
	// Skip button
	private TiledTextureRegion skip_TextureRegion;
		
	private VertexBufferObjectManager mBufferObjectManager = this.mActivity.getVertexBufferObjectManager();
		
	public SceneStory(BaseGameActivity pActivity, SmoothCamera pCamera) {
		super(pActivity, pCamera);
		Debug.e("SceneStory");
	}


	public void onCreateResources() {
		
		bg_Space = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024, 
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		
		// story 
		story1_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space, 
    			this.mActivity, "06_story1_bg.jpg", 0, 0);
		
		story2_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space, 
    			this.mActivity, "06_story3_bg.jpg", 0, 480);
		
		// puppy
		puppy_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space, 
    			this.mActivity, "06_story1_dog.png", 800, 0);
		
		// skip button
		skip_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
    			this.mActivity, "06_skip_btn.png", 800, 162, 1, 2);
		
		bg_Space.load();
	}


	public void onCreateScene() {		
		this.createStroy1();
	}

	public void registerUpdateHandler() {
		// TODO Auto-generated method stub		
	}

	private void createSkipButton(){
		
		float _skipX = mCamera.getWidth() - skip_TextureRegion.getWidth();
		float _skipY = mCamera.getHeight() - skip_TextureRegion.getHeight();
		ButtonSprite skip_btn = new ButtonSprite(_skipX, _skipY, skip_TextureRegion, 
				mBufferObjectManager, new skipButtonListener());
		this.attachChild(skip_btn);
		this.registerTouchArea(skip_btn);
	}
	
	private void createStroy1(){
		Debug.e("createStroy1");
		this.setStroyTag(0);
		
		mStory = new Sprite(0, 0, 800, 480, story1_TextureRegion, mBufferObjectManager);
		this.attachChild(mStory);
		
		this.createSkipButton();
		float _puppyX = (float) ((mCamera.getWidth() - puppy_TextureRegion.getWidth()) *0.5) + 80;
		float _puppyY = (float) ((mCamera.getHeight() - puppy_TextureRegion.getHeight()) *0.5) + 100;
		Sprite puppy = new Sprite(_puppyX, _puppyY, puppy_TextureRegion, mBufferObjectManager);
		MainActivity.playSound(SoundType.BARK_STORY);
		
		/* 跳跳動畫 */
		puppy.registerEntityModifier(new SequenceEntityModifier(
				new DelayModifier(0.8f),
				new MoveYModifier(0.2f, _puppyY, _puppyY - 150), new MoveYModifier(0.2f, _puppyY - 150, _puppyY),
				new MoveYModifier(0.2f, _puppyY, _puppyY - 150), new MoveYModifier(0.2f, _puppyY - 150, _puppyY),
				new MoveYModifier(0.2f, _puppyY, _puppyY - 150), new MoveYModifier(0.2f, _puppyY - 150, _puppyY),
				new MoveYModifier(0.2f, _puppyY, _puppyY - 150), new MoveYModifier(0.2f, _puppyY - 150, _puppyY),
				new DelayModifier(1.0f),
				new MoveYModifier(0.2f, _puppyY, _puppyY - 150), new MoveYModifier(0.2f, _puppyY - 150, _puppyY),
				new MoveYModifier(0.2f, _puppyY, _puppyY - 150), new MoveYModifier(0.2f, _puppyY - 150, _puppyY),
				new MoveYModifier(0.2f, _puppyY, _puppyY - 150), new MoveYModifier(0.2f, _puppyY - 150, _puppyY),
				new MoveYModifier(0.2f, _puppyY, _puppyY - 150), new MoveYModifier(0.2f, _puppyY - 150, _puppyY)				
				));
		this.attachChild(puppy);
		
	}
	
	private void creatStroy2(){
		Debug.e("creatStroy2");
		this.setStroyTag(1);
		
		// 移除全部子物件
		this.detachChildren();
		
		mStory = new Sprite(0, 0, 800, 480, story2_TextureRegion, mBufferObjectManager);
		this.attachChild(mStory);
		MainActivity.playSound(SoundType.PARK_BIRDS);
		
		this.createSkipButton();
	}
	
	private void setStroyTag(int pStoryTag){
		this.storyTag = pStoryTag;
	}
	
	
	class skipButtonListener implements OnClickListener
	{

		@Override
		public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

			switch(storyTag){
				
			case 0:
				creatStroy2();
				break;
				
			case 1:
				GameSceneManager mGM = new GameSceneManager(mActivity, mCamera);
				mGM.creatingScene(GameSceneType.OUTDOOR);
				break;				
			
			}
			
		}
		
	}
	
}
