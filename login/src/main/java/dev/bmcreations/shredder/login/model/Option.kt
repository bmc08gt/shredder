package dev.bmcreations.shredder.login.model

import android.graphics.Path
import android.graphics.Rect
import android.text.TextPaint

interface Option {
    var drawPath: Path
    var textBounds: Rect
    var hOffset: Float
    var vOffset: Float
    val label: String
    val textPaint: TextPaint
}
data class Login(
    override var drawPath: Path = Path(),
    override var hOffset: Float = 0f,
    override var vOffset: Float = 0f,
    override val label: String = "Login",
    override var textBounds: Rect = Rect(),
    override val textPaint: TextPaint = TextPaint()
) : Option

data class SignUp(
    override var drawPath: Path = Path(),
    override var hOffset: Float = 0f,
    override var vOffset: Float = 0f,
    override val label: String = "Sign Up",
    override var textBounds: Rect = Rect(),
    override val textPaint: TextPaint = TextPaint()
) : Option
