package pt.allrandom.androidui.subactivities;

import java.util.ArrayList;
import java.util.Random;

import pt.allrandom.androidui.MainActivity;
import pt.allrandom.androidui.R;
import pt.allrandom.androidui.SettingsActivity;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GenerateCoinActivity extends Activity {
	private static final int LAST_RES_PIC_MAX_HEIGHT = 150;
	private static final String DEFAULT_COIN_NAME = "coin_";
	private static final int NO_COIN = 0;
	private ArrayList<String> lastToss;
	private Random generator = new Random();
	private String lastResult = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generate_coin);
		// Show the Up button in the action bar.
		setupActionBar();
		clear(null);
	}

	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		if(savedInstanceState != null){
			savedInstanceState.putString("lastResult", lastResult);
			savedInstanceState.putStringArrayList("lastToss", lastToss);
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if(savedInstanceState != null){
			lastResult = savedInstanceState.getString("lastResult");
			lastToss = savedInstanceState.getStringArrayList("lastToss");
			int drawableId = MainActivity.getResId(lastResult, R.drawable.class);
			ImageView iv= (ImageView)findViewById(R.id.image_coin_view);
			iv.setImageResource(drawableId);
			showAllToss();
		}
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.generate_coin, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.action_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void generateCoin(View v){
		int result = 0;
		if(generator.nextBoolean()){
			result = 1;
		} else {
			result = 2;
		}
		lastResult = DEFAULT_COIN_NAME + result;
		lastToss.add(lastResult);
		int drawableId = MainActivity.getResId(lastResult, R.drawable.class);
		ImageView iv= (ImageView)findViewById(R.id.image_coin_view);
		iv.setImageResource(drawableId);
		addCoinFaceToLayout(drawableId);
	}
	public void clear(View v){
		lastToss = new ArrayList<String>();
		lastResult = DEFAULT_COIN_NAME + NO_COIN;
		ImageView iv= (ImageView)findViewById(R.id.image_coin_view);
		iv.setImageResource(R.drawable.coin_0);
		showAllToss();
	}
	
	private void addCoinFaceToLayout(int drawableId) {
		LinearLayout taken = (LinearLayout) findViewById(R.id.last_coin_layout);
		ImageView takenCoin = new ImageView(this);
		takenCoin.setMaxHeight(LAST_RES_PIC_MAX_HEIGHT);
		takenCoin.setAdjustViewBounds(true);
		takenCoin.setImageResource(drawableId);
		taken.addView(takenCoin,0);
	}

	private void showAllToss(){
		LinearLayout taken = (LinearLayout) findViewById(R.id.last_coin_layout);
		taken.removeAllViews();
		for(String coin : lastToss){
			int drawableId = MainActivity.getResId(coin, R.drawable.class);
			ImageView takenCoin = new ImageView(this);
			takenCoin.setMaxHeight(LAST_RES_PIC_MAX_HEIGHT);
			takenCoin.setAdjustViewBounds(true);
			takenCoin.setImageResource(drawableId);
			taken.addView(takenCoin,0);
		}
	}

}
