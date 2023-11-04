package com.example.utsbuku.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.utsbuku.LoginActivity;
import com.example.utsbuku.R;
import com.example.utsbuku.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private TextView TvEmail,TvUsername;

    private Button BtnExit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TvEmail = (TextView) root.findViewById(R.id.TvEmail);
        TvUsername = (TextView) root.findViewById(R.id.TvUsername);
        BtnExit = (Button) root.findViewById(R.id.BtnExit);

        String username = getActivity().getIntent().getStringExtra("username");
        String email = getActivity().getIntent().getStringExtra("email");

        TvUsername.setText(username);
        TvEmail.setText(email);

        final TextView textView = binding.tvwellcome;
        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        BtnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}