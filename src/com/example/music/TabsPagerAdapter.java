package com.example.music;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {

	TabsPagerAdapter(FragmentManager f)
	{
		super(f);
	}
	@Override
	public Fragment getItem(int index) {
		// TODO Auto-generated method stub
		switch (index) {
        case 0:
            // Top Rated fragment activity
            return new firstfragment();
        case 1:
            // Games fragment activity
            return new seconfragment();
        case 2:
            // Movies fragment activity
            return new thirdfragment();
        }
 
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	

}
