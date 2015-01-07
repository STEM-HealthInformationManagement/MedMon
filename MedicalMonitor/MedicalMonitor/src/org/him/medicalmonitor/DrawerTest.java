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

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class DrawerTest extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles = {"Home", "Doctor's Panel", "Panel", "Help", "About"};
/*    public static String responseContent;*/
    public static String dataRetrieveError = "Error Retrieving Data";
/*    static JSONObject evaluator;
	static String evaluatorValue;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_test);
   
        mTitle = mDrawerTitle = getTitle();
        //Array of Strings which show up when the drawer is opened
        //mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) 
        {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) 
        {
        case R.id.action_websearch:
            // create intent to perform web search for this planet
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) 
            {
                startActivity(intent);
            } 
            else
            {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
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
        //Fragment fragment = new PlanetFragment();
    	Fragment fragment = null;
        Bundle args = new Bundle();
        switch(position){
        
        case 0:
        	DrawerTest.this.getActionBar().hide();
        	fragment = new StaticViewer();
            args.putInt("static_viewer", position);
            break;
        case 1:
        	DrawerTest.this.getActionBar().hide();
        	fragment = new DoctorPanel();
            args.putInt("doctor_panel", position);
            break;
        case 2:
        	DrawerTest.this.getActionBar().hide();
        	fragment = new Panel();
        	args.putInt("panel", position);
        	break;
        case 3:
        	fragment = new Help();
        	args.putInt("help", position);
        case 4:
        	fragment = new About();
        	args.putInt("about", position);
        default:
        	DrawerTest.this.getActionBar().show();
        	break;
        }
        //args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) 
    {
        mTitle = title;
        getActionBar().setTitle(mTitle);
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
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) 
    {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
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
}