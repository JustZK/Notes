package com.example.zk.notes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ZK on 2017/5/24.
 */

public class MainActivityListAdapter extends RecyclerView.Adapter<MainActivityListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> list;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;

    public MainActivityListAdapter(Context mContext, ArrayList<String> list) {
        this.mContext = mContext;
        this.list = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.item_main_tv.setText(list.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(onClickListener);
        view.setOnLongClickListener(onLongClickListener);
        return viewHolder;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, (int) v.getTag());
            }
        }
    };

    public View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemLongClick(v, (int) v.getTag());
                return true;
            }
            return false;
        }
    };

    /**
     * 处理item的点击事件和长按事件
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    /**
     * 向指定位置添加元素
     *
     * @param position
     * @param value
     */
    public void addItem(String value, int position) {
        if (position > list.size()) {
            position = list.size();
        }
        if (position < 0) {
            position = 0;
        }
        list.add(position, value);
        /**
         * 使用notifyItemInserted/notifyItemRemoved会有动画效果
         * 而使用notifyDataSetChanged()则没有
         */
        notifyItemInserted(position);
    }

    /**
     * 添加元素
     *
     * @param value
     */
    public void addItem(String value) {
        list.add(value);
        notifyItemInserted(list.size() - 1);
    }

    /**
     * 移除指定位置元素
     *
     * @param position
     * @return
     */
    public String removeItem(int position) {
        if (position > list.size() - 1) {
            return null;
        }
        String value = list.remove(position);
        notifyItemRemoved(position);
        return value;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView item_main_tv;

        public ViewHolder(View view) {
            super(view);
            item_main_tv = (TextView) view.findViewById(R.id.item_main_tv);
        }
    }
}
