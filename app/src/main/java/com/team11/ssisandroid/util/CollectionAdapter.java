package com.team11.ssisandroid.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.team11.ssisandroid.R;
import com.team11.ssisandroid.fragments.CollectionFragment;
import com.team11.ssisandroid.models.Collection;

public class CollectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_BUTTON = 2;

    private String role;
    private Context mContext;
    private Collection collection;

    public CollectionAdapter(Context mContext, Collection collection, String role) {
        this.mContext = mContext;
        this.collection = collection;
        this.role = role;
    }

    // Create new view
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //If store clerk, we will only inflate layout with items and header
        if(role.contains("StoreClerk")) {
            if (viewType == TYPE_ITEM) {
                // Here Inflating your recyclerview item layout
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_collection_fragment, parent, false);
                return new CollectionViewHolder(itemView);
            } else if (viewType == TYPE_HEADER) {
                // Here Inflating your header view
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_collection_header_fragment, parent, false);
                return new CollectionHeaderViewHolder(itemView);
            }
        }

        //If Employee, we will inflate layout with items, header and confirmation button
        if(role.contains("Employee")) {
            if (viewType == TYPE_ITEM) {
                // Here Inflating your recyclerview item layout
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_collection_fragment, parent, false);
                return new CollectionViewHolder(itemView);
            } else if (viewType == TYPE_HEADER) {
                // Here Inflating your header view
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_collection_header_fragment, parent, false);
                return new CollectionHeaderViewHolder(itemView);
            } else if(viewType == TYPE_BUTTON){
                //Here Inflating your button view
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_collection_button_fragment, parent, false);
                return new CollectionButtonViewHolder(itemView);
            }
        }

        return null;
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

            if(role.contains("StoreClerk")){
                itemViewHolder.mTextViewDescription.setText(collection.getItemDisbursements()[position - 1].getItemDescription());
                itemViewHolder.mTextViewItemCode.setText(collection.getItemDisbursements()[position - 1].getItemCode());
                itemViewHolder.mTextViewQuantity.setText("Quantity: " + collection.getItemDisbursements()[position - 1].getAvailableQuantity());
            }
            else if(role.contains("Employee")){
                if(position != collection.getItemDisbursements().length + 1){
                    itemViewHolder.mTextViewDescription.setText(collection.getItemDisbursements()[position - 1].getItemDescription());
                    itemViewHolder.mTextViewItemCode.setText(collection.getItemDisbursements()[position - 1].getItemCode());
                    itemViewHolder.mTextViewQuantity.setText("Quantity: " + collection.getItemDisbursements()[position - 1].getAvailableQuantity());
                }
            }
        }
        else if (holder instanceof CollectionButtonViewHolder){

            final CollectionButtonViewHolder buttonViewHolder = (CollectionButtonViewHolder) holder;

            buttonViewHolder.collection_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(collection.getItemDisbursements().length < 1){
                        Toast.makeText(view.getContext(), "You have no collections to confirm", Toast.LENGTH_SHORT).show();
                    } else {
                        //Create fragment that you want to go to
                        CollectionFragment collectionFragment = new CollectionFragment();

                        //Go to new fragment on button click
                        AppCompatActivity activity  =(AppCompatActivity) view.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, collectionFragment).addToBackStack(null).commit();
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position){

        //If role contains Employee, itemViewType can be header/item/confirmationButton
        if(role.contains("Employee")){
            if(position == 0){
                return TYPE_HEADER;
            } else if(position == collection.getItemDisbursements().length + 1){
                return TYPE_BUTTON;
            } else {
                return TYPE_ITEM;
            }
        }
        //If role contains StoreClerk, should only have header and items
        else if (role.contains("StoreClerk")){
            if(position == 0){
                return TYPE_HEADER;
            } else {
                return TYPE_ITEM;
            }
        } else {
            return TYPE_ITEM;
        }
    }

    // getItemCount increase the position depending on role.
    @Override
    public int getItemCount() {

        // If role contains StoreClerk, should show Header + Items
        if(role.contains("StoreClerk")){
            if (collection.getItemDisbursements().length > 0)
                return collection.getItemDisbursements().length + 1;
        }
        // If role contains Employee, should show Header + Items + Approval button
        else if(role.contains("Employee")){
            if(collection.getItemDisbursements().length > 0)
                return collection.getItemDisbursements().length + 2;
        } else {
            return 0;
        }

        return 0;
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

    //Inner class to hold reference to confirmation button of Recycler View
    public class CollectionButtonViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout collection_button;

        public CollectionButtonViewHolder(View itemView) {
            super(itemView);
            collection_button = itemView.findViewById(R.id.collection_button);
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
