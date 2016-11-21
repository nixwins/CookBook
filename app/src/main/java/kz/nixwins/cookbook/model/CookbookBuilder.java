package kz.nixwins.cookbook.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nixwins on 11/20/16.
 */

public class CookbookBuilder {

    private String category;
    private String title;
    private String previewText;
    private String ingredient;
    private String text;
    private String mainImageUrl;
    private ArrayList<String> images;

    public CookBookModel build(){
        return  new CookBookModel(this);
    }

    public CookbookBuilder category(String category){
        this.category = category;
        return this;
    }

    public CookbookBuilder title(String title){
        this.title = title;
        return  this;
    }

    public CookbookBuilder previewText(String previewText){
        this.previewText = previewText;
        return  this;
    }
    public CookbookBuilder ingredient(String ingredient){
        this.ingredient = ingredient;
        return this;
    }
    public CookbookBuilder text(String text){
        this.text = text;
        return this;
    }
    public CookbookBuilder mainImageUrl(String mainImageUrl){
        this.mainImageUrl = mainImageUrl;
        return this;
    }
    public CookbookBuilder images(ArrayList<String> images){
        this.images = images;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getPreviewText() {
        return previewText;
    }

    public String getIngredient() {
        return ingredient;
    }

    public String getText() {
        return text;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public ArrayList<String> getImages() {
        return images;
    }
}
