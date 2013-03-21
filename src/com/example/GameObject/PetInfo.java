package com.example.GameObject;

import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.debug.Debug;

import android.content.SharedPreferences;

public class PetInfo {

	public static String petName = null;
	public static int petType;
	public static boolean petGender;
	public static boolean[] petFavorite = new boolean[3];
	
	public PetInfo(String pName, int pType, boolean pGender, boolean[] pFavorite){
		
		petName = pName;
		petType = pType;
		petGender = pGender;
		petFavorite = pFavorite;
		
	}
	
	public static void setPetInfo(String pName, int pType, boolean pGender, boolean[] pFavorite){
		PetInfo.setPetName(pName);
		PetInfo.setPetType(pType);
		PetInfo.setPetGender(pGender);
		PetInfo.setPetFavorite(pFavorite);
	}
	
	public static void savePetInfo(BaseGameActivity pActivity){
		
		SharedPreferences mSaveData;
		SharedPreferences.Editor mSaveEditor;
		
		/*
		 * "PetInitial" 表示存檔的名稱
		 * MODE_PRIVATE (0)表示應用程式專用；MODE_WORLD_WRITEABLE (1)
		 * */
	    mSaveData = pActivity.getSharedPreferences("PetInitial", 0);
	    mSaveEditor = mSaveData.edit();
	    
	    // data save
	    Debug.e("Save Data!!!");
		mSaveEditor.putString("PetName", getPetName());
		mSaveEditor.putBoolean("PetGender", petGender);
		mSaveEditor.putInt("PetType", petType);
		
		for(int i=0; i<petFavorite.length; i++){
			
			// 0:toy , 1:food, 2:cloth
			mSaveEditor.putBoolean("petInterest[" + i + "]", petFavorite[i]);
		}
		
		// sent data
		mSaveEditor.commit();
	}
	
	// 讀取檔案
	public static boolean loadPetInfo(BaseGameActivity pActivity){
			
		SharedPreferences mSaveData;
		mSaveData = pActivity.getSharedPreferences("PetInitial", 0);
			
		// data loading 
		Debug.e("==== Load Data!!!");
		
		// 設定姓名
		setPetName(mSaveData.getString("PetName", getPetName()));
		
		if(getPetName()!= null){
			
			// 設定性別
			setPetGender(mSaveData.getBoolean("PetGender", petGender));
			// 設定種類
			petType = mSaveData.getInt("PetType", petType);
		
			Debug.e("petName: " + petName);
			Debug.e("petGender: " + petGender);
			Debug.e("petType: "+ petType);
					
			// 設定最愛, 陣列長度寫死的 @3@
			for(int i=0; i<3; i++){
				
				// 取得 petInterest[] 裡面的資料, false為假設值, 會依據存檔數據而有所改變
				petFavorite[i] = mSaveData.getBoolean("petInterest[" + i + "]", false);
				Debug.e("petInterest[" + i + "]: "+petFavorite[i]);
			}
			Debug.e("載入你的狗狗");
			return true;
		}
		else{
			Debug.e("請創建你的狗狗");
			return false;
			
		}
		
	}
	public static String getPetName() {
		return petName;
	}

	public static void setPetName(String pPetName) {
		PetInfo.petName = pPetName;
	}	
	
	public static int getPetType(){
		return petType;
	}
	
	public static void setPetType(int pPetType){
		PetInfo.petType = pPetType;
	}
	
	public static boolean getPetGender(){
		return petGender;
	}
	
	public static void setPetGender(boolean pPetGender){
		PetInfo.petGender = pPetGender;
	}
	
	public static boolean[] getPetFavorite(){
		return petFavorite;
	}
	
	public static void setPetFavorite(boolean[] pPetFavorite){
		PetInfo.petFavorite = pPetFavorite;
	}
	
	public static void showInfo(){
		Debug.e("=================================================PetInfo");
		Debug.e("petName:" + PetInfo.petName);
		Debug.e("petType:" + PetInfo.petType);
		Debug.e("petGender:" + PetInfo.petGender);
		Debug.e("petFavorite[0]:" + PetInfo.petFavorite[0]);
		Debug.e("petFavorite[1]:" + PetInfo.petFavorite[1]);
		Debug.e("petFavorite[2]:" + PetInfo.petFavorite[2]);
		
	}


	
}
