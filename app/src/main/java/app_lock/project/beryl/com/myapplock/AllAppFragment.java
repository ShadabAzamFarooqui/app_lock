package app_lock.project.beryl.com.myapplock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app_lock.project.beryl.com.myapplock.navigation.HomeActivity;

import static app_lock.project.beryl.com.myapplock.navigation.HomeActivity.mProgressDialog;

/**
 * Created by Shadab Aazam on 11/09/2018.
 */
public class AllAppFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    static String requiredAppsType;

    public static AllAppFragment newInstance(String requiredApps) {
        requiredAppsType = requiredApps;
        AllAppFragment f = new AllAppFragment();
        return (f);
    }


    public AllAppFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_all_apps, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ApplicationListAdapter(((HomeActivity) getActivity()).getListOfInstalledApp(getActivity()), getActivity(), requiredAppsType);
        mRecyclerView.setAdapter(mAdapter);
        mProgressDialog.dismiss();
        return v;

    }
}
