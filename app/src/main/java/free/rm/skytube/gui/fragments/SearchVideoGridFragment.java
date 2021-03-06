/*
 * SkyTube
 * Copyright (C) 2016  Ramon Mifsud
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation (version 3 of the License).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package free.rm.skytube.gui.fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import free.rm.skytube.R;
import free.rm.skytube.businessobjects.MainActivityListener;
import free.rm.skytube.businessobjects.VideoCategory;
import free.rm.skytube.gui.businessobjects.LoadingProgressBar;
import free.rm.skytube.gui.businessobjects.VideoGridAdapter;

/**
 * Fragment that will hold a list of videos corresponding to the user's query.
 */
public class SearchVideoGridFragment extends BaseVideosGridFragment {

	public static final String QUERY = "SearchVideoGridFragment.Query";
	protected RecyclerView		gridView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// inflate the layout for this fragment
		View view = inflater.inflate(R.layout.videos_searchview, container, false);

		// setup the toolbar/actionbar
		Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		this.gridView = (RecyclerView) view.findViewById(R.id.grid_view);

		// set up the loading progress bar
		LoadingProgressBar.get().setProgressBar(view.findViewById(R.id.loading_progress_bar));

		if (videoGridAdapter == null) {
			videoGridAdapter = new VideoGridAdapter(getActivity());
			videoGridAdapter.setListener((MainActivityListener)getActivity());

			String searchQuery = getArguments().getString(QUERY);
			if (searchQuery != null) {
				// set the video category (if the user wants to search)... otherwise it will be set-
				// up by the VideoGridFragment
				this.videoGridAdapter.setVideoCategory(VideoCategory.SEARCH_QUERY, searchQuery);

				// set the action bar's title
				ActionBar actionBar = getSupportActionBar();
				if (actionBar != null)
					actionBar.setTitle(searchQuery);
			}
		}

		this.gridView.setHasFixedSize(false);
		this.gridView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.video_grid_num_columns)));
		this.gridView.setAdapter(this.videoGridAdapter);

		return view;
	}
}
