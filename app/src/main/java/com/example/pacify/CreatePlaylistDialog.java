package com.example.pacify;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * This class is for the dialog that opens when a premium user want to create a
 * playlist it opens an edit text to receive the new playlist name, and if the user
 * pressed create to confirm the name is sent to Navigation Activity
 */
public class CreatePlaylistDialog extends AppCompatDialogFragment {

    private EditText playlistName;
    private CreatePlaylistDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.create_playlist_dialog,null);

        playlistName = view.findViewById(R.id.playlist_name);

        builder.setView(view)
                .setTitle("Enter Playlist Name")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.sendPlaylistName(playlistName.getText().toString().trim());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
        });

        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (CreatePlaylistDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement CreatePlaylistDialogListener");
        }
    }
    public interface CreatePlaylistDialogListener {
        void sendPlaylistName(String playlistName);
    }
}
