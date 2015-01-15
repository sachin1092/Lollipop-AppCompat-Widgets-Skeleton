package com.sachinshinde.lollipopappcompatskeleton.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.callizto.xyztravelapp.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorInflater;

import java.util.ArrayList;
import java.util.List;

import static com.callizto.xyztravelapp.utils.LogUtils.makeLogTag;


/**
 * A list fragment representing a list of Persons. This fragment also supports
 * tablet devices by allowing list items to be given an 'activated' state upon
 * selection. This helps indicate which item is currently being viewed in a
 * {@link EventDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class EventsListFragment extends Fragment {

    private static final String TAG = makeLogTag(EventsListFragment.class);
    private static final int ANIM_DURATION = 250;
    private static final long ANIMATION_DURATION = 500;
    private Context mAppContext;
//    private RecyclerView mCollectionView;
    private TextView mEmptyView;
    private View mLoadingView;
    private boolean mWasPaused = false;
    private Bundle mArguments;
    private int mDefaultSessionColor;
    int itemCount = 0;

    public boolean canCollectionViewScrollUp() {
        return ViewCompat.canScrollVertically(mRecyclerView, -1);
    }

    @Override
    public void onPause() {
        super.onPause();
        mWasPaused = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWasPaused) {
            mWasPaused = false;
            LOGD(TAG, "Reloading data as a result of onResume()");
//            reloadSessionData(false);
        }
    }

	/**
     * The serialization (saved instance state) Bundle key representing the
	 * activated item position. Only used on tablets.
	 */
	private static final String STATE_ACTIVATED_POSITION = "activated_position";

	/**
	 * The fragment's current callback object, which is notified of list item
	 * clicks.
	 */
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * The current activated item position. Only used on tablets.
	 */
	private int mActivatedPosition = ListView.INVALID_POSITION;
    private boolean should_update = true;

    /**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemSelected(long l);
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {
		@Override
		public void onItemSelected(long id) {
		}
	};

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public EventsListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mDefaultSessionColor = getResources().getColor(R.color.default_session_color);

//		setListAdapter(new SimpleCursorAdapter(getActivity(),
//				R.layout.Event_listitem, null, new String[] {
//						Events.KEY_NAME, Events.KEY_IS_MY_LOC,
//						Events.KEY_GMT, Events.KEY_LAT, Events.KEY_LON }, new int[] { R.id.cardEventName,
//						R.id.cardIsMyEvent, R.id.cardGMT, R.id.cardLat, R.id.cardLon}, 0));


	}

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAppContext = getActivity().getApplicationContext();
    }

    Animator grow, shrink;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
//    private RecyclerView.Adapter<CustomViewHolder> mAdapter;
    RecyclerView.Adapter mAdapter;
    private List<Events> mItems;

    ViewGroup root;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        root = (ViewGroup) inflater.inflate(R.layout.fragment_Events, container, false);
        mEmptyView = (TextView) root.findViewById(R.id.empty_text);
        mLoadingView = root.findViewById(R.id.loading);
//        ((ImageButton)root.findViewById(R.id.ibAddEvent)).setColorFilter(getResources().getColor(R.color.theme_primary));
        setupAnimation();
//        root.findViewById(R.id.ibAddEvent).setVisibility(View.GONE);
        root.findViewById(R.id.ibAddEvent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View mView = root.findViewById(R.id.ibAddEvent);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(mView, (int) mView.getX() + mView.getWidth() / 2, (int) mView.getY() + mView.getHeight() / 2, mView.getWidth(), mView.getHeight());
//                Intent intent = new Intent(getActivity(), Suggest.class);
//                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            }
        });
        mRecyclerView = (RecyclerView)root.findViewById(R.id.rvMain);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mItems = new ArrayList<>();

        mAdapter = new RecyclerView.Adapter<CustomViewHolder>() {

            @Override
            public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_Event
                        , viewGroup, false);
                return new CustomViewHolder(view);
            }

            @Override
            public void onBindViewHolder(CustomViewHolder viewHolder, int position) {
                viewHolder.Event_name.setText(mItems.get(position).city_name);
//                viewHolder.is_my_loc.setText(mItems.get(position).is_my_loc);
//                viewHolder.gmt.setText(mItems.get(position).gmt);
//                viewHolder.lat.setText(mItems.get(position).lat);
//                viewHolder.lon.setText(mItems.get(position).lon);
            }

            @Override
            public long getItemId(int position) {
                return mItems.get(position).id;
            }

            @Override
            public int getItemCount() {
                return mItems.size();
            }
        };

//        Load the content
        getLoaderManager().initLoader(0, null, new LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                Log.d("TheWeatherApp", "onCreateLoader");
                return new CursorLoader(getActivity(),
                        EventsProvider.URI_EVENTS, Events.FIELDS, null, null,
                        null);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
                Log.d("TheWeatherApp", "onLoaderFinished");
//                mAdapter.swapCursor(c);
                if(should_update) {
//                    mItems = EventDBHandler.getInstance(getActivity()).getEvents();
                    mItems.clear();
                    c.moveToFirst();
                    for(int i = 0 ; i < c.getCount() ; i++) {
                        mItems.add(new Events(c));
                        c.moveToNext();
                    }

                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> arg0) {
                Log.d("TheWeatherApp", "onLoaderReset");
                if(should_update) {
                    mAdapter.notifyDataSetChanged();
                }
//                mAdapter.swapCursor(null);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        SwipeDismissRecyclerViewTouchListener touchListener =
                new SwipeDismissRecyclerViewTouchListener(
                        mRecyclerView,
                        new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    mLayoutManager.removeView(mLayoutManager.getChildAt(position));
                                    mItems.remove(position);
                                    mAdapter.notifyItemRemoved(position);
                                    should_update = false;
                                    EventDBHandler.getInstance(getActivity()).removeEvent(mItems.get(position));

                                }
//                                mAdapter.notifyDataSetChanged();
                            }
                        });
        mRecyclerView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        mRecyclerView.setOnScrollListener(touchListener.makeScrollListener());
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), "Clicked ", Toast.LENGTH_SHORT).show();
                        mCallbacks.onItemSelected(mAdapter.getItemId(position));
                    }
                }));

        return root;
	}

    private void setupAnimation() {
        grow = AnimatorInflater.loadAnimator(getActivity(), R.anim.noa_grow);
        grow.setDuration(ANIMATION_DURATION);
        grow.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                root.findViewById(R.id.ibAddEvent).setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Do nothing.
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // Do nothing.
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Do nothing.
            }
        });
        grow.setTarget(root.findViewById(R.id.ibAddEvent));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                grow.start();
            }
        }, 500);

        shrink = AnimatorInflater.loadAnimator(getActivity(), R.anim.noa_shrink);
        shrink.setDuration(ANIMATION_DURATION);
        shrink.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // Do nothing.
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                root.findViewById(R.id.ibAddEvent).setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // Do nothing.
            }
        });

        shrink.setTarget(root.findViewById(R.id.ibAddEvent));

        mActionBarAutoHideMinY = getResources().getDimensionPixelSize(
                R.dimen.fab_auto_hide_min_y);
        mActionBarAutoHideSensivity = getResources().getDimensionPixelSize(
                R.dimen.fab_auto_hide_sensivity);

//        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            final static int ITEMS_THRESHOLD = 1;
//            int lastFvi = 0;
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
////                super.onScrolled(recyclerView, dx, dy);
//                onMainContentScrolled(firstVisibleItem <= ITEMS_THRESHOLD ? 0 : Integer.MAX_VALUE,
//                        lastFvi - firstVisibleItem > 0 ? Integer.MIN_VALUE :
//                                lastFvi == firstVisibleItem ? 0 : Integer.MAX_VALUE
//                );
//                lastFvi = firstVisibleItem;
//            }
//
//            //            @Override
////            public void onScrollStateChanged(AbsListView absListView, int i) {
////
////            }
////
////            @Override
////            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//////                com.sachinshinde.theweatherapp.util.Log.d(firstVisibleItem, visibleItemCount, totalItemCount);
////
////                onMainContentScrolled(firstVisibleItem <= ITEMS_THRESHOLD ? 0 : Integer.MAX_VALUE,
////                        lastFvi - firstVisibleItem > 0 ? Integer.MIN_VALUE :
////                                lastFvi == firstVisibleItem ? 0 : Integer.MAX_VALUE
////                );
////                lastFvi = firstVisibleItem;
////
////
////            }
//        });

    }

    private int mActionBarAutoHideSensivity = 0;
    private int mActionBarAutoHideMinY = 0;
    private int mActionBarAutoHideSignal = 0;

    private void onMainContentScrolled(int currentY, int deltaY) {
        if (deltaY > mActionBarAutoHideSensivity) {
            deltaY = mActionBarAutoHideSensivity;
        } else if (deltaY < -mActionBarAutoHideSensivity) {
            deltaY = -mActionBarAutoHideSensivity;
        }

        if (Math.signum(deltaY) * Math.signum(mActionBarAutoHideSignal) < 0) {
            // deltaY is a motion opposite to the accumulated signal, so reset signal
            mActionBarAutoHideSignal = deltaY;
        } else {
            // add to accumulated signal
            mActionBarAutoHideSignal += deltaY;
        }



        boolean shouldShow = currentY < mActionBarAutoHideMinY ||
                (mActionBarAutoHideSignal <= -mActionBarAutoHideSensivity);

//        com.sachinshinde.theweatherapp.util.Log.d(shouldShow);
        if (!shouldShow) {
            if (root.findViewById(R.id.ibAddEvent).getVisibility() == View.VISIBLE)
                if (!shrink.isRunning())
                    shrink.start();
        } else {
            if (root.findViewById(R.id.ibAddEvent).getVisibility() == View.GONE)
                if (!grow.isRunning())
                    grow.start();
        }
    }

    public Context getBaseContext() {
        return mAppContext;
    }

    private void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
    }

    private void showEmptyView() {

        // Showing sessions as a result of search or filter, so say "No matching sessions."
        mEmptyView.setText("No Cities");
        mEmptyView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
    }
//

    private class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView Event_name;
        private TextView is_my_loc;
        private TextView gmt;
        private TextView lat;
        private TextView lon;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.Event_name = (TextView) itemView.findViewById(R.id.cardEventName);
//            this.is_my_loc = (TextView) itemView.findViewById(R.id.cardIsMyEvent);
//            this.gmt = (TextView) itemView.findViewById(R.id.cardGMT);
//            this.lat = (TextView) itemView.findViewById(R.id.cardLat);
//            this.lon = (TextView) itemView.findViewById(R.id.cardLon);
        }
    }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		// Restore the previously serialized activated item position.
		if (savedInstanceState != null
				&& savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            Log.d("TheWeatherApp", "restoring " + savedInstanceState
                    .getInt(STATE_ACTIVATED_POSITION));
//            setActivatedPosition(savedInstanceState
//					.getInt(STATE_ACTIVATED_POSITION));
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onDetach() {
		super.onDetach();

		// Reset the active callbacks interface to the dummy implementation.
		mCallbacks = sDummyCallbacks;
	}

//	@Override
//	public void onListItemClick(ListView listView, View view, int position,
//			long id) {
//		super.onListItemClick(listView, view, position, id);
//
//		// Notify the active callbacks interface (the activity, if the
//		// fragment is attached to one) that an item has been selected.
//		mCallbacks.onItemSelected(getListAdapter().getItemId(position));
//	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (mActivatedPosition != ListView.INVALID_POSITION) {
			// Serialize and persist the activated item position.
			outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
		}
	}

//	/**
//	 * Turns on activate-on-click mode. When this mode is on, list items will be
//	 * given the 'activated' state when touched.
//	 */
//	public void setActivateOnItemClick(boolean activateOnItemClick) {
//		// When setting CHOICE_MODE_SINGLE, ListView will automatically
//		// give items the 'activated' state when touched.
////		mRecyclerView.setChoiceMode(
////				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
////						: ListView.CHOICE_MODE_NONE);
//	}
//
//	private void setActivatedPosition(int position) {
////		if (position == ListView.INVALID_POSITION) {
////			getListView().setItemChecked(mActivatedPosition, false);
////		} else {
////			getListView().setItemChecked(position, true);
////		}
//
//		mActivatedPosition = position;
//	}

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.newEvent:
//                result = true;
                // Create a new person.
                Events p = new Events("","","","","", -1);
                EventDBHandler.getInstance(getActivity()).putEvent(p);
                // Open a new fragment with the new id
//                onItemSelected(p.id);
                return true;
            case R.id.action_remove:
//                mAdapter.removeItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void animateReload() {
        //int curTop = mCollectionView.getTop();
        mRecyclerView.setAlpha(0);
        //mCollectionView.setTop(getResources().getDimensionPixelSize(R.dimen.browse_sessions_anim_amount));
        //mCollectionView.animate().y(curTop).alpha(1).setDuration(ANIM_DURATION).setInterpolator(new DecelerateInterpolator());
        mRecyclerView.animate().alpha(1).setDuration(ANIM_DURATION).setInterpolator(new DecelerateInterpolator());
    }

}