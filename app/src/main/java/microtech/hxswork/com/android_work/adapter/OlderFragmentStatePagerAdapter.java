package microtech.hxswork.com.android_work.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import microtech.hxswork.com.android_work.ui.fragment.servicefragmentItem.ServiceitemFragment1;


/**
 *老人菜场的ViewPager适配器
 */
public class OlderFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private String[] tabTilte;

    public OlderFragmentStatePagerAdapter(FragmentManager fm, String[] tabTitle) {
        super(fm);
        this.tabTilte = tabTitle;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ServiceitemFragment1();
            case 1:
                return new ServiceitemFragment1();
            case 2:
                return new ServiceitemFragment1();
            case 3:
                return new ServiceitemFragment1();
            case 4:
                return new ServiceitemFragment1();
            case 5:
                return new ServiceitemFragment1();
            case 6:
                return new ServiceitemFragment1();
            case 7:
                return new ServiceitemFragment1();
            case 8:
                return new ServiceitemFragment1();
            case 9:
                return new ServiceitemFragment1();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTilte.length;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


    @Override
    public void finishUpdate(ViewGroup container) {
        try{
            super.finishUpdate(container);
        } catch (NullPointerException nullPointerException){
            System.out.println("Catch the NullPointerException in FragmentPagerAdapter.finishUpdate");
        }
    }
}