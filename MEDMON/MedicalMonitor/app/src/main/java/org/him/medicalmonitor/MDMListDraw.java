package org.him.medicalmonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MDMListDraw extends ActionBarActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView leftDrawerList;
    private ArrayAdapter<String> navigationDrawerAdapter;
    private String[] drawerData = {"Home", "Doctor's Panel", "Panel", "Help", "About"};
    /*    public static String responseContent;*/
    public static String dataRetrieveError = "Error Retrieving Data";
/*    static JSONObject evaluator;
	static String evaluatorValue;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mdm_list_draw);
        MDMListDraw.this.getSupportActionBar().hide();
        //Array of Strings which show up when the drawer is opened
        //drawerData = getResources().getStringArray(R.array.data_array);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // set a custom shadow that overlays the main content when the drawer opens
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        leftDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawerData));
        leftDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        /*getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);*/

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            selectItem(position);
        }
    }

    private void selectItem(int position)
    {
        // update the main content by replacing fragments
        //Fragment fragment = new dataFragment();
        Fragment fragment = null;
        Bundle args = new Bundle();
        switch(position){

            case 0:
                MDMListDraw.this.getSupportActionBar().hide();
                fragment = new StaticViewer();
                args.putInt("static_viewer", position);
                break;
            case 1:
                MDMListDraw.this.getSupportActionBar().hide();
                fragment = new DoctorPanel();
                args.putInt("doctor_panel", position);
                break;
            case 2:
                MDMListDraw.this.getSupportActionBar().hide();
                fragment = new Panel();
                args.putInt("panel", position);
                break;
            case 3:
                MDMListDraw.this.getSupportActionBar().hide();
                fragment = new Help();
                args.putInt("help", position);
            case 4:
                MDMListDraw.this.getSupportActionBar().hide();
                fragment = new About();
                args.putInt("about", position);
            default:
                MDMListDraw.this.getSupportActionBar().show();
                break;
        }
        //args.putInt(dataFragment.ARG_data_NUMBER, position);
        assert fragment != null;
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        leftDrawerList.setItemChecked(position, true);
        setTitle(drawerData[position]);
        toolbar.setTitle(drawerData[position]);
        drawerLayout.closeDrawer(leftDrawerList);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public static class Help extends Fragment
    {
        public Help()
        {
            // Empty constructor required for fragment subclasses
        }

        public ProgressBar p2;
        public TextView t2;
        private static JSONObject evaluator;
        private static String evaluatorValue;
        private static String responseContent;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View rootView3 = inflater.inflate(R.layout.help, container, false);
            getActivity().setTitle("Help");
            t2 = (TextView) rootView3.findViewById(R.id.helpResultView);

            p2 = (ProgressBar) rootView3.findViewById(R.id.helpProgress);
            p2.setVisibility(View.INVISIBLE);

            //Now start looking for the help text from our database
            p2.setVisibility(View.VISIBLE);
            p2.animate();
            final JSONObject jObj = new JSONObject();
            final String url = "http://www.njcuacm.org/restricted/stem_test/app/MedMon/Setting_Write/mdm_read_settings_2.php";

            new Thread() {
                @Override
                public void run() {
                    try
                    {

                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(url);

                        //This sets a custom header for PHP, to read it via
                        //$_SERVER['HTTP_JSON']; which is assigned to a variable
                        //and controlled later in the PHP script.
                        //httppost.setHeader("json", jObj.toString());
                        //httppost.getParams().setParameter("jsonpost", allJsonObjects);

                        /********************ORIGINAL CODE*******************************/
                        //httppost.setEntity(new UrlEncodedFormEntity(postParameters));
                        /****************************************************************/
                        System.out.print(jObj);
                        HttpResponse response = httpclient.execute(httppost);

                        //response.getAllHeaders();
                        if(response != null)
                        {
                            InputStream is = response.getEntity().getContent();

                            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                            StringBuilder sb = new StringBuilder();

                            String line = null;

                            try
                            {
                                while ((line = reader.readLine()) != null)
                                {
                                    sb.append(line + "\n");
                                }
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                            finally
                            {
                                try
                                {
                                    is.close();
                                }
                                catch (IOException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            responseContent = sb.toString();
                            evaluator = new JSONObject(responseContent);
                            evaluatorValue = evaluator.getString("helpText");

                            if(evaluatorValue != null)
                            {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        p2.setVisibility(View.INVISIBLE);
                                        p2.clearAnimation();
                                        t2.setText(evaluatorValue);
                                    }
                                });

                            }
                            else
                            {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        p2.setVisibility(View.INVISIBLE);
                                        p2.clearAnimation();
                                        t2.setText(dataRetrieveError);
                                    }
                                });
                            }
                        }
                        else
                        {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    p2.setVisibility(View.INVISIBLE);
                                    p2.clearAnimation();
                                    t2.setText(dataRetrieveError);
                                }
                            });
                        }
                    }
                    catch (final Exception e)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                p2.setVisibility(View.INVISIBLE);
                                p2.clearAnimation();
                                t2.setText(dataRetrieveError);
                            }
                        });
                        e.printStackTrace();
                    }

                    super.run();
                }
            }.start();


            return rootView3;
        }
    }

    public static class About extends Fragment {
        public About() {
            // Empty constructor required for fragment subclasses
        }

        public ProgressBar p1;
        public TextView t1;
        private static JSONObject evaluator;
        private static String evaluatorValue;
        private static String responseContent;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View rootView2 = inflater.inflate(R.layout.about, container, false);
            getActivity().setTitle("About");

            p1 = (ProgressBar) rootView2.findViewById(R.id.aboutProgress);
            p1.setVisibility(View.INVISIBLE);
            t1 = (TextView) rootView2.findViewById(R.id.aboutResultView);

            //Now start looking for the About data available on our database.
            p1.setVisibility(View.VISIBLE);
            p1.animate();
            final JSONObject jObj = new JSONObject();
            final String url = "http://www.njcuacm.org/restricted/stem_test/app/MedMon/Setting_Write/mdm_read_settings.php";

            new Thread() {
                @Override
                public void run() {
                    try
                    {

                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(url);

                        //This sets a custom header for PHP, to read it via
                        //$_SERVER['HTTP_JSON']; which is assigned to a variable
                        //and controlled later in the PHP script.
                        //httppost.setHeader("json", jObj.toString());
                        //httppost.getParams().setParameter("jsonpost", allJsonObjects);

                        /********************ORIGINAL CODE*******************************/
                        //httppost.setEntity(new UrlEncodedFormEntity(postParameters));
                        /****************************************************************/
                        System.out.print(jObj);
                        HttpResponse response = httpclient.execute(httppost);

                        //response.getAllHeaders();
                        if(response != null)
                        {
                            InputStream is = response.getEntity().getContent();

                            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                            StringBuilder sb = new StringBuilder();

                            String line = null;

                            try
                            {
                                while ((line = reader.readLine()) != null)
                                {
                                    sb.append(line + "\n");
                                }
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                            finally
                            {
                                try
                                {
                                    is.close();
                                }
                                catch (IOException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            responseContent = sb.toString();
                            evaluator = new JSONObject(responseContent);
                            evaluatorValue = evaluator.getString("aboutText");

                            if(evaluatorValue != null)
                            {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        p1.setVisibility(View.INVISIBLE);
                                        p1.clearAnimation();
                                        t1.setText(evaluatorValue);
                                    }
                                });
                            }
                            else
                            {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        p1.setVisibility(View.INVISIBLE);
                                        p1.clearAnimation();
                                        t1.setText(dataRetrieveError);
                                    }
                                });
                            }
                        }
                        else
                        {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    p1.setVisibility(View.INVISIBLE);
                                    p1.clearAnimation();
                                    t1.setText(dataRetrieveError);
                                }
                            });
                        }
                    }
                    catch (final Exception e)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                p1.setVisibility(View.INVISIBLE);
                                p1.clearAnimation();
                                t1.setText(dataRetrieveError);
                            }
                        });
                        e.printStackTrace();
                    }

                    super.run();
                }
            }.start();

            return rootView2;
        }
    }

}