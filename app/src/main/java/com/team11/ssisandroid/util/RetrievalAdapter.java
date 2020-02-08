package com.team11.ssisandroid.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team11.ssisandroid.R;
import com.team11.ssisandroid.models.Retrieval;

public class RetrievalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_BUTTON = 0;
    private static final int TYPE_ITEM = 1;

    private Context mContext;
    private Retrieval[] retrievals;
    private Dialog myDialog;

    TextView dialogItemDescription;
    EditText dialogRemarks;
    EditText dialogQuantity;
    Button dialogBtn;

    public RetrievalAdapter(Context mContext, Retrieval[] retrievals){
        this.mContext = mContext;
        this.retrievals = retrievals;
    }

    // Create new view
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // ini Dialog
        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_stockadjustment);
        dialogItemDescription = myDialog.findViewById(R.id.dialog_item_description);
        dialogRemarks = myDialog.findViewById(R.id.dialog_remarks);
        dialogQuantity = myDialog.findViewById(R.id.dialog_quantity);
        dialogBtn = myDialog.findViewById(R.id.dialog_confirm_button);

        if(viewType == TYPE_ITEM){
            // Here Inflating your recyclerview item layout
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_retrieval_fragment, parent, false);
            return new RetrievalAdapter.RetrievalViewHolder(itemView);
        } else if(viewType == TYPE_BUTTON){
            // Here Inflating your header view
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_retrieval_button_fragment, parent, false);
            return new RetrievalAdapter.RetrievalButtonViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof RetrievalViewHolder){
            final RetrievalViewHolder itemViewHolder = (RetrievalViewHolder) holder;

            if(position != retrievals.length){
                itemViewHolder.mTextViewItemDescription.setText(retrievals[position].getItemDescription());
                itemViewHolder.mTextViewItemCode.setText("Item Code: " + retrievals[position].getItemCode());
                itemViewHolder.mTextViewQuantity.setText("Quantity: " + retrievals[position].getAvailableQuantity());
            }

            itemViewHolder.retrieval_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogItemDescription.setText(retrievals[position].getItemDescription());
                    myDialog.show();
                    dialogBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myDialog.dismiss();
                            Toast.makeText(mContext, dialogQuantity.getText() + " | " + dialogRemarks.getText(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        else if (holder instanceof RetrievalButtonViewHolder){
            final RetrievalButtonViewHolder buttonViewHolder = (RetrievalButtonViewHolder) holder;

            buttonViewHolder.retrieval_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "Button Clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == retrievals.length){
            return TYPE_BUTTON;
        } else {
            return TYPE_ITEM;
        }
    }

    // Last item will be retrieval confirmation button
    @Override
    public int getItemCount() {
        if(retrievals != null){
            return retrievals.length + 1;
        } else {
            return 0;
        }
    }

    //Inner class to hold reference to Recycler View
    public class RetrievalViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout retrieval_item;
        private TextView mTextViewItemDescription;
        private TextView mTextViewItemCode;
        private TextView mTextViewQuantity;

        public RetrievalViewHolder(View itemView) {
            super(itemView);
            // Get entire cardview_retrieval_fragment layout for onclick
            retrieval_item = itemView.findViewById(R.id.retrieval_item);

            mTextViewItemDescription = itemView.findViewById(R.id.retrieval_item_description);
            mTextViewItemCode = itemView.findViewById(R.id.retrieval_item_code);
            mTextViewQuantity = itemView.findViewById(R.id.retrieval_quantity);
        }
    }

    //Inner class to hold reference to confirmation button of Recycler View
    public class RetrievalButtonViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout retrieval_button;


        public RetrievalButtonViewHolder(@NonNull View itemView) {
            super(itemView);
            retrieval_button = itemView.findViewById(R.id.retrieval_button);
        }
    }

}
