package kz.kmg.qorgau.data.model.home;

import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;

public class PromoModel {
    private final Drawable image;
    private final String text;
    private final String destination;
    private final int backgroundTintColor;

    public PromoModel(
            Drawable image,
            String text, String destination,
            @ColorInt
                    int backgroundTintColor) {
        this.image = image;
        this.text = text;
        this.destination = destination;
        this.backgroundTintColor = backgroundTintColor;
    }

    public Drawable getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public String getDestination() {
        return destination;
    }

    public int getBackgroundTintColor() {
        return backgroundTintColor;
    }
}
