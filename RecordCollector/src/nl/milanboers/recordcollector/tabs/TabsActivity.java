package nl.milanboers.recordcollector.tabs;

import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class TabsActivity extends SherlockFragmentActivity implements TabListener {
	private ViewPager pager;
	
	protected void setupTabs(List<TabFragment> tabs, int pager) {
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// Add the fragments to the activity
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		for(TabFragment fragment : tabs)
		{
			fragmentTransaction.add(pager, fragment);
		}
		
		fragmentTransaction.commit();
		
		// Make a pagerAdapter and pass the simple master
		FragmentPagerAdapter pagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), tabs);
		
		// Make a pager
		this.pager = (ViewPager) findViewById(pager);
		this.pager.setAdapter(pagerAdapter);
		this.pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int pos) {
				actionBar.setSelectedNavigationItem(pos);
			}
		});
		
		// Create the tabs
		for(TabFragment tab : tabs) {
			Tab t = actionBar.newTab().setText(tab.getNameId()).setTabListener(this);
			actionBar.addTab(t);
		}
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		this.pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}
}
