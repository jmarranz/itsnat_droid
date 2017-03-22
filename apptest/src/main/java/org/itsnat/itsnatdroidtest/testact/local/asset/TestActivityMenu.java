package org.itsnat.itsnatdroidtest.testact.local.asset;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.itsnat.itsnatdroidtest.R;

public class TestActivityMenu extends Activity
{
    protected boolean dynamic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        final View compiledRootView = getLayoutInflater().inflate(R.layout.test_local_layout_menu_container_compiled, null);
        setContentView(compiledRootView);

        bindCompiledBackButton(compiledRootView);
        bindCompiledReloadButton(compiledRootView);

        Intent intent = getIntent();
        this.dynamic = intent.getBooleanExtra("dynamic", false);

        if (dynamic)
        {
            Toast.makeText(this, "OK DYNAMIC", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "OK COMPILED", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_local_menu_compiled, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        */
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
