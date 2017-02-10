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
	// �洢Intent��������ѧ������
	private String[] StuNames;
	private String selectedTableName;

	// boolean�����жϵ�ǰ�Ƿ��������
	// private boolean isFinished;

	// �洢����ʱû������������ʽ��",����,����,..."
	private StringBuilder presentStuName;
	// �洢����ʱû������������ʽ��",����,����,..."
	private StringBuilder absentStuName;
	// �洢����ʱ��ٵ���������ʽ��",����,����,..."
	private StringBuilder leaveStuName;

	SpeechSynthesizer mTts;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// ��InitListener�������غϳɵ�SpeechSynthesizer
		mTts = SpeechSynthesizer.createSynthesizer(this, null);
		// �ϳɲ�������
		mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
		mTts.setParameter(SpeechConstant.SPEED, "60");
		mTts.setParameter(SpeechConstant.VOLUME, "80");

		// ��ȡintent�����bundle����
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

		// ��layoutInflater����flipper_view�Ĳ���
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		ScrollView resultView = (ScrollView) layoutInflater.inflate(
				R.layout.flipper_view, null);
		// �������������ȡ�����Ƽ��������ص�������ҳ�沢��ʾ����������Ϣ��
		if (currentNumber == sum) {

			Intent i = new Intent(CallNameViewFlipperActivity.this,
					CallNameResultActivity.class);
			i.putExtra("presentStuNames", getResultNames(presentStuName));
			i.putExtra("absentStuNames", getResultNames(absentStuName));
			i.putExtra("leaveStuName", getResultNames(leaveStuName));
			i.putExtra("selectedTableName", selectedTableName);
			startActivity(i);
		} else {
			// ��stuNames�ı�TextView����
			
			((Button) resultView.findViewById(R.id.bt_finishCallName))
					.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							// ��ת�������������
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
			// RadioGroup����ÿ���˵�ǩ����Ϣ
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
							// �ѵ�
							case R.id.rb_present:
								presentStuName.append(","
										+ StuNames[currentNumber]);
								System.out.println(StuNames[currentNumber]
										+ ":�ѵ�");
								removeCalledStuName(StuNames[currentNumber],
										absentStuName, leaveStuName);
								// isFinished = isCallNameFinished();
								break;
							// û��
							case R.id.rb_absent:
								absentStuName.append(","
										+ StuNames[currentNumber]);
								System.out.println(StuNames[currentNumber]
										+ ":û��");
								removeCalledStuName(StuNames[currentNumber],
										presentStuName, leaveStuName);
								// isFinished = isCallNameFinished();
								break;
							// ���
							case R.id.rb_leave:
								leaveStuName.append(","
										+ StuNames[currentNumber]);
								System.out.println(StuNames[currentNumber]
										+ ":���");
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

	// �����޸�ѧ��ǩ��״̬ʱ���ã��޸���������StringBuilder�����������
	private void removeCalledStuName(String name, StringBuilder a,
			StringBuilder b) {
		// ��a�����Ѿ�������name�����",<name>"�滻Ϊ","
		if (a.toString().contains(name)) {
			a = a.replace(a.lastIndexOf(name) - 1,
					a.lastIndexOf(name) + name.length(), ",");
		}
		if (b.toString().contains(name)) {
			b = b.replace(b.lastIndexOf(name) - 1,
					b.lastIndexOf(name) + name.length(), ",");
		}
	}

	// ȥ��StringBuilder������ͬ�����֡�
	private String getResultNames(StringBuilder sb) {

		String[] temp = sb.toString().split(",");
		StringBuilder newsb = new StringBuilder();

		for (int i = 0; i < temp.length; i++) {
			System.out.println(temp[i]);
		}
		for (int i = 0; i < temp.length; i++) {
			for (int j = i + 1; j < temp.length; j++) {
				// ��ǰ�����������"654321", �����������һ��������ͬ����Ѻ���������ĳ�"654321"
				if ((!temp[i].equals("654321")) && temp[i].equals(temp[j])) {
					temp[j] = "654321";
				}
			}
		}
		for (int i = 0; i < temp.length; i++) {
			// ��������Ϊ"654321",��append��StringBuilder��ȥ
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
		// �˳�ʱ�ͷ�����
		mTts.destroy();
	};
}
