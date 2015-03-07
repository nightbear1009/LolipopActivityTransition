package com.activityshareelement;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import de.greenrobot.event.EventBus;


public class MainActivity extends Activity {

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

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.list_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.bindview(position);
            if(position == 30) {
                Picasso.with(holder.itemView.getContext())
                        .load("http://d1n6pk67leuy4e.cloudfront.net/webapi/images/o/400/400/SalePage/972013/0/17452")
                        .error(R.drawable.ic_launcher)
                        .into(((MyViewHolder) holder).imageView);
            }else{
                Picasso.with(holder.itemView.getContext())
                        .load("http")
                        .error(R.drawable.ic_launcher)
                        .into(((MyViewHolder) holder).imageView);
            }
        }

        @Override
        public int getItemCount() {
            return 200;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
