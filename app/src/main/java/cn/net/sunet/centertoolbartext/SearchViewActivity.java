package cn.net.sunet.centertoolbartext;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:  Leland
 * Emial:   AC_Dreamer@163.com
 * Website: www.sunet.net.cn
 * Date:    2017/8/23
 * Function:
 */
public class SearchViewActivity extends AppCompatActivity {

	@BindView(R.id.tv_toolbar)
	TextView tvToolbar;
	@BindView(R.id.view_toolbar)
	Toolbar toolbar;
	@BindView(R.id.tv_content)
	TextView tvContent;

	private SearchView mSearchView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_new);
		ButterKnife.bind(this);

		String title = getIntent().getStringExtra("toolbarTitle") + "Activity";
		tvToolbar.setText(title);
		tvContent.setText(title);

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
		@Override
		public boolean onQueryTextSubmit(String query) {
			Toast.makeText(SearchViewActivity.this, "Search Data in Activity", Toast.LENGTH_SHORT).show();
			return false;
		}

		@Override
		public boolean onQueryTextChange(String newText) {
			return false;
		}
	};

	private void queryData(final MenuItem item) {
		SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		mSearchView = (SearchView) item.getActionView();
		mSearchView.setIconifiedByDefault(true);
		if (mSearchView != null) {
			mSearchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
			mSearchView.setOnQueryTextListener(onQueryTextListener);
			mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View view, boolean focused) {
					if (!focused && !mSearchView.isIconified()) {
						MenuItemCompat.collapseActionView(item);
					}
				}
			});
		}
	}
}
