package com.example.excelParser;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.excelParser.MyViewFlipper.OnViewFlipperListener;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.teacher.helper.R;

public class CallNameViewFlipperActivity extends Activity implements
		OnViewFlipperListener {

	private MyViewFlipper myViewFlipper;
	private int currentNumber;
	private int sum;
	// 存储Intent传过来的学生数据
	private String[] StuNames;
	private String selectedTableName;

	// boolean变量判断当前是否点名结束
	// private boolean isFinished;

	// 存储点名时没到的人名。格式：",张三,李四,..."
	private StringBuilder presentStuName;
	// 存储点名时没到的人名。格式：",张三,李四,..."
	private StringBuilder absentStuName;
	// 存储点名时请假的人名。格式：",张三,李四,..."
	private StringBuilder leaveStuName;

	SpeechSynthesizer mTts;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// 用InitListener创建本地合成的SpeechSynthesizer
		mTts = SpeechSynthesizer.createSynthesizer(this, null);
		// 合成参数设置
		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
		mTts.setParameter(SpeechConstant.SPEED, "60");
		mTts.setParameter(SpeechConstant.VOLUME, "80");

		// 获取intent里面的bundle对象
		Intent i = getIntent();
		Bundle bundle = i.getExtras();
		StuNames = bundle.getStringArray("selectTableStuNames");
		selectedTableName = bundle.getString("selectedTableName");

		sum = StuNames.length;

		absentStuName = new StringBuilder();
		leaveStuName = new StringBuilder();
		presentStuName = new StringBuilder();

		currentNumber = 0;

		myViewFlipper = (MyViewFlipper) findViewById(R.id.myViewFlipper);
		myViewFlipper.setOnViewFlipperListener(this);
		myViewFlipper.addView(creatView(currentNumber));

		mTts.startSpeaking(StuNames[currentNumber], mSynListener);
	}

	public View getNextView() {
		currentNumber = currentNumber == sum ? sum : currentNumber + 1;
		return creatView(currentNumber);
	}

	public View getPreviousView() {
		currentNumber = currentNumber == 0 ? 0 : currentNumber - 1;
		return creatView(currentNumber);
	}

	private View creatView(final int currentNumber) {

		int currentTemp = currentNumber;

		// 用layoutInflater加载flipper_view的布局
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		ScrollView resultView = (ScrollView) layoutInflater.inflate(
				R.layout.flipper_view, null);
		// 如果点名结束，取消手势监听。加载点名结束页面并显示出点名的信息。
		if (currentNumber == sum) {

			Intent i = new Intent(CallNameViewFlipperActivity.this,
					CallNameResultActivity.class);
			i.putExtra("presentStuNames", getResultNames(presentStuName));
			i.putExtra("absentStuNames", getResultNames(absentStuName));
			i.putExtra("leaveStuName", getResultNames(leaveStuName));
			i.putExtra("selectedTableName", selectedTableName);
			startActivity(i);
		} else {
			// 用stuNames改变TextView文字
			
			((Button) resultView.findViewById(R.id.bt_finishCallName))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							// 跳转到点名结果界面
							// presentStuName = getResultNames(presentStuName);
							// absentStuName = getResultNames(absentStuName);
							// leaveStuName = getResultNames(leaveStuName);
							Intent i = new Intent(
									CallNameViewFlipperActivity.this,
									CallNameResultActivity.class);
							i.putExtra("presentStuNames",
									getResultNames(presentStuName));
							i.putExtra("absentStuNames",
									getResultNames(absentStuName));
							i.putExtra("leaveStuName",
									getResultNames(leaveStuName));
							i.putExtra("selectedTableName", selectedTableName);
							startActivity(i);
						}
					});
			((TextView) resultView.findViewById(R.id.tv_stu_name))
					.setText(StuNames[currentNumber]);
			// RadioGroup保存每个人的签到信息
			((RadioGroup) resultView.findViewById(R.id.radio_group))
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							// TODO Auto-generated method stub
							if (currentNumber < sum-1) {
								mTts.startSpeaking(StuNames[currentNumber + 1],
										mSynListener);
							}
							switch (checkedId) {
							// 已到
							case R.id.rb_present:
								presentStuName.append(","
										+ StuNames[currentNumber]);
								System.out.println(StuNames[currentNumber]
										+ ":已到");
								removeCalledStuName(StuNames[currentNumber],
										absentStuName, leaveStuName);
								// isFinished = isCallNameFinished();
								break;
							// 没到
							case R.id.rb_absent:
								absentStuName.append(","
										+ StuNames[currentNumber]);
								System.out.println(StuNames[currentNumber]
										+ ":没到");
								removeCalledStuName(StuNames[currentNumber],
										presentStuName, leaveStuName);
								// isFinished = isCallNameFinished();
								break;
							// 请假
							case R.id.rb_leave:
								leaveStuName.append(","
										+ StuNames[currentNumber]);
								System.out.println(StuNames[currentNumber]
										+ ":请假");
								removeCalledStuName(StuNames[currentNumber],
										presentStuName, absentStuName);
								// isFinished = isCallNameFinished();
								break;
							default:
								break;
							}
							myViewFlipper.flingToNext();
						}
					});
		}
		return resultView;

	}

	// 重新修改学生签到状态时调用，修改另外两个StringBuilder里面的姓名。
	private void removeCalledStuName(String name, StringBuilder a,
			StringBuilder b) {
		// 若a里面已经存在了name，则把",<name>"替换为","
		if (a.toString().contains(name)) {
			a = a.replace(a.lastIndexOf(name) - 1,
					a.lastIndexOf(name) + name.length(), ",");
		}
		if (b.toString().contains(name)) {
			b = b.replace(b.lastIndexOf(name) - 1,
					b.lastIndexOf(name) + name.length(), ",");
		}
	}

	// 去掉StringBuilder里面相同的名字。
	private String getResultNames(StringBuilder sb) {

		String[] temp = sb.toString().split(",");
		StringBuilder newsb = new StringBuilder();

		for (int i = 0; i < temp.length; i++) {
			System.out.println(temp[i]);
		}
		for (int i = 0; i < temp.length; i++) {
			for (int j = i + 1; j < temp.length; j++) {
				// 若前面的姓名不是"654321", 并且与后面任一个姓名相同，便把后面的人名改成"654321"
				if ((!temp[i].equals("654321")) && temp[i].equals(temp[j])) {
					temp[j] = "654321";
				}
			}
		}
		for (int i = 0; i < temp.length; i++) {
			// 若姓名不为"654321",则append到StringBuilder中去
			if (!temp[i].equals("654321")) {
				newsb.append(temp[i] + ",");
			}
		}
		return (String) newsb.subSequence(0, newsb.length() - 1);

	}

	private SynthesizerListener mSynListener = new SynthesizerListener() {
		public void onCompleted(SpeechError error) {
		}

		public void onBufferProgress(int percent, int beginPos, int endPos,
				String info) {
		}

		public void onSpeakBegin() {
		}

		public void onSpeakPaused() {
		}

		public void onSpeakProgress(int percent, int beginPos, int endPos) {
		}

		public void onSpeakResumed() {
		}

		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
		}

	};
	protected void onDestroy() {
		super.onDestroy();
		finish();
		mTts.stopSpeaking();
		// 退出时释放连接
		mTts.destroy();
	};
}
