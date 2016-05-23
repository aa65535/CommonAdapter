package commonadapter.library;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * DefaultAdapter
 * Created by Jian Chang on 2016-05-23.
 */
public abstract class DefaultAdapter<T> extends CommonAdapter<T, DefaultViewHolder> {
    public DefaultAdapter(@NonNull Context context,
                          @NonNull List<T> data,
                          @LayoutRes int... layoutIds) {
        super(context, data, layoutIds);
    }

    @Override
    protected DefaultViewHolder getHolder(@NonNull LayoutInflater inflater,
                                          @Nullable View convertView,
                                          @NonNull ViewGroup parent,
                                          @LayoutRes int layoutId) {
        return DefaultViewHolder.getHolder(inflater, convertView, parent, layoutId);
    }
}
