
package vote_app_test.com.test_library_import;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.eazegraph.lib.charts.*;
import org.eazegraph.lib.models.*;
import vote_app_test.com.test_library_import.vote;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    Vector<Pair<String,Integer>> codes;

    private DatabaseReference mDatabase;
    Button again_button;
    boolean FirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codes = new Vector<>();

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading ...");
        pd.show();

        final PieChart mPieChart = (PieChart) findViewById(R.id.piechart);
        AddCode("Work","#FE6DA8");
        AddCode("Eating","#56B7F1");
        AddCode("Sleep","#CDA67F");
        AddCode("Freetime","#FED70E");
        final Intent myIntent = getIntent();
        mPieChart.setCurrentItem(myIntent.getIntExtra("selected",0));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = mDatabase.getRef();

        again_button = (Button)findViewById(R.id.button_again);

        FirstTime = true;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                /*long o1 = (long)dataSnapshot.child("encuesta").child("o1").getValue();
                long o2 = (long)*/
                pd.dismiss();
                mPieChart.clearChart();
                for (int i = 0;i < codes.size();i++){
                    final Integer integer_i = i+1;
                    long value = (long)dataSnapshot.child("encuesta").child("o"+String.valueOf(integer_i)).getValue();
                    mPieChart.addPieSlice(new PieModel(codes.get(i).first,value,codes.get(i).second));
                }
                if (FirstTime){
                    FirstTime = false;
                    mPieChart.setCurrentItem(myIntent.getIntExtra("selected",0));
                }
                mPieChart.startAnimation();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        again_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,vote.class));
            }
        });
        /*mPieChart.addPieSlice(new PieModel("Freetime", 15, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Sleep", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Work", 35, Color.parseColor("#CDA67F")));
        mPieChart.addPieSlice(new PieModel("Eating", 9, Color.parseColor("#FED70E")));

        mPieChart.startAnimation();*/
    }
    private void AddCode(String name,String color){
        Pair<String,Integer> p = new Pair<>(name,Color.parseColor(color));
        codes.add(p);
    }
}
