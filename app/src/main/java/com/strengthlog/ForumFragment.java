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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.strengthlog.db.DataBridge;
import com.strengthlog.db.sql.LogContract;
import com.strengthlog.db.sql.ProgramContract;
import com.strengthlog.utils.Logger;

import java.util.List;


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
  private static String tag = ForumFragment.class.getSimpleName();

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
      if (view.program.getSelectedItem().toString().equals(empty)){
        Logger.d(tag, "program empty");
        return false;
      }
      if (view.date.getText().toString().equals(empty)){
        Logger.d(tag, "date empty");
        return false;
      }
      if (view.weight.getText().toString().equals(empty)){
        Logger.d(tag, "weight empty");
        return false;
      }
      if (view.reps.getText().toString().equals(empty)){
        Logger.d(tag, "reps empty");
        return false;
      }
      if (view.sets.getText().toString().equals(empty)){
        Logger.d(tag, "sets empty");
        return false;
      }

      return true;
    }

    private ProgramContract.EntryHolder getSelectedProgram(){
      ProgramContract.EntryHolder programEntryHolder = (ProgramContract.EntryHolder)view.program.getAdapter().getItem(view.program.getSelectedItemPosition());
      return programEntryHolder;
    }

    private void saveInput(){
      ProgramContract.EntryHolder programEntryHolder = getSelectedProgram();
      LogContract.EntryHolder entryHolder = new LogContract.EntryHolder();
      entryHolder.key = String.valueOf(programEntryHolder.hashCode());
      entryHolder.date = view.date.getText().toString();
      entryHolder.weight = Float.parseFloat(view.weight.getText().toString());
      entryHolder.reps = Integer.parseInt(view.reps.getText().toString());
      entryHolder.sets = Integer.parseInt(view.sets.getText().toString());
      entryHolder.comment = view.comment.getText().toString();
      saveInputToDb(entryHolder);
    }

    private void saveInputToDb(LogContract.EntryHolder entryHolder){
      Logger.d(tag, String.format("Calling addLog with %s", entryHolder.toString()));
      boolean ret = DataBridge.dataBridge.addLog(entryHolder);
      Logger.d(tag, String.format("addLog returns %b", ret));
    }
  }

  public static class FragmentModule
  {
  }

  public static class FragmentView
  {
    private View view;
    private Context context;
    public Spinner program;
    public Spinner exercise;
    public EditText date;
    public EditText weight;
    public EditText reps;
    public EditText sets;
    public EditText comment;

    public FragmentView(View view, Context context)
    {
      this.view = view;
      this.context = context;
      program = (Spinner) view.findViewById(R.id.program);
      exercise = (Spinner) view.findViewById(R.id.exercise);
      date = (EditText) view.findViewById(R.id.date);
      weight = (EditText) view.findViewById(R.id.weight);
      reps = (EditText) view.findViewById(R.id.reps);
      sets = (EditText) view.findViewById(R.id.sets);
      comment = (EditText) view.findViewById(R.id.comment);
      setupSpinners();
    }

    private void setupSpinners(){

      //TODO set spinners and onclick/on select
      program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
      {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
          Logger.d(ForumFragment.tag, String.format("On Item Click"));
          ProgramContract.EntryHolder entryHolder = (ProgramContract.EntryHolder)program.getAdapter().getItem(position);
          updateExerciseSpinner(entryHolder);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
          Logger.d(ForumFragment.tag, String.format("On Nothing Click"));
        }

      });

      // Create an ArrayAdapter using the string array and a default spinner layout
      List<ProgramContract.EntryHolder> items = DataBridge.dataBridge.retrieveAllPrograms();
      ArrayAdapter<ProgramContract.EntryHolder> adapter = new ArrayAdapter<ProgramContract.EntryHolder>(context, android.R.layout.simple_spinner_item, items);
      // Specify the layout to use when the list of choices appears
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      // Apply the adapter to the spinner
      program.setAdapter(adapter);
    }

    private void updateExerciseSpinner(ProgramContract.EntryHolder entryHolder){
      List<String> items = DataBridge.dataBridge.retrieveExercises(entryHolder);
      ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, items);
      // Specify the layout to use when the list of choices appears
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      // Apply the adapter to the spinner
      exercise.setAdapter(adapter);
    }
  }
}
