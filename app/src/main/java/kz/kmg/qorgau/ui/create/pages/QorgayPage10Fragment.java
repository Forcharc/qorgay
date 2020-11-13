package kz.kmg.qorgau.ui.create.pages;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

import butterknife.BindView;
import kz.kmg.qorgau.R;

import static android.content.Intent.EXTRA_ALLOW_MULTIPLE;

public class QorgayPage10Fragment extends BaseQorgayPageFragment {

    private static final String TAG = "QorgayPage10Fragment";

    @BindView(R.id.b_choose_files)
    Button chooseFilesButton;

    @BindView(R.id.rv_files)
    RecyclerView filesRecycler;

    private static final int REQUEST_SELECT_FILES = 2;

    public QorgayPage10Fragment() {
        super(10, R.string.photos_documents);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chooseFilesButton.setOnClickListener(v -> {
            openFile();
        });
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
                //viewModel.setPage10Files();
                // Perform operations on the document using its URI.
                Log.d(TAG, "onActivityResult: " + uri.getPath());
            }
        }
    }

}
