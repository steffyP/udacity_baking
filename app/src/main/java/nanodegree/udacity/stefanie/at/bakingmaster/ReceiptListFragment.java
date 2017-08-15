package nanodegree.udacity.stefanie.at.bakingmaster;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import nanodegree.udacity.stefanie.at.bakingmaster.data.Receipt;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

/**
 * Created by steffy on 13/08/2017.
 */

public class ReceiptListFragment extends Fragment {

    private static final String TAG = ReceiptListFragment.class.getSimpleName();
    private View progressBar;
    private RecyclerView recyclerView;
    private View errorView;
    private ReceiptAdapter receiptAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        progressBar = view.findViewById(R.id.progress);
        errorView = view.findViewById(R.id.error_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        if(getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {

            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        }
        receiptAdapter = new ReceiptAdapter(getContext());
        recyclerView.setAdapter(receiptAdapter);

        new ReceiptRequest().execute();

        return view;

    }

    private class ReceiptRequest extends AsyncTask<Object, Object, JSONArray> {


        @Override
        protected JSONArray doInBackground(Object... voids) {
            URL url = null;
            try {
                url = new URL("https://go.udacity.com/android-baking-app-json");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setInstanceFollowRedirects(true);
                urlConnection.connect();
               /* if(String.valueOf(urlConnection.getResponseCode()).startsWith("3")){
                    String locationHeader = urlConnection.getHeaderField("location");
                    if(locationHeader != null){
                        url = new URL(locationHeader);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.connect();
                    }
                }*/
                if (urlConnection.getResponseCode() == 200) {
                    InputStream is = urlConnection.getInputStream();

                    Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
                    String inputString = s.hasNext() ? s.next() : "";
                    is.close();

                    JSONArray jsonObject = new JSONArray(inputString);
                    return jsonObject;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
           if(jsonArray == null){
               showConnectionUnsuccessful();
           } else {
               showList(jsonArray);
           }
        }
    }

    private void showList(JSONArray jsonArray) {
        Log.d(TAG, "got object: " + jsonArray);
        ArrayList<Receipt> receiptList = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            try {
                receiptList.add(new Receipt(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        receiptAdapter.updateData(receiptList);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showConnectionUnsuccessful() {
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }
}
