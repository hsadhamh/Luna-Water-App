package factor.app.luna;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hassanhussain on 7/8/2016.
 */
public class FragmentStateAdapter extends FragmentStatePagerAdapter {
    List<Fragment> moTriViews = new ArrayList<>();

    public FragmentStateAdapter(FragmentManager fm, List<Fragment> listViews) {
        super(fm);
        moTriViews.addAll(listViews);
    }

    @Override
    public int getItemPosition(Object object) { return POSITION_NONE; }

    @Override
    public Fragment getItem(int position) {
        return moTriViews.get(position);
    }

    @Override
    public int getCount() {
        return moTriViews.size();
    }
}
