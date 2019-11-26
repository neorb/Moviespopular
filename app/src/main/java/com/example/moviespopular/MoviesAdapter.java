package com.example.moviespopular;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.moviespopular.model.Movie;
import com.squareup.picasso.Picasso;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {


    private Movie[] mMovieData;
    private final MovieAdapterOnClickHandler mClickHandler;

    public MoviesAdapter(Movie[] movie, MovieAdapterOnClickHandler clickHandler){
        mMovieData = movie;
        mClickHandler = clickHandler;

    }

    public interface MovieAdapterOnClickHandler{
        void onClick(int adapterPosition);
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final ImageView mMovieListImageView;

        public MoviesAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieListImageView = (ImageView) itemView.findViewById(R.id.iv_movie_posters);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }

    @NonNull
    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutItdForListItem = R.layout.movies_item_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToparantImmediately = false;

        View view = inflater.inflate(layoutItdForListItem, viewGroup, shouldAttachToparantImmediately);

        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapterViewHolder holder, int position) {

        String movieToBind = mMovieData[position].getPoster();
        //use Picasso to easily load album art thumbnails into  views
        //Picasso supports both download and error placeholders as optional features
        Picasso.get()
                .load(movieToBind)
                .placeholder(R.drawable.movie)
                .error(R.drawable.cloud_off)
                .into(holder.mMovieListImageView);

    }

    @Override
    public int getItemCount() {
        if (null == mMovieData){
            return 0;
        }
        return mMovieData.length;
    }



}
