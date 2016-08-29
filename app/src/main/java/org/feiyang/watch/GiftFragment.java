package org.feiyang.watch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GiftFragment extends Fragment {

    private static final String ARG_SCORE = "ArgScore";
    private static final String ARG_NAME = "ArgName";

    private GiftInfo mGiftInfo = new GiftInfo();

    public GiftFragment() {
        // Required empty public constructor
    }

    public static GiftFragment newInstance(GiftInfo giftInfo) {
        GiftFragment fragment = new GiftFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SCORE, giftInfo.mScore);
        args.putString(ARG_NAME, giftInfo.mName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGiftInfo.mScore = getArguments().getString(ARG_SCORE);
            mGiftInfo.mName = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gift, container, false);
        TextView tvScore = (TextView) view.findViewById(R.id.gift_score_text);
        TextView tvName = (TextView) view.findViewById(R.id.gift_name_text);
        tvScore.setText(mGiftInfo.mScore);
        tvName.setText(mGiftInfo.mName);
        return view;
    }

}
