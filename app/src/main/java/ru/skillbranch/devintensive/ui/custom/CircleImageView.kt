package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.*
import androidx.annotation.Dimension.DP
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRectF
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.convertDpToPx
import ru.skillbranch.devintensive.extensions.convertPxToDp
import ru.skillbranch.devintensive.extensions.dpToPx
import kotlin.math.max

/**
 * Created on 01.03.2020.
 *
 * @author Alexander Tavtorkin (av.tavtorkin@gmail.com)
 */
class CircleImageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_BORDER_WIDTH = 2f
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_SIZE = 40
    }

    @Px
    var borderWidth: Float = convertDpToPx(context, DEFAULT_BORDER_WIDTH)

    @ColorInt
    private var borderColor: Int = Color.WHITE

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val avatarPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val viewRect = Rect()
    private val borderRect = Rect()
    private var size = 0

    init {
        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderWidth =
                    ta.getDimension(
                            R.styleable.CircleImageView_cv_borderWidth,
                            convertDpToPx(context, DEFAULT_BORDER_WIDTH))
            borderColor =
                    ta.getColor(
                            R.styleable.CircleImageView_cv_borderColor,
                            DEFAULT_BORDER_COLOR
                    )
            ta.recycle()
        }
        scaleType = ScaleType.CENTER_INSIDE
        setup()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val initSize = resolveDefaultSize(widthMeasureSpec)
        val maxSize = max(initSize, size)
        setMeasuredDimension(maxSize, maxSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w == 0) return
        with(viewRect) {
            left = 0
            top = 0
            right = w
            bottom = h
        }
        prepareShader(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        if (drawable != null) drawAvatar(canvas)

        val half = (borderWidth / 2f).toInt()
        with(borderRect) {
            set(viewRect)
            inset(half, half)
        }
        canvas.drawOval(borderRect.toRectF(), borderPaint)
    }

    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        prepareShader(width, height)
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        prepareShader(width, height)
    }

    override fun setImageResource(@DrawableRes resId: Int) {
        super.setImageResource(resId)
        prepareShader(width, height)
    }

    @Dimension(unit = DP)
    fun getBorderWidth(): Int {
        return convertPxToDp(context, borderWidth).toInt()
    }

    fun setBorderWidth(@Dimension width: Int) {
        borderWidth = convertDpToPx(context, width.toFloat())
        borderPaint.strokeWidth = borderWidth
        invalidate()
    }

    @ColorInt
    fun getBorderColor(): Int {
        return borderColor
    }

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
        this.invalidate()
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = ContextCompat.getColor(context, colorId)
        this.invalidate()
    }

    private fun setup() {
        with(borderPaint) {
            style = Paint.Style.STROKE
            strokeWidth = borderWidth
            color = borderColor
        }
    }

    private fun prepareShader(w: Int, h: Int) {
        if (w == 0 || drawable == null) return
        val srcBm = drawable.toBitmap(w, h, Bitmap.Config.ARGB_8888)
        avatarPaint.shader = BitmapShader(srcBm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val localM = Matrix()
        localM.setScale((viewRect.width() / srcBm.width).toFloat(), (viewRect.width() / srcBm.width).toFloat())
        localM.setTranslate(0.5.toFloat(), 1f)
        avatarPaint.shader.setLocalMatrix(localM)
        invalidate()
    }

    private fun resolveDefaultSize(spec: Int): Int {
        return when (MeasureSpec.getMode(spec)) {
            MeasureSpec.UNSPECIFIED -> context.dpToPx(DEFAULT_SIZE).toInt() //resolveDefaultSize()
            MeasureSpec.AT_MOST -> MeasureSpec.getSize(spec) // from spec
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec) // from spec
            else -> MeasureSpec.getSize(spec)
        }
    }

    private fun drawAvatar(canvas: Canvas) {
        canvas.drawOval(viewRect.toRectF(), avatarPaint)
    }
}