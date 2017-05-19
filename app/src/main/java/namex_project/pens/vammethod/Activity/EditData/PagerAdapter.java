package namex_project.pens.vammethod.Activity.EditData;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import namex_project.pens.vammethod.Activity.EditData.Fragment.CostFragment;
import namex_project.pens.vammethod.Activity.EditData.Fragment.DestinationFragment;
import namex_project.pens.vammethod.Activity.EditData.Fragment.SourceFragment;

/**
 * Created by SHERLY on 19/05/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PagerAdapter (FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SourceFragment tabSource = new SourceFragment();
                return tabSource;
            case 1:
                DestinationFragment tabDestination = new DestinationFragment();
                return tabDestination;
            case 2:
                CostFragment tabCost = new CostFragment();
                return tabCost;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
