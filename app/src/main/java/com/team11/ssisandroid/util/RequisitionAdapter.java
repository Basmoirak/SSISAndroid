package com.team11.ssisandroid.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team11.ssisandroid.R;
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
        return new RequisitionViewHolder(view);
    }

    // Replace the contents of your fragment view (invoked by layout manager)
    @Override
    public void onBindViewHolder(@NonNull RequisitionViewHolder holder, int position) {

        holder.mTextViewRequisitionId.setText(mRequisitionArr[position].getRequisitionId());
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
        private TextView mTextViewRequisitionId;

        public RequisitionViewHolder(View itemView) {
            super(itemView);
            mTextViewRequisitionId = itemView.findViewById(R.id.requisitionViewTitle);
        }

    }
}
