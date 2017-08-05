package com.project.raizasocial.Activities;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.activeandroid.ActiveAndroid;
import com.project.raizasocial.R;
import com.project.raizasocial.Services.SaveSharedPreference;

public class MainActivity extends Activity implements OnClickListener {
	Button btnSignIn;
	Button btnSignUp;

    /**
     * {@inheritDoc}
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActiveAndroid.initialize(this);
        setContentView(R.layout.activity_main);
        if (!(SaveSharedPreference.getUserEmail(MainActivity.this).equals("") &&
            !SaveSharedPreference.getLoggedInValue(MainActivity.this))) {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            MainActivity.this.finish();
        }
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    /**
     * {@inheritDoc}
     * @param v
     */
	@Override
	public void onClick(View v) {
		Intent i;
		int id = v.getId();
		if (id == R.id.btnSignIn) {
			i = new Intent(this,SignInActivity.class);
            startActivity(i);
		} else if (id == R.id.btnSignUp) {
            i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        }
	}
    
}
