package com.youzi.okredoo.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.youzi.okredoo.BaseActivity;
import com.youzi.okredoo.R;
import com.youzi.okredoo.model.Dynamic;
import com.youzi.okredoo.model.DynamicImg;
import com.youzi.okredoo.model.User;
import com.youzi.okredoo.net.Api;
import com.youzi.okredoo.net.RequestUtils;
import com.youzi.okredoo.net.ResponseCallBack;
import com.youzi.okredoo.net.ServiceException;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人相册适配器
 * Created by hjw on 17/1/23.
 */

public class MyCircleAdapter extends RecyclerView.Adapter<MyCircleAdapter.ViewHolder> implements View.OnClickListener {

    public static final String TAG = MyCircleAdapter.class.getSimpleName();

    private RecyclerViewListener mRecyclerViewListener;

    public interface RecyclerViewListener<T> {

        void onItemClick(View view, T data);

    }

    private List<Dynamic> mDynamicList = new ArrayList<>();

    private BaseActivity mActivity;

    private int bitMargin, smallMargin;

    private User mUser;

    public MyCircleAdapter(Activity activity, User user) {
        this.mActivity = (BaseActivity) activity;
        bitMargin = DensityUtil.dip2px(24);
        smallMargin = DensityUtil.dip2px(6);
        mUser = user;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_my_circle_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
//        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final Dynamic dynamic = mDynamicList.get(position);
        holder.tvDate.setText(dynamic.getLastDate());
        holder.tvMonth.setText(dynamic.getMonth());
        holder.tvDay.setText(dynamic.getDay());
        holder.llRootView.setPadding(0, dynamic.isMarginTop() ? bitMargin : smallMargin, 0, 0);

        holder.tvContent.setText(dynamic.getContent());

//        holder.itemView.setTag(dynamic);
//        holder.itemView.setOnClickListener(this);

        String type = dynamic.getType();

        holder.rlRight.setBackgroundColor(0);
        holder.rlRight.setPadding(0, 0, 0, 0);
        holder.tvShareContent.setVisibility(View.GONE);
        holder.tvContent.setTextColor(0xff333333);

        if (type.equals(String.valueOf(Dynamic.TYPE_FORWORD))) {
            //转发动态
            holder.rlRight.setBackgroundColor(mActivity.getResources().getColor(R.color.sysBg));
            holder.rlRight.setPadding(12, 12, 12, 12);
            if (!TextUtils.isEmpty(dynamic.getContent())) {
                holder.tvShareContent.setVisibility(View.VISIBLE);
                holder.tvShareContent.setText(dynamic.getContent());
            }
            holder.tvContent.setTextColor(0xff666666);
            holder.tvContent.setText(dynamic.getSourceContent());

            holder.tvImgCount.setVisibility(View.GONE);
            holder.llContent.setVisibility(View.VISIBLE);
            holder.llImgView.setVisibility(View.VISIBLE);
            holder.ivAdd.setVisibility(View.GONE);
            holder.rlVideo.setVisibility(View.GONE);
            holder.llContent.setVisibility(View.VISIBLE);
//            holder.llRootView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ForwardUtils.target(mActivity, dynamic.getSourceUrl());
//                }
//            });
            holder.rlImage.setVisibility(View.VISIBLE);
            holder.oneImg.setVisibility(View.VISIBLE);
            holder.llImg2.setVisibility(View.GONE);
            holder.llImg3.setVisibility(View.GONE);
            holder.llImg4.setVisibility(View.GONE);

            Glide.with(mActivity).load(dynamic.getSourceCover()).into(holder.oneImg);
//            ImageUtils.displayImage(holder.oneImg, dynamic.getSourceCover());
            if (dynamic.getViewStatus().equals("2")) {
                holder.ivImageLock.setVisibility(View.VISIBLE);
            } else {
                holder.ivImageLock.setVisibility(View.GONE);
            }


        } else if (type.equals(String.valueOf(Dynamic.TYPE_IMAGE))) {
            //图片动态
            holder.tvImgCount.setVisibility(View.VISIBLE);
            holder.llContent.setVisibility(View.VISIBLE);
            holder.llImgView.setVisibility(View.VISIBLE);
            holder.ivAdd.setVisibility(View.GONE);
            holder.rlVideo.setVisibility(View.GONE);
            holder.tvImgCount.setText("共" + dynamic.getImages().size() + "张  ");
            holder.llContent.setVisibility(View.VISIBLE);

            holder.rlImage.setVisibility(View.VISIBLE);
            setImages(holder, dynamic.getImages());
            if (dynamic.getViewStatus().equals("2")) {
                holder.ivImageLock.setVisibility(View.VISIBLE);
            } else {
                holder.ivImageLock.setVisibility(View.GONE);
            }


        } else if (type.equals(String.valueOf(Dynamic.TYPE_VIDEO))) {

            //视频动态
            holder.rlImage.setVisibility(View.GONE);
            holder.llContent.setVisibility(View.VISIBLE);
            holder.llImgView.setVisibility(View.VISIBLE);
            holder.ivAdd.setVisibility(View.GONE);
            holder.rlVideo.setVisibility(View.VISIBLE);
            holder.tvImgCount.setVisibility(View.GONE);
            holder.llContent.setVisibility(View.VISIBLE);
            holder.iconPlay.setVisibility(View.VISIBLE);

            hideImages(holder);

            if (dynamic.getViewStatus().equals("2")) {
                holder.ivVideoLock.setVisibility(View.VISIBLE);
            } else {
                holder.ivVideoLock.setVisibility(View.GONE);
            }

            //这里表示视频是从数据库获取的正在上传的视频
//            if (dynamic.getLocalType() == Dynamic.LOCAL_TYPE_UPLOAD_VIDEO) {
//
//                holder.iconPlay.setVisibility(View.GONE);
//                holder.progressLayout.setVisibility(View.VISIBLE);
//                holder.progressView.setVisibility(View.VISIBLE);
//
//                holder.progressView.setProgress(dynamic.getProgress());
//
//                Glide.with(mActivity).load("file://" + dynamic.getVideoCover()).into(holder.ivVideo);
////                ImageUtils.displayImage(holder.ivVideo, dynamic.getVideoCover());
//                holder.llRootView.setOnClickListener(null);
//
//            } else {
            holder.progressLayout.setVisibility(View.GONE);
//                holder.progressView.setVisibility(View.GONE);

            if (!TextUtils.isEmpty(dynamic.getVideoCover())) {
//                    if (dynamic.isRecord()) {
//                        Glide.with(mActivity).load(dynamic.getVideoCover()).into(holder.ivVideo);
//                        ImageUtils.displayImage(holder.ivVideo, dynamic.getVideoCover());
//                    } else {
//                        Glide.with(mActivity).load(dynamic.getVideoCover()).into(holder.ivVideo);
//                        ImageUtils.display300(holder.ivVideo, dynamic.getVideoCover());
//                    }
                Glide.with(mActivity).load(dynamic.getVideoCover()).into(holder.ivVideo);
            } else {
                holder.ivVideo.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.youzi_logo));
            }

//            holder.llRootView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    showDynamicDetail(dynamic);
//                }
//            });
//            }

        } else if (type.equals("10")) {
            //添加
            holder.ivAdd.setVisibility(View.VISIBLE);
            holder.llContent.setVisibility(View.GONE);
            holder.llImgView.setVisibility(View.GONE);
//            holder.llRootView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    addDynamic();
//                }
//            });
        }

//        //这里表示视频是从数据库获取的正在上传的视频
//        if (dynamic.getLocalType() == Dynamic.LOCAL_TYPE_UPLOAD_VIDEO) {
//            holder.tvDelete.setVisibility(View.GONE);
//            holder.tvDelete.setOnClickListener(null);
//        } else {
        if (!TextUtils.isEmpty(dynamic.getUid()) && dynamic.getUid().equals(mUser.getUid())) {
            holder.tvDelete.setVisibility(View.VISIBLE);
            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteDynamic(dynamic, position);
                }
            });
        } else {
            holder.tvDelete.setVisibility(View.GONE);
            holder.tvDelete.setOnClickListener(null);
        }

        holder.llRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRecyclerViewListener != null) {
                    mRecyclerViewListener.onItemClick(v, dynamic);
                }
            }
        });

//        }

    }

    private void deleteDynamic(final Dynamic dy, final int position) {

        Map<String, String> para = new HashMap<>();
        para.put("did", dy.getDid());
        RequestUtils.sendPostRequest(Api.DELETE_DYNAMIC, mUser.getUid(), mUser.getToken(), para, new ResponseCallBack<Object>() {
            @Override
            public void onSuccess(Object data) {
                super.onSuccess(data);
                removeData(position);
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(ServiceException e) {
                mActivity.showToast(e.getMsg());
            }
        });

    }

    private void addDynamic() {
//        CommonHelper.showDynamicPop(mActivity);
    }

    private void showDynamicDetail(Dynamic dn) {
//        if (dn.getType().equals("2")) {
//            //视频
//            String params = "?did=" + dn.getDid();
//            ForwardUtils.target(mActivity, Constant.DYNAMIC_VIDEO_DETAIL + params);
//        } else if (dn.getType().equals("1")) {
//            //图片
//            List<DynamicImg> imgList = dn.getImages();
//            CommonHelper.previewDynamicImage(dn.getDid(), mActivity, imgList);
//        }
    }


    private void setImages(ViewHolder holder, final List<DynamicImg> images) {
        if (images.size() > 3) {
            holder.llImg4.setVisibility(View.VISIBLE);
            holder.oneImg.setVisibility(View.GONE);
            holder.llImg2.setVisibility(View.GONE);
            holder.llImg3.setVisibility(View.GONE);

            Glide.with(mActivity).load(images.get(0).getImage()).into(holder.fourImg1);
            Glide.with(mActivity).load(images.get(1).getImage()).into(holder.fourImg2);
            Glide.with(mActivity).load(images.get(2).getImage()).into(holder.fourImg3);
            Glide.with(mActivity).load(images.get(3).getImage()).into(holder.fourImg4);
//            ImageUtils.display150(holder.fourImg1, images.get(0).getImage());
//            ImageUtils.display150(holder.fourImg2, images.get(1).getImage());
//            ImageUtils.display150(holder.fourImg3, images.get(2).getImage());
//            ImageUtils.display150(holder.fourImg4, images.get(3).getImage());

        } else if (images.size() == 3) {
            holder.llImg3.setVisibility(View.VISIBLE);
            holder.oneImg.setVisibility(View.GONE);
            holder.llImg2.setVisibility(View.GONE);
            holder.llImg4.setVisibility(View.GONE);

            Glide.with(mActivity).load(images.get(0).getImage()).into(holder.threeImg1);
            Glide.with(mActivity).load(images.get(1).getImage()).into(holder.threeImg2);
            Glide.with(mActivity).load(images.get(2).getImage()).into(holder.threeImg3);

//            ImageUtils.display150(holder.threeImg1, images.get(0).getImage());
//            ImageUtils.display150(holder.threeImg2, images.get(1).getImage());
//            ImageUtils.display150(holder.threeImg3, images.get(2).getImage());
        } else if (images.size() == 2) {
            holder.llImg2.setVisibility(View.VISIBLE);
            holder.oneImg.setVisibility(View.GONE);
            holder.llImg3.setVisibility(View.GONE);
            holder.llImg4.setVisibility(View.GONE);
            Glide.with(mActivity).load(images.get(0).getImage()).into(holder.twoImg1);
            Glide.with(mActivity).load(images.get(1).getImage()).into(holder.twoImg2);
//            ImageUtils.display150(holder.twoImg1, images.get(0).getImage());
//            ImageUtils.display150(holder.twoImg2, images.get(1).getImage());


        } else if (images.size() == 1) {
            holder.oneImg.setVisibility(View.VISIBLE);
            holder.llImg2.setVisibility(View.GONE);
            holder.llImg3.setVisibility(View.GONE);
            holder.llImg4.setVisibility(View.GONE);
            Glide.with(mActivity).load(images.get(0).getImage()).into(holder.oneImg);
//            ImageUtils.display150(holder.oneImg, images.get(0).getImage());
        }
    }

    private void hideImages(ViewHolder holder) {
        holder.llImg4.setVisibility(View.GONE);
        holder.oneImg.setVisibility(View.GONE);
        holder.llImg2.setVisibility(View.GONE);
        holder.llImg3.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mDynamicList.size();
    }


    public void addData(List<Dynamic> data) {
        if (data.size() > 0) {
            mDynamicList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addData(Dynamic data) {
        mDynamicList.add(data);
        notifyDataSetChanged();
    }

    public void addData(int index, Dynamic data) {
        mDynamicList.add(index, data);
        notifyDataSetChanged();
    }


    public void removeData(int position) {
        mDynamicList.remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        mDynamicList.clear();
        notifyDataSetChanged();
    }

    public int getDataSize() {
        return mDynamicList.size();
    }


    public void setOnItemClickListener(RecyclerViewListener listener) {
        this.mRecyclerViewListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mRecyclerViewListener != null) {
            //注意这里使用getTag方法获取数据
            mRecyclerViewListener.onItemClick(view, view.getTag());
        }
    }

    public List<Dynamic> getDynamicList() {
        return mDynamicList;
    }

//    public void updateProgress(Progress progress) {
//        for (int i = 0; i < mDynamicList.size(); i++) {
//            Dynamic dynamic = mDynamicList.get(i);
//            if (!TextUtils.isEmpty(dynamic.getVideoURL())) {
//                if (dynamic.getVideoURL().equalsIgnoreCase(progress.getId())) {
//                    dynamic.setProgress(progress.getProgress());
//                    notifyItemChanged(i + 1);
//                    return;
//                }
//            }
//        }
//    }

    public void updateDynamic(Dynamic dynamic) {
        for (int i = 1; i < mDynamicList.size(); i++) {
            Dynamic dy = mDynamicList.get(i);
            if (TextUtils.isEmpty(dy.getDid())) {
                dy.update(dynamic);
                notifyItemChanged(i + 1);
                return;
            }
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llRootView;
        public RelativeLayout rlVideo;
        public ImageView ivVideo;
        public ImageView ivVideoLock;
        public TextView tvDate;
        public TextView tvMonth;
        public TextView tvDay;
        public TextView tvContent;
        public TextView tvImgCount;
        public RelativeLayout rlImage;
        public ImageView ivImageLock;
        public ImageView oneImg;
        public LinearLayout llImg2;
        public ImageView twoImg1;
        public ImageView twoImg2;
        public LinearLayout llImg3;
        public ImageView threeImg1;
        public ImageView threeImg2;
        public ImageView threeImg3;
        public LinearLayout llImg4;
        public ImageView fourImg1;
        public ImageView fourImg2;
        public ImageView fourImg3;
        public ImageView fourImg4;

        public ImageView ivAdd;
        public RelativeLayout llContent;
        public LinearLayout llImgView;

        public TextView tvDelete;

        //        public CircleProgressBar progressView;
        public View iconPlay;

        public View rlRight;
        public TextView tvShareContent;

        private final View progressLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.llRootView = (LinearLayout) itemView.findViewById(R.id.llRootView);

            this.tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            this.tvMonth = (TextView) itemView.findViewById(R.id.tvMonth);
            this.tvDay = (TextView) itemView.findViewById(R.id.tvDay);
            this.tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            this.tvImgCount = (TextView) itemView.findViewById(R.id.tvImgCount);


            this.rlImage = (RelativeLayout) itemView.findViewById(R.id.rlImage);
            this.ivImageLock = (ImageView) itemView.findViewById(R.id.ivImageLock);
            this.ivVideoLock = (ImageView) itemView.findViewById(R.id.ivVideoLock);
            this.oneImg = (ImageView) itemView.findViewById(R.id.oneImg);
            this.llImg2 = (LinearLayout) itemView.findViewById(R.id.llImg2);
            this.twoImg1 = (ImageView) itemView.findViewById(R.id.twoImg1);
            this.twoImg2 = (ImageView) itemView.findViewById(R.id.twoImg2);

            this.llImg3 = (LinearLayout) itemView.findViewById(R.id.llImg3);
            this.threeImg1 = (ImageView) itemView.findViewById(R.id.threeImg1);
            this.threeImg2 = (ImageView) itemView.findViewById(R.id.threeImg2);
            this.threeImg3 = (ImageView) itemView.findViewById(R.id.threeImg3);

            this.llImg4 = (LinearLayout) itemView.findViewById(R.id.llImg4);
            this.fourImg1 = (ImageView) itemView.findViewById(R.id.fourImg1);
            this.fourImg2 = (ImageView) itemView.findViewById(R.id.fourImg2);
            this.fourImg3 = (ImageView) itemView.findViewById(R.id.fourImg3);
            this.fourImg4 = (ImageView) itemView.findViewById(R.id.fourImg4);

            this.rlVideo = (RelativeLayout) itemView.findViewById(R.id.rlVideo);
            this.ivVideo = (ImageView) itemView.findViewById(R.id.ivVideo);

            this.ivAdd = (ImageView) itemView.findViewById(R.id.ivAdd);

            llContent = (RelativeLayout) itemView.findViewById(R.id.llContent);
            llImgView = (LinearLayout) itemView.findViewById(R.id.llImgView);
            this.tvDelete = (TextView) itemView.findViewById(R.id.tvDelete);

//            progressView = (CircleProgressBar) itemView.findViewById(R.id.progress);
//            progressView.setProgressTextSize(CommonUtil.dp2px(mActivity, 16));
//            progressView.setProgressTextColor(Color.WHITE);
//            progressView.setProgressStrokeWidth(CommonUtil.dp2px(mActivity, 5));

            iconPlay = itemView.findViewById(R.id.icon_play);
            progressLayout = itemView.findViewById(R.id.progress_layout);

            rlRight = itemView.findViewById(R.id.rlRight);
            tvShareContent = (TextView) itemView.findViewById(R.id.tvShareContent);
        }
    }
}