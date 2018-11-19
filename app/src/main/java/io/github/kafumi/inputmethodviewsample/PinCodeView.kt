package io.github.kafumi.inputmethodviewsample

import android.content.Context
import android.graphics.Rect
import android.text.InputType
import android.text.SpannableStringBuilder
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.BaseInputConnection
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager

class PinCodeView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {
    private val editable = SpannableStringBuilder()
    private val keyListener = DigitsKeyListener.getInstance("0123456789")

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)

        if (gainFocus) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    override fun onCheckIsTextEditor(): Boolean {
        return true
    }

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection {
        outAttrs.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
        outAttrs.imeOptions = EditorInfo.IME_ACTION_DONE

        return BaseInputConnection(this, false)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val handled = keyListener.onKeyDown(this, editable, keyCode, event)
        Log.d(TAG, "onKeyDown: handled=$handled, text=$editable")
        return handled
    }

    companion object {
        private const val TAG = "PinCodeView"
    }
}