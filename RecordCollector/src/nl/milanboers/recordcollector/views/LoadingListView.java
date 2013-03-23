package nl.milanboers.recordcollector.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

public class LoadingListView extends ListView {
	public static interface Loader {
		public void load(int page);
		public boolean isLoading();
	};

	private int currentPage = 0;
	
	// Create dummy loader to prevent crashes when scrolling before a loader has been set
	private Loader loader = new Loader() {
		@Override
		public void load(int page) {}
		
		@Override
		public boolean isLoading() { return false; }
	};
	
	public LoadingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// Make scrolling auto-search
        this.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				// If it's not currently loading and it's at the end, load the next page
				if(!LoadingListView.this.loader.isLoading() && firstVisibleItem + visibleItemCount >= totalItemCount) {
					LoadingListView.this.loadNext();
				}
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {}
        });
	}
	
	public void setLoader(Loader loader) {
		this.loader = loader;
	}
	
	public void reset() {
		this.currentPage = 0;
	}
	
	public void loadNext() {
		if(loader == null)
			return;

		this.currentPage++;
		loader.load(currentPage);
	}

}
