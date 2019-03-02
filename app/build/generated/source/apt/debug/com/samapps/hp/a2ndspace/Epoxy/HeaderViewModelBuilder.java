package com.samapps.hp.a2ndspace.Epoxy;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.OnModelBoundListener;
import com.airbnb.epoxy.OnModelUnboundListener;
import com.airbnb.epoxy.OnModelVisibilityChangedListener;
import com.airbnb.epoxy.OnModelVisibilityStateChangedListener;
import java.lang.CharSequence;
import java.lang.Number;

public interface HeaderViewModelBuilder {
  HeaderViewModelBuilder onBind(OnModelBoundListener<HeaderViewModel_, HeaderView> listener);

  HeaderViewModelBuilder onUnbind(OnModelUnboundListener<HeaderViewModel_, HeaderView> listener);

  HeaderViewModelBuilder onVisibilityStateChanged(
      OnModelVisibilityStateChangedListener<HeaderViewModel_, HeaderView> listener);

  HeaderViewModelBuilder onVisibilityChanged(
      OnModelVisibilityChangedListener<HeaderViewModel_, HeaderView> listener);

  HeaderViewModelBuilder id(long id);

  HeaderViewModelBuilder id(@Nullable Number... arg0);

  HeaderViewModelBuilder id(long id1, long id2);

  HeaderViewModelBuilder id(@Nullable CharSequence arg0);

  HeaderViewModelBuilder id(@Nullable CharSequence arg0, @Nullable CharSequence... arg1);

  HeaderViewModelBuilder id(@Nullable CharSequence arg0, long arg1);

  HeaderViewModelBuilder layout(@LayoutRes int arg0);

  HeaderViewModelBuilder spanSizeOverride(@Nullable EpoxyModel.SpanSizeOverrideCallback arg0);
}
