package com.team11.ssisandroid.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team11.ssisandroid.R;
import com.team11.ssisandroid.models.Collection;

public class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Context mContext;
    private Collection collection;

    public CollectionAdapter(Context mContext, Collection collection) {
        this.mContext = mContext;
        this.collection = collection;
    }

    // Create new view
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            // Here Inflating your recyclerview item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_collection_fragment, parent, false);
            return new CollectionViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            // Here Inflating your header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_collection_header_fragment, parent, false);
            return new CollectionHeaderViewHolder(itemView);
        }
        else return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof CollectionHeaderViewHolder){
            CollectionHeaderViewHolder headerViewHolder = (CollectionHeaderViewHolder) holder;

            // You have to set your header items values with the help of model class and you can modify as per your needs
            headerViewHolder.mTextViewCollectionPoint.setText(collection.getCollectionPoint());
            headerViewHolder.mTextViewDepartmentName.setText(collection.getDepartmentName());

        }
        else if (holder instanceof CollectionViewHolder){

            final CollectionViewHolder itemViewHolder = (CollectionViewHolder) holder;

            // You have to set your listview items values with the help of model class and you can modify as per your needs
            itemViewHolder.mTextViewDescription.setText(collection.getItemDisbursements()[position - 1].getItemDescription());
            itemViewHolder.mTextViewItemCode.setText(collection.getItemDisbursements()[position - 1].getItemCode());
            itemViewHolder.mTextViewQuantity.setText("Quantity: " + collection.getItemDisbursements()[position - 1].getAvailableQuantity());
        }
    }

    @Override
    public int getItemViewType(int position){
        if(position == 0){
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    // getItemCount increase the position by 1. This will be the row of the header.
    @Override
    public int getItemCount() {
        if( collection.getItemDisbursements().length > 0){
            return collection.getItemDisbursements().length + 1;
        } else {
            return 0;
        }
    }


    // Inner class to hold reference to header of Recycler View
    public class CollectionHeaderViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextViewCollectionPoint;
        private TextView mTextViewDepartmentName;

        public CollectionHeaderViewHolder(View view) {
            super(view);

            mTextViewCollectionPoint = itemView.findViewById(R.id.collectionCollectionPoint);
            mTextViewDepartmentName = itemView.findViewById(R.id.collectionDepartmentName);
        }
    }

    // Inner class to hold reference to header of Recycler View
    public class CollectionViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextViewDescription;
        private TextView mTextViewItemCode;
        private TextView mTextViewQuantity;

        public CollectionViewHolder(View itemView) {
            super(itemView);

            mTextViewDescription = itemView.findViewById(R.id.collectionDescription);
            mTextViewItemCode = itemView.findViewById(R.id.collectionItemCode);
            mTextViewQuantity = itemView.findViewById(R.id.collectionQuantity);
        }
    }
}
