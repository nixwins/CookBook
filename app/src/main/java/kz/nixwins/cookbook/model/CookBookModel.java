package kz.nixwins.cookbook.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by nixwins on 11/17/16.
 */

public class CookBookModel implements Serializable{

    private String category;
    private String title;
    private String previewText;
    private String ingredient;
    private String text;
    private String mainImageUrl;
    private ArrayList<String> images;

    CookBookModel(CookbookBuilder cookbookBuilder){
        this.category       = cookbookBuilder.getCategory();
        this.title          = cookbookBuilder.getTitle();
        this.previewText    = cookbookBuilder.getPreviewText();
        this.ingredient     = cookbookBuilder.getIngredient();
        this.text           = cookbookBuilder.getText();
        this.mainImageUrl   = cookbookBuilder.getMainImageUrl();
        this.images         = cookbookBuilder.getImages();
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getTitle() {
        if(title.length() != 0)
            return title.replace("\n", "");
        else
            return title;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public String getText() {
        return text;
    }

    public String getCategory() {
        return category;
    }

    public String getPreviewText(){
        return previewText;
    }

}
