package com.activityshareelement;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.squareup.picasso.Callback.EmptyCallback;

/**
 * Created by tedliang on 15/3/7.
 */
public class NextActivity extends Activity {
    public static final String VIEW_NAME = "next.view.name";

    private RecyclerView mRecyclerview;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new MyEvent(v));
                }
            });
        }

        public void bindview(int pos){
        }
    }

    public static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<String> list;
        public MyAdapter(){
            list = new ArrayList<>();
            for(int i=0 ; i<5 ;i++) {
                list.add("http://d1n6pk67leuy4e.cloudfront.net/webapi/images/o/400/400/SalePage/972013/0/17452");
            }
            for(String url : Data.URLS){
                list.add(url);
            }
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.list_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.bindview(position);
            Picasso.with(holder.itemView.getContext())
                    .load(list.get(position))
                    .error(R.drawable.ic_launcher)
                    .into(((MyViewHolder) holder).imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d("Ted","success");

                        }

                        @Override
                        public void onError() {

                        }
                    });
            if(position >= 5) {
                holder.imageView.setTransitionName(VIEW_NAME + (position - 5));
                holder.imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        EventBus.getDefault().post(new StartEvent());
                        return true;
                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return Data.URLS.length + 5;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postponeEnterTransition();
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(new MyAdapter());

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(MyEvent event){
        Intent intent = new Intent(this,NextActivity.class);
        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(this,
                        Pair.create(event.getView(),
                                NextActivity.VIEW_NAME));
        startActivity(intent, options.toBundle());
    }

    public void onEventMainThread(StartEvent e){
        startPostponedEnterTransition();
    }

    public static class MyEvent {
        private View view;

        public View getView() {
            return view;
        }

        public MyEvent(View view) {

            this.view = view;
        }
    }
}
