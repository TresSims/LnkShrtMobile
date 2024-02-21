package net.lnkshrt.lnkshrtmobile.ShortenLinks;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import net.lnkshrt.lnkshrtmobile.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

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

public class ShortenLinkFragment extends Fragment {
    EditText long_link;
    Button submit;
    Button share;
    TextView error;
    String tag = "shorten link";
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");


    public ShortenLinkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shorten_link, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        long_link = view.findViewById(R.id.long_link);
        submit = view.findViewById(R.id.submit);
        share = view.findViewById(R.id.share);
        error = view.findViewById(R.id.error_message);


        submit.setOnClickListener((v) -> {
            String link = long_link.getText().toString();
            String url = "https://lnkshrt.net/api/";
            JSONObject json = new JSONObject();

            try {
                json.put("link", link);
            } catch (JSONException e) {
                Log.e(tag, "Couldn't create request body");
                showError();
            }

            RequestBody body = RequestBody.create(json.toString(), JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Log.d(tag, request.toString());

            try {
                post(request);
            } catch (IOException e) {
                Log.d(tag, "Couldn't make Request");
                showError();
            }
        });

        share.setOnClickListener((v) -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, share.getText());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        });

        long_link.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    new URL(s.toString()).toURI();
                    setValidUrl(true);
                } catch (Exception e){
                    Log.i("shorten link", String.format("URL couldn't be validated. %s", e.getMessage()));
                    setValidUrl(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void showShare(String link) {
        error.setVisibility(View.GONE);
        share.setText(link);
        share.setVisibility(View.VISIBLE);
    }

    void showError(){
        share.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
    }

    void setValidUrl(boolean enable) {
        submit.setEnabled(enable);
    }

    void post(Request request) throws IOException {
        client.newCall(request).enqueue(new Callback() {

            public void onFailure(@NonNull final Call call, @NonNull IOException e) {
                Log.e(tag, "call failed");
                try {
                    getActivity().runOnUiThread(() -> showError());
                } catch (NullPointerException npe){
                    Log.e(tag, "Couldn't find UI Thread");
                }
            }

            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                String id;

                if (response.isSuccessful()) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        id = json.getString("id");
                    } catch (JSONException e) {
                        Log.e(tag, "Couldn't parse response body");
                        getActivity().runOnUiThread(() -> showError());
                        return;
                    } catch (NullPointerException e) {
                        Log.e(tag, "Couldn't find response body");
                        getActivity().runOnUiThread(() -> showError());
                        return;
                    }

                    try{
                        getActivity().runOnUiThread(() -> showShare(String.format("https://lnkshrt.net/api/%s", id)));
                    } catch (NullPointerException e){
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
