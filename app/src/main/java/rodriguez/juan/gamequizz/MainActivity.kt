package rodriguez.juan.gamequizz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import rodriguez.juan.gamequizz.databinding.ActivityMainBinding
import kotlin.math.log

private const val  TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // Handle the result
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }

    }


    //private lateinit var trueButton: Button
    //private lateinit var falseButton : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "Se llamó al onCreate")

        Log.d(TAG, "tengo un QuizViewModel: $quizViewModel")

        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        //trueButton = findViewById(R.id.true_button)
        //falseButton = findViewById(R.id.false_button)

        binding.trueButton.setOnClickListener { view: View ->

            /*
            Toast.makeText(
                this,
                R.string.correct_toast,
                Toast.LENGTH_SHORT
            ).show()

             */
            checkAnswer(true)
            // Do something in response to the click here
        }

        binding.falseButton.setOnClickListener { view: View ->


            /*
            Toast.makeText(
                this,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT
            ).show()

             */
            // do something else here to the click

            /*
            var snackbar = Snackbar.make(view, R.string.correct_toast, Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(resources.getColor(R.color.white))
            snackbar.show()

             */
            checkAnswer(false)
        }



        binding.nextButton.setOnClickListener {
            //currentIndex = (currentIndex + 1) % questionBank.size
            //val questionTextResId = questionBank[currentIndex].textResId
            //binding.questionTextView.setText(questionTextResId)

            quizViewModel.moveToNext()

            updateQuestion()
        }
        //funciona al tocar el textview en la aplicacion

        binding.cheatButton?.setOnClickListener {
            //start CheatActivity
            //val intent = Intent(this, CheatActivity::class.java)
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            //startActivity(intent)
            cheatLauncher.launch(intent)
        }

        binding.questionTextView.setOnClickListener {
            //currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.prevButton.setOnClickListener{

            quizViewModel.moveToPrev()
            updateQuestion()
        /* if(currentIndex != 0){
                currentIndex = (currentIndex - 1) % questionBank.size
                updateQuestion()
            }else{
                //do nothing :D
            }
        */
        }
        updateQuestion()
     //   val questionTextResId = questionBank[currentIndex].textResId
     //   binding.questionTextView.setText(questionTextResId)

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Se llamó onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Se llamó onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Se llamó onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Se llamó a onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Se llamó a onDestroy")
    }

    private fun checkAnswer(userAnswer: Boolean) {
        //val correctAnswer = questionBank[currentIndex].respuesta
        val correctAnswer = quizViewModel.currentQuestionAnswer
        /*
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        */
        val messageResId = when {
            userAnswer == correctAnswer -> R.string.correct_toast
            quizViewModel.isCheater -> R.string.judgment_toast
            else -> R.string.incorrect_toast
        }



        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }


    private fun updateQuestion() {
        //val questionTextResId = questionBank[currentIndex].textResId
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }
}