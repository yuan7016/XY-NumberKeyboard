package com.xydemo.numberkeyboard.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import com.xydemo.numberkeyboard.R
import kotlinx.android.synthetic.main.number_keyboard_layout.view.*

import java.lang.reflect.Method

/**
 * Created by YuanZhiQiang on 2018/12/18
 * 自定义数字键盘
 */
class XYNumberKeyboardView : LinearLayout, View.OnClickListener {
    private var editText: EditText? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet? = null) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?,defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    init {
        View.inflate(context, R.layout.number_keyboard_layout, this)

        tvKey0.setOnClickListener(this)
        tvKey1.setOnClickListener(this)
        tvKey2.setOnClickListener(this)
        tvKey3.setOnClickListener(this)
        tvKey4.setOnClickListener(this)
        tvKey5.setOnClickListener(this)
        tvKey6.setOnClickListener(this)
        tvKey7.setOnClickListener(this)
        tvKey8.setOnClickListener(this)
        tvKey9.setOnClickListener(this)
        tvKeyDot.setOnClickListener(this)
        ivKeyDelete.setOnClickListener(this)
    }

    fun bindEditText(editText: EditText){
        this.editText = editText

        editText.setOnTouchListener { v, event ->
            hideSystemKeyboard(editText)
            false
        }
    }


    override fun onClick(v: View) {
        when (v) {
            tvKey0 -> {
                insert("0")
            }
            tvKey1 -> {
                insert("1")
            }
            tvKey2 -> {
                insert("2")
            }
            tvKey3 -> {
                insert("3")
            }
            tvKey4 -> {
                insert("4")
            }
            tvKey5 -> {
                insert("5")
            }
            tvKey6 -> {
                insert("6")
            }
            tvKey7 -> {
                insert("7")
            }
            tvKey8 -> {
                insert("8")
            }
            tvKey9 -> {
                insert("9")
            }
            tvKeyDot -> {
                insert(".")
            }

            ivKeyDelete -> {
                delete()
            }
        }
    }

    private fun insert(text: String) {
        editText?.let {
            it.editableText.insert(it.selectionStart, text)
        }
    }

    private fun delete() {
        editText?.let {
            val start = it.selectionStart
            if (start > 0) {
                it.editableText.delete(start - 1, start)
            }
        }
    }

    /**
     * 隐藏系统键盘
     *
     * @param editText
     */
    private fun hideSystemKeyboard(editText: EditText) {
        try {
            val cls = EditText::class.java
            val setShowSoftInputOnFocus: Method
            setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", Boolean::class.javaPrimitiveType)
            setShowSoftInputOnFocus.isAccessible = true
            setShowSoftInputOnFocus.invoke(editText, false)

        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // 如果软键盘已经显示，则隐藏
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.hideSoftInputFromWindow(editText.windowToken, 0)
    }

}