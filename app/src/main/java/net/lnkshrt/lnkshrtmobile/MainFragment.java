package net.lnkshrt.lnkshrtmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import net.lnkshrt.lnkshrtmobile.ManageLinks.ManageLinksFragment;
import net.lnkshrt.lnkshrtmobile.ShortenLinks.ShortenLinkFragment;

public class MainFragment extends Fragment {

    MainFragmentCollectionAdapter mainFragmentCollectionAdapter;
    ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        mainFragmentCollectionAdapter = new MainFragmentCollectionAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(mainFragmentCollectionAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);

        // TODO: Make this automatically pick up the number of existing tabs and get text
        CharSequence[] tabText = new CharSequence[]{
                tabLayout.getTabAt(0).getText(),
                tabLayout.getTabAt(1).getText()
        };

        TabLayoutMediator.TabConfigurationStrategy strategy = new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabText[position]);
            }
        };

        new TabLayoutMediator(tabLayout, viewPager, strategy).attach();
    }
}

class MainFragmentCollectionAdapter extends FragmentStateAdapter {
    public MainFragmentCollectionAdapter(Fragment fragment){
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;

        if(position == 0){
            fragment = new ShortenLinkFragment();
        }else{
            fragment = new ManageLinksFragment();
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
