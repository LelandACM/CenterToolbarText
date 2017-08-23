package cn.net.sunet.centertoolbartext;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author:  Leland
 * Emial:   AC_Dreamer@163.com
 * Website: www.sunet.net.cn
 * Date:    2017/8/15
* Function: the fragment page for view page
 */

public class NewFragment extends SimpleFragment {

	@BindView(R.id.tv_content)
	TextView tvContent;
	@BindView(R.id.view_toolbar)
	Toolbar toolbar;
	@BindView(R.id.tv_toolbar)
	TextView tvToolbar;

	private String title;
	private SearchView mSearchView;

	public static NewFragment newInstance(String title) {

		Bundle args = new Bundle();

		NewFragment fragment = new NewFragment();
		fragment.title = title;
		fragment.setArguments(args);
		return fragment;
	}

	public static NewFragment newInstance() {
		return newInstance(null);
	}

	@Override
	protected int getLayoutId() {
		return R.layout.fragment_new;
	}

	@Override
	protected void initEventAndData() {
		if (TextUtils.isEmpty(title)) {
			title = getString(R.string.app_name);
		}
		//set textView content
		tvContent.setText(title);

		//set toolbar textView content
		tvToolbar.setText(title);

		//toolbar add menu
		toolbar.inflateMenu(R.menu.menu_search);
		toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
	}

	//toolbar search menu click listener
	private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			if (item.getItemId() == R.id.action_search) {
				queryData(item);
			}
			return false;
		}
	};

	//on query text listener
	private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
		@Override //submit the search condition
		public boolean onQueryTextSubmit(String query) {
			Toast.makeText(getContext(), "Search Data in fragment", Toast.LENGTH_SHORT).show();
			return false;
		}

		@Override //the search condition once changed will invoke this funtion
		public boolean onQueryTextChange(String newText) {
			return false;
		}
	};

	private void queryData(final MenuItem item) {
		SearchManager manager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
		mSearchView = (SearchView) item.getActionView();
		if (mSearchView != null) {
			mSearchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));

			//add listener of query text condition
			mSearchView.setOnQueryTextListener(onQueryTextListener);

			//the focus of the query condition box once changed , will call this funtion
			mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean focused) {
					//charge the focus is changed and the searchview icon isn't iconified
					if (!focused && !mSearchView.isIconified()) {
						//in the fragment you mus you this way can solve it
						MenuItemCompat.collapseActionView(item);
					}
				}
			});
		}
	}

	@OnClick(R.id.tv_content)
	public void onClick() {
		Intent intent = new Intent(getContext(), SearchViewActivity.class);
		intent.putExtra("toolbarTitle", title);
		startActivity(intent);
	}
}
