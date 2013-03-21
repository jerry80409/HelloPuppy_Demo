package com.example.GameObject;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.BaseGameActivity;

public class PetResource {

	private BaseGameActivity mActivity;
	private SmoothCamera mCamera; 
	private float camera_W, camera_H;
	
	private BitmapTextureAtlas animation_Walk0,
								animation_Hungry0,
								animation_Teach0,
								animation_Walk1,
								animation_Hungry1,
								animation_Teach1,
								animation_Walk2,
								animation_Hungry2,
								animation_Teach2,
								animation_Walk3,
								animation_Hungry3,
								animation_Teach3;
	
	private TiledTextureRegion dog0_walk_TextureRegion, 
								dog0_hungry_TextureRegion, 
								dog0_teach_TextureRegion,
								dog1_walk_TextureRegion, 
								dog1_hungry_TextureRegion, 
								dog1_teach_TextureRegion,
								dog2_walk_TextureRegion, 
								dog2_hungry_TextureRegion, 
								dog2_teach_TextureRegion,
								dog3_walk_TextureRegion, 
								dog3_hungry_TextureRegion, 
								dog3_teach_TextureRegion;
	
	private VertexBufferObjectManager VBOM;
	
	public PetResource(BaseGameActivity pActivity, SmoothCamera pCamera){
		this.mActivity = pActivity;
		this.mCamera = pCamera;
		this.camera_W = pCamera.getWidth();
		this.camera_H = pCamera.getHeight();
		this.VBOM = this.mActivity.getVertexBufferObjectManager();
		
	}
	
	//
	public TiledTextureRegion getWalkTextureRegion0(){
		this.loadWalkResources0();
		return dog0_walk_TextureRegion;
	}
	public TiledTextureRegion getHungryTextureRegion0(){
		this.loadHungryResources0();
		return dog0_hungry_TextureRegion;
	}
	public TiledTextureRegion getTeachTextureRegion0(){
		this.loadTeachResources0();
		return dog0_teach_TextureRegion;
	}
	
	//
	public TiledTextureRegion getWalkTextureRegion1(){
		this.loadWalkResources1();
		return dog1_walk_TextureRegion;
	}
	public TiledTextureRegion getHungryTextureRegion1(){
		this.loadHungryResources1();
		return dog1_hungry_TextureRegion;
	}
	public TiledTextureRegion getTeachTextureRegion1(){
		this.loadTeachResources1();
		return dog1_teach_TextureRegion;
	}
	
	//
	public TiledTextureRegion getWalkTextureRegion2(){
		this.loadWalkResources2();
		return dog2_walk_TextureRegion;
	}
	public TiledTextureRegion getHungryTextureRegion2(){
		this.loadHungryResources2();
		return dog2_hungry_TextureRegion;
	}
	public TiledTextureRegion getTeachTextureRegion2(){
		this.loadTeachResources2();
		return dog2_teach_TextureRegion;
	}
	
	//
	public TiledTextureRegion getWalkTextureRegion3(){
		this.loadWalkResources3();
		return dog3_walk_TextureRegion;
	}
	public TiledTextureRegion getHungryTextureRegion3(){
		this.loadHungryResources3();
		return dog3_hungry_TextureRegion;
	}
	public TiledTextureRegion getTeachTextureRegion3(){
		this.loadTeachResources3();
		return dog3_teach_TextureRegion;
	}
	
	
	private void loadWalkResources0(){
		animation_Walk0 = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		dog0_walk_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animation_Walk0, 
				this.mActivity, "09_dog0_action_walk.png", 0, 0, 4, 2);
		animation_Walk0.load();
	}
	
	private void loadHungryResources0(){
		animation_Hungry0 = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		dog0_hungry_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animation_Hungry0, 
				this.mActivity, "09_dog0_action_eat.png", 0, 0, 3, 4);
		animation_Hungry0.load();
	}
	
	private void loadTeachResources0(){
		animation_Teach0 = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		dog0_teach_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animation_Teach0, 
				this.mActivity, "09_dog0_action_teach.png", 0, 0, 4, 2);
		animation_Teach0.load();
	}
	
	private void loadWalkResources1(){
		animation_Walk1 = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		dog1_walk_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animation_Walk1, 
				this.mActivity, "09_dog1_action_walk.png", 0, 0, 4, 2);
		animation_Walk1.load();
	}
	
	private void loadHungryResources1(){
		animation_Hungry1 = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		dog1_hungry_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animation_Hungry1, 
				this.mActivity, "09_dog1_action_eat.png", 0, 0, 3, 4);
		animation_Hungry1.load();
	}
	
	private void loadTeachResources1(){
		animation_Teach1 = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		dog1_teach_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animation_Teach1, 
				this.mActivity, "09_dog1_action_teach.png", 0, 0, 4, 2);
		animation_Teach1.load();
	}
	
	private void loadWalkResources2(){
		animation_Walk2 = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		dog2_walk_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animation_Walk2, 
				this.mActivity, "09_dog2_action_walk.png", 0, 0, 4, 2);
		animation_Walk2.load();
	}
	
	private void loadHungryResources2(){
		animation_Hungry2 = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		dog2_hungry_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animation_Hungry2, 
				this.mActivity, "09_dog2_action_eat.png", 0, 0, 3, 4);
		animation_Hungry2.load();
	}
	
	private void loadTeachResources2(){
		animation_Teach2 = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		dog2_teach_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animation_Teach2, 
				this.mActivity, "09_dog2_action_teach.png", 0, 0, 4, 2);
		animation_Teach2.load();
	}
	
	private void loadWalkResources3(){
		animation_Walk3 = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		dog3_walk_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animation_Walk3, 
				this.mActivity, "09_dog3_action_walk.png", 0, 0, 4, 2);
		animation_Walk3.load();
	}
	
	private void loadHungryResources3(){
		animation_Hungry3 = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		dog3_hungry_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animation_Hungry3, 
				this.mActivity, "09_dog3_action_eat.png", 0, 0, 3, 4);
		animation_Hungry3.load();
	}
	
	private void loadTeachResources3(){
		animation_Teach3 = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		dog3_teach_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(animation_Teach3, 
				this.mActivity, "09_dog3_action_teach.png", 0, 0, 4, 2);
		animation_Teach3.load();
	}
	

}
