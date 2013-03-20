package com.example.GameScene;

public enum GameSceneType {

	SPLASH,					// Logo
	TITLE,					// Title 畫面, touch to start
	STORY,					// 簡單的story 介紹
	CREATING_PET,			// 建立寵物, 讀寫個人遊戲資料
	HOME,					// 主要遊戲頁面, 使用者互動(餵食、訓練、Play)
	OUTDOOR,					// 外出, 與人互動, 即時連線, (Socket, 定位)
	WEBPAGE,
	TALKPAGE
}
