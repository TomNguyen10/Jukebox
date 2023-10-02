package com.csc396.jukebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.csc396.jukebox.databinding.ActivityPlayerBinding;

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;
    private int red;
    private int green;
    private int blue;
    private MediaPlayer mediaPlayer;

    private SeekBar.OnSeekBarChangeListener seekbars_on_click_listener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()) {
                case R.id.seekbar_red:
                    binding.textviewRed.setText(String.valueOf(progress));
                    red = progress;
                    break;
                case R.id.seekbar_green:
                    binding.textviewGreen.setText(String.valueOf(progress));
                    green = progress;
                    break;
                case R.id.seekbar_blue:
                    binding.textviewBlue.setText(String.valueOf(progress));
                    blue = progress;
                    break;
            }
            ConstraintLayout root = binding.getRoot();
            root.setBackgroundColor(Color.rgb(red, green, blue));

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private RadioGroup.OnCheckedChangeListener radiogroup_songs_on_checked_listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            mediaPlayer.stop();
            streamMusic(checkedId);
        }
    };

    private SeekBar.OnSeekBarChangeListener seekbar_song_position_on_seekbar_change_listener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mediaPlayer.start();
            mediaPlayer.seekTo(mediaPlayer.getDuration() * progress / 100);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    public void streamMusic (int songID) {
        switch (songID) {
            case R.id.radio_song1:
                binding.imageviewAlbumCover.setImageDrawable(getResources().getDrawable(R.drawable.track1,getTheme()));
                mediaPlayer = MediaPlayer.create(PlayerActivity.this, R.raw.track1);
                break;
            case R.id.radio_song2:
                binding.imageviewAlbumCover.setImageDrawable(getResources().getDrawable(R.drawable.track2,getTheme()));
                mediaPlayer = MediaPlayer.create(PlayerActivity.this, R.raw.track2);
                break;
            case R.id.radio_song3:
                binding.imageviewAlbumCover.setImageDrawable(getResources().getDrawable(R.drawable.track3,getTheme()));
                mediaPlayer = MediaPlayer.create(PlayerActivity.this, R.raw.track3);
                break;
        }
        mediaPlayer.start();
    }

    private View.OnClickListener button_cast_vote_on_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            float rating = binding.ratingbarVoterRating.getRating();
            float progress;
            int numVotes;
            int songID = binding.radiogroupSongs.getCheckedRadioButtonId();
            switch (songID) {
                case R.id.radio_song1:
                    progress = binding.progressbarAverageRating1.getProgress();
                    numVotes = Integer.valueOf((String) binding.textviewNumVotes1.getText());
                    binding.progressbarAverageRating1.setProgress((Math.round(progress * numVotes + rating) / (numVotes + 1)));
                    binding.textviewNumVotes1.setText(String.valueOf(numVotes + 1));
                    break;
                case R.id.radio_song2:
                    progress = binding.progressbarAverageRating2.getProgress();
                    numVotes = Integer.valueOf((String) binding.textviewNumVotes2.getText());
                    binding.progressbarAverageRating2.setProgress((Math.round(progress * numVotes + rating) / (numVotes + 1)));
                    binding.textviewNumVotes2.setText(String.valueOf(numVotes + 1));
                    break;
                case R.id.radio_song3:
                    progress = binding.progressbarAverageRating3.getProgress();
                    numVotes = Integer.valueOf((String) binding.textviewNumVotes3.getText());
                    binding.progressbarAverageRating3.setProgress((Math.round(progress * numVotes + rating) / (numVotes + 1)));
                    binding.textviewNumVotes3.setText(String.valueOf(numVotes + 1));
                    break;
            }
            binding.ratingbarVoterRating.setProgress(0);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.seekbarBlue.setOnSeekBarChangeListener(seekbars_on_click_listener);
        binding.seekbarGreen.setOnSeekBarChangeListener(seekbars_on_click_listener);
        binding.seekbarRed.setOnSeekBarChangeListener(seekbars_on_click_listener);

        binding.radiogroupSongs.setOnCheckedChangeListener(radiogroup_songs_on_checked_listener);
        binding.seekbarSongPosition.setOnSeekBarChangeListener(seekbar_song_position_on_seekbar_change_listener);
        binding.buttonCastVote.setOnClickListener(button_cast_vote_on_click_listener);
        streamMusic(binding.radiogroupSongs.getCheckedRadioButtonId());
    }
}