package com.team11.ssisandroid.util;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.team11.ssisandroid.R;
import com.team11.ssisandroid.fragments.CollectionFragment;
import com.team11.ssisandroid.fragments.RetrievalFragment;
import com.team11.ssisandroid.interfaces.CollectionApi;
import com.team11.ssisandroid.interfaces.RetrievalApi;
import com.team11.ssisandroid.interfaces.StockAdjustmentApi;
import com.team11.ssisandroid.models.Retrieval;
import com.team11.ssisandroid.models.StockAdjustment;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrievalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TYPE_BUTTON = 0;
    private static final int TYPE_ITEM = 1;

    private Context mContext;
    private Retrieval[] retrievals;
    private String token;
    private String email;
    private Dialog myDialog;

    TextView dialogItemDescription;
    EditText dialogRemarks;
    EditText dialogQuantity;
    Button dialogConfirmBtn;
    Button dialogCancelBtn;

    public RetrievalAdapter(Context mContext, Retrieval[] retrievals, String email, String token){
        this.mContext = mContext;
        this.retrievals = retrievals;
        this.email = email;
        this.token = token;
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
        dialogConfirmBtn = myDialog.findViewById(R.id.dialog_confirm_button);
        dialogCancelBtn = myDialog.findViewById(R.id.dialog_cancel_button);

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
                    dialogQuantity.setText("");
                    dialogRemarks.setText("");
                    myDialog.show();

                    // Confirmation Dialog
                    dialogConfirmBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // If input is empty or null
                            if(TextUtils.isEmpty(dialogQuantity.getText().toString())){
                                Toast.makeText(mContext, "Please provide a number", Toast.LENGTH_SHORT).show();
                            } else {
                                myDialog.dismiss();

                                //Parse string into integer
                                final Integer movement = Integer.parseInt(dialogQuantity.getText().toString());
                                String remarks = dialogRemarks.getText().toString();
                                String itemId = retrievals[position].getItemID();

                                createStockAdjustment(itemId, remarks,
                                        movement, new CreateStockAdjustmentListener() {
                                            @Override
                                            public void onResponse() {
                                                Toast.makeText(mContext,"Stock Adjustment of " + movement + " successfully created", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    });

                    // Cancel Dialog
                    dialogCancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myDialog.dismiss();
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

                    // If no retrievals to confirm
                    if(retrievals.length < 1){
                        Toast.makeText(mContext, "You have no retrievals to confirm", Toast.LENGTH_SHORT).show();
                    } else {

                        // Send request to server to confirm retrievals
                        confirmRetrieval(new ConfirmRetrievalListener() {
                            @Override
                            public void onResponse() {
                                Toast.makeText(mContext, "Retrievals Confirmed", Toast.LENGTH_SHORT).show();

                                // Create fragment that you want to go to
                                RetrievalFragment retrievalFragment = new RetrievalFragment();

                                // Go to new fragment on button click
                                AppCompatActivity activity  =(AppCompatActivity) mContext;
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, retrievalFragment).addToBackStack(null).commit();
                            }
                        });
                    }

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

    public interface CreateStockAdjustmentListener {
        void onResponse();
    }

    private void createStockAdjustment(String itemId, String remarks, Integer movement, final CreateStockAdjustmentListener listener){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://team11ssis.azurewebsites.net/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        StockAdjustmentApi stockAdjustmentApi = retrofit.create(StockAdjustmentApi.class);

        // Set up StockAdjustment
        StockAdjustment stockAdjustment = new StockAdjustment(null, itemId, email, remarks, movement);
        Call<ResponseBody> call = stockAdjustmentApi.createStockAdjustment(token, stockAdjustment);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    listener.onResponse();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public interface ConfirmRetrievalListener {
        void onResponse();
    }

    private void confirmRetrieval(final ConfirmRetrievalListener listener){
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://team11ssis.azurewebsites.net/").addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        RetrievalApi retrievalApi = retrofit.create(RetrievalApi.class);

        Call<ResponseBody> call = retrievalApi.confirmRetrievals(token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onResponse();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
