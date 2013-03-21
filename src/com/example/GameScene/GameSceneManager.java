package com.example.GameScene;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.BaseGameActivity;


public class GameSceneManager {
	
	private BaseGameActivity mActivity;
	private SmoothCamera mCamera;
	
	private Scene currentScene;
	public static GameSceneType mSceneType;
	
	
	// Constructor
	public GameSceneManager(BaseGameActivity pActivity, SmoothCamera pCamera) {
				
		this.mActivity = pActivity;
		this.mCamera = pCamera;
	}

	public void creatingScene(GameSceneType pSceneType){
		
		switch(pSceneType)
		{
			case SPLASH:
				this.currentScene = new SceneSplash(this.mActivity, this.mCamera);
				this.mSceneType = GameSceneType.SPLASH;
				break;
				
			case TITLE:
				this.currentScene = new SceneTitle(this.mActivity, this.mCamera);
				this.mSceneType = GameSceneType.TITLE;
				break;
				
			case HOME:
				this.currentScene = new SceneHome(this.mActivity, this.mCamera);
				this.mSceneType = GameSceneType.HOME;				
				break;
				
			case CREATING_PET:
				this.currentScene = new SceneNew(this.mActivity, this.mCamera);
				this.mSceneType = GameSceneType.CREATING_PET;
				break;
				
			case STORY:
				this.currentScene = new SceneStory(this.mActivity, this.mCamera);
				this.mSceneType = GameSceneType.STORY;
				break;
							
			case OUTDOOR:
				this.currentScene = new SceneOutdoor(this.mActivity, this.mCamera);
				this.mSceneType = GameSceneType.OUTDOOR;
				break;
				
			case WEBPAGE:
				this.currentScene = new SceneWebPage(this.mActivity, this.mCamera);
				this.mSceneType = GameSceneType.WEBPAGE;
				break;
			
			case TALKPAGE:
				this.currentScene = new SceneTalk(this.mActivity, this.mCamera);
				this.mSceneType = GameSceneType.TALKPAGE;
				break;
		}
		
		// 設定目前的Scene
		if(mSceneType != GameSceneType.SPLASH)
			this.applyScene();
	}
	
	public void applyScene(){
		this.mActivity.getEngine().setScene(this.currentScene);
	}
	
	public Scene getApplyScene(){
		return this.mActivity.getEngine().getScene();
	}
	
	public Scene getCurrentScene(){
		
		return currentScene;
	}



	
}
