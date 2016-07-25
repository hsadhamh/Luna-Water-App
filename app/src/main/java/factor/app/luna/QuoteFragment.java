package factor.app.luna;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by hassanhussain on 7/8/2016.
 */
public class QuoteFragment extends Fragment {

    @BindView(R.id.id_quote_text)
    TextView mTxt;

    String Quote = "";
    private Unbinder unbinder;

    public static QuoteFragment newInstance(String s){
        QuoteFragment f = new QuoteFragment();
        f.setQuote(s);
        return f;
    }

    public String getQuote() {
        return Quote;
    }

    public void setQuote(String quote) {
        Quote = quote;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy(){ super.onDestroy(); }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_quote, container, false);
        unbinder = ButterKnife.bind(this, view);
        mTxt.setText(Quote);
        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
