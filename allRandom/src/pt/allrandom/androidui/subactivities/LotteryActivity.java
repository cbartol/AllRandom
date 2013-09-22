package pt.allrandom.androidui.subactivities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

public class LotteryActivity extends Activity {
	private static final int MAX_STAR = 11;
	private static final int MAX_KEY = 50;
	private Random generator = new Random();
	private String lastResult = "";
	private String lastResultStar = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lottery);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		if(savedInstanceState != null){
			savedInstanceState.putString("lastResult", lastResult);
			savedInstanceState.putString("lastResultStar", lastResultStar);
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if(savedInstanceState != null){
			lastResult = savedInstanceState.getString("lastResult");
			lastResultStar = savedInstanceState.getString("lastResultStar");
			TextView result_ticket = (TextView) findViewById(R.id.result_lottery);
			result_ticket.setText(lastResult);
			TextView result_ticket_star = (TextView) findViewById(R.id.result_star_lottery);
			result_ticket_star.setText(lastResultStar);
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
		getMenuInflater().inflate(R.menu.lottery, menu);
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

	public void generateTicket(View v){
		TextView result_ticket = (TextView) findViewById(R.id.result_lottery);
		TextView result_ticket_star = (TextView) findViewById(R.id.result_star_lottery);
		Set<Integer> main_key = new HashSet<Integer>();
		Set<Integer> star_key = new HashSet<Integer>();
		while(main_key.size() < 5){
			main_key.add(1+generator.nextInt(MAX_KEY));
		}
		while(star_key.size() < 2){
			star_key.add(1+generator.nextInt(MAX_STAR));
		}

		//main key
		List<Integer> result = new ArrayList<Integer>(main_key);
		Collections.sort(result);
		String res = "";
		for(Integer i : result){
			res += i + "  ";
		}
		lastResult = res;
		result_ticket.setText(res);

		//star key
		result = new ArrayList<Integer>(star_key);
		Collections.sort(result);
		res = "";
		for(Integer i : result){
			res += i + "  ";
		}
		lastResultStar = res;
		result_ticket_star.setText(res);
	}

	public void clear(View v){
		TextView result_ticket = (TextView) findViewById(R.id.result_lottery);
		result_ticket.setText(R.string.default_result);
		TextView result_ticket_star = (TextView) findViewById(R.id.result_star_lottery);
		result_ticket_star.setText(R.string.default_result);
		lastResult = lastResultStar = getString(R.string.default_result);
	}
}
