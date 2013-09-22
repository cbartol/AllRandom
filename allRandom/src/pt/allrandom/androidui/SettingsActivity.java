package pt.allrandom.androidui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class SettingsActivity extends Activity {
	public static final boolean UNLOADED_REPEAT_VALUE = true;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		// Show the Up button in the action bar.
		setupActionBar();
		setupRepeatToggleButton();
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
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void setupRepeatToggleButton() {
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.user_properties), Context.MODE_PRIVATE);
		boolean on = sharedPref.getBoolean(getString(R.string.repeat_state_key), UNLOADED_REPEAT_VALUE);
		Log.i("SettingsActivity", "Repeating mode on? ["+on+"]");
		ToggleButton toggle = (ToggleButton) findViewById(R.id.repeat_mode_btn);
		toggle.setChecked(on);

		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedPreferences sharedPref = getSharedPreferences(getString(R.string.user_properties), Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putBoolean(getString(R.string.repeat_state_key), isChecked);
				editor.commit();
			}
		});		
	}

}
