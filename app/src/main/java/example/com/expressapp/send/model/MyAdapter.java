
package example.com.expressapp.send.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import example.com.expressapp.R;

/*
 * Custom Adapter for the list view
 * 
 */
public class MyAdapter extends ArrayAdapter<String> {

	Context context;
	ArrayList<String> directions;

	public MyAdapter(Context c, ArrayList<String> currDirections) {
		super(c, R.layout.send_route_list_item, R.id.send_route_list_item_textview, currDirections);
		this.context = c;
		directions = currDirections;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = inflater.inflate(R.layout.send_route_list_item, parent, false);
		ImageView myImage = (ImageView) row.findViewById(R.id.send_route_list_item_imageview);

		String segment = directions.get(position);

		//For segment icons on the list.
		if (segment.contains("left")) {
			myImage.setImageResource(R.drawable.ic_route_list_left);

		} else if (segment.contains("right")) {
			myImage.setImageResource(R.drawable.ic_route_list_right);

		} else if (segment.contains("Continue") || segment.contains("straight")
				|| segment.contains("Go") || segment.contains("ramp")) {
			myImage.setImageResource(R.drawable.ic_route_list_straight);

		} else if (segment.contains("U-turn")) {
			myImage.setImageResource(R.drawable.ic_route_list_uturn);
		}

		//For Starting and Ending Point icons
		if (position == 0) {
			myImage.setImageResource(R.drawable.ic_send_gps);
		}
		if (position == directions.size() - 1) {
			myImage.setImageResource(R.drawable.ic_cardview_address);

		}

		TextView myTitle = (TextView) row.findViewById(R.id.send_route_list_item_textview);
		myTitle.setText(segment);
		return row;
	}

}
