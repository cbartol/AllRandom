package pt.allrandom.androidui;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainOptionsListAdapter extends BaseAdapter {
	public static final int NUMBER = 0;
	public static final int DICE = 1;
	public static final int COIN = 2;
	public static final int CARD = 3;
	public static final int BINGO = 4;
	public static final int LOTTERY = 5;
	private SparseArray<String> options;
	private Context context;
	
	public MainOptionsListAdapter(Context context, SparseArray<String> listData) {
		this.context = context;
		this.options = listData;
	}
		
	@Override
	public int getCount() {
		return options.size();
	}

	@Override
	public Object getItem(int position) {
		return options.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
			View rowView = inflater.inflate(R.layout.list_row_layout, parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.option);
			ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
			textView.setText(options.get(position));
	 
			// Change icon based on name
			switch (position) {
			case NUMBER:
				imageView.setImageResource(R.drawable.icon_123);
				break;
			case DICE:
				imageView.setImageResource(R.drawable.dice);
				break;
			case COIN:
				imageView.setImageResource(R.drawable.coin);
				break;
			case LOTTERY:
				imageView.setImageResource(R.drawable.lottery);
				break;
			case CARD:
				imageView.setImageResource(R.drawable.cards);
				break;
			case BINGO:
				imageView.setImageResource(R.drawable.bingo);
				break;

			default:
				imageView.setImageResource(R.drawable.default_logo);
				break;
			}
			return rowView;
	}
}
