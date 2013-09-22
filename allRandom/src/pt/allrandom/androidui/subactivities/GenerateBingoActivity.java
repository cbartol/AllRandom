package pt.allrandom.androidui.subactivities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
import android.widget.TextView;

public class GenerateBingoActivity extends Activity {
	private static final int GAME_LIMIT_1 = 90;
//	private static final int GAME_LIMIT_2 = 75;
//	private static final int GAME_LIMIT_3 = 30;
	private Random generator = new Random();
	private String lastResult = "";
	private ArrayList<Integer> takenNumbers;
	private ArrayList<Integer> remainingNumbers;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generate_bingo);
		// Show the Up button in the action bar.
		setupActionBar();
		
		
		reset(GAME_LIMIT_1);
		
		lastResult = getString(R.string.default_result);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		if(savedInstanceState != null){
			savedInstanceState.putString("lastResult", lastResult);
			savedInstanceState.putIntegerArrayList("takenNumbers", takenNumbers);
			savedInstanceState.putIntegerArrayList("remainingNumbers", remainingNumbers);
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if(savedInstanceState != null){
			lastResult = savedInstanceState.getString("lastResult");
			takenNumbers = savedInstanceState.getIntegerArrayList("takenNumbers");
			remainingNumbers = savedInstanceState.getIntegerArrayList("remainingNumbers");
			TextView result_dice = (TextView) findViewById(R.id.result_numb);
			result_dice.setText(lastResult);
			TextView takenResult = (TextView) findViewById(R.id.taken_numbers);
			takenResult.setText(getSortedString(takenNumbers));
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
		getMenuInflater().inflate(R.menu.generate_bingo, menu);
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

	public void generateUniqueNumber(View v){
		TextView result = (TextView) findViewById(R.id.result_numb);
		TextView takenResult = (TextView) findViewById(R.id.taken_numbers);
		String num;
		if(remainingNumbers.size() != 0){
			int n = remainingNumbers.remove(generator.nextInt(remainingNumbers.size()));
			takenNumbers.add(n);
			num = String.valueOf(n);
		} else {
			num = "END";
		}
		result.setText(num);
		takenResult.setText(getSortedString(takenNumbers));
		lastResult = result.getText().toString();
	}

	public void clear(View v){
		TextView result = (TextView) findViewById(R.id.result_numb);
		result.setText(R.string.default_result);
		lastResult = getString(R.string.default_result);
		reset(GAME_LIMIT_1);
		TextView takenResult = (TextView) findViewById(R.id.taken_numbers);
		takenResult.setText(getSortedString(takenNumbers));
	}
	
	private String getSortedString(List<Integer> num) {
		String out = "";
		Collections.sort(num);
		for(Integer i : num){
			out += i + "  ";
		}
		return out;
	}
	
	private void reset(int max) {
		takenNumbers = new ArrayList<Integer>();
		ArrayList<Integer> all = new ArrayList<Integer>();
		for(int i = 0 ; i < max ; i++){
			all.add(i+1);
		}
		Collections.shuffle(all);
		remainingNumbers = all;
	}

}
