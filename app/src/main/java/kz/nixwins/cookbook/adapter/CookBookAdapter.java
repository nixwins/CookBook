package kz.nixwins.cookbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kz.nixwins.cookbook.GalleryActitvity;
import kz.nixwins.cookbook.HowToCookActitvity;
import kz.nixwins.cookbook.R;
import kz.nixwins.cookbook.model.CookBookModel;

/**
 * Created by nixwins on 11/17/16.
 */

public class CookBookAdapter extends RecyclerView.Adapter<CookBookAdapter.ViewHolder> {
    private RecyclerView recyclerView;
    private Context context;
    private List<CookBookModel> items = new ArrayList<>();


    public CookBookAdapter(List<CookBookModel> data, Context context, RecyclerView recyclerView){
        items = data;
        this.context = context;
        this.recyclerView = recyclerView;
    }


    @Override
    public CookBookAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cook_book_card_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CookBookAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /*
    * ViewHolder
    *
    * */
    public class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ll;
        private TextView title;
        private ImageView mainImageView;
        private TextView previewTextView;
        private Button howToBtn;
        private Button ingredientBtn;
        private TextView cookIngridentInfo;
        private ExpandableRelativeLayout exrL;

        public ViewHolder(View itemView) {

            super(itemView);
            ll = (LinearLayout) itemView.findViewById(R.id.cook_card_linear);
            title           = (TextView) itemView.findViewById(R.id.cook_book_title);
            previewTextView = (TextView) itemView.findViewById(R.id.cook_preview_text_view);
            Typeface typeFace=Typeface.createFromAsset(context.getAssets(),"fonts/robotolight.ttf");
            previewTextView.setTypeface(typeFace);

            mainImageView   = (ImageView) itemView.findViewById(R.id.cook_main_image);
            howToBtn        = (Button) itemView.findViewById(R.id.how_to_cook_btn);
            howToBtn.setTypeface(typeFace);

            ingredientBtn   = (Button) itemView.findViewById(R.id.cook_ingredient);
            ingredientBtn.setTypeface(typeFace);
            cookIngridentInfo=(TextView) itemView.findViewById(R.id.cook_ingredient_info);
            cookIngridentInfo.setTypeface(typeFace);
            exrL             = (ExpandableRelativeLayout) itemView.findViewById(R.id.expandableLayout1);

        }


        public void bind(final CookBookModel cookBookModel){



            Picasso.with(context)
                    .load(cookBookModel.getMainImageUrl())
                    .placeholder(R.drawable.placeholder)
                    .into(mainImageView);

            mainImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent  intent = new Intent(context, GalleryActitvity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("data", cookBookModel);
                    context.startActivity(intent);
                }
            });

            previewTextView.setText(cookBookModel.getPreviewText());
            title.setText(cookBookModel.getTitle());

            cookIngridentInfo.setText(cookBookModel.getIngredient());
            ingredientBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exrL.toggle();
                }
            });
            ingredientBtn.setText(R.string.ingredient);

            howToBtn.setText(R.string.howt_to_btn);
            howToBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, HowToCookActitvity.class);
                    intent.putExtra("cookBookModel", cookBookModel);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });


        }
    }

    public void notifyDataChanged(){
        notifyDataSetChanged();
    }



}
