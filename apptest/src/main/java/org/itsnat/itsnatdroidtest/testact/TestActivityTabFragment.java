package org.itsnat.itsnatdroidtest.testact;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.itsnat.itsnatdroidtest.R;
import org.itsnat.itsnatdroidtest.testact.local.asset.TestSetupAssetLayout1;
import org.itsnat.itsnatdroidtest.testact.local.asset.TestSetupAssetLayout2;
import org.itsnat.itsnatdroidtest.testact.local.asset.TestSetupAssetLayoutAnimations1;
import org.itsnat.itsnatdroidtest.testact.local.asset.TestSetupAssetLayoutAnimations2;
import org.itsnat.itsnatdroidtest.testact.local.asset.TestSetupAssetLayoutDrawables;
import org.itsnat.itsnatdroidtest.testact.local.asset.TestSetupAssetLayoutMenu;
import org.itsnat.itsnatdroidtest.testact.local.intern.TestSetupInternLayoutCleanReloadDrawables;
import org.itsnat.itsnatdroidtest.testact.local.intern.TestSetupInternLayoutDrawables;
import org.itsnat.itsnatdroidtest.testact.remote.TestSetupRemoteControl;
import org.itsnat.itsnatdroidtest.testact.remote.TestSetupRemoteCore;
import org.itsnat.itsnatdroidtest.testact.remote.TestSetupRemoteIncludeLayout;
import org.itsnat.itsnatdroidtest.testact.remote.TestSetupRemotePage;
import org.itsnat.itsnatdroidtest.testact.remote.TestSetupRemotePageNoItsNat;

/**
 * Created by jmarranz on 12/08/14.
 */
public class TestActivityTabFragment extends Fragment
{
    protected View rootView;
    protected int sectionNumber;
    public boolean changed = false;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TestActivityTabFragment newInstance(int sectionNumber) {
        TestActivityTabFragment fragment = new TestActivityTabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    public void onCreateContextMenu (ContextMenu menu,View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        Log.v("fragment","Hello");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // TODO Add your menu entries here
        menu.clear();

        inflater.inflate(R.menu.test_local_menu_compiled,menu);

        for (int i = 0; i < menu.size(); i++ )
        {
            MenuItem item = menu.getItem(i);
            SpannableString s = new SpannableString(item.getTitle());
            s.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);
            item.setTitle(s);
        }

        int size = menu.size();
    }


    public int getSectionNumber()
    {
        return sectionNumber;
    }

    public void setRootView(View rootView)
    {
        this.rootView = rootView;
    }

    public TestActivity getTestActivity()
    {
        return (TestActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (rootView != null) return rootView;

        Bundle bundle = getArguments();
        this.sectionNumber = bundle.getInt(ARG_SECTION_NUMBER);

        this.rootView = inflater.inflate(R.layout.fragment_test_index, container, false);

        View testAsset1 = rootView.findViewById(R.id.testAssetLayout1);
        testAsset1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new TestSetupAssetLayout1(TestActivityTabFragment.this).test();
            }
        });

        View testAsset2 = rootView.findViewById(R.id.testAssetLayout2);
        testAsset2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new TestSetupAssetLayout2(TestActivityTabFragment.this).test();
            }
        });

        View testAssetDrawables = rootView.findViewById(R.id.testAssetDrawables);
        testAssetDrawables.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new TestSetupAssetLayoutDrawables(TestActivityTabFragment.this).test();
            }
        });

        View testAssetAnimations1 = rootView.findViewById(R.id.testAssetAnimations1);
        testAssetAnimations1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new TestSetupAssetLayoutAnimations1(TestActivityTabFragment.this).test();
            }
        });

        View testAssetAnimations2 = rootView.findViewById(R.id.testAssetAnimations2);
        testAssetAnimations2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new TestSetupAssetLayoutAnimations2(TestActivityTabFragment.this).test();
            }
        });

        View testAssetMenu = rootView.findViewById(R.id.testAssetMenu);
        testAssetMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new TestSetupAssetLayoutMenu(TestActivityTabFragment.this).test();
            }
        });


        View testInternLoadDrawables = rootView.findViewById(R.id.testLoadInternDrawables);
        testInternLoadDrawables.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new TestSetupInternLayoutCleanReloadDrawables(TestActivityTabFragment.this).test();
            }
        });

        View testInternDrawables = rootView.findViewById(R.id.testInternDrawables);
        testInternDrawables.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new TestSetupInternLayoutDrawables(TestActivityTabFragment.this).test();
            }
        });


        final TestActivity act = getTestActivity();

        View testRemoteCore = rootView.findViewById(R.id.testRemoteLayoutCore);
        testRemoteCore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String url = act.getUrlTestCore();
                TestSetupRemoteCore test = new TestSetupRemoteCore(TestActivityTabFragment.this, act.getItsNatDroidBrowser());
                test.testSSLSelfSigned(url);
            }
        });

        View testRemoteIncludeLayout = rootView.findViewById(R.id.testRemoteIncludeLayout);
        testRemoteIncludeLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String url = act.getUrlTestIncludeLayout();
                TestSetupRemoteIncludeLayout test = new TestSetupRemoteIncludeLayout(TestActivityTabFragment.this, act.getItsNatDroidBrowser());
                test.test(url);
            }
        });


        View testRemoteDrawables = rootView.findViewById(R.id.testRemoteLayoutDrawables);
        testRemoteDrawables.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String url = act.getUrlTestRemoteDrawables();
                TestSetupRemotePage test = new TestSetupRemotePage(TestActivityTabFragment.this, act.getItsNatDroidBrowser());
                test.test(url);
            }
        });

        View testRemoteAnimations1 = rootView.findViewById(R.id.testRemoteAnimations1);
        testRemoteAnimations1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String url = act.getUrlTestRemoteAnimations1();
                TestSetupRemotePage test = new TestSetupRemotePage(TestActivityTabFragment.this, act.getItsNatDroidBrowser());
                test.test(url);
            }
        });

        View testRemoteAnimations2 = rootView.findViewById(R.id.testRemoteAnimations2);
        testRemoteAnimations2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String url = act.getUrlTestRemoteAnimations2();
                TestSetupRemotePage test = new TestSetupRemotePage(TestActivityTabFragment.this, act.getItsNatDroidBrowser());
                test.test(url);
            }
        });

        View testRemoteControl = rootView.findViewById(R.id.testRemoteControl);
        testRemoteControl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String url = act.getUrlTestRemCtrl();
                TestSetupRemoteControl test = new TestSetupRemoteControl(TestActivityTabFragment.this, act.getItsNatDroidBrowser());
                test.test(url);
            }
        });

        View testRemoteStatelessCore = rootView.findViewById(R.id.testRemoteStatelessCore);
        testRemoteStatelessCore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String url = act.getUrlTestStatelessCore();
                TestSetupRemotePage test = new TestSetupRemotePage(TestActivityTabFragment.this, act.getItsNatDroidBrowser());
                test.test(url);
            }
        });

        View testRemoteComponents = rootView.findViewById(R.id.testRemoteComponents);
        testRemoteComponents.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String url = act.getUrlTestComponents();
                TestSetupRemotePage test = new TestSetupRemotePage(TestActivityTabFragment.this, act.getItsNatDroidBrowser());
                test.test(url);
            }
        });

        View testRemoteNoItsNat = rootView.findViewById(R.id.testRemoteNoItsNat);
        testRemoteNoItsNat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String url = act.getUrlTestRemoteNoItsNat();
                TestSetupRemotePageNoItsNat test = new TestSetupRemotePageNoItsNat(TestActivityTabFragment.this, act.getItsNatDroidBrowser());
                test.test(url);
            }
        });

        return rootView;
    }

    public void gotoLayoutIndex()
    {
        this.rootView = null;
        updateFragmentLayout();
    }

    public void updateFragmentLayout()
    {
        this.changed = true;
        TestActivityPagerAdapter pagerAdapter = getTestActivity().getTestActivityPagerAdapter();
        pagerAdapter.notifyDataSetChanged(); // Provoca la llamada FragmentPagerAdapter.getItemPosition(Object) para cada fragmento

        //act.getViewPager().invalidate();
        //act.getViewPager().destroyDrawingCache();
        //act.getViewPager().forceLayout();
        //act.getViewPager().requestLayout();
    }
}
