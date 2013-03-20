package com.example.GameScene;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.helperclasses.InputText;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import android.graphics.Typeface;

public class SceneTalk extends GameScene implements IScene {

	private BitmapTextureAtlas bg_Space;
	private TextureRegion bg_TextureRegion;
	
	// *InputText 
	private Font inputFont;
	private TiledTextureRegion edit_TextureRegion;
	private InputText talkTextInput;
		
	// talk & social button
	private TiledTextureRegion talk_TextureRegion, 
					email_TextureRegion, fb_TextureRegion, twitter_TextureRegion;
	
	private TiledTextureRegion back_TextureRegion;
	final GameSceneManager mGM = new GameSceneManager(mActivity, mCamera);
	public SceneTalk(BaseGameActivity pActivity, SmoothCamera pCamera) {
		super(pActivity, pCamera);
		Debug.e("SceneTalk");
	}

	public void onCreateResources(){
		bg_Space = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024, 
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		bg_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space, 
				this.mActivity, "07_talk_bg.jpg", 0, 0);
		
		talk_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "05_talk_btn.png", 153, 480, 1, 2);
		
		email_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "05_mail_btn.png", 0, 480, 1, 2);
		
		fb_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "05_fb_btn.png", 51, 480, 1, 2);
		
		twitter_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "05_twitter_btn.png", 102, 480, 1, 2);
		
		edit_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
    			this.mActivity, "07_talkInput.png", 413, 480, 1, 1);
		
		back_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
    			this.mActivity, "07_back_btn.png", 413, 511, 1, 2);
		
		bg_Space.load();
		
		// input Font 
    	inputFont = FontFactory.create(this.mActivity.getFontManager(), this.mActivity.getTextureManager(),
                256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 28);    	
		inputFont.load();
	}
	
	public void onCreateScene(){
		
		VertexBufferObjectManager mBufferObjectManager = this.mActivity.getVertexBufferObjectManager();
		Sprite talk_bg = new Sprite(0, 0, bg_TextureRegion, mBufferObjectManager);
		this.attachChild(talk_bg);

		// button
		this.createButton(mBufferObjectManager);
		
		// input text
		this.createText(mBufferObjectManager);
		
		// back button
		this.createBackButton(mBufferObjectManager);
	}
	
	private void createBackButton(VertexBufferObjectManager mBufferObjectManager){
		 
		float _backX = this.mCamera.getWidth() - back_TextureRegion.getWidth(0);
		float _backY = this.mCamera.getHeight() - back_TextureRegion.getHeight(0);
		ButtonSprite back = new ButtonSprite(_backX, _backY, back_TextureRegion, mBufferObjectManager, new OnClickListener(){
			@Override
			public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				mGM.creatingScene(GameSceneType.OUTDOOR);
			}
		});
		this.registerTouchArea(back);
		this.attachChild(back);
	}
	
	private void createText(VertexBufferObjectManager mBufferObjectManager){
		
		talkTextInput = new InputText(360, 334, "Message", "點選下列方塊輸入", 
				edit_TextureRegion, inputFont, 13, 0, mBufferObjectManager, mActivity);
		talkTextInput.setText("Hello");
		
		this.registerTouchArea(talkTextInput);
		this.attachChild(talkTextInput);
	}
	
	private void createButton(VertexBufferObjectManager mBufferObjectManager){
						
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
}
