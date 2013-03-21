package com.example.GameScene;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

public class GameScene extends Scene implements IScene{

	protected BaseGameActivity mActivity;
	protected SmoothCamera mCamera;
	protected float camera_W, camera_H;
		
	public GameScene(BaseGameActivity pActivity, SmoothCamera pCamera){
				
		this.mActivity = pActivity;
		this.mCamera = pCamera;
		this.camera_W = this.mCamera.getWidth();
		this.camera_H = this.mCamera.getHeight();
		
		this.initialScene();
		this.onCreateResources();
		this.onCreateScene();
		this.registerUpdateHandler();
	}

	@Override
	public void onCreateResources() {}

	@Override
	public void onCreateScene() {}

	@Override
	public void registerUpdateHandler() {}
	
	private void initialScene(){
		Debug.e("initial Scene");
		this.mCamera.setZoomFactor(1.0f);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	}
}
