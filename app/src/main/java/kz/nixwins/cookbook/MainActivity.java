package kz.nixwins.cookbook;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import kz.nixwins.cookbook.adapter.CookBookAdapter;
import kz.nixwins.cookbook.helpers.EndlessRecyclerViewScrollListener;
import kz.nixwins.cookbook.model.CookBookModel;
import kz.nixwins.cookbook.model.CookbookBuilder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nixwins on 11/17/16.
 */

public class MainActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private LinearLayoutManager cookLinearLoyoutManager;
    private CookBookAdapter recyclerViewAdapter;

    private TextView cookBookPreviewTextView;

    private OkHttpClient client;
    private HttpUrl.Builder urlBuilder;

    private String  urlWallGet = "https://api.vk.com/method/wall.get";
    private String  owner_id   = "-133276014";
    private Integer count      = 10;

    List<CookBookModel> items = new ArrayList<>();

    private  SwipeRefreshLayout cookSwipeRefreshLayout;
    private EndlessRecyclerViewScrollListener scrollListener;

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        /*
        * Config RecyclerView;
        * */

        recyclerView = (RecyclerView) findViewById(R.id.mainRView);

        cookLinearLoyoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(cookLinearLoyoutManager);

        recyclerViewAdapter = new CookBookAdapter(items, getApplicationContext(), recyclerView);
        recyclerView.setAdapter(recyclerViewAdapter);

        cookBookPreviewTextView = (TextView) recyclerView.findViewById(R.id.cook_preview_text_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        /*
        * Get DATA from VK API
        * */
        getDataFromVKApi("0");

        scrollListener = new EndlessRecyclerViewScrollListener(cookLinearLoyoutManager) {
            @Override
            public void onLoadMore(int totalItemsCount, RecyclerView view) {

                getDataFromVKApi(Integer.toString(totalItemsCount));

            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        cookSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        cookSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();

                clear();

            }
        });



    }
    public void clear() {
        items.clear();
        recyclerViewAdapter.notifyDataSetChanged();
    }

    void refreshItems() {
        // Load items
        // ...
        getDataFromVKApi("0");
        cookSwipeRefreshLayout.setRefreshing(false);
        // Load complete
        //onItemsLoadComplete();
    }



    private void getDataFromVKApi(final String offset){

        urlBuilder =  HttpUrl.parse(urlWallGet).newBuilder();
        urlBuilder.addQueryParameter("owner_id", owner_id);
        urlBuilder.addQueryParameter("count", count.toString());
        urlBuilder.addQueryParameter("offset", offset);
        urlBuilder.addQueryParameter("extended", "1");
        urlBuilder.addQueryParameter("v", "5.60");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();


        client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
              e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                    initCookBookModel(response, Integer.valueOf(offset));
                   // Log.d("RESPONSE", response.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerViewAdapter.notifyDataSetChanged();
                        }

                    });
            }
        });

    }

    private JSONObject parseResponse(Response response){

        JSONObject jsonObject = null;

        try {
            String jsonData = response.body().string();

            jsonObject = new JSONObject(jsonData);

        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  jsonObject;
    }

    private List<CookBookModel> initCookBookModel(Response response, int offset){

        List<CookBookModel> cookBookList = new ArrayList<>();
        JSONObject jsonObj = parseResponse(response);
        JSONObject eachItem;
        JSONArray  copyHistoryArr;
        JSONObject copyHistory;
        JSONArray  attachments;
        JSONObject firstPhoto;
        JSONObject photoObj;


        try {

            JSONObject responseJsonObj = jsonObj.getJSONObject("response");
            JSONArray  itemsArr        = responseJsonObj.getJSONArray("items");

            for (int i=0; i < itemsArr.length(); i++){
                ArrayList<String> images        = new ArrayList<>();
                eachItem         = itemsArr.getJSONObject(i);
                copyHistoryArr   = eachItem.getJSONArray("copy_history");
                copyHistory      = copyHistoryArr.getJSONObject(0);
                attachments      = copyHistory.getJSONArray("attachments");
                firstPhoto       = attachments.getJSONObject(0);
                photoObj         = firstPhoto.getJSONObject("photo");
                Map<String, String> meta = new HashMap<>();
                if(eachItem.getString("text").length() != 0) {
                    meta = parseText(eachItem.getString("text"));
                }

                //Log.d("DATA", eachItem.toString());

                for(int j=0; j < attachments.length(); j ++){

                    images.add(attachments.getJSONObject(j).getJSONObject("photo").getString("photo_604"));
                }

                if(offset == 0) {

                    items.add(new CookbookBuilder()
                            .category(meta.get("category"))
                            .title(meta.get("title"))
                            .previewText(meta.get("preview_text"))
                            .ingredient(meta.get("ingredient"))
                            .mainImageUrl(photoObj.getString("photo_604"))
                            .images(images)
                            .text(meta.get("full_text"))
                            .build()
                    );
                }else{

                    items.add(new CookbookBuilder()
                            .category(meta.get("category"))
                            .title(meta.get("title"))
                            .previewText(meta.get("preview_text"))
                            .ingredient(meta.get("ingredient"))
                            .mainImageUrl(photoObj.getString("photo_604"))
                            .images(images)
                            .text(meta.get("full_text"))
                            .build()
                    );

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(offset != 0){
            items.addAll(cookBookList);

        }
        return items;
    }

    private Map<String, String> parseText(String text){

        Map<String, String> metaData = new HashMap<>();

        StringTokenizer textTokenizer = new StringTokenizer(text, ";");
        metaData.put("category", textTokenizer.nextToken());
        metaData.put("title", textTokenizer.nextToken());
        metaData.put("preview_text", textTokenizer.nextToken());
        metaData.put("ingredient", textTokenizer.nextToken());
        metaData.put("full_text", textTokenizer.nextToken());

        return metaData;
    }
}
