package com.example.board111;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mMainRecyclerView;
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private MainAdapter mAdapter;
    private List<Board> mBoardList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainRecyclerView = findViewById(R.id.main_recyclerview);

        findViewById(R.id.main_write_button).setOnClickListener(this);

        mBoardList = new ArrayList<>();

        mStore.collection("board").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshot, @Nullable FirebaseFirestoreException error) {
                for(DocumentChange dc : queryDocumentSnapshot.getDocumentChanges()) {
                    String id = (String) dc.getDocument().getData().get("id");
                    String title = (String) dc.getDocument().getData().get("title");
                    String contents = (String) dc.getDocument().getData().get("contents");
                    String name = (String) dc.getDocument().getData().get("name");
                    Board data = new Board(id, title, contents, name);

                    mBoardList.add(data);
                }
                mAdapter = new MainAdapter(mBoardList);
                mMainRecyclerView.setAdapter(mAdapter);
            }
        });
    }


    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, WriteActivity.class));
    }

    private class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{

        private List<Board> mBoardlist;

        public MainAdapter(List<Board> mBoardlist) {
            this.mBoardlist = mBoardlist;
        }

        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
            Board data = mBoardlist.get(position);
            holder.mTitleTextView.setText(data.getTitle());
            holder.mNameTextView.setText(data.getName());
        }

        @Override
        public int getItemCount() {
            return mBoardlist.size();
        }

        class MainViewHolder extends RecyclerView.ViewHolder{
            private TextView mTitleTextView;
            private TextView mNameTextView;
            public MainViewHolder(@NonNull View itemView) {
                super(itemView);
                mTitleTextView = itemView.findViewById(R.id.item_post_title);
                mNameTextView = itemView.findViewById(R.id.item_post_contents);
            }
        }
    }
}