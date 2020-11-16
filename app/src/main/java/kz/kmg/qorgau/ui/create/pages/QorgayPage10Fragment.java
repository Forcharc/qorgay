package kz.kmg.qorgau.ui.create.pages;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.ui.base.adapter.BaseAdapter;
import kz.kmg.qorgau.ui.base.adapter.BaseViewHolder;
import kz.kmg.qorgau.utils.files.FileUtil;

import static android.content.Intent.EXTRA_ALLOW_MULTIPLE;

public class QorgayPage10Fragment extends BaseQorgayPageFragment {

    private static final String TAG = "QorgayPage10Fragment";

    @BindView(R.id.b_choose_files)
    Button chooseFilesButton;

    @BindView(R.id.rv_files)
    RecyclerView filesRecycler;

    private static final int REQUEST_SELECT_FILES = 2;

    private FilesAdapter filesAdapter = new FilesAdapter();

    public QorgayPage10Fragment() {
        super(10, R.string.photos_documents);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chooseFilesButton.setOnClickListener(v -> {
            openFile();
        });

        filesAdapter.submitList(viewModel.getPage10Files());

        filesRecycler.setAdapter(filesAdapter);
        filesRecycler.setHasFixedSize(false);

    }

    private void openFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        String[] mimeTypes = {"image/png", "image/jpeg", "image/gif", "application/pdf", "text/plain", "application/vnd.ms-excel", "application/msword"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
/*
        intent.putExtra(EXTRA_ALLOW_MULTIPLE, true);
*/

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.

        startActivityForResult(intent, REQUEST_SELECT_FILES);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page10;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_FILES && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
                // Perform operations on the document using its URI.
                try {
                    File file = FileUtil.from(requireContext(), uri);
                    addFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addFile(File file) {
        List<File> fileList = viewModel.getPage10Files();
        fileList.add(file);
        filesAdapter.submitList(fileList);
        filesAdapter.notifyDataSetChanged();
        Log.d(TAG, "addFile: fileListSize" + fileList.size());
        Log.d(TAG, "addFile: filesAdapterSize" + filesAdapter.getItemCount());
    }

    private void removeFile(File file) {
        List<File> fileList = viewModel.getPage10Files();
        fileList.remove(file);
        filesAdapter.submitList(fileList);
        filesAdapter.notifyDataSetChanged();
    }


    class FilesAdapter extends BaseAdapter<File> {

        @NonNull
        @Override
        public DiffUtil.ItemCallback<File> getDiffCallback() {
            return null;
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
            return new FileViewHolder(root);
        }

        class FileViewHolder extends BaseViewHolder<File> {
            @BindView(R.id.tv_file_name)
            TextView fileNameTextView;

            @BindView(R.id.ib_close)
            ImageButton closeButton;

            public FileViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @Override
            public void setData(File item) {
                fileNameTextView.setText(item.getName());
                closeButton.setOnClickListener(v -> {
                    removeFile(item);
                });
            }
        }
    }
}
