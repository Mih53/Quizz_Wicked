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

        // Referência ao botão "Começar"
        val startButton: Button = findViewById(R.id.start_button)

        // Evento de clique no botão "Começar"
        startButton.setOnClickListener {
            // Inicia a MainActivity (onde está o quiz)
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

        // Inicialmente, esconde o botão "Reiniciar"
        submitButton.visibility = View.GONE

        totalQuestionsTextView.text = "Total de questões: ${perguntas.questions.size}"

        // Atualiza a questão atual
        fun updateQuestion() {
            questionTextView.text = perguntas.questions[currentQuestionIndex]
            answerButtonA.text = perguntas.choices[currentQuestionIndex][0]
            answerButtonB.text = perguntas.choices[currentQuestionIndex][1]
            answerButtonC.text = perguntas.choices[currentQuestionIndex][2]
        }

        // Verifica a resposta e avança para a próxima pergunta
        fun checkAnswer(selectedAnswer: String) {
            if (selectedAnswer == perguntas.correctAnswers[currentQuestionIndex]) {
                score++
            }
            currentQuestionIndex++
            if (currentQuestionIndex < perguntas.questions.size) {
                updateQuestion()
            } else {
                // Finaliza o quiz e exibe o botão de reinício
                questionTextView.text = "Você acertou $score de ${perguntas.questions.size} perguntas!"
                answerButtonA.isEnabled = false
                answerButtonB.isEnabled = false
                answerButtonC.isEnabled = false
                submitButton.visibility = View.VISIBLE // Exibe o botão
                submitButton.text = "Reiniciar"
            }
        }

        // Configura o clique nos botões de resposta
        answerButtonA.setOnClickListener { checkAnswer(answerButtonA.text.toString()) }
        answerButtonB.setOnClickListener { checkAnswer(answerButtonB.text.toString()) }
        answerButtonC.setOnClickListener { checkAnswer(answerButtonC.text.toString()) }

        // Clique no botão "Reiniciar"
        submitButton.setOnClickListener {
            score = 0
            currentQuestionIndex = 0
            answerButtonA.isEnabled = true
            answerButtonB.isEnabled = true
            answerButtonC.isEnabled = true
            submitButton.visibility = View.GONE // Esconde o botão novamente
            updateQuestion()
        }

        // Aplica o efeito de hover com delay nos botões de resposta
        applyHoverEffect(answerButtonA)
        applyHoverEffect(answerButtonB)
        applyHoverEffect(answerButtonC)

        // Inicializa o quiz
        updateQuestion()
    }

    // Função para aplicar o efeito de hover com delay
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
