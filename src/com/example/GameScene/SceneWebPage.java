package com.example.GameScene;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

public class SceneWebPage extends GameScene implements IScene{

	public static int webTag;
	private ButtonSprite fakeWeb;
	private BitmapTextureAtlas bg_Space1, bg_Space2;
	private TiledTextureRegion gamil_TextureRegion, 
							fb_TextureRegion,
							twitter_TextureRegion;
	private VertexBufferObjectManager mBufferObjectManager = this.mActivity.getVertexBufferObjectManager();
	private HUD mHUD;
	public SceneWebPage(BaseGameActivity pActivity, SmoothCamera pCamera) {
		super(pActivity, pCamera);
		Debug.e("SceneWebPage");
	}
		
	public void onCreateResources() {
		
		bg_Space1 = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024, 
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		bg_Space2 = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 512, 
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		gamil_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space1, 
    			this.mActivity, "08_gmail.jpg", 0, 0, 1, 1);
		fb_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space1, 
    			this.mActivity, "08_fb.jpg", 0, 480, 1, 1);
		twitter_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space2, 
    			this.mActivity, "08_twitter.jpg", 0, 0, 1, 1);
		bg_Space1.load();
		bg_Space2.load();
	}

	
	public void onCreateScene() {
		mHUD = this.mCamera.getHUD();
		switch(webTag){
		
			case 0:
			fakeWeb = new ButtonSprite(0, 0, gamil_TextureRegion, mBufferObjectManager, new WebClickListener());
			break;
				
			case 1:
				fakeWeb = new ButtonSprite(0, 0, fb_TextureRegion, mBufferObjectManager, new WebClickListener());				
				break;
				
			case 2:
				fakeWeb = new ButtonSprite(0, 0, twitter_TextureRegion, mBufferObjectManager, new WebClickListener());				
				break;
		}
		fakeWeb.setRotation(180);
		this.registerTouchArea(fakeWeb);
		this.attachChild(fakeWeb);
		
		mHUD.setVisible(false);
	}

	
	public void registerUpdateHandler() {
		
		
	}

// ===========================================================
// Inner and Anonymous Classes
// ===========================================================
	
class WebClickListener implements OnClickListener{
	GameSceneManager mGM = new GameSceneManager(mActivity, mCamera);

	public WebClickListener(){}
	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		Debug.e("test");
		mGM.creatingScene(GameSceneType.OUTDOOR);
		mHUD.setVisible(true);
		
	}
		
}



}
