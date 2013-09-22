package pt.allrandom.androidui.subactivities;

import java.util.Random;

import pt.allrandom.androidui.R;
import pt.allrandom.androidui.SettingsActivity;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GenerateNumberActivity extends Activity implements TextWatcher{
	private Random generator = new Random();
	private String lastResult = "";
	private EditText min_edit;
	private EditText max_edit;
	private Button gen_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generate_number);
		// Show the Up button in the action bar.
		setupActionBar();
		
		min_edit = (EditText) findViewById(R.id.min_edit);
		max_edit = (EditText) findViewById(R.id.max_edit);
		gen_button = (Button) findViewById(R.id.generate_number_btn);
		
		gen_button.setEnabled(false);
		
		min_edit.addTextChangedListener(this);
		max_edit.addTextChangedListener(this);
		
		lastResult = getString(R.string.default_result);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		if(savedInstanceState != null){
			savedInstanceState.putString("lastResult", lastResult);
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if(savedInstanceState != null){
			lastResult = savedInstanceState.getString("lastResult");
			TextView result_numb = (TextView) findViewById(R.id.result_numb);
			result_numb.setText(lastResult);
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
		getMenuInflater().inflate(R.menu.generate_number, menu);
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

	public void generateNumber(View v){
		try{
			int min = Integer.parseInt(min_edit.getText().toString());
			int max = Integer.parseInt(max_edit.getText().toString());
			int n = 0;
			TextView result = (TextView) findViewById(R.id.result_numb);
			if(max >= min){
				n = max - min;
				result.setText(String.valueOf(min+generator.nextInt(n+1)));
			} else {
				n = min - max;
				result.setText(String.valueOf(max+generator.nextInt(n+1)));
				min_edit.setText(String.valueOf(max));
				max_edit.setText(String.valueOf(min));
			}
			lastResult = result.getText().toString();
		} catch(RuntimeException e){
			Toast.makeText(this, getString(R.string.overflow) + " " + (Integer.MAX_VALUE -1), Toast.LENGTH_LONG).show();
		}

	}

	public void clear(View v){
		TextView result = (TextView) findViewById(R.id.result_numb);
		result.setText(R.string.default_result);
		lastResult = getString(R.string.default_result);
	}
	
	
	@Override
	public void afterTextChanged(Editable arg0) {
		if(min_edit.getText().toString().equals("") || max_edit.getText().toString().equals("")){
			gen_button.setEnabled(false);
		} else {
			gen_button.setEnabled(true);
		}
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
}
