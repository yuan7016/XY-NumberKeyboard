package com.xydemo.numberkeyboard;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.xydemo.numberkeyboard.view.XYNumberKeyboardView;

import java.lang.reflect.Method;

import kotlin.Function;

/**
 * Created by YuanZhiqiang on 2018/12/17 17:02.
 *
 */
public class NumberKeyboardDialogFragment extends DialogFragment {
    public static final String TAG = "NumberKeyboardDialogFragment";
    private EditText mEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = inflater.inflate(R.layout.dialog_fragment_offer, container,false);

        mEditText = view.findViewById(R.id.etPrice);
        XYNumberKeyboardView keyboardView = view.findViewById(R.id.numberKeyboardView);
        Button btnConfirm = view.findViewById(R.id.btnConfirm);

        keyboardView.bindEditText(mEditText);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEditText.getText().toString())){
                    Toast.makeText(getActivity(), "请输入报价", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if (mInputCompleteListener != null){
                        mInputCompleteListener.onInputComplete(mEditText.getText().toString());
                    }

                    dismiss();
                }
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
      //  lp.windowAnimations = R.style.DialogFragmentAnimation;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置dialog的位置在底部
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
        //去掉dialog默认的padding
//        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    public interface InputCompleteListener{
        void onInputComplete(String content);
    }

    private InputCompleteListener mInputCompleteListener;

    public void setInputCompleteListener(InputCompleteListener listener){
        this.mInputCompleteListener = listener;
    }


    /**
     * 隐藏系统键盘
     *
     * @param editText
     */
    public static void hideSystemKeyboard(Context context, EditText editText) {

        try {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
            setShowSoftInputOnFocus.setAccessible(true);
            setShowSoftInputOnFocus.invoke(editText, false);

        } catch (SecurityException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 如果软键盘已经显示，则隐藏
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }
}
