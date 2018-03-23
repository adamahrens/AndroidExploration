package com.appsbyahrens.timefighter

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class GameActivity : AppCompatActivity() {

    // View Elements
    internal lateinit var gameScoreTextView: TextView
    internal lateinit var timeLeftTextView: TextView
    internal lateinit var tapButton: Button
    internal lateinit var currentHighScoreTextView: TextView

    // Persistence
    private lateinit var preferences : SharedPreferences

    // Game Logic
    private var gameStarted = false
    private lateinit var countDownTimer: CountDownTimer
    internal var countDownInterval: Long = 1000
    private var timeLeft = 60
    private var currentScore = 0

    // LogCat
    private val TAG = GameActivity::class.java.simpleName

    companion object {
        private val SCORE_KEY = "SCORE_KEY"
        private val TIME_LEFT_KEY = "TIME_LEFT_KEY"
        private val HIGH_SCORE_KEY = "HIGH_SCORE_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        Log.d(TAG, "onCreate called. Current score is $currentScore")

        preferences = PreferenceManager.getDefaultSharedPreferences(this)

        gameScoreTextView = findViewById<TextView>(R.id.game_score_text_view)
        timeLeftTextView = findViewById<TextView>(R.id.time_left_text_view)
        tapButton = findViewById<Button>(R.id.tap_me_button)
        currentHighScoreTextView = findViewById<TextView>(R.id.high_score_text_view)

        tapButton.setOnClickListener { view -> incrementScore() }

        val savedScore = savedInstanceState?.getInt(SCORE_KEY)
        val savedTimeLeft = savedInstanceState?.getInt(TIME_LEFT_KEY)

        if (savedScore != null && savedTimeLeft != null) {
            currentScore = savedScore!!
            timeLeft = savedTimeLeft!!
            Log.d(TAG, "Restored score to $currentScore, timeLeft to $timeLeft")
            restoreGame()
        } else {
            resetGame()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putInt(SCORE_KEY, currentScore)
        outState?.putInt(TIME_LEFT_KEY, timeLeft)
        countDownTimer.cancel()

        Log.d(TAG, "onSavedInstanceState called, Score is $currentScore, Time Left is $timeLeft")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy called")
    }

    private fun restoreGame() {
        updateScoreDisplay()
        configureTimer()
        startGame()
    }

    private fun incrementScore() {
        if (!gameStarted) this.startGame()

        currentScore += 1
        updateScoreDisplay()
    }

    private fun resetGame() {
        currentScore = 0
        timeLeft = 60
        updateScoreDisplay()
        configureTimer()
        gameStarted = false
    }

    private fun startGame() {
        countDownTimer.start()
        gameStarted = true
    }

    private fun endGame() {
        Toast.makeText(this, getString(R.string.game_over_message, Integer.toString(currentScore)), Toast.LENGTH_LONG).show()

        // Save High Score if applicable

        val highScore = preferences.getInt(HIGH_SCORE_KEY, -1)

        if (currentScore > highScore) {
            val editor = preferences.edit()
            editor.putInt(HIGH_SCORE_KEY, currentScore)
            editor.apply()

            Log.d(TAG, "Saved new high score of $currentScore")
        }

        resetGame()
    }

    private fun updateScoreDisplay() {
        val newScoreText = getString(R.string.your_score, Integer.toString(currentScore))
        gameScoreTextView.text = newScoreText

        // Check for HighScore
        val savedHighScore = preferences.getInt(HIGH_SCORE_KEY, -1)

        if (savedHighScore != -1) {
            val currentHighScore = getString(R.string.current_high_score_text, Integer.toString(savedHighScore))
            currentHighScoreTextView.text = currentHighScore

            currentHighScoreTextView.visibility = View.VISIBLE
            Log.d(TAG, "Showing saved high score of $savedHighScore")
        } else {
            currentHighScoreTextView.visibility = View.INVISIBLE

            Log.d(TAG, "Setting high score text view to invisible")
        }
    }

    private fun configureTimer() {
        Log.d(TAG, "Configuring Timer. Current Time Left is $timeLeft")
        val newTimeLeft = getString(R.string.time_left, Integer.toString(timeLeft))
        timeLeftTextView.text = newTimeLeft

        var startingCountDown = (timeLeft * 1000).toLong()

        countDownTimer = object : CountDownTimer(startingCountDown, countDownInterval) {
            override fun onFinish() {
                endGame()
            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000
                val timeLeftString = getString(R.string.time_left, Integer.toString(timeLeft))
                timeLeftTextView.text = timeLeftString
            }
        }
    }
}
