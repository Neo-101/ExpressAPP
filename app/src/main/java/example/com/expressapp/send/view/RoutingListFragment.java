/* Copyright 2015 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package example.com.expressapp.send.view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import example.com.expressapp.send.model.MyAdapter;
import example.com.expressapp.R;

/*
 * This fragment populates the Navigation Drawer with
 * a customized listview and also provides the interface 
 * for communicating with the activity.
 */
public class RoutingListFragment extends Fragment implements
		ListView.OnItemClickListener, TextToSpeech.OnInitListener {
	public static ListView mDrawerList;
	onDrawerListSelectedListener mCallback;
	private TextToSpeech textToSpeech;
	private boolean isSoundOn = true;

	// Container Activity must implement this interface
	public interface onDrawerListSelectedListener {
		void onSegmentSelected(String segment);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mCallback = (onDrawerListSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement onDrawerListSelectedListener");
		}
		setHasOptionsMenu(true);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		textToSpeech = new TextToSpeech(getActivity(), this);

		MyAdapter adapter = new MyAdapter(getActivity(),
				SendActivity.curDirections);
		mDrawerList = (ListView) getActivity().findViewById(R.id.send_activity_layout_route_list);

		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(this);

		SwitchCompat sound_toggle = (SwitchCompat) getActivity().findViewById(R.id.send_activity_layout_switch);
		sound_toggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				isSoundOn = isChecked;
			}
		});

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		((MyAdapter)mDrawerList.getAdapter()).setSelectedItemID(position);
		TextView segment = (TextView) view.findViewById(R.id.send_route_list_item_textview);
		SendActivity.mDrawerLayout.closeDrawers();
		if (isSoundOn)
			speakOut(segment.getText().toString());
		mCallback.onSegmentSelected(segment.getText().toString());
		((MyAdapter)mDrawerList.getAdapter()).notifyDataSetChanged();
	}

	private void speakOut(String text) {
		textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu,inflater);
	}

/*	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.direction:
			if (SendActivity.mDrawerLayout.isDrawerOpen(GravityCompat.END)) {//原来是Gravity.END
				SendActivity.mDrawerLayout.closeDrawers();
			} else {
				SendActivity.mDrawerLayout.openDrawer(GravityCompat.END);//原来是Gravity.END
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}*/

	@Override
	public void onInit(int status) {}

	@Override
	public void onStop() {
		super.onStop();
		textToSpeech.shutdown();
	}

}
