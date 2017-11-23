package haythem.hd.customprogress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button addProgressBtn;
    private Button setProgressBtn;
    private Button mToPicActivity;

    private EditText poucentageEditText;

    private CircleProgressBar circleProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findByID();

        addProgressBtn.setOnClickListener(this);
        setProgressBtn.setOnClickListener(this);
        mToPicActivity.setOnClickListener(this);

    }

    private void findByID() {
        poucentageEditText = findViewById(R.id.pourcentage);
        circleProgressBar = findViewById(R.id.custom_progressBar);
        addProgressBtn = findViewById(R.id.add_btn);
        setProgressBtn = findViewById(R.id.set_btn);
        mToPicActivity = findViewById(R.id.to_pic);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                circleProgressBar.AddTenWithAnimation(this.getApplicationContext());
            break;
            case R.id.set_btn:
                Toast.makeText(getApplicationContext(), "Change progress", Toast.LENGTH_LONG).show();
                circleProgressBar.setProgress(Float.parseFloat(poucentageEditText.getText().toString()));
                break;
            case R.id.to_pic:
                Intent in = new Intent(this, DownloadPicActivity.class);
                startActivity(in);
                finish();
                break;
        }

    }
}
