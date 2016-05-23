package commonadapter.library;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * DefaultViewHolder
 * Created by Jian Zhang on 2016-05-23.
 */
@SuppressWarnings("unused")
public class DefaultViewHolder implements ViewHolder {

    private View mFindView;
    private final View mConvertView;
    private final SparseArray<Object> mTags;

    /**
     * 构造器, 只允许通过 getHolder() 方法获取 DefaultViewHolder 对象
     */
    private DefaultViewHolder(LayoutInflater inflater, ViewGroup parent, int layoutId) {
        mTags = new SparseArray<>();
        mFindView = mConvertView = inflater.inflate(layoutId, parent, false);
        mConvertView.setTag(this);
    }

    /**
     * 获取一个 DefaultViewHolder 对象
     * 如果 convertView 为 null 就调用构造方法创建一个 DefaultViewHolder 对象
     * 如果 convertView 不为 null 表示是复用的 Item, 直接去 Tag 中取 DefaultViewHolder 对象
     */
    public static DefaultViewHolder getHolder(@NonNull LayoutInflater inflater,
                                              @Nullable View convertView,
                                              @NonNull ViewGroup parent,
                                              @LayoutRes int layoutId) {
        if (null == convertView)
            return new DefaultViewHolder(inflater, parent, layoutId);
        return (DefaultViewHolder) convertView.getTag();
    }

    /**
     * 返回当前 convertView: 就是 ListView 的 Item 视图
     */
    @Override
    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 返回当前 findView: 表示 getView() 方法从哪个视图上取一个视图对象
     * 默认和 convertView 是同一个对象, 当使用 setFindViewAt() 方法后就是 convertView 的某一个子视图
     */
    public View getFindView() {
        return mFindView;
    }

    /**
     * 根据 viewId 获取一个 View 对象
     */
    @NonNull
    public View getView(@IdRes int viewId) {
        View view;
        Object viewObj = mFindView.getTag(viewId);
        if (null == viewObj) {
            view = mFindView.findViewById(viewId);
            mFindView.setTag(viewId, view);
        } else
            view = (View) viewObj;
        return view;
    }

    /**
     * 设置从 convertView 指定位置的子视图上 getView()
     * getView() 方法将从该子视图上 findViewById()
     * 设置 index 为负数时, 将直接从 convertView 上 findViewById()
     */
    public DefaultViewHolder setFindViewAt(int index) {
        if (0 <= index && (mConvertView instanceof ViewGroup)) {
            ViewGroup viewGroup = (ViewGroup) this.mConvertView;
            if (index < viewGroup.getChildCount())
                mFindView = viewGroup.getChildAt(index);
            else
                throw new IllegalArgumentException("invalid index.");
        } else
            mFindView = mConvertView;
        return this;
    }

    /**
     * 根据 viewId 获取一个 ImageView 对象
     */
    public ImageView getImageView(@IdRes int viewId) {
        return (ImageView) getView(viewId);
    }

    /**
     * 根据 viewId 获取一个 TextView 对象
     */
    public TextView getTextView(@IdRes int viewId) {
        return (TextView) getView(viewId);
    }

    /**
     * 根据 viewId 获取一个 CheckBox 对象
     */
    public CheckBox getCheckBox(@IdRes int viewId) {
        return (CheckBox) getView(viewId);
    }

    /**
     * 为指定 viewId 的 ImageView 对象设置图片
     */
    public DefaultViewHolder setImageBitmap(@IdRes int viewId, Bitmap bm) {
        getImageView(viewId).setImageBitmap(bm);
        return this;
    }

    /**
     * 为指定 viewId 的 ImageView 对象设置图片
     */
    public DefaultViewHolder setImageDrawable(@IdRes int viewId, @Nullable Drawable drawable) {
        getImageView(viewId).setImageDrawable(drawable);
        return this;
    }

    /**
     * 为指定 viewId 的 ImageView 对象设置图片
     */
    public DefaultViewHolder setImageResource(@IdRes int viewId, @DrawableRes int resId) {
        getImageView(viewId).setImageResource(resId);
        return this;
    }

    /**
     * 为指定 viewId 的 ImageView 对象设置网络图片
     *
     * @param url         图片的 URL 地址或者图片绑定器支持的可获取图片的字符串
     * @param imageBinder 实现了 {@link ImageBinder} 接口的图片绑定器对象
     */
    public DefaultViewHolder bindImage(@IdRes int viewId, @NonNull String url,
                                       @NonNull ImageBinder imageBinder) {
        imageBinder.bind(getImageView(viewId), url);
        return this;
    }

    /**
     * 为指定 viewId 的 TextView 对象设置文字
     */
    public DefaultViewHolder setText(@IdRes int viewId, @StringRes int resid) {
        getTextView(viewId).setText(resid);
        return this;
    }

    /**
     * 为指定 viewId 的 TextView 对象设置文字
     */
    public DefaultViewHolder setText(@IdRes int viewId, CharSequence text) {
        getTextView(viewId).setText(text);
        return this;
    }

    /**
     * 为指定 viewId 的 TextView 对象设置文字颜色
     */
    public DefaultViewHolder setTextColor(@IdRes int viewId, @ColorInt int color) {
        getTextView(viewId).setTextColor(color);
        return this;
    }

    /**
     * 为指定 viewId 的 View 对象设置 TAG
     */
    public DefaultViewHolder setTag(@IdRes int viewId, final Object tag) {
        getView(viewId).setTag(tag);
        return this;
    }

    /**
     * 为指定 viewId 的 View 对象设置背景图片
     */
    public DefaultViewHolder setBackgroundResource(@IdRes int viewId, @DrawableRes int resid) {
        getView(viewId).setBackgroundResource(resid);
        return this;
    }

    /**
     * 使用当前 DefaultViewHolder 记录一个 TAG
     */
    public DefaultViewHolder putTag(int key, final Object tag) {
        mTags.put(key, tag);
        return this;
    }

    /**
     * 从当前 DefaultViewHolder 中取出一个TAG
     */
    public Object getTag(int key) {
        return mTags.get(key);
    }

    /**
     * 为指定 viewId 的 CheckBox 对象设置选中状态
     */
    public DefaultViewHolder setChecked(@IdRes int viewId, boolean checked) {
        getCheckBox(viewId).setChecked(checked);
        return this;
    }

    /**
     * 切换指定 viewId 的 CheckBox 的选中状态
     */
    public DefaultViewHolder toggle(@IdRes int viewId) {
        getCheckBox(viewId).toggle();
        return this;
    }

    /**
     * 为指定 viewId 的 View 对象设置点击监听
     */
    public DefaultViewHolder setOnClickListener(@IdRes int viewId,
                                                @Nullable OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    /**
     * 为指定 viewId 的 View 对象设置长按监听
     */
    public DefaultViewHolder setOnLongClickListener(@IdRes int viewId,
                                                    @Nullable OnLongClickListener listener) {
        getView(viewId).setOnLongClickListener(listener);
        return this;
    }

    /**
     * 使指定 viewId 的 View 对象消失
     */
    public DefaultViewHolder setGone(@IdRes int viewId) {
        return setGone(viewId, true);
    }

    /**
     * 为指定 viewId 的 View 对象设置是否消失
     */
    public DefaultViewHolder setGone(@IdRes int viewId, boolean gone) {
        return setVisibility(viewId, gone ? View.GONE : View.VISIBLE);
    }

    /**
     * 使指定 viewId 的 View 对象隐藏
     */
    public DefaultViewHolder setInvisibile(@IdRes int viewId) {
        return setInvisibile(viewId, true);
    }

    /**
     * 为指定 viewId 的 View 对象设置是否隐藏
     */
    public DefaultViewHolder setInvisibile(@IdRes int viewId, boolean invisible) {
        return setVisibility(viewId, invisible ? View.INVISIBLE : View.VISIBLE);
    }

    /**
     * 使指定 viewId 的 View 对象可见
     */
    public DefaultViewHolder setVisibile(@IdRes int viewId) {
        return setVisibile(viewId, true);
    }

    /**
     * 为指定 viewId 的 View 对象设置是否可见
     */
    public DefaultViewHolder setVisibile(@IdRes int viewId, boolean visible) {
        return setVisibility(viewId, visible ? View.VISIBLE : View.GONE);
    }

    /**
     * 为指定 viewId 的 View 对象设置可见性
     */
    public DefaultViewHolder setVisibility(@IdRes int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    /**
     * 为指定 viewId 的 View 对象设置 LayoutParams
     */
    public DefaultViewHolder setLayoutParams(@IdRes int viewId, @NonNull LayoutParams params) {
        getView(viewId).setLayoutParams(params);
        return this;
    }

    /**
     * 图片绑定接口
     */
    public interface ImageBinder {

        /**
         * 根据指定的 URL 将图片绑定到指定的 ImageView 上
         */
        void bind(@NonNull ImageView imageView, @NonNull String url);

    }

}
