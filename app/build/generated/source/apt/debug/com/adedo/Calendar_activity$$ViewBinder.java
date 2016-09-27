// Generated code from Butter Knife. Do not modify!
package com.adedo;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Calendar_activity$$ViewBinder<T extends com.adedo.Calendar_activity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493067, "field 'widget'");
    target.widget = finder.castView(view, 2131493067, "field 'widget'");
    view = finder.findRequiredView(source, 2131493048, "field 'publicados'");
    target.publicados = finder.castView(view, 2131493048, "field 'publicados'");
  }

  @Override public void unbind(T target) {
    target.widget = null;
    target.publicados = null;
  }
}
