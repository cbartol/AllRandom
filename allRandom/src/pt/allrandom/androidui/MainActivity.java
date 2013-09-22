package pt.allrandom.androidui;

import java.lang.reflect.Field;

import pt.allrandom.androidui.subactivities.LotteryActivity;
import pt.allrandom.androidui.subactivities.GenerateBingoActivity;
import pt.allrandom.androidui.subactivities.GenerateCardActivity;
import pt.allrandom.androidui.subactivities.GenerateCoinActivity;
import pt.allrandom.androidui.subactivities.GenerateDiceActivity;
import pt.allrandom.androidui.subactivities.GenerateNumberActivity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends ListActivity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SparseArray<String> options = new SparseArray<String>();
		options.put(MainOptionsListAdapter.NUMBER,getString(R.string.number));
		options.put(MainOptionsListAdapter.DICE,getString(R.string.dice));
		options.put(MainOptionsListAdapter.COIN,getString(R.string.coin));
		options.put(MainOptionsListAdapter.LOTTERY,getString(R.string.lottery));
		options.put(MainOptionsListAdapter.BINGO,getString(R.string.bingo));
		options.put(MainOptionsListAdapter.CARD,getString(R.string.card));
		
		setListAdapter(new MainOptionsListAdapter(this, options));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent;
		switch (position) {
		case MainOptionsListAdapter.NUMBER:
			intent = new Intent(MainActivity.this, GenerateNumberActivity.class);
			startActivity(intent);
			break;
		case MainOptionsListAdapter.DICE:
			intent = new Intent(MainActivity.this, GenerateDiceActivity.class);
			startActivity(intent);
			break;
		case MainOptionsListAdapter.COIN:
			intent = new Intent(MainActivity.this, GenerateCoinActivity.class);
			startActivity(intent);
			break;
		case MainOptionsListAdapter.LOTTERY:
			intent = new Intent(MainActivity.this, LotteryActivity.class);
			startActivity(intent);
			break;
		case MainOptionsListAdapter.CARD:
			intent = new Intent(MainActivity.this, GenerateCardActivity.class);
			startActivity(intent);
			break;
		case MainOptionsListAdapter.BINGO:
			intent = new Intent(MainActivity.this, GenerateBingoActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
 
	}
	
	public static int getResId(String variableName, Class<?> c) {

		try {
		    Field field = c.getField(variableName);
		    int drawableId = field.getInt(null);
		    return drawableId;
		}
		catch (Exception e) {
		    Log.e("MyTag", "Failure to get drawable id.", e);
		    return -1;
		}
	}
}
