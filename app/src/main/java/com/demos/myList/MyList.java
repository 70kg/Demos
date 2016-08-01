package com.demos.myList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Mr_Wrong on 16/3/8.
 */
public class MyList extends ViewGroup {
    public MyList(Context context) {
        super(context);
    }

    public MyList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int mFirstPosition;
    RecycleBin mRecycler;
    boolean mDataChanged;
    BaseAdapter mAdapter;
    int mItemCount = mAdapter.getCount();
    final boolean[] mIsScrap = new boolean[1];

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childrenTop = 0;
        final int childrenBottom = getMeasuredHeight();
        final int childCount = getChildCount();

        boolean dataChanged = mDataChanged;

        final int firstPosition = mFirstPosition;
        final RecycleBin recycleBin = mRecycler;
        if (dataChanged) {
            for (int i = 0; i < childCount; i++) {
                recycleBin.addScrapView(getChildAt(i));
            }
        } else {
            recycleBin.fillActiveViews(childCount, firstPosition);
        }

        detachAllViewsFromParent();

        if (childCount == 0) {
            fillFromTop(childrenTop);
        } else {
            if (mFirstPosition < mItemCount) {
                fillSpecific(mFirstPosition,childrenTop);
            } else {
                fillSpecific(0,childrenTop);
            }
        }


    }

    private View fillSpecific(int position, int top) {

        View temp = makeAndAddView(position, top);

        return temp;

    }

    private View fillFromTop(int nextTop) {

        View selectedView = null;
        int end = getBottom() - getTop();
        while (nextTop < end && mFirstPosition < mItemCount) {
            View child = makeAndAddView(mFirstPosition, nextTop);
            nextTop = child.getBottom();
            mFirstPosition++;
        }
        return selectedView;
    }

    private View makeAndAddView(int position, int y) {
        View child;
        if (!mDataChanged) {
            child = mRecycler.getActiveView(position);
            if (child != null) {
                setupChild(child, position, y, true);
                return child;
            }
        }
        child = obtainView(position, mIsScrap);
        setupChild(child, position, y, mIsScrap[0]);
        return child;
    }

    private void setupChild(View child, int position, int y, boolean recycled) {

        if (recycled) {
            attachViewToParent(child, 0, child.getLayoutParams());
        } else {
            addViewInLayout(child, 0, child.getLayoutParams(), true);
        }
        final int w = child.getMeasuredWidth();
        final int h = child.getMeasuredHeight();
        final int childTop = y;
        if (!recycled) {
            final int childRight =  w;
            final int childBottom = childTop + h;
            child.layout(0, childTop, childRight, childBottom);
        } else {
            child.offsetLeftAndRight(0 - child.getLeft());
            child.offsetTopAndBottom(childTop - child.getTop());
        }

    }


    View obtainView(int position, boolean[] isScrap) {
        View scrapView;
        scrapView = mRecycler.getScrapView(position);
        View child;
        if (scrapView != null) {
            child = mAdapter.getView(position, scrapView, this);
            if (child != scrapView) {
                mRecycler.addScrapView(scrapView);
            }
        } else {
            child = mAdapter.getView(position, null, this);
        }
        return child;
    }

    class RecycleBin {
        private int mFirstActivePosition;
        private View[] mActiveViews = new View[0];
        private ArrayList<View> mCurrentScrap;

        void fillActiveViews(int childCount, int firstActivePosition) {
            if (mActiveViews.length < childCount) {
                mActiveViews = new View[childCount];
            }
            mFirstActivePosition = firstActivePosition;
            final View[] activeViews = mActiveViews;
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                activeViews[i] = child;
            }
        }

        View getActiveView(int position) {
            int index = position - mFirstActivePosition;
            final View[] activeViews = mActiveViews;
            if (index >= 0 && index < activeViews.length) {
                final View match = activeViews[index];
                activeViews[index] = null;
                return match;
            }
            return null;
        }

        void addScrapView(View scrap) {
            mCurrentScrap.add(scrap);
        }

        View getScrapView(int position) {
            ArrayList<View> scrapViews = mCurrentScrap;
            int size = scrapViews.size();
            if (size > 0) {
                return scrapViews.remove(size - 1);
            } else {
                return null;
            }
        }
    }
}
