package com.example.wollf.togather;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileTab.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ProfileTab extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SharedPreferences editor;
    private OnFragmentInteractionListener mListener;

    public ProfileTab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editor = this.getActivity().getSharedPreferences("user_data", MODE_PRIVATE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        String id = editor.getString("ID", null);
        DataBase db = new DataBase();
        User u = DataBase.getUser(id);
        TextView n = rootView.findViewById(R.id.user_profile_name);
        n.setText(u.getName());
        TextView e = rootView.findViewById(R.id.user_profile_short_bio);
        e.setText(u.getEmail());
        TextView t = rootView.findViewById(R.id.iban_field);
        t.setText(Html.fromHtml(t.getText() + " <big><b>" + u.getIBAN() + "</b></big>"));
        TextView p = rootView.findViewById(R.id.phone_field);
        p.setText(Html.fromHtml(p.getText() + " <big><b>" + u.getPhone() + "</b></big>"));
        return rootView;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
