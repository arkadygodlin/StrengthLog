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
import com.strengthlog.db.sql.ExerciseContract;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseFragment extends Fragment
{
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  private OnFragmentInteractionListener mListener;
  private FragmentController controller;
  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment ExerciseFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ExerciseFragment newInstance(String param1, String param2)
  {
    ExerciseFragment fragment = new ExerciseFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  public ExerciseFragment()
  {
    // Required empty public constructor
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
    View view = inflater.inflate(R.layout.fragment_exercise, container, false);

    FragmentModule module = new FragmentModule();
    FragmentView v = new FragmentView(view);
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
    try
    {
      mListener = (OnFragmentInteractionListener) activity;
    } catch (ClassCastException e)
    {
      throw new ClassCastException(activity.toString() + " must implement OnFragmentInteractionListener");
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
    public void onFragmentInteraction(Uri uri);
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
      if (view.exercise.getText().toString().equals(empty)){
        return false;
      }
      return true;
    }

    private void saveInput(){
      ExerciseContract.EntryHolder entryHolder = new ExerciseContract.EntryHolder();
      entryHolder.exercise = view.exercise.getText().toString();
      saveInputToDb(entryHolder);
    }

    private void saveInputToDb(ExerciseContract.EntryHolder entryHolder){
      DataBridge.dataBridge.addExercise(entryHolder);
    }
  }

  public static class FragmentModule
  {
  }

  public static class FragmentView
  {
    private View view;
    public EditText exercise;

    public FragmentView(View view){
      this.view = view;
      exercise = (EditText) view.findViewById(R.id.exercise);
    }
  }

}
