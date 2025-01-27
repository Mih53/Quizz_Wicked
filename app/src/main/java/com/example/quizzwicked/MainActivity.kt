package com.example.quizzwicked

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val startButton: Button = findViewById(R.id.start_button)

        startButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}

class MainActivity : AppCompatActivity() {

    private val perguntas = Perguntas()
    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val questionTextView: TextView = findViewById(R.id.questoes)
        val totalQuestionsTextView: TextView = findViewById(R.id.total_questoes)
        val answerButtonA: Button = findViewById(R.id.ans_A)
        val answerButtonB: Button = findViewById(R.id.ans_B)
        val answerButtonC: Button = findViewById(R.id.ans_C)
        val submitButton: Button = findViewById(R.id.submit_btn)

        submitButton.visibility = View.GONE

        totalQuestionsTextView.text = "Total de questões: ${perguntas.questions.size}"

        fun updateQuestion() {
            questionTextView.text = perguntas.questions[currentQuestionIndex]
            answerButtonA.text = perguntas.choices[currentQuestionIndex][0]
            answerButtonB.text = perguntas.choices[currentQuestionIndex][1]
            answerButtonC.text = perguntas.choices[currentQuestionIndex][2]
        }

        fun checkAnswer(selectedAnswer: String) {
            if (selectedAnswer == perguntas.correctAnswers[currentQuestionIndex]) {
                score++
            }
            currentQuestionIndex++
            if (currentQuestionIndex < perguntas.questions.size) {
                updateQuestion()
            } else {
                questionTextView.text = "Você acertou $score de ${perguntas.questions.size} perguntas!"
                answerButtonA.visibility = View.GONE
                answerButtonB.visibility = View.GONE
                answerButtonC.visibility = View.GONE
                submitButton.visibility = View.VISIBLE // Exibe o botão "Reiniciar"
                submitButton.text = "Reiniciar"
            }
        }

        answerButtonA.setOnClickListener { checkAnswer(answerButtonA.text.toString()) }
        answerButtonB.setOnClickListener { checkAnswer(answerButtonB.text.toString()) }
        answerButtonC.setOnClickListener { checkAnswer(answerButtonC.text.toString()) }

        submitButton.setOnClickListener {
            score = 0
            currentQuestionIndex = 0
            answerButtonA.visibility = View.VISIBLE
            answerButtonB.visibility = View.VISIBLE
            answerButtonC.visibility = View.VISIBLE
            submitButton.visibility = View.GONE // Esconde o botão novamente
            updateQuestion()
        }

        applyHoverEffect(answerButtonA)
        applyHoverEffect(answerButtonB)
        applyHoverEffect(answerButtonC)

        updateQuestion()
    }

    private fun applyHoverEffect(button: Button) {
        val handler = Handler(Looper.getMainLooper())
        button.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    handler.postDelayed({
                        view.isPressed = true
                    }, 100) // Delay de 100ms
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    handler.postDelayed({
                        view.isPressed = false
                    }, 100)
                }
            }
            false
        }
    }
}

