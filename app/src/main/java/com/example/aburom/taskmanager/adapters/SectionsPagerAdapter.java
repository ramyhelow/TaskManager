package com.example.aburom.taskmanager.adapters;

import com.example.aburom.taskmanager.fragments.HomeFragment;
import com.example.aburom.taskmanager.fragments.TasksFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1: return new TasksFragment();
            case 0: return new HomeFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}