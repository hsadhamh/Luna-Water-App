package factor.app.luna;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.SimpleDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.id_quotes_list)
    ViewPager mPager;

    List<Fragment> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Intent inIntent = getIntent();
        if(inIntent != null){
            String s = inIntent.getStringExtra("CONTENT");
            String s1 = inIntent.getStringExtra("BIG_CONTENT");

            if((s!=null && !s.isEmpty()) || (s1!=null && !s1.isEmpty())){

            SimpleDialog.Builder builder = new SimpleDialog.Builder(R.style.SimpleDialogLight){
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        fragment.dismiss();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        fragment.dismiss();
                        super.onNegativeActionClicked(fragment);
                    }
                };

                (builder).message(s + "\n" + s1)
                        .title("Your Message")
                        .positiveAction("OK");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getSupportFragmentManager(), "HELLO");
            }
        }

        Intent i = new Intent();
        i.putExtra("REQUEST", LunaConstants.REQUEST_SCHEDULE_ALARMS);
        i.setClass(MainActivity.this, LunaService.class);
        startService(i);

        String[] mQuotes = getResources().getStringArray(R.array.quotes);
        for (String s : mQuotes) {
            mList.add(QuoteFragment.newInstance(s));
        }

        FragmentStateAdapter adap = new FragmentStateAdapter(getSupportFragmentManager(), mList);
        mPager.setAdapter(adap);
    }
}
