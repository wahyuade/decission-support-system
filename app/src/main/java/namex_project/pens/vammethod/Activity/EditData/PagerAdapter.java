package namex_project.pens.vammethod.Activity.EditData;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import namex_project.pens.vammethod.Activity.EditData.Fragment.Cost.CostFragment;
import namex_project.pens.vammethod.Activity.EditData.Fragment.Destination.DestinationFragment;
import namex_project.pens.vammethod.Activity.EditData.Fragment.Source.SourceFragment;

/**
 * Created by SHERLY on 19/05/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    String id;

    public PagerAdapter (FragmentManager fm, int NumOfTabs, String id) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SourceFragment tabSource = new SourceFragment();
                tabSource.setId(id);
                return tabSource;
            case 1:
                DestinationFragment tabDestination = new DestinationFragment();
                tabDestination.setId(id);
                return tabDestination;
            case 2:
                CostFragment tabCost = new CostFragment();
                tabCost.setId(id);
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
