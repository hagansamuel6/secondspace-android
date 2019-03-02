package com.samapps.hp.a2ndspace.Epoxy;

import android.content.Context;
import android.widget.LinearLayout;

import com.airbnb.epoxy.ModelView;
import com.samapps.hp.a2ndspace.R;

@ModelView(defaultLayout = R.layout.my_epoxy_view)
public class HeaderView extends LinearLayout {
    public HeaderView(Context context) {
        super(context);
    }
  /*  String Title;
    public HeaderView(Context context) {
        super(context);
    }

    @TextProp
    public void setTitle(String title) {
        Title = title;
    }*/
}
