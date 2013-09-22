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

public class GenerateDiceActivity extends Activity {
	private static final int LAST_RES_PIC_MAX_HEIGHT = 150;
	private static final int DICE_N_FACES = 6;
	private static final int NO_DICE = 0;
	private static final String DEFAULT_DICE_NAME = "dice_";
	private ArrayList<String> lastRolls;
	private Random generator = new Random();
	private String lastResult = "";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generate_dice);
		// Show the Up button in the action bar.
		setupActionBar();
		clear(null);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		if(savedInstanceState != null){
			savedInstanceState.putString("lastResult", lastResult);
			savedInstanceState.putStringArrayList("lastRolls", lastRolls);
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if(savedInstanceState != null){
			lastResult = savedInstanceState.getString("lastResult");
			lastRolls = savedInstanceState.getStringArrayList("lastRolls");
			int drawableId = MainActivity.getResId(lastResult, R.drawable.class);
			ImageView iv= (ImageView)findViewById(R.id.image_dice_view);
			iv.setImageResource(drawableId);
			showAllRolls();
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
		getMenuInflater().inflate(R.menu.generate_dice, menu);
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

	public void generateDice(View v){
			int roll = 1+generator.nextInt(DICE_N_FACES);
			lastResult = DEFAULT_DICE_NAME + roll;
			lastRolls.add(lastResult);
			int drawableId = MainActivity.getResId(DEFAULT_DICE_NAME + roll, R.drawable.class);
			ImageView iv= (ImageView)findViewById(R.id.image_dice_view);
			iv.setImageResource(drawableId);
			addRollToLayout(drawableId);
	}

	public void clear(View v){
		lastRolls = new ArrayList<String>();
		lastResult = DEFAULT_DICE_NAME + NO_DICE;
		ImageView iv= (ImageView)findViewById(R.id.image_dice_view);
		iv.setImageResource(R.drawable.dice_0);
		showAllRolls();
	}
	
	private void addRollToLayout(int drawableId) {
		LinearLayout taken = (LinearLayout) findViewById(R.id.last_roll_layout);
		ImageView takenDice = new ImageView(this);
		takenDice.setMaxHeight(LAST_RES_PIC_MAX_HEIGHT);
		takenDice.setAdjustViewBounds(true);
		takenDice.setImageResource(drawableId);
		taken.addView(takenDice,0);
	}

	private void showAllRolls(){
		LinearLayout taken = (LinearLayout) findViewById(R.id.last_roll_layout);
		taken.removeAllViews();
		for(String dice : lastRolls){
			int drawableId = MainActivity.getResId(dice, R.drawable.class);
			ImageView takenDice = new ImageView(this);
			takenDice.setMaxHeight(LAST_RES_PIC_MAX_HEIGHT);
			takenDice.setAdjustViewBounds(true);
			takenDice.setImageResource(drawableId);
			taken.addView(takenDice,0);
		}
	}
}
