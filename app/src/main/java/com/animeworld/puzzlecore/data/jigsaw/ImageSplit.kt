package com.animeworld.puzzlecore.data.jigsaw

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode


object ImageSplit {
     fun splitImage(bitmap: Bitmap): ArrayList<Bitmap> {
        val piecesNumber = 50
        val rows = 5
        val cols = 10

        val pieces = ArrayList<Bitmap>(piecesNumber)

        // Calculate the with and height of the pieces
        val pieceWidth = bitmap.width / cols
        val pieceHeight = bitmap.height / rows

        // Create each bitmap piece and add it to the resulting array
        var yCoord = 0
        for (row in 0..<rows) {
            var xCoord = 0
            for (col in 0..<cols) {
                var offsetX = 0
                var offsetY = 0
                if (col > 0) {
                    offsetX = pieceWidth / 3
                }
                if (row > 0) {
                    offsetY = pieceHeight / 3
                }

                // apply the offset to each piece

                // apply the offset to each piece
                val pieceBitmap = Bitmap.createBitmap(
                    bitmap,
                    xCoord - offsetX,
                    yCoord - offsetY,
                    pieceWidth + offsetX,
                    pieceHeight + offsetY
                )
//                val piece = PuzzlePiece(getApplicationContext())
//                piece.setImageBitmap(pieceBitmap)
//                piece.xCoord = xCoord - offsetX + imageView.getLeft()
//                piece.yCoord = yCoord - offsetY + imageView.getTop()
//                piece.pieceWidth = pieceWidth + offsetX
//                piece.pieceHeight = pieceHeight + offsetY

                // this bitmap will hold our final puzzle piece image

                // this bitmap will hold our final puzzle piece image
                val puzzlePiece = Bitmap.createBitmap(
                    pieceWidth + offsetX,
                    pieceHeight + offsetY,
                    Bitmap.Config.ARGB_8888
                )

                // draw path

                // draw path
                val bumpSize = pieceHeight / 4
                val canvas = Canvas(puzzlePiece)
                val path = Path()
                path.moveTo(offsetX.toFloat(), offsetY.toFloat())
                if (row == 0) {
                    // top side piece
                    path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat())
                } else {
                    // top bump
                    path.lineTo(offsetX.toFloat() + (pieceBitmap.width - offsetX).toFloat() / 3f, offsetY.toFloat())
                    path.cubicTo(
                        offsetX + (pieceBitmap.width - offsetX).toFloat() / 6,
                        offsetY.toFloat() - bumpSize,
                        offsetX + (pieceBitmap.width - offsetX).toFloat() / 6 * 5,
                        offsetY - bumpSize.toFloat(),
                        offsetX + (pieceBitmap.width - offsetX).toFloat() / 3 * 2,
                        offsetY.toFloat()
                    )
                    path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat())
                }

                if (col == cols - 1) {
                    // right side piece
                    path.lineTo(pieceBitmap.width.toFloat(), pieceBitmap.height.toFloat())
                } else {
                    // right bump
                    path.lineTo(pieceBitmap.width.toFloat(), offsetY.toFloat() + (pieceBitmap.height - offsetY) / 3)
                    path.cubicTo(
                        pieceBitmap.width.toFloat() - bumpSize,
                        offsetY.toFloat() + (pieceBitmap.height - offsetY) / 6,
                        pieceBitmap.width.toFloat() - bumpSize,
                        offsetY.toFloat() + (pieceBitmap.height - offsetY) / 6 * 5,
                        pieceBitmap.width.toFloat(),
                        offsetY.toFloat() + (pieceBitmap.height - offsetY) / 3 * 2
                    )
                    path.lineTo(pieceBitmap.width.toFloat(), pieceBitmap.height.toFloat())
                }

                if (row == rows - 1) {
                    // bottom side piece
                    path.lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())
                } else {
                    // bottom bump
                    path.lineTo(offsetX.toFloat() + (pieceBitmap.width - offsetX) / 3 * 2, pieceBitmap.height.toFloat())
                    path.cubicTo(
                        offsetX.toFloat() + (pieceBitmap.width - offsetX) / 6 * 5,
                        pieceBitmap.height.toFloat() - bumpSize,
                        offsetX.toFloat() + (pieceBitmap.width - offsetX) / 6,
                        pieceBitmap.height.toFloat() - bumpSize,
                        offsetX.toFloat() + (pieceBitmap.width - offsetX) / 3,
                        pieceBitmap.height.toFloat()
                    )
                    path.lineTo(offsetX.toFloat(), pieceBitmap.height.toFloat())
                }

                if (col == 0) {
                    // left side piece
                    path.close()
                } else {
                    // left bump
                    path.lineTo(offsetX.toFloat(), offsetY.toFloat() + (pieceBitmap.height - offsetY) / 3 * 2)
                    path.cubicTo(
                        offsetX.toFloat() - bumpSize,
                        offsetY.toFloat() + (pieceBitmap.height - offsetY) / 6 * 5,
                        offsetX.toFloat() - bumpSize,
                        offsetY.toFloat() + (pieceBitmap.height - offsetY) / 6,
                        offsetX.toFloat(),
                        offsetY.toFloat() + (pieceBitmap.height - offsetY) / 3
                    )
                    path.close()
                }

                // mask the piece

                // mask the piece
                val paint = Paint()
                paint.color = -0x1000000
                paint.style = Paint.Style.FILL

                canvas.drawPath(path, paint)
                paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
                canvas.drawBitmap(pieceBitmap, 0f, 0f, paint)

                // draw a black border
                var border = Paint()
                border = Paint()
                border.color = -0x8000000
                border.style = Paint.Style.STROKE
                border.strokeWidth = 2.0f
                canvas.drawPath(path, border)

                // set the resulting bitmap to the piece

                // set the resulting bitmap to the piece
//                piece.setImageBitmap(puzzlePiece)

                pieces.add(puzzlePiece)
                xCoord += pieceWidth
            }
            yCoord += pieceHeight
        }

        return pieces
    }
}