package kz.nixwins.cookbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import kz.nixwins.cookbook.R;

/**
 * Created by nixwins on 11/21/16.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>{

    private Context context;
    private ArrayList<String> imagesUrl;


    public GalleryAdapter(Context context, ArrayList imagesUrl){
        this.context = context;
        this.imagesUrl = imagesUrl;
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_card_view_layout, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        holder.bind(imagesUrl.get(position));
    }

    @Override
    public int getItemCount() {
        return imagesUrl.size();
    }


    public class  GalleryViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.gallery_imageView);

        }
        public void bind(String imageUrl){
            Picasso.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);
        }

    }
}
