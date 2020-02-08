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
import com.team11.ssisandroid.fragments.RequisitionDetailsFragment;
import com.team11.ssisandroid.models.Requisition;

public class RequisitionAdapter extends RecyclerView.Adapter<RequisitionAdapter.RequisitionViewHolder> {
    private Context mContext;
    private Requisition[] mRequisitionArr;

    public RequisitionAdapter(Context mContext, Requisition[] mRequisitionArr) {
        this.mContext = mContext;
        this.mRequisitionArr = mRequisitionArr;
    }

    // Create new view (invoked by layout manager)
    @NonNull
    @Override
    public RequisitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.cardview_requisition_fragment, parent, false);
        final RequisitionViewHolder viewHolder = new RequisitionViewHolder(view);

        viewHolder.requisition_item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                //Create fragment that you want to go to
                RequisitionDetailsFragment requisitionDetailsFragment = new RequisitionDetailsFragment();

                //Data to pass to requisition details fragment
                Bundle bundle = new Bundle();
                bundle.putString("requisitionId", mRequisitionArr[viewHolder.getAdapterPosition()].getRequisitionId());
                requisitionDetailsFragment.setArguments(bundle);
                //Go to new fragment on button click
                AppCompatActivity activity  =(AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, requisitionDetailsFragment).addToBackStack(null).commit();

            }
        });

        return viewHolder;
    }

    // Replace the contents of your fragment view (invoked by layout manager)
    @Override
    public void onBindViewHolder(@NonNull RequisitionViewHolder holder, int position) {

        holder.mTextViewRequisitionId.setText("ID: " + mRequisitionArr[position].getRequisitionId());
        holder.mTextViewRemarks.setText("Remarks: " + mRequisitionArr[position].getRemarks());
    }

    // Return the size of your data (invoked by layout manager)
    @Override
    public int getItemCount() {
        if( mRequisitionArr.length > 0){
            return mRequisitionArr.length;
        } else {
            return 0;
        }
    }

    // Inner class to hold reference to each item of Recycler View
    public class RequisitionViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout requisition_item;
        private TextView mTextViewRequisitionId;
        private TextView mTextViewRemarks;

        public RequisitionViewHolder(View itemView) {
            super(itemView);
            // Get entire cardview_requisition_fragment layout for onclick
            requisition_item = itemView.findViewById(R.id.requisition_item);

            mTextViewRequisitionId = itemView.findViewById(R.id.requisitionViewTitle);
            mTextViewRemarks = itemView.findViewById(R.id.requisitionViewRemarks);
        }

    }

}
