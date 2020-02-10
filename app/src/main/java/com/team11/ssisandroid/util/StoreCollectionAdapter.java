package com.team11.ssisandroid.util;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.team11.ssisandroid.R;
import com.team11.ssisandroid.fragments.CollectionFragment;
import com.team11.ssisandroid.models.StoreCollection;

public class StoreCollectionAdapter extends RecyclerView.Adapter<StoreCollectionAdapter.StoreCollectionViewHolder>{
    private Context mContext;
    private StoreCollection mStoreCollection;

    public StoreCollectionAdapter(Context mContext, StoreCollection mStoreCollection) {
        this.mContext = mContext;
        this.mStoreCollection = mStoreCollection;
    }

    @NonNull
    @Override
    public StoreCollectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_store_collection_fragment, parent, false);
        final StoreCollectionViewHolder viewHolder = new StoreCollectionViewHolder(view);

        viewHolder.store_collection_item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                // Create fragment that you want to go to
                CollectionFragment collectionFragment = new CollectionFragment();

                // Data to pass to collection fragment
                Bundle bundle = new Bundle();
                bundle.putString("departmentId", mStoreCollection.getGroupedDepartmentCollections()[viewHolder.getAdapterPosition()].getDepartmentId());
                collectionFragment.setArguments(bundle);

                // Go to new fragment on button click
                AppCompatActivity activity  =(AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, collectionFragment).addToBackStack(null).commit();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoreCollectionViewHolder holder, int position) {

        holder.mTextViewDepartmentName.setText(mStoreCollection.getGroupedDepartmentCollections()[position].getDepartmentName());
        holder.mTextViewCollectionPoint.setText(mStoreCollection.getGroupedDepartmentCollections()[position].getCollectionPoint());
    }

    @Override
    public int getItemCount() {
        if( mStoreCollection.getGroupedDepartmentCollections().length > 0){
            return mStoreCollection.getGroupedDepartmentCollections().length;
        } else {
            return 0;
        }
    }

    // Inner class to hold reference to each item of Recycler View
    public class StoreCollectionViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout store_collection_item;
        private TextView mTextViewDepartmentName;
        private TextView mTextViewCollectionPoint;

        public StoreCollectionViewHolder(View itemView) {
            super(itemView);
            // Get entire cardview_requisition_fragment layout for onclick
            store_collection_item = itemView.findViewById(R.id.store_collection_item);

            mTextViewDepartmentName = itemView.findViewById(R.id.store_collectionDepartmentName);
            mTextViewCollectionPoint = itemView.findViewById(R.id.store_collectionCollectionPoint);
        }
    }
}
