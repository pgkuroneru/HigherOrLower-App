package net.pgkuroneru.highorlower;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

	AppCompatTextView mTxtScore;
	AppCompatTextView mTxtNum;
	Button mBtnHigher;
	Button mBtnLower;

	private int score = 0;
	private boolean gameover = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTxtScore = (AppCompatTextView) findViewById(R.id.txt_score);
		mTxtNum = (AppCompatTextView) findViewById(R.id.txt_num);
		mBtnHigher = (Button) findViewById(R.id.btn_higher);
		mBtnLower = (Button) findViewById(R.id.btn_lower);

		mTxtScore.setText(getString(R.string.score, 0));

		mBtnHigher.setOnClickListener(mHigher);
		mBtnLower.setOnClickListener(mLower);
	}

	public void start(String choice) {
		if (gameover == false) {
			if (check(choice) == true) {
				score++;
				mTxtScore.setText(getString(R.string.score, score));
			} else {
				mBtnHigher.setEnabled(false);
				mBtnLower.setEnabled(false);
				Toast.makeText(getApplicationContext(), R.string.game_over, Toast.LENGTH_SHORT).show();
				gameover = true;
			}
		}
	}

	public boolean check(String choice) {
		int currentValue = Integer.parseInt(mTxtNum.getText().toString());
		int num = getRandomValues();
		mTxtNum.setText(String.valueOf(num));
		int nextValue = Integer.parseInt(mTxtNum.getText().toString());
		
		if (choice == "lower") {
			if (nextValue < currentValue) { return true; } else { return false; }
		} else {
			if (currentValue < nextValue) { return true; } else { return false; }
		}
	}

	public int getRandomValues() {
		int num = (int) (Math.random() * 100 + 1);
		int currentValue = Integer.parseInt(mTxtNum.getText().toString());
		int nextValue = num;
		if (currentValue == nextValue) {
			getRandomValues();
		}
		return nextValue;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.reset:
				score = 0;
				mTxtScore.setText(getString(R.string.score, score));
				mTxtNum.setText(R.string.default_num);
				mBtnHigher.setEnabled(true);
				mBtnLower.setEnabled(true);
				gameover = false;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	View.OnClickListener mHigher = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			start("higher");
		}
	};

	View.OnClickListener mLower = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			start("lower");
		}
	};
}
