package com.strengthlog;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.strengthlog.db.DataBridge;
import com.strengthlog.utils.Logger;

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks,
  ForumFragment.OnFragmentInteractionListener, LogHistoryFragment.OnFragmentInteractionListener,
  ProgramFragment.OnFragmentInteractionListener, ExerciseFragment.OnFragmentInteractionListener
{
  private static String tag = ForumFragment.class.getSimpleName();
  /**
   * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
   */
  private NavigationDrawerFragment mNavigationDrawerFragment;

  private CharSequence mTitle;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
    mTitle = getTitle();

    // Set up the drawer.
    mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

    Logger.d(tag, "Creating DataBase");
    //TODO use this in async Call
    DataBridge.initDataBridge(getApplicationContext());
    Logger.d(tag, "Done Loading DataBase");

  }

  @Override
  public void onNavigationDrawerItemSelected(int position)
  {
    // update the main content by replacing fragments
    FragmentTransaction transaction = getFragmentManager().beginTransaction();
    Fragment newFragment = null;

    Logger.d(tag, String.format("onNavigationDrawerItemSelected : %d", position));

    switch (position)
    {
      case 0:
        //TODO Create Home View
        newFragment = PlaceholderFragment.newInstance(0);
        mTitle = getString(R.string.title_section0);
        break;
      case 1:
        newFragment = ForumFragment.newInstance("", "");
        mTitle = getString(R.string.title_section1);
        break;
      case 2:
        mTitle = getString(R.string.title_section2);
        newFragment = LogHistoryFragment.newInstance(mTitle.toString(), "");
        break;
      case 3:
        mTitle = getString(R.string.title_section3);
        newFragment = LogHistoryFragment.newInstance(mTitle.toString(), "");
        break;
      case 4:
        newFragment = ProgramFragment.newInstance("", "");
        mTitle = getString(R.string.title_section4);
        break;
      case 5:
        newFragment = ExerciseFragment.newInstance("", "");
        mTitle = getString(R.string.title_section5);
        break;
      case 6:
        mTitle = getString(R.string.title_section6);
        newFragment = LogHistoryFragment.newInstance(mTitle.toString(), "");
        break;
      case 7:
        mTitle = getString(R.string.title_section7);
        newFragment = LogHistoryFragment.newInstance(mTitle.toString(), "");
        break;
      default:
    }
    transaction.replace(R.id.container, newFragment);
    transaction.commit();
  }

  public void onSectionAttached(int number)
  {

    Logger.d(tag, String.format("onSectionAttached : %d", number));
    switch (number)
    {
      case 0:
        mTitle = getString(R.string.title_section0);
        break;
      case 1:
        mTitle = getString(R.string.title_section1);
        break;
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    if (!mNavigationDrawerFragment.isDrawerOpen())
    {
      // Only show items in the action bar relevant to this screen
      // if the drawer is not showing. Otherwise, let the drawer
      // decide what to show in the action bar.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
    }
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onFragmentInteraction(Uri uri)
  {
    //TODO: impl this ?
  }

  @Override
  public void onFragmentInteraction(int id)
  {
    //TODO: impl this ?
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment
  {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment()
    {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber)
    {
      PlaceholderFragment fragment = new PlaceholderFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
      View rootView = inflater.inflate(R.layout.fragment_main, container, false);
      return rootView;
    }

    @Override
    public void onAttach(Activity activity)
    {
      super.onAttach(activity);
      ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
  }


}
