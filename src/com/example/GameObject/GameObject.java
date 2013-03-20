package com.example.GameObject;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public abstract class GameObject extends AnimatedSprite{

	public GameObject(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.animate(200);
	}
	
	/*
	 * When Animation Sprite playing will call this method
	 * frames change run 
	 * */
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		walkinHome(pSecondsElapsed);
		super.onManagedUpdate(pSecondsElapsed);
		
	}
	
	public abstract void walkinHome(float pSecondsElapsed);
	
}
