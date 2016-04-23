package com.strengthlog;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.strengthlog.db.DataBridge;
import com.strengthlog.db.sql.WeightContract;
import com.strengthlog.utils.Logger;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeightFragment extends Fragment
{

  private static String tag = WeightFragment.class.getSimpleName();

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;
  private FragmentController controller;

  public WeightFragment()
  {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment WeightFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static WeightFragment newInstance(String param1, String param2)
  {
    WeightFragment fragment = new WeightFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    if (getArguments() != null)
    {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_weight, container, false);
    FragmentView v = new FragmentView(view, getActivity());
    FragmentModule module = new FragmentModule();
    controller = new FragmentController(v, module);
    controller.setContext(getActivity());
    return view;
  }

  // TODO: Rename method, update argument and hook method into UI event
  public void onButtonPressed(Uri uri)
  {
    if (mListener != null)
    {
      mListener.onFragmentInteraction(uri);
    }
  }

  @Override
  public void onAttach(Activity activity)
  {
    super.onAttach(activity);
    if (activity instanceof OnFragmentInteractionListener)
    {
      mListener = (OnFragmentInteractionListener) activity;
    } else
    {
      throw new RuntimeException(activity.toString() + " must implement OnFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach()
  {
    super.onDetach();
    mListener = null;
  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p/>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnFragmentInteractionListener
  {
    // TODO: Update argument type and name
    void onFragmentInteraction(Uri uri);
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.accept_menu, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    if (id == R.id.action_accept) {
      controller.acceptPressed();
    }

    return super.onOptionsItemSelected(item);
  }

  public static class FragmentController
  {
    Context context;
    FragmentView view;
    FragmentModule module;

    public void setContext(Context context)
    {
      this.context = context;
    }

    public void setModule(FragmentModule module)
    {
      this.module = module;
    }

    public void setView(FragmentView view)
    {

      this.view = view;
    }

    public FragmentController(FragmentView view, FragmentModule module)
    {
      this.view = view;
      this.module = module;
    }

    public void acceptPressed(){
      if(validateInput()){
        saveInput();
      }
    }

    private boolean validateInput(){
      String empty = "";
      if (view.weight.getText().toString().equals(empty)){
        Logger.d(tag, "weight empty");
        return false;
      }
      if (view.date.getText().toString().equals(empty)){
        Logger.d(tag, "date empty");
        return false;
      }
      if (view.time.getText().toString().equals(empty)){
        //TODO add NOW time if time is empty.
        Logger.d(tag, "time empty");
      }
      return true;
    }

    private void saveInput(){
      WeightContract.EntryHolder programentryHolder = new WeightContract.EntryHolder();
      programentryHolder.weight = Double.valueOf(view.weight.getText().toString());
      programentryHolder.date = view.date.getText().toString();
      programentryHolder.time = view.time.getText().toString();

      saveInputToDb(programentryHolder);
    }

    private void saveInputToDb(WeightContract.EntryHolder entryHolder){
      Logger.d(tag, String.format("Calling addProgram with %s", entryHolder.toString()));
      boolean ret = DataBridge.dataBridge.addWeight(entryHolder);
      Logger.d(tag, String.format("addExercise returns %b", ret));
    }
  }

  public static class FragmentModule
  {
  }

  public static class FragmentView
  {
    private View view;
    public EditText weight;
    public EditText date;
    public EditText time;

    public FragmentView(View view, Context context){
      this.view = view;
      weight = (EditText) view.findViewById(R.id.weight);
      date = (EditText) view.findViewById(R.id.date);
      time = (EditText) view.findViewById(R.id.time);

    }
  }

}
