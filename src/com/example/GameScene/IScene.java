package com.example.GameScene;


public interface IScene {
	
	public void onCreateResources();
	
	public void onCreateScene();
	
	public void registerUpdateHandler();
}
