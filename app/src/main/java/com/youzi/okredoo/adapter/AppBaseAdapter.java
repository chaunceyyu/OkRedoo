package com.youzi.okredoo.adapter;/**
 * Created by Jackzjj on 16-7-5.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;
import android.widget.BaseAdapter;

import java.util.AbstractList;

/**
 * 抽象的适配器，可用于大部分的列表适配器，继承并声明一个可用于适配器的列表对象
 * User: Jacky Cheung(jackzjj@qq.com)
 * Date: 2016-07-05
 */
public abstract class AppBaseAdapter<T extends AbstractList> extends BaseAdapter implements
        RecyclerListener {

    protected Context mContext;
    protected LayoutInflater mInflater;
    private T mDataList;

    public AppBaseAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public void changeDataSet(T list) {
        initListData(list);
        notifyDataSetChanged();
    }

    private void initListData(T list) {
        mDataList = list;
    }

    @Override
    public int getCount() {
        if (mDataList != null) {
            return mDataList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDataList != null && position < mDataList.size()) {
            return mDataList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (mDataList != null && position < mDataList.size()) {
            return mDataList.get(position).hashCode();
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = newView(position, parent);
        } else {
            v = convertView;
        }
        bindView(v, position);
        return v;
    }

    /**
     * 数据绑定到视图，配合Binding接口使用，如果子类需要扩展可重写此方法
     *
     * @param view
     * @param position
     */
    public void bindView(View view, int position) {

        if (view instanceof Binding) {
            Binding item = (Binding) view;
            item.bind(mDataList.get(position), this, position);
        }

    }

    /**
     * 根据layoutId创建一个Item视图，如果子类需要扩展可重写此方法
     *
     * @param position
     * @param parent
     * @return
     */
    public View newView(int position, ViewGroup parent) {
        return mInflater.inflate(getLayoutId(), parent, false);
    }

    /**
     * 子类返回创建的layoutId，注意XML文件中需要使用自定义视图
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 返回数据列表
     *
     * @return
     */
    public T getDataList() {
        return mDataList;
    }

    @Override
    public void onMovedToScrapHeap(View view) {
        if (view instanceof Binding) {
            Binding item = (Binding) view;
            item.unBind();
        }
    }

    /**
     * 可绑定数据的接口，自定义视图中必须实现此接口
     *
     * @param <E>
     */
    public interface Binding<E> {
        /**
         * 绑定数据
         *
         * @param data
         * @param baseAdapter
         * @param position
         */
        void bind(E data, AppBaseAdapter baseAdapter, int position);

        /**
         * 数据回收
         */
        void unBind();
    }
}
