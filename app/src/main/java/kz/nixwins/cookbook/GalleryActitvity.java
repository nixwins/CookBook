package kz.nixwins.cookbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import kz.nixwins.cookbook.adapter.GalleryAdapter;
import kz.nixwins.cookbook.model.CookBookModel;

/**
 * Created by nixwins on 11/21/16.
 */

public class GalleryActitvity extends Activity {

    private RecyclerView recyclerView;
    private GalleryAdapter galleryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_layout);

        LinearLayoutManager llManger = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        Intent intent = getIntent();
        CookBookModel cookBook = (CookBookModel) intent.getSerializableExtra("data");

        recyclerView = (RecyclerView) findViewById(R.id.galleryRecyclerView);

        recyclerView.setLayoutManager(llManger);

        galleryAdapter = new GalleryAdapter(getApplicationContext(), cookBook.getImages());


        recyclerView.setAdapter(galleryAdapter);

    }

}
