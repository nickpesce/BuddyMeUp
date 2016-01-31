package com.github.nickpesce.buddymeup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Friend {
    String name;
    String id;
    int color;
    Context context;
    double distance;
    boolean requested;
    boolean tethered;
    double latitude;
    double longitude;

    public Friend(Context context, String name, String id) {
        this.id = id;
        this.name = name;
        this.context = context;
        this.color = Color.argb(255, (name.hashCode()>>1)%255, (name.hashCode()>>3)%255, (name.hashCode()>>6)%255);
        tethered = false;
        requested = false;
        distance = -1;
    }

    public void setDistance(double d)
    {
        distance = d;
    }

    public ImageView getNewIcon(final boolean compact)
    {
        ImageView icon = new ImageView(context);
        final int radius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, context.getResources().getDisplayMetrics());

        icon.setImageDrawable(new Drawable() {
            @Override
            public void draw(Canvas canvas) {
                final Paint paint = new Paint();
                paint.setColor(color);
                canvas.drawOval(new RectF(0, 0, radius, radius), paint);
                paint.setColor(Color.BLACK);
                if (compact) {
                    paint.setTextSize(radius / 4);
                    canvas.drawText(name, radius / 2 - paint.measureText(name) / 2, radius + paint.getTextSize(), paint);
                } else {
                    paint.setTextSize(radius/2);
                    String nameDist = name + " " + (distance<1000? (distance*5280 + " ft") : (distance + " mi"));
                    canvas.drawText(nameDist + " " + distance, radius, radius/2 + paint.getTextSize()/2, paint);
                }
                paint.setColor(Color.WHITE);
                paint.setTextSize(radius / 2);
                String initial = name.substring(0, 1);
                canvas.drawText(initial, (radius / 2) - paint.measureText(initial) / 2, (radius / 2) + paint.getTextSize() / 2, paint);
            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                radius);
        params.setMargins(0, 10, 0, 10);

        icon.setLayoutParams(params);
        return icon;
    }

    public void setTethered(boolean f)
    {
        tethered = f;
    }

    public void setLocation(double latitude, double longitude)
    {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
