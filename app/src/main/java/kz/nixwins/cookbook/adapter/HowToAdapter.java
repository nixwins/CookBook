package kz.nixwins.cookbook.adapter;

import android.content.Context;
import android.content.Intent;
import android.sax.TextElementListener;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import kz.nixwins.cookbook.GalleryActitvity;
import kz.nixwins.cookbook.R;
import kz.nixwins.cookbook.model.CookBookModel;

/**
 * Created by nixwins on 11/21/16.
 */

public class HowToAdapter extends RecyclerView.Adapter<HowToAdapter.HowToViewHolder> {

    private Context context;
    private CookBookModel cookBookModel;

    public HowToAdapter(Context context, CookBookModel cookBookModel) {
        this.context = context;
        this.cookBookModel = cookBookModel;
    }

    @Override
    public HowToViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.how_to_card_layout, parent, false);

        return new HowToViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HowToViewHolder holder, int position) {
        holder.bind(cookBookModel);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class HowToViewHolder extends RecyclerView.ViewHolder{

        private ImageView mainImg;
        private TextView cookFullDesc;
        private ExpandableRelativeLayout expandableLinearLayout;
        private ExpandableRelativeLayout ingredientExpLL;
        private TextView howToCookCaption;
        private TextView ingredient;

        public HowToViewHolder(View itemView) {
            super(itemView);
            mainImg = (ImageView) itemView.findViewById(R.id.how_to_main_image);
            cookFullDesc = (TextView) itemView.findViewById(R.id.how_to_cook_text);
            howToCookCaption = (TextView) itemView.findViewById(R.id.how_to_cook_caption);
            ingredient = (TextView) itemView.findViewById(R.id.how_to_ingredient);
            expandableLinearLayout = (ExpandableRelativeLayout) itemView.findViewById(R.id.how_to_expandableLayout);

        }
        public  void bind(final CookBookModel cookBookModel){
            Picasso.with(context)
                    .load(cookBookModel.getMainImageUrl())
                    .into(mainImg);

            mainImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, GalleryActitvity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("data", cookBookModel);
                    context.startActivity(intent);
                }
            });

            ingredient.setText(cookBookModel.getIngredient());

            cookFullDesc.setText(cookBookModel.getText());
            Log.d("TEXT", cookBookModel.getText());
            ///expandableLinearLayout.showContextMenu();
            expandableLinearLayout.setExpanded(true);
            howToCookCaption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    expandableLinearLayout.toggle();
                }
            });

        }

    }
}
