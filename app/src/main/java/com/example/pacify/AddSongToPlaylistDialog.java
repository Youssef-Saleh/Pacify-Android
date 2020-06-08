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
 * This class is for the dialog that opens when a premium user want add a song to a playlist
 * it opens an edit text to receive the playlist name that the user want to add the song to
 * when the user press add , the name is sent to Navigation Activity
 */
public class AddSongToPlaylistDialog extends AppCompatDialogFragment {

    private EditText playlistName;
    private AddSongToPlaylistDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_song_to_playlist_dialog,null);

        playlistName = view.findViewById(R.id.playlist_name);

        builder.setView(view)//Add Song to
                .setTitle("Add Song to")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.sendPlaylistNameToAddSong(playlistName.getText().toString().trim());
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
            listener = (AddSongToPlaylistDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement AddSongToPlaylistDialogListener");
        }
    }
    public interface AddSongToPlaylistDialogListener {
        void sendPlaylistNameToAddSong(String playlistName);
    }
}
