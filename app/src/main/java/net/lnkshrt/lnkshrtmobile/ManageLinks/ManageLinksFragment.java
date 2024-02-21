package net.lnkshrt.lnkshrtmobile.ManageLinks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.lnkshrt.lnkshrtmobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageLinksFragment extends Fragment {
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    String tag = "manage links";
    FragmentContainerView linkListFragmentContainer;
    LinkListFragment linkList;
    TextView error;
    TextView loading;

    public ManageLinksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_links, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        error = view.findViewById(R.id.manage_error_message);
        loading = view.findViewById(R.id.loading_message);
        linkListFragmentContainer = view.findViewById(R.id.link_list_container);

        get();
    }

    void showError(){
        loading.setVisibility(View.GONE);
        linkListFragmentContainer.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
    }

    void showLoading(){
        error.setVisibility(View.GONE);
        linkListFragmentContainer.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
    }

    void showData(JSONArray data){
        loading.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        linkListFragmentContainer.setVisibility(View.VISIBLE);

        ArrayList<String> links = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();

        for(int i = 0; i < data.length(); i++){
            try{
                JSONObject link = data.getJSONObject(i);
                links.add(link.getString("link"));
                ids.add(link.getInt("id"));
            } catch (JSONException e){
                Log.e(tag, "Couldn't read json object");
            }
        }

        Log.d(tag, data.toString());

        Bundle args = new Bundle();
        args.putStringArrayList("links", links);
        args.putIntegerArrayList("ids", ids);

        getActivity().getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.link_list_container, LinkListFragment.class, args)
                .commit();
    }

    void get(){
        Request request = new Request.Builder()
                .url("https://lnkshrt.net/api/list/?page_size=25")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(tag, "Call failed");
                try {
                    getActivity().runOnUiThread(() -> showError());
                } catch (NullPointerException npe){
                    Log.e(tag, "Couldn't find UI Thread");
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                JSONArray data;

                if(response.isSuccessful()){
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        data = json.getJSONArray("results");
                    } catch (JSONException e) {
                        Log.e(tag, "Couldn't parse response body");
                        getActivity().runOnUiThread(() -> showError());
                        return;
                    } catch (NullPointerException e){
                        Log.e(tag, "Couldn't find response body");
                        getActivity().runOnUiThread(() -> showError());
                        return;
                    }

                    try{
                        getActivity().runOnUiThread(() -> showData(data));
                    } catch (NullPointerException e) {
                        Log.e(tag, "Couldn't find UI Thread");
                    }
                }
                else {
                    try {
                        getActivity().runOnUiThread(() -> showError());
                    } catch (NullPointerException e) {
                        Log.e(tag, "Couldn't find UI Thread");
                    }
                }
            }
        });
    }
}