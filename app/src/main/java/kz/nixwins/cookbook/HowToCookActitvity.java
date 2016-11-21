package kz.nixwins.cookbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import kz.nixwins.cookbook.adapter.HowToAdapter;
import kz.nixwins.cookbook.model.CookBookModel;

/**
 * Created by nixwins on 11/21/16.
 */

public class HowToCookActitvity extends Activity{


    private HowToAdapter howToAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_cook_layout);

        Intent intent = getIntent();
        CookBookModel cookBookModel = (CookBookModel) intent.getSerializableExtra("cookBookModel");

        LinearLayoutManager llManager = new LinearLayoutManager(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.how_to_recycler_view);

        howToAdapter = new HowToAdapter(getApplicationContext(), cookBookModel);

        recyclerView.setLayoutManager(llManager);
        recyclerView.setAdapter(howToAdapter);



    }
}
