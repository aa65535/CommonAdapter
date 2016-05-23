package commonadapter.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * CommonAdapter
 * Created by Jian Zhang on 2016-05-23.
 */
@SuppressWarnings("unused")
public abstract class CommonAdapter<T, H extends ViewHolder> extends BaseAdapter {

    protected List<T> mData;
    protected Context mContext;
    protected final int[] mLayoutIds;
    protected final LayoutInflater mInflater;

    /**
     * @param context   Context 对象
     * @param data      List数据集合
     * @param layoutIds 布局ID, 使用分类型 ListView 时传递多个即可, 至少设置一个
     */
    public CommonAdapter(@NonNull Context context,
                         @NonNull List<T> data,
                         @LayoutRes int... layoutIds) {
        if (0 == layoutIds.length)
            throw new IllegalArgumentException("Providing at least one layoutId.");
        mData = data;
        mContext = context;
        mLayoutIds = layoutIds;
        mInflater = LayoutInflater.from(context);
    }

    /**
     * 获取 Adapter 中设置的数据
     */
    public List<T> getData() {
        return mData;
    }

    /**
     * 设置 Adapter 中的数据
     */
    public void setData(List<T> data) {
        this.mData = data;
    }

    /**
     * 获取和此 Adapter 关联的 Context 对象
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 获取 mData 元素的数量, ListView 会根据其生成对应数量的 Item
     */
    @Override
    public int getCount() {
        return mData.size();
    }

    /**
     * 返回对应位置 Item 所需的数据
     */
    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    /**
     * 获取布局类型(数据类型)的数量, 会根布局类型生成相对应的 Item.
     */
    @Override
    public int getViewTypeCount() {
        return mLayoutIds.length;
    }

    /**
     * 默认返回 0, 使用分类型 ListView 时需要重写此方法, 返回对应的 Type,
     * Adapter 才可以根据此 Type 使用相应的 Item 布局和 ViewHolder.
     */
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * 获取 Item 布局的 ID
     */
    public int getLayoutId(int position) {
        return mLayoutIds[getItemViewType(position)];
    }

    /**
     * 获取 Item 的 ID, 不常用
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 判断当前是否是最后一个 Item
     */
    public boolean isFirst(int position) {
        return position == 0;
    }

    /**
     * 判断当前是否是最后一个 Item
     */
    public boolean isLast(int position) {
        return position == getCount() - 1;
    }

    /**
     * 根据指定的位置获取一个视图, 用于显示到 ListView 中
     *
     * @param convertView 被重用的指定类型的 View, 可能为 null
     * @param parent      返回的 View 将要被绑定到的父视图
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int layoutId = getLayoutId(position);
        H holder = getHolder(mInflater, convertView, parent, layoutId);
        convert(holder, position);
        return holder.getConvertView();
    }

    /**
     * 获取一个 ViewHolder 对象
     * 如果 convertView 为 null 表示是要新生成一个 Item
     * 如果 convertView 不为 null 表示是复用的 Item
     */
    protected abstract H getHolder(@NonNull LayoutInflater inflater,
                                   @Nullable View convertView,
                                   @NonNull ViewGroup parent,
                                   @LayoutRes int layoutId);

    /**
     * 设置视图数据的抽象方法
     * 如果使用了分类型 ListView, 需要重写 getItemViewType(position) 返回对应的 Type 值,
     * 此 Type 值作为数组 mLayoutIds 的下标, 从而加载对应的布局.
     *
     * @param holder   与布局类型对应的 ViewHolder 对象
     * @param position 当前要处理视图(数据)的位置
     */
    protected abstract void convert(H holder, int position);

    /**
     * 向列表的尾部添加一个 Item
     */
    public synchronized void add(T object) {
        mData.add(object);
        notifyDataSetChanged();
    }

    /**
     * 添加指定 Collection 中的所有元素到此列表的结尾
     */
    public synchronized void addAll(Collection<? extends T> collection) {
        mData.addAll(collection);
        notifyDataSetChanged();
    }

    /**
     * 在列表的指定位置插入指定 Item
     */
    public synchronized void insert(int index, T object) {
        mData.add(index, object);
        notifyDataSetChanged();
    }

    /**
     * 移除列表中指定位置的 Item
     */
    public synchronized T remove(int index) {
        T o = mData.remove(index);
        notifyDataSetChanged();
        return o;
    }

    /**
     * 从此列表中移除第一次出现的指定 Item
     */
    public synchronized boolean remove(T object) {
        boolean b = mData.remove(object);
        notifyDataSetChanged();
        return b;
    }

    /**
     * 从列表中移除所有 Item
     */
    public synchronized void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    /**
     * 根据指定比较器产生的顺序对列表进行排序
     *
     * @param comparator 确定列表顺序的比较器, null 值指示应该使用 Item 的自然顺序
     */
    public synchronized void sort(Comparator<? super T> comparator) {
        Collections.sort(mData, comparator);
        notifyDataSetChanged();
    }

    /**
     * 反转列表中 Item 的顺序
     */
    public synchronized void reverse() {
        Collections.reverse(mData);
        notifyDataSetChanged();
    }

    /**
     * 带回调的启动
     */
    public void startActivityForResult(Intent intent, int requestCode) {
        if (mContext instanceof Activity)
            ((Activity) mContext).startActivityForResult(intent, requestCode);
    }

    /**
     * 带回调的启动的回调方法, 子类选择实现
     * 同时需要在包含此 Adapter 的 Activity 中重写 onActivityResult 方法并调用此方法
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

}
