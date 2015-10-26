package com.strengthlog;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.strengthlog.db.sql.DbHelper;
import com.strengthlog.db.sql.LogContract;
import com.strengthlog.db.sql.ProgramContract;
import com.strengthlog.utils.Logger;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ForumFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ForumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//TODO: Rename fragment to better describe.
public class ForumFragment extends Fragment
{
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;
  private ViewsContainer viewsContainer;
  private OnFragmentInteractionListener mListener;

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment ForumFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static ForumFragment newInstance(String param1, String param2)
  {
    ForumFragment fragment = new ForumFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  public ForumFragment()
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
    View view = inflater.inflate(R.layout.fragment_forum, container, false);
    viewsContainer = new ViewsContainer(view);
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
    inflater.inflate(R.menu.forum_menu, menu);
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
      if (validateInput()){
        saveInput();
      }
      else{
        Logger.i("ForumFragment", "Input not Valid");
      }
    }

    return super.onOptionsItemSelected(item);
  }

  private boolean validateInput(){
    String empty = "";
    if (viewsContainer.program.getText().toString().equals(empty)){
      return false;
    }
    if (viewsContainer.workout.getText().toString().equals(empty)){
      return false;
    }
    if (viewsContainer.exercise.getText().toString().equals(empty)){
      return false;
    }
    if (viewsContainer.date.getText().toString().equals(empty)){
      return false;
    }
    if (viewsContainer.weight.getText().toString().equals(empty)){
      return false;
    }
    if (viewsContainer.reps.getText().toString().equals(empty)){
      return false;
    }
    if (viewsContainer.sets.getText().toString().equals(empty)){
      return false;
    }

    ProgramContract.EntryHolder programEntryHolder = createProgramEntryHolder();
    DbHelper db = new DbHelper(getActivity());
    return db.isProgramExists(programEntryHolder);
  }

  private ProgramContract.EntryHolder createProgramEntryHolder(){
    ProgramContract.EntryHolder programEntryHolder = new ProgramContract.EntryHolder();
    programEntryHolder.program = viewsContainer.program.getText().toString();
    programEntryHolder.workout = viewsContainer.workout.getText().toString();
    programEntryHolder.exercise = viewsContainer.exercise.getText().toString();
    return programEntryHolder;
  }

  private void saveInput(){
    ProgramContract.EntryHolder programEntryHolder = createProgramEntryHolder();

    LogContract.EntryHolder entryHolder = new LogContract.EntryHolder();
    entryHolder.key = String.valueOf(programEntryHolder.hashCode());
    entryHolder.date = viewsContainer.date.getText().toString();
    entryHolder.weight = Float.parseFloat(viewsContainer.weight.getText().toString());
    entryHolder.reps = Integer.parseInt(viewsContainer.reps.getText().toString());
    entryHolder.sets = Integer.parseInt(viewsContainer.sets.getText().toString());
    entryHolder.comment = viewsContainer.comment.getText().toString();
    saveInputToDb(entryHolder);
  }

  private void saveInputToDb(LogContract.EntryHolder entryHolder){
    DbHelper db = new DbHelper(getActivity());
    db.insertLog(entryHolder);
  }

  private class ViewsContainer{
    public EditText program;
    public EditText workout;
    public EditText exercise;
    public EditText date;
    public EditText weight;
    public EditText reps;
    public EditText sets;
    public EditText comment;

    public ViewsContainer(View view){
      program = (EditText) view.findViewById(R.id.program);
      workout = (EditText) view.findViewById(R.id.workout);
      exercise = (EditText) view.findViewById(R.id.exercise);
      date = (EditText) view.findViewById(R.id.date);
      weight = (EditText) view.findViewById(R.id.weight);
      reps = (EditText) view.findViewById(R.id.reps);
      sets = (EditText) view.findViewById(R.id.sets);
      comment = (EditText) view.findViewById(R.id.comment);
    }
  }

}
