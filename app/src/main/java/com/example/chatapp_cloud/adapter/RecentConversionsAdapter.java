package com.example.chatapp_cloud.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp_cloud.databinding.ItemContainerRecentConversionBinding;
import com.example.chatapp_cloud.listeners.ConversionListener;
import com.example.chatapp_cloud.models.ChatMessage;
import com.example.chatapp_cloud.models.Student;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class RecentConversionsAdapter extends RecyclerView.Adapter<RecentConversionsAdapter.ConversionViewHolder> {
    private final List<ChatMessage>chatMessages;
    private final ConversionListener conversionListener;

    public RecentConversionsAdapter(List<ChatMessage> chatMessages, ConversionListener conversionListener) {
        this.chatMessages = chatMessages;
        this.conversionListener = conversionListener;
    }

    @NonNull
    @Override
    public ConversionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConversionViewHolder(
                ItemContainerRecentConversionBinding.inflate(
                        LayoutInflater.from( parent.getContext() ),
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ConversionViewHolder holder, int position) {
        holder.setDate( chatMessages.get( position ) );

    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    class ConversionViewHolder extends RecyclerView.ViewHolder{
        ItemContainerRecentConversionBinding binding;

        ConversionViewHolder(ItemContainerRecentConversionBinding itemContainerRecentConversionBinding){
            super(itemContainerRecentConversionBinding.getRoot());
            binding = itemContainerRecentConversionBinding;
        }
        void setDate(ChatMessage chatMessage){
            binding.imageprofile.setImageBitmap( getconversionImage( chatMessage.conversionImage ) );
            binding.textName.setText(chatMessage.conversionName  );
            binding.textRecentMessage.setText(chatMessage.message  );
            binding.getRoot().setOnClickListener( v -> {
                Student student = new Student();
                student.id = chatMessage.conversionId;
                student.Firstname = chatMessage.conversionName;
                student.image = chatMessage.conversionImage;
                conversionListener.onConversionClicked(student );
            } );

        }
    }
    private Bitmap getconversionImage(String encodedImage){
        byte[] bytes = Base64.decode( encodedImage,Base64.DEFAULT );
        return BitmapFactory.decodeByteArray( bytes,0,bytes.length );
    }

}
