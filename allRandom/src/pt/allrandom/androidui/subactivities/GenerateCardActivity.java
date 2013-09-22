package pt.allrandom.androidui.subactivities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import pt.allrandom.androidui.MainActivity;
import pt.allrandom.androidui.R;
import pt.allrandom.androidui.SettingsActivity;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GenerateCardActivity extends Activity {
	private static final int LAST_RES_PIC_MAX_HEIGHT = 200;
	private static final int N_NAIPES = 4;
	private static final int NAIPE_SIZE = 13;
	private static final int HEARTS = 0; 
	private static final int SPADES = 1;
	private static final int DIAMONDS = 2;
	private static final int CLUBS = 3;
	
	private static final String DEFAULT_CARD_NAME = "card_";
	private static final String BACK_CARD = "back_0";
	private boolean repeatMode;
	private ArrayList<String> takenCards;
	private ArrayList<String> remainingCards;
	private Random generator = new Random();
	private String lastResult = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generate_card);
		// Show the Up button in the action bar.
		setupActionBar();
		reset();
		lastResult = BACK_CARD;
	}

	@Override
	protected void onResume() {
		SharedPreferences sharedPref = getSharedPreferences(getString(R.string.user_properties), Context.MODE_PRIVATE);
		repeatMode = sharedPref.getBoolean(getString(R.string.repeat_state_key), SettingsActivity.UNLOADED_REPEAT_VALUE);
		Button btn = (Button)findViewById(R.id.clear_btn);
		if(repeatMode){
			btn.setText(R.string.clear);
			reset();
		} else {
			btn.setText(R.string.reset);
			showAllTakenCards();
		}
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		if(savedInstanceState != null){
			savedInstanceState.putString("lastResult", lastResult);
			savedInstanceState.putStringArrayList("takenCards", takenCards);
			savedInstanceState.putStringArrayList("remainingCards", remainingCards);
		}
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if(savedInstanceState != null){
			lastResult = savedInstanceState.getString("lastResult");
			takenCards = savedInstanceState.getStringArrayList("takenCards");
			remainingCards = savedInstanceState.getStringArrayList("remainingCards");
			showTakenCards();
			ImageView iv= (ImageView)findViewById(R.id.image_card_view);
			iv.setImageResource(MainActivity.getResId(lastResult, R.drawable.class));
		}
	}

	private void showTakenCards() {
		// TODO Auto-generated method stub
		
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
		getMenuInflater().inflate(R.menu.generate_card, menu);
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

	public void generateCard(View v){
		if(repeatMode){
			normalGenerateCard();
		} else {
			uniqueGenerateCard();
		}
	}

	private void uniqueGenerateCard() {
		ImageView result = (ImageView)findViewById(R.id.image_card_view);
		String card = DEFAULT_CARD_NAME+BACK_CARD;
		int drawableId = R.drawable.card_back_0;
		if(remainingCards.size() != 0){
			String n = remainingCards.remove(generator.nextInt(remainingCards.size()));
			takenCards.add(n);
			card = n;
			drawableId = MainActivity.getResId(card, R.drawable.class);
			addCardToTakenLayout(drawableId);
		}
		result.setImageResource(drawableId);
		lastResult = card;
		
	}

	private void normalGenerateCard() {
		int card = 1+generator.nextInt(NAIPE_SIZE);
		String deck = "";
		switch (generator.nextInt(N_NAIPES)) {
		case HEARTS:
			deck = "heart";
			break;
		case DIAMONDS:
			deck = "diamond";
			break;
		case CLUBS:
			deck = "club";
			break;
		case SPADES:
			deck = "spade";
			break;
			
		default:
			deck = "back";
			card = 0;
			break;
		}
		int drawableId = MainActivity.getResId(DEFAULT_CARD_NAME+deck + "_" + String.valueOf(card), R.drawable.class);
		ImageView iv= (ImageView)findViewById(R.id.image_card_view);
		iv.setImageResource(drawableId);
		
		lastResult = DEFAULT_CARD_NAME + deck + "_" + String.valueOf(card);
	}

	public void clear(View v){
		lastResult = DEFAULT_CARD_NAME + BACK_CARD;
		if(!repeatMode){
			reset();
		}
		ImageView iv= (ImageView)findViewById(R.id.image_card_view);
		iv.setImageResource(R.drawable.card_back_0);
	}
	
	private void reset() {
		takenCards = new ArrayList<String>();
		ArrayList<String> all = new ArrayList<String>();
		for(int card = 1 ; card <= NAIPE_SIZE ; card++){
			all.add(DEFAULT_CARD_NAME+ "heart" + "_" + String.valueOf(card));
		}
		for(int card = 1 ; card <= NAIPE_SIZE ; card++){
			all.add(DEFAULT_CARD_NAME+ "diamond" + "_" + String.valueOf(card));
		}
		for(int card = 1 ; card <= NAIPE_SIZE ; card++){
			all.add(DEFAULT_CARD_NAME+ "club" + "_" + String.valueOf(card));
		}
		for(int card = 1 ; card <= NAIPE_SIZE ; card++){
			all.add(DEFAULT_CARD_NAME+ "spade" + "_" + String.valueOf(card));
		}
	
		Collections.shuffle(all);
		remainingCards = all;
		showAllTakenCards();
	}
	
	private void addCardToTakenLayout(int drawableId) {
		LinearLayout taken = (LinearLayout) findViewById(R.id.taken_cards_layout);
		ImageView takenCard = new ImageView(this);
		takenCard.setMaxHeight(LAST_RES_PIC_MAX_HEIGHT);
		takenCard.setAdjustViewBounds(true);
		takenCard.setImageResource(drawableId);
		taken.addView(takenCard,0);
	}

	private void showAllTakenCards(){
		LinearLayout taken = (LinearLayout) findViewById(R.id.taken_cards_layout);
		taken.removeAllViews();
		for(String card : takenCards){
			int drawableId = MainActivity.getResId(card, R.drawable.class);
			ImageView takenCard = new ImageView(this);
			takenCard.setMaxHeight(LAST_RES_PIC_MAX_HEIGHT);
			takenCard.setAdjustViewBounds(true);
			takenCard.setImageResource(drawableId);
			taken.addView(takenCard,0);
		}
	}
	
}
