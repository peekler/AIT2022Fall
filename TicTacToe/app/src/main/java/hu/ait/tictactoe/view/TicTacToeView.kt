package hu.ait.tictactoe.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.ait.tictactoe.MainActivity
import hu.ait.tictactoe.R
import hu.ait.tictactoe.model.TicTacToeModel

class TicTacToeView(context: Context?, attrs: AttributeSet?)
    : View(context, attrs) {

    var paintBackGround = Paint()
    var paintLine = Paint()
    var paintText = Paint()

    var bitmapDemo = BitmapFactory.decodeResource(
        resources, R.drawable.grass
    )

    init {
        paintBackGround.color = Color.BLACK
        paintBackGround.style = Paint.Style.FILL

        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f

        paintText.color = Color.GREEN
        paintText.textSize = 100f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        paintText.textSize = height/3f

        bitmapDemo = Bitmap.createScaledBitmap(
            bitmapDemo,
            width/3,height/3,false
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas?.drawRect(0f, 0f,
            width.toFloat(), height.toFloat(), paintBackGround)

        canvas?.drawBitmap(bitmapDemo,
            width/3f,height/3f,null)

        drawGameArea(canvas)

        drawPlayers(canvas)

        canvas.drawText("5",0f,height/3f,paintText)

    }

    private fun drawGameArea(canvas: Canvas) {
        // border
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // two horizontal lines
        canvas.drawLine(
            0f, (height / 3).toFloat(), width.toFloat(), (height / 3).toFloat(),
            paintLine
        )
        canvas.drawLine(
            0f, (2 * height / 3).toFloat(), width.toFloat(),
            (2 * height / 3).toFloat(), paintLine
        )

        // two vertical lines
        canvas.drawLine(
            (width / 3).toFloat(), 0f, (width / 3).toFloat(), height.toFloat(),
            paintLine
        )
        canvas.drawLine(
            (2 * width / 3).toFloat(), 0f, (2 * width / 3).toFloat(), height.toFloat(),
            paintLine
        )
    }

    private fun drawPlayers(canvas: Canvas) {
        for (i in 0..2) {
            for (j in 0..2) {
                if (TicTacToeModel.getFieldContent(i, j) ==
                    TicTacToeModel.CIRCLE) {
                    val centerX = (i * width / 3 + width / 6).toFloat()
                    val centerY = (j * height / 3 + height / 6).toFloat()
                    val radius = height / 6 - 2

                    canvas.drawCircle(centerX, centerY, radius.toFloat(), paintLine)
                } else if (TicTacToeModel.getFieldContent(i, j)
                    == TicTacToeModel.CROSS) {
                    canvas.drawLine((i * width / 3).toFloat(), (j * height / 3).toFloat(),
                        ((i + 1) * width / 3).toFloat(),
                        ((j + 1) * height / 3).toFloat(), paintLine)

                    canvas.drawLine(((i + 1) * width / 3).toFloat(), (j * height / 3).toFloat(),
                        (i * width / 3).toFloat(), ((j + 1) * height / 3).toFloat(), paintLine)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event?.action==MotionEvent.ACTION_DOWN) {

            if ((context as MainActivity).isFlagModeOn()) {

            } else
            {

            }


            val tX = event.x.toInt() / (width / 3)
            val tY = event.y.toInt() / (height / 3)

            if (tX < 3 && tY < 3
                && TicTacToeModel.getFieldContent(tX, tY) ==
                TicTacToeModel.EMPTY)
            {
                TicTacToeModel.setFieldContent(tX, tY,
                    TicTacToeModel.getNextPlayer())

                TicTacToeModel.changeNextPlayer()

                var nextPlayer = "O"
                if (TicTacToeModel.getNextPlayer() ==
                        TicTacToeModel.CROSS) {
                    nextPlayer = "X"
                }

                (context as MainActivity).setStatusText(
                    resources.getString(R.string.next_player_text,
                     nextPlayer)
                )


                // tell Android that the view is
                // not valid any more and it should be redrawn
                // then the Android system will call the onDraw again
                invalidate()
            }

        }

        return true
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }

    public fun resetGame() {
        TicTacToeModel.resetModel()
        invalidate()
    }

}