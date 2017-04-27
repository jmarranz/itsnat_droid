package org.itsnat.itsnatdroidtest.testact.local.asset;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.itsnat.droid.AttrResourceInflaterListener;
import org.itsnat.droid.InflateLayoutRequest;
import org.itsnat.droid.InflatedLayout;
import org.itsnat.droid.ItsNatDroidException;
import org.itsnat.droid.ItsNatDroidRoot;
import org.itsnat.droid.Page;
import org.itsnat.itsnatdroidtest.R;

import java.io.FileNotFoundException;

public class TestActivityMenu extends Activity
{
    protected boolean dynamic = false;
    protected InflatedLayout inflatedLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // Muestra y activa el simbolito del back y define el android.R.id.home cuando se pulsa

        Intent intent = getIntent();
        this.dynamic = intent.getBooleanExtra("dynamic", false);

        View rootView;

        if (dynamic)
        {
            int teslayout = 0;
            switch(teslayout)
            {
                case 0:
                    this.inflatedLayout = prepareLayoutDynamicMenu("@assets:layout/res/layout/test_local_layout_menu_container_asset.xml");
                    break;
                case 1:
                    try
                    {
                        this.inflatedLayout = prepareLayoutDynamicMenu("@intern:layout/res/layout/test_local_layout_menu_container_inner.xml"); // expected FileNotFound this is valid
                    }
                    catch (ItsNatDroidException ex)
                    {
                        Toast.makeText(this, "EXPECTED FileNotFoundException", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
            }
            rootView = inflatedLayout.getItsNatDoc().getRootView();

            Toast.makeText(this, "OK DYNAMIC", Toast.LENGTH_SHORT).show();
        }
        else
        {
            rootView = getLayoutInflater().inflate(R.layout.test_local_layout_menu_container_compiled, null);

            this.inflatedLayout = null;
            Toast.makeText(this, "OK COMPILED", Toast.LENGTH_SHORT).show();
        }

        setContentView(rootView);

        bindCompiledBackButton(rootView);
        bindCompiledReloadButton(rootView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (dynamic)
        {
            inflatedLayout.getItsNatDoc().getItsNatResources().getMenu("@assets:menu/res/menu/test_local_menu_asset.xml",menu);
        }
        else
        {
            getMenuInflater().inflate(R.menu.test_local_menu_compiled, menu);
        }

        //MenuItem item = menu.findItem(R.id.item_4);
        // item.setAlphabeticShortcut('f'); // Con ALT opcional. Creo que sólo funciona en creación del MenuItem
        //char as = item.getAlphabeticShortcut();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else if (id == R.id.item_0)
        {
            item.setChecked(!item.isChecked());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    protected void bindCompiledBackButton(View rootView)
    {
         View backButton = rootView.findViewById(R.id.back);
         backButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    finish();
                }
            });
    }

    protected void bindCompiledReloadButton(View rootView)
    {
        View reloadButton = rootView.findViewById(R.id.buttonReload);
        reloadButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();

                Intent intent = new Intent(TestActivityMenu.this, TestActivityMenu.class);
                intent.putExtra("dynamic", !dynamic);
                TestActivityMenu.this.startActivity(intent);
            }
        });

    }

    protected InflatedLayout prepareLayoutDynamicMenu(String layoutForLoading)
    {
        // Sólo para testear carga local

        // Alternativa es poner los layout root en raw: InputStream input = act.getResources().openRawResource(rawResId);
        // el problema es que raw no deja formar árboles de recursos con directorios, es mejor assets

        AttrResourceInflaterListener listener = new AttrResourceInflaterListener()
        {
            @Override
            public boolean setAttribute(Page page, Object resource, String namespace, String name, String value)
            {
                Log.v("TestActivityMenu",namespace+ " " + name);
                return true;
            }

            @Override
            public boolean removeAttribute(Page page, Object resource, String namespace, String name)
            {
                throw new RuntimeException();
            }
        };

        InflateLayoutRequest inflateRequest = ItsNatDroidRoot.get().createInflateLayoutRequest();
        InflatedLayout layout = inflateRequest
                .setEncoding("UTF-8")
                .setBitmapDensityReference(DisplayMetrics.DENSITY_XHIGH) // 320
                .setAttrResourceInflaterListener(listener)
                .setContext(this)
                .inflate(layoutForLoading,null,-1);

        return layout;
    }

    public void test()
    {
        Toast.makeText(this, "OK COMPILED", Toast.LENGTH_SHORT).show();

/*
        bindBackButton(compiledRootView);

        final View compiledRootView = getLayoutInflater().inflate(R.layout.test_local_layout_menu_container_compiled, null);

        final View buttonReload = compiledRootView.findViewById(R.id.buttonReload);
        buttonReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TEST de carga dinámica de layout guardado localmente
                InflatedLayout layout = loadAssetAndBindBackReloadButtons("res/menu/test_local_layout_menu_container_asset.xml");
                View dynamicRootView = layout.getItsNatDoc().getRootView();

                initialConfiguration(TestActivityMenu.this, dynamicRootView);

                // TestAssetLayoutDrawables.test((ScrollView) compiledRootView, (ScrollView) dynamicRootView);
            }
        });

        initialConfiguration(this, compiledRootView);

        */
    }

    private static void initialConfiguration(TestActivityMenu act, View rootView)
    {
    }
}
