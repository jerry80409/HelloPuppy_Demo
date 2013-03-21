package com.example.GameScene;

import org.andengine.engine.camera.SmoothCamera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.helperclasses.InputText;
import org.andengine.input.touch.TouchEvent;
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

import com.example.GameObject.PetInfo;
import com.example.hellopuppy_09.MainActivity;

public class SceneNew extends GameScene implements IScene{

	// *Background Resource 
	private BitmapTextureAtlas bg_Space;
	private TextureRegion bg_TextureRegion;
	private Sprite title_bg; 
	
	// *Dog List	
	private TiledTextureRegion dogList_TextureRegion, dogListName_TextureRegion;
	private TiledSprite chooseDog, chooseType;
	
	// *Choose btn
	private TiledTextureRegion chooseBtn_TextureRegion;
		 
	// *InputText 
	private Font inputFont;
	private TiledTextureRegion edit_TextureRegion;
	private InputText petNameText;
	
	// *Gender btn
	private TiledTextureRegion btn_male_TextureRegion, btn_female_TextureRegion;
	private TiledSprite btn_male, btn_female;
	private TextureRegion male_ornament_TextureRegion, female__ornament_TextureRegion;
	private Sprite male_ornament, female_ornament;
	
	// *Favorite btn
	private TiledTextureRegion btn_toy_TextureRegion, btn_food_TextureRegion, btn_clothes_TextureRegion;
	
	// *tag
	private TextureRegion toyTag_TextureRegion, foodTag_TextureRegion, clothesTag_TextureRegion;
	private Sprite toyTag, foodTag, clothesTag;
	
	// *finish btn
	private TiledTextureRegion finish_btn_TextureRegion;
	
	// pet's info
	private String petName;
	private boolean petGender = true; 					// 預設true 為男生
	private int petType = 0;							// 預設為0
	private boolean[] petFavorite = new boolean[3];
	
	private GameSceneManager mGM = new GameSceneManager(mActivity, mCamera);
	
	public SceneNew(BaseGameActivity pActivity, SmoothCamera pCamera) {
		super(pActivity, pCamera);
		Debug.e("SceneNew");
	}

	public void onCreateResources(){
		// background
		bg_Space = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 1024, 1024, 
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
    	bg_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space, 
    			this.mActivity, "04_new_bg.jpg", 0, 0);
    	
    	// choose button
    	chooseBtn_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "04_choose_btn.png", 800, 0, 1, 2);
    	// dog List
    	dogList_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "04_dogList.png", 0, 480, 2, 2);
    	// dog List Name
    	dogListName_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "04_dogList_name.png", 800, 150, 2, 2);
    	// edit
    	edit_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
    			this.mActivity, "04_nameLine.png", 800, 202, 1, 1);
    	
    	// gender button
    	btn_male_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "04_male_btn.png", 800, 245, 1, 2);
    	btn_female_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "04_female_btn.png", 876, 245, 1, 2);
    	
    	// gender ornament
    	male_ornament_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space, 
    			this.mActivity, "04_boy_ornament.png", 875, 0);
    	female__ornament_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space, 
    			this.mActivity, "04_girl_ornament.png", 875, 40);
    	
    	// favorite button
    	btn_toy_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "04_like_toy.png", 444, 480, 1, 2);
    	btn_food_TextureRegion =  BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "04_like_food.png", 520, 480, 1, 2);
    	btn_clothes_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "04_like_clothes.png", 596, 480, 1, 2);
    	
    	// tag
    	toyTag_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space, 
    			this.mActivity, "04_toys_tag.png", 444, 640);
    	foodTag_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space, 
    			this.mActivity, "04_food_tag.png", 444, 721);
    	clothesTag_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bg_Space, 
    			this.mActivity, "04_clothes_tag.png", 444, 802);
    	
    	// finish button
    	finish_btn_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bg_Space, 
				this.mActivity, "04_finish_btn.png", 672, 480, 1, 2);
    	
    	bg_Space.load();
    	
    	// input Font 
    	inputFont = FontFactory.create(this.mActivity.getFontManager(), this.mActivity.getTextureManager(),
                256, 256, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 28);    	
		inputFont.load();
		
		
	}
	
	public void onCreateScene(){
		
		VertexBufferObjectManager mBufferObjectManager = this.mActivity.getVertexBufferObjectManager(); 
		
		// 建立Background
		title_bg = new Sprite(0, 0, bg_TextureRegion, mBufferObjectManager);
		this.attachChild(title_bg);
		
		boolean fileExist = PetInfo.loadPetInfo(mActivity);
		if(fileExist != false){
			Debug.e("FileExist:" + fileExist);
			this.considerPage(mBufferObjectManager);
		}
		else{
			// choose button
			this.createChooseButton(mBufferObjectManager);
			
			// Dog List
			this.createDogList(mBufferObjectManager);
			
			// input pet's name
			this.createNameLine(mBufferObjectManager);
			
			// gender button
			this.createGenderButton(mBufferObjectManager);
			
			// tag
			this.createTag(mBufferObjectManager);
			
			// Favorite button
			this.createFavoriteButton(mBufferObjectManager);
			
			// Finish button
			this.createFinishButton(mBufferObjectManager);
		}
		
	}
	
	public void registerUpdateHandler(){
		
	}
	
	private void considerPage(final VertexBufferObjectManager pBufferObjectManager){
		this.detachChild(title_bg);
		this.setBackground(new Background(1, 1, 1, 0.5f));
		
		// =============================
		BitmapTextureAtlas consider_Space;
		TextureRegion poolPuppy_TextureRegion;
		TiledTextureRegion delete_TextureRegion, cancle__TextureRegion;
		
		consider_Space = new BitmapTextureAtlas(this.mActivity.getTextureManager(), 512, 512, 
				TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		poolPuppy_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(consider_Space, 
    			this.mActivity, "04_consider_puppy.png", 0, 0);
		delete_TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(consider_Space, 
				this.mActivity, "04_consider_delete.png", 268, 0, 1, 2);
		cancle__TextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(consider_Space, 
				this.mActivity, "04_consider_cancle.png", 268, 164, 1, 2);
		consider_Space.load();
		
		Sprite poolPuppy = new Sprite((camera_W-poolPuppy_TextureRegion.getWidth())*0.5f, 
				(camera_H-poolPuppy_TextureRegion.getHeight())*0.5f, 
				poolPuppy_TextureRegion, pBufferObjectManager);
		
		ButtonSprite delete = new ButtonSprite(436, 371, delete_TextureRegion, 
				pBufferObjectManager, new OnClickListener(){
					@Override
					public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
						PetInfo.setPetName(null);
						PetInfo.savePetInfo(mActivity);
						mGM.creatingScene(GameSceneType.CREATING_PET);
					}
			
		});
		ButtonSprite cancle = new ButtonSprite(200, 371, cancle__TextureRegion,
				pBufferObjectManager, new OnClickListener(){
					@Override
					public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
						mGM.creatingScene(GameSceneType.HOME);
					}
		});
		this.attachChild(poolPuppy);
		this.attachChild(delete);
		this.attachChild(cancle);
		this.registerTouchArea(delete);
		this.registerTouchArea(cancle);
	}
	
	// finish button
	private void createFinishButton(final VertexBufferObjectManager pBufferObjectManager){
		
		ButtonSprite finish = new ButtonSprite(458, 390, finish_btn_TextureRegion, 
				pBufferObjectManager, new OnClickListener(){
					
					@Override
					public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
						
						// save pet info
						SceneNew.this.svaePetInfo();
						
						// 切換回主要場景
						GameSceneManager mGM = new GameSceneManager(mActivity, mCamera);
						mGM.creatingScene(GameSceneType.HOME);
					}
		});
		
		this.attachChild(finish);
		this.registerTouchArea(finish);
	}
	
	private void svaePetInfo(){
		petName = petNameText.getText();
		
		// 要按照步驟來喔... 要不然會爆炸
		PetInfo.setPetInfo(petName, petType, petGender, petFavorite);
		PetInfo.savePetInfo(mActivity);
	}
	
	
	// Tag
	private void createTag(final VertexBufferObjectManager pBufferObjectManager){
		
		toyTag = new Sprite(252, 110, toyTag_TextureRegion, pBufferObjectManager); 
		foodTag = new Sprite(280, 280, foodTag_TextureRegion, pBufferObjectManager);  
		clothesTag = new Sprite(44, 286, clothesTag_TextureRegion, pBufferObjectManager); 
		
		this.attachChild(toyTag);
		this.attachChild(foodTag);
		this.attachChild(clothesTag);
		this.toyTag.setVisible(false);
		this.foodTag.setVisible(false);
		this.clothesTag.setVisible(false);
	}
	
	// Favoriet button
	private void createFavoriteButton(final VertexBufferObjectManager pBufferObjectManager){
		
		TiledSprite btn_toy = new TiledSprite(448, 267, btn_toy_TextureRegion, pBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.getAction()== TouchEvent.ACTION_UP)
				{
					if(this.getCurrentTileIndex() == 0){
						this.setCurrentTileIndex(1);
						SceneNew.this.toyTag.setVisible(true);
						SceneNew.this.petFavorite[0] = true;
					}
					else{
						this.setCurrentTileIndex(0);
						SceneNew.this.toyTag.setVisible(false);
						SceneNew.this.petFavorite[0] = false;
					}	
				}
				return true;
			}
		};
		
		TiledSprite btn_food = new TiledSprite(569, 267, btn_food_TextureRegion, pBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.getAction()== TouchEvent.ACTION_UP)
				{
					if(this.getCurrentTileIndex() == 0){
						this.setCurrentTileIndex(1);
						SceneNew.this.foodTag.setVisible(true);
						SceneNew.this.petFavorite[1] = true;
					}
					else{
						this.setCurrentTileIndex(0);
						SceneNew.this.foodTag.setVisible(false);
						SceneNew.this.petFavorite[1] = false;
					}
				}
				return true;
			}
		};
		
		TiledSprite btn_clothes = new TiledSprite(690, 267, btn_clothes_TextureRegion, pBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.getAction()== TouchEvent.ACTION_UP)
				{
					if(this.getCurrentTileIndex() == 0){
						this.setCurrentTileIndex(1);
						SceneNew.this.clothesTag.setVisible(true);
						SceneNew.this.petFavorite[2] = true;
					}
					else{
						this.setCurrentTileIndex(0);
						SceneNew.this.clothesTag.setVisible(false);
						SceneNew.this.petFavorite[0] = false;
					}
				}
				return true;
			}
		};
		
		this.attachChild(btn_toy);
		this.attachChild(btn_food);
		this.attachChild(btn_clothes);
		this.registerTouchArea(btn_toy);
		this.registerTouchArea(btn_food);
		this.registerTouchArea(btn_clothes);
		
	}
	
	// gender button
	private void createGenderButton(final VertexBufferObjectManager pBufferObjectManager){
		
		male_ornament = new Sprite(105, 228, male_ornament_TextureRegion, pBufferObjectManager);
		this.attachChild(male_ornament);
		female_ornament = new Sprite(168, 164, female__ornament_TextureRegion, pBufferObjectManager);
		this.attachChild(female_ornament);

		// 預設female 裝飾不可見
		this.female_ornament.setVisible(false);
		
		btn_male = new TiledSprite(569, 133, btn_male_TextureRegion, pBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.getAction()== TouchEvent.ACTION_UP)
				{								
					if(this.getCurrentTileIndex() == 0){
						this.setCurrentTileIndex(1);
						btn_female.setCurrentTileIndex(0);
						SceneNew.this.female_ornament.setVisible(false);
						SceneNew.this.male_ornament.setVisible(true);
						SceneNew.this.petGender = true;
					}
					else{
						this.setCurrentTileIndex(0);
						btn_female.setCurrentTileIndex(1);
						SceneNew.this.male_ornament.setVisible(false);
						SceneNew.this.female_ornament.setVisible(true);
						SceneNew.this.petGender = false;
					}
				}
				return true;
			}
		};
		
		// 預設Gender male  
		this.btn_male.setCurrentTileIndex(1);
		
		btn_female = new TiledSprite(690, 133, btn_female_TextureRegion, pBufferObjectManager){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.getAction()== TouchEvent.ACTION_UP)
				{								
					if(this.getCurrentTileIndex() == 0){
						this.setCurrentTileIndex(1);
						btn_male.setCurrentTileIndex(0);	
						SceneNew.this.male_ornament.setVisible(false);
						SceneNew.this.female_ornament.setVisible(true);
						SceneNew.this.petGender = false;
					}
					else{
						this.setCurrentTileIndex(0);
						btn_male.setCurrentTileIndex(1);	
						SceneNew.this.female_ornament.setVisible(false);
						SceneNew.this.male_ornament.setVisible(true);
						SceneNew.this.petGender = true;
					}
				}
				return true;
			}
		};		
			
		this.attachChild(btn_male);
		this.attachChild(btn_female);
		this.registerTouchArea(btn_female);
		this.registerTouchArea(btn_male);		
	}
	
	// Name Line
	private void createNameLine(final VertexBufferObjectManager pBufferObjectManager){
		
		petNameText = new InputText(560, 60, "設定寵物姓名", "點選下列方塊輸入", 
				edit_TextureRegion, inputFont, 13, 0, pBufferObjectManager, mActivity);
		petNameText.setText("設定寵物姓名");
		
		this.attachChild(petNameText);
		this.registerTouchArea(petNameText);
	}
	
	// dog List
	private void createDogList(final VertexBufferObjectManager pBufferObjectManager){
		
		chooseDog = new TiledSprite(94, 104, dogList_TextureRegion, pBufferObjectManager);
		chooseType = new TiledSprite(157, 411, dogListName_TextureRegion, pBufferObjectManager);
		
		this.attachChild(chooseDog);
		this.attachChild(chooseType);
	}

	// choose button
	private void createChooseButton(final VertexBufferObjectManager pBufferObjectManager){
		
		ButtonSprite leftBtn = new ButtonSprite(54, 390, chooseBtn_TextureRegion, pBufferObjectManager
				, new ChooseDogListener(0));
		
		ButtonSprite rightBtn = new ButtonSprite(272, 390, chooseBtn_TextureRegion, pBufferObjectManager
				, new ChooseDogListener(1));

		rightBtn.setRotation(180);
		
		this.attachChild(leftBtn);
		this.attachChild(rightBtn);
		this.registerTouchArea(leftBtn);
		this.registerTouchArea(rightBtn);
	}
	
	class ChooseDogListener implements OnClickListener{
		
		private int mAspect = 0;
				
		public ChooseDogListener(int pAspect){
			this.mAspect = pAspect;			
		}

		@Override
		public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
			MainActivity.playSound(SoundType.CLICKING);
			if(this.mAspect == 0){	// 左選擇鍵
				if(chooseType.getCurrentTileIndex() == 0){
					chooseType.setCurrentTileIndex(3);
					chooseDog.setCurrentTileIndex(3);
				}
				else{
					chooseType.setCurrentTileIndex(chooseType.getCurrentTileIndex()-1);
					chooseDog.setCurrentTileIndex(chooseDog.getCurrentTileIndex()-1);
				}
				
			}else{					// 右選擇鍵
				if(chooseType.getCurrentTileIndex() == 3){
					chooseType.setCurrentTileIndex(0);
					chooseDog.setCurrentTileIndex(0);
				}
				else{
					chooseType.setCurrentTileIndex(chooseType.getCurrentTileIndex()+1);
					chooseDog.setCurrentTileIndex(chooseDog.getCurrentTileIndex()+1);
				}
			}
			
			// 取得目前寵物的type
			SceneNew.this.petType = chooseType.getCurrentTileIndex();
		}
		
	}
}
