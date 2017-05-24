package com.example.myshop.Adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshop.R;
import com.example.myshop.bean.Camplain;
import com.example.myshop.bean.HomeCamplain;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.myshop.R.color.blue;

/**
 * Created by 刘博良 on 2017/4/19.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<HomeCamplain> mList;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private static int VIEW_TYPE_LEFT =0;
    private static int VIEW_TYPE_RIGHT =1;
    private onCaomplainClickListener mListerer;

    public interface onCaomplainClickListener{
        void onClick(View view,Camplain campaign);
    }

    public RecyclerAdapter(List<HomeCamplain> mList, Context context) {
        this.mList = mList;
        this.mContext =context;
        layoutInflater =LayoutInflater.from(context);
    }

    public void setOnCamClickListener(onCaomplainClickListener listener){
        mListerer =listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LEFT){
            return new ViewHolder(layoutInflater.inflate(R.layout.template_home_cardview_1,null));

        }else{
            return new ViewHolder(layoutInflater.inflate(R.layout.template_home_cardview_2,null));
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeCamplain camplain =mList.get(position);
        holder.textView.setText(camplain.getTitle());
        String testUrl =camplain.getCpOne().getImageUrl();
        Picasso.with(mContext).load(camplain.getCpOne().getImageUrl()).into(holder.bigImageView);
        Log.e("RecyclerAdapter",testUrl+"...");
        Picasso.with(mContext).load(camplain.getCpTwo().getImageUrl()).into(holder.upImageView);

        Picasso.with(mContext).load(camplain.getCpThree().getImageUrl()).into(holder.downImageView);
    }

    @Override
    public int getItemViewType(int position) {

        if (position %2 ==0){
            return VIEW_TYPE_LEFT;
        }else
            return VIEW_TYPE_RIGHT;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView;
        private ImageView bigImageView;
        private ImageView upImageView;
        private ImageView downImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.text_title);
            bigImageView = (ImageView) itemView.findViewById(R.id.imageView_big);
            upImageView = (ImageView) itemView.findViewById(R.id.imageView_small_up);
            downImageView = (ImageView) itemView.findViewById(R.id.imageView_small_down);
            bigImageView.setOnClickListener(this);
            upImageView.setOnClickListener(this);
            downImageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            anim(v);
        }

        private void anim(final View v){
            ObjectAnimator objectAnimator =ObjectAnimator.ofFloat(v,"rotationX",0.0F,360F)
                    .setDuration(200);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    HomeCamplain homeCamplain =mList.get(getLayoutPosition());
                    if (mListerer != null){
                        switch (v.getId()){
                            case  R.id.imageView_big:
                                mListerer.onClick(v,homeCamplain.getCpOne());
                                break;
                            case R.id.imageView_small_up:
                                mListerer.onClick(v,homeCamplain.getCpTwo());
                                break;
                            case R.id.imageView_small_down:
                                mListerer.onClick(v,homeCamplain.getCpThree());
                                break;
                        }

                    }
                }
            });
            objectAnimator.start();
        }
    }
}
