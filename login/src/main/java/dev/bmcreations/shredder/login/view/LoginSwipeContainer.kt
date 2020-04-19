package dev.bmcreations.shredder.login.view

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Typeface.BOLD
import android.text.TextPaint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.contains
import androidx.core.view.GestureDetectorCompat
import dev.bmcreations.shredder.core.extensions.dp
import dev.bmcreations.shredder.core.extensions.sp
import dev.bmcreations.shredder.login.R
import dev.bmcreations.shredder.login.model.Login
import dev.bmcreations.shredder.login.model.Option
import dev.bmcreations.shredder.login.model.PointArray
import dev.bmcreations.shredder.login.model.SignUp
import kotlin.math.roundToInt


data class SwipeArea(
    val rectF: RectF = RectF(),
    val drawPath: Path = Path(),
    val animationMultiplier: Float = 0f,
    val isAnimating: Boolean = false,
    val points: PointArray = PointArray(),
    val startedSwipe: Boolean = false,
    val isSwiped: Boolean = false
)


interface OnLoginInteractionListener {
    fun onSwiped()
    fun onLoginClicked()
    fun onSignUpClicked()
    fun onAnimationStateChanged(progress: Float)
}

class LoginSwipeContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val loginBackgroundColor = context.getColor(R.color.color_secondary)
    private val signUpBackgroundColor = context.getColor(R.color.color_login_sign_in_background)

    private val backgroundPaint = Paint().apply {
        color = loginBackgroundColor
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val swipePaint = Paint().apply {
        color = context.getColor(R.color.color_background)
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val pointPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val optionPaint = TextPaint().apply {
        color = Color.BLACK
        typeface = ResourcesCompat.getFont(context, R.font.roboto_mono)
        textSize = 24.sp.toFloat()
        isAntiAlias = true
    }

    private val animatingRectF: RectF = RectF()

    private var swipeArea = SwipeArea()

    private val options = mutableListOf(
        Login().apply {
            textPaint.apply {
                set(optionPaint)
                textAlign = Paint.Align.LEFT
            }
        }, SignUp().apply {
            textPaint.apply {
                set(optionPaint)
                textAlign = Paint.Align.LEFT
            }
        }
    )

    private var selectedOption: Option = options.first()

    private val radius = 50.dp

    var onInteractionListener: OnLoginInteractionListener? = null

    private val gestureScanner: GestureDetectorCompat =
        GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent?,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (!swipeArea.startedSwipe || !swipeArea.isSwiped) {
                    val point = PointF(e1?.rawX ?: 0f, e1?.rawY ?: 0f)
                    if (swipeArea.rectF.contains(point)) {
                        if (velocityY < -100) {
                            updateSwipeArea {
                                copy(startedSwipe = true, isSwiped = false, isAnimating = false)
                            }
                            swipeAnimation.start()
                            return true
                        }
                    }
                }
                return false
            }

            override fun onDown(e: MotionEvent): Boolean = true

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                if (swipeArea.isSwiped) {
                    val point = Point(e?.rawX?.toInt() ?: 0, e?.rawY?.toInt() ?: 0)
                    val hit = options.find {
                        it.textBounds.contains(point)
                    }
                    return hit?.let {
                        if (selectedOption != it) {
                            selectedOption = it
                            when (it) {
                                is Login -> {
                                    onInteractionListener?.onLoginClicked()
                                    reverseColorAnimation.start()
                                }
                                is SignUp -> {
                                    onInteractionListener?.onSignUpClicked()
                                    colorAnimation.start()
                                }
                            }
                            true
                        } else {
                            false
                        }
                    } ?: false
                }
                return false
            }
        })

    private val swipeAnimation =
        ObjectAnimator.ofFloat(
            this,
            "AnimationMultiplier",
            .3f, .7f
        ).apply {
            duration = 800
            interpolator = OvershootInterpolator()
        }

    private val colorAnimation =
        ObjectAnimator.ofObject(
            backgroundPaint,
            "color",
            ArgbEvaluator(),
            loginBackgroundColor,
            signUpBackgroundColor
        ).apply {
            duration = 400
            addUpdateListener { invalidate() }
        }

    private val reverseColorAnimation =
        ObjectAnimator.ofObject(
            backgroundPaint,
            "color",
            ArgbEvaluator(),
            signUpBackgroundColor,
            loginBackgroundColor
        ).apply {
            duration = 400
            addUpdateListener { invalidate() }
        }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPaint(backgroundPaint)
        drawSwipeArea(canvas)
        if (swipeArea.isSwiped) {
            drawOptions(canvas)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureScanner.onTouchEvent(event).let { result ->
            if (!result) {
                event?.action == MotionEvent.ACTION_UP
            } else true
        }
    }

    private fun drawSwipeArea(canvas: Canvas?) {
        canvas?.let {
            val rect = it.clipBounds
            it.save()

            animatingRectF.set(
                rect.left.toFloat(),
                rect.top.toFloat(),
                rect.right.toFloat(),
                rect.bottom.toFloat()
            )
            it.clipRect(animatingRectF)

            swipeArea.apply {
                rectF.set(
                    left(animatingRectF),
                    top(animatingRectF),
                    right(animatingRectF),
                    bottom(animatingRectF)
                )

                points.apply {
                    start.set(rectF.left.toInt(), (rectF.top + radius).toInt())
                    end.set(rectF.right.toInt(), (rectF.top + radius).toInt())
                    one.set(rectF.centerX().toInt() / 2, (start.y - radius * 2.25).toInt())
                    two.set(
                        (rectF.centerX() * 1.5).toInt(),
                        (end.y - radius * 2.25).toInt()
                    )
                }

                val width = options.maxBy { o ->
                    o.textPaint.measureText(o.label).roundToInt()
                }?.let { o ->
                    o.textPaint.measureText(o.label).roundToInt()
                } ?: 0

                // update options draw path
                // first option is from start to mid on bezier
                // second option is from mid to end on bezier
                options.apply {
                    forEach { option ->
                        val path = option.drawPath
                        path.reset()
                        path.moveTo(points.start.x.toFloat(), points.start.y.toFloat())
                        path.cubicTo(
                            points.one.x.toFloat(), points.one.y.toFloat(),
                            points.two.x.toFloat(), points.two.y.toFloat(),
                            points.end.x.toFloat(), points.end.y.toFloat()
                        )

                        when (option) {
                            is Login -> {
                                option.textPaint.getTextBounds(
                                    option.label,
                                    0,
                                    option.label.length,
                                    option.textBounds
                                )

                                options.replace(option, option.apply {
                                    drawPath = path
                                    hOffset = swipeArea.rectF.centerX().toInt() - width.toFloat() * 1.25f
                                    vOffset = -16.dp.toFloat()
                                })

                                option.textBounds.set(
                                    option.hOffset.toInt() - option.textBounds.width() / 2,
                                    option.textBounds.height(),
                                    (option.hOffset.toInt() + option.textBounds.width()) + option.textBounds.width() / 2,
                                    swipeArea.rectF.top.toInt()
                                )
                            }
                            is SignUp -> {
                                option.textPaint.getTextBounds(
                                    option.label,
                                    0,
                                    option.label.length,
                                    option.textBounds
                                )

                                options.replace(option, option.apply {
                                    drawPath = path
                                    hOffset = swipeArea.rectF.centerX().toInt() + width.toFloat()
                                    vOffset = -16.dp.toFloat()
                                })

                                option.textBounds.set(
                                    option.hOffset.toInt() - option.textBounds.width() / 2,
                                    option.textBounds.height(),
                                    (option.hOffset.toInt() + option.textBounds.width()) + option.textBounds.width() / 2,
                                    swipeArea.rectF.top.toInt()
                                )
                            }
                        }
                    }
                }
            }

            swipeArea.draw(it)
            //drawDebugInfo(it)
            it.restore()
        }
    }

    private fun drawOptions(canvas: Canvas?) {
        options.forEach { option ->
            canvas?.drawTextOnPath(
                option.label,
                option.drawPath,
                option.hOffset,
                option.vOffset,
                option.textPaint.apply {
                    color = if (option == selectedOption) Color.WHITE else Color.BLACK
                }
            )
        }
    }

    @Suppress("unused")
    private fun setAnimationMultiplier(value: Float) {
        onInteractionListener?.onAnimationStateChanged(value.percentOf(.3f, .7f))
        val wasSwiped = swipeArea.isSwiped
        updateSwipeArea {
            copy(
                isAnimating = value != .3f || value != .7f,
                animationMultiplier = value,
                isSwiped = wasSwiped || value >= .7f
            )
        }
        val nowSwiped = swipeArea.isSwiped
        if (!wasSwiped && nowSwiped) {
            onInteractionListener?.onSwiped()
        }
        invalidate()
    }

    private fun updateSwipeArea(stateModifier: SwipeArea.() -> SwipeArea) {
        swipeArea = stateModifier.invoke(swipeArea)
    }

    private fun SwipeArea.top(rect: RectF) =
        (bottom(rect) - rect.height() * if (isAnimating) animationMultiplier else if (isSwiped) .7f else .3f)

    private fun SwipeArea.bottom(rect: RectF) = rect.bottom
    private fun SwipeArea.left(rect: RectF) = rect.left
    private fun SwipeArea.right(rect: RectF) = rect.right
    private fun SwipeArea.width(rect: RectF) = right(rect) - left(rect)
    private fun SwipeArea.draw(canvas: Canvas?) {
        drawPath.apply {
            reset()
            moveTo(points.start.x.toFloat(), points.start.y.toFloat())
            cubicTo(
                points.one.x.toFloat(), points.one.y.toFloat(),
                points.two.x.toFloat(), points.two.y.toFloat(),
                points.end.x.toFloat(), points.end.y.toFloat()
            )
            lineTo(rectF.right, rectF.bottom)
            lineTo(rectF.left, rectF.bottom)
        }

        canvas?.drawPath(drawPath, swipePaint)
    }

    private fun SwipeArea.drawPoints(canvas: Canvas?) {
        canvas?.drawCircle(points.start.x.toFloat(), points.start.y.toFloat(), 12.5f, pointPaint)
        canvas?.drawCircle(points.one.x.toFloat(), points.one.y.toFloat(), 12.5f, pointPaint)
        canvas?.drawCircle(points.two.x.toFloat(), points.two.y.toFloat(), 12.5f, pointPaint)
        canvas?.drawCircle(points.end.x.toFloat(), points.end.y.toFloat(), 12.5f, pointPaint)
    }

    private fun drawDebugInfo(canvas: Canvas?) {
        swipeArea.drawPoints(canvas)
        canvas?.drawLine(swipeArea.rectF.centerX(), 0f, swipeArea.rectF.centerX(), swipeArea.rectF.bottom, pointPaint)

    }

}

private fun Float.percentOf(min: Float, max: Float): Float {
    return ((this - min) / (max - min))
}

fun <E> Iterable<E>.replace(old: E, new: E) = map { if (it == old) new else it }

