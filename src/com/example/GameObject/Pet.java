package com.example.GameObject;

import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;

import com.example.hellopuppy_09.MainActivity;

public class Pet extends GameObject {
	
	private int camera_W = MainActivity.CAMERA_WIDTH;
		
	private float velocityX = 5; 
	
	private TiledTextureRegion mTiledTextureRegion = (TiledTextureRegion) this.getTiledTextureRegion();
	
	public Pet(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);

	}

	/*
	 * Set Pet walking in home
	 * */
	@Override
	public void walkinHome(float pSecondsElapsed) {
		float currentX = this.getX();

		if(currentX == camera_W - this.mTiledTextureRegion.getWidth(0)){
			velocityX = -5;
			this.animate(new long[]{150,150,150,150}, 0, 3, true);
			this.setPosition(this.getX()+ velocityX, this.getY());
		}
		else if(currentX == 0){
			velocityX = 5;
			this.animate(new long[]{150,150,150,150}, 4, 7, true);
			this.setPosition(this.getX() + velocityX, this.getY());
		}
		
		this.setPosition(this.getX()+ velocityX, this.getY());
		
	}
	
	 
	public void showtest(){
		Debug.e("X:" + this.getX());
		Debug.e("Y:" + this.getY());
	}

}
