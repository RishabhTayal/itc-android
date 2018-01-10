package com.rtayal.itunesconnect;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rtayal.itunesconnect.models.AppItem;
import com.rtayal.itunesconnect.models.ReviewItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ReviewFragment extends Fragment {

    private static final String ARG_App = "app-item";

    private OnListFragmentInteractionListener mListener;

    private AppItem appItem;

    private ArrayList<ReviewItem> items = new ArrayList<>();

    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReviewFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ReviewFragment newInstance(AppItem appItem) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_App, appItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            appItem = (AppItem) getArguments().getSerializable(ARG_App);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_review_list, container, false);

        new ServiceCaller().getReviews(appItem.bundle_id, "US", new CallbackOnMain() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("error: ", e.toString());
            }

            @Override
            public void onResponse(Call call, String response) throws IOException {
                Log.i("reviews: ", response);
                List<ReviewItem> appItems = new Gson().fromJson(response, new TypeToken<ArrayList<ReviewItem>>() {
                }.getType());
                items.clear();
                items.addAll(appItems);
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(new MyReviewRecyclerViewAdapter(items, mListener));

        return recyclerView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ReviewItem item);
    }
}
