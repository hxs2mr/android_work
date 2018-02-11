package microtech.hxswork.com.android_work.Util;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.CallSuper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import microtech.hxswork.com.android_work.R;

/**
 */
public class CustomEditText extends EditText {
    /**
     * <enum name="NONE" value="0" />
     * <enum name="PHONE" value="1" />
     * <enum name="BANK_CARD" value="2" />
     */
    int inputType;
    MyTextWatcher textWatcher = new MyTextWatcher();
    private Drawable dRight;
    private Rect rBounds;
    private MyTextChangeListener myTextChangeListener;

    public CustomEditText(Context context) {
        super(context);
        init(context, null);
    }


    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    public void setInputType(InputType inputType) {
        this.inputType = inputType.getValue();
        initTextWatcher();
        setText(getTextWithoutSpace());
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomEditText, 0, 0);
        try {
            inputType = a.getInt(R.styleable.CustomEditText_inputType, 0);
        } finally {
            a.recycle();
        }
        initTextWatcher();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (!focused) {
        } else {
        }
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }


    private void initTextWatcher() {
        this.addTextChangedListener(textWatcher);
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        super.addTextChangedListener(watcher);
    }

    /**
     * 获取每四位分隔的银行卡号码
     *
     * @param textWithSpace
     */
    private String getFormatedText(String textWithSpace) {
        textWithSpace = textWithSpace.replace(" ", "");
        if (inputType == InputType.PHONE.getValue()) {
            //手机号，机智的在最前面加一个空格
            textWithSpace = " " + textWithSpace;
        }
        String arrs[] = textWithSpace.split("");
        int len = arrs.length;
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < len; i++) {
            sb.append(arrs[i]);
            if (i % 4 == 0) {
                sb.append(" ");
            }
        }
        if (len % 4 == 1) {
            sb.append(" ");
        }
        return sb.toString().trim();//机智的trim掉可能是手机号的空格
    }

    public String getTextWithoutSpace() {
        return getText().toString().replaceAll(" ", "");
    }


    public enum InputType {
        NONE(0), PHONE(1), BANK_CARD(2);
        int value;

        InputType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 添加触摸事件 点击之后 出现 清空editText的效果
     */

    /**
     * 检查是否需要隐藏右侧的清除图标
     */


    Drawable start, top, end, bottom;

    class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            removeTextChangedListener(textWatcher);
            if (inputType != InputType.NONE.value) {
                String text = editable.toString().replace(" ", "");
                String afterText = getFormatedText(text);
                if (!afterText.endsWith(" ")) {
                    setText(afterText);
                }
                setSelection(afterText.length());
            }
            addTextChangedListener(textWatcher);

            if (myTextChangeListener != null){
                myTextChangeListener.afterTextChanged(editable);
            }

        }
    }


    public void addMyTextChangeListener(MyTextChangeListener myTextChangeListener){
        this.myTextChangeListener = myTextChangeListener;
    }

    public void reMoveMyTextChangeListener(){
        this.myTextChangeListener = null;
    }

    public interface MyTextChangeListener{
        void afterTextChanged(Editable editable);
    }
}
