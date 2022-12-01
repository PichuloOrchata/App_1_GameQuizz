package rodriguez.juan.gamequizz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"

const val IS_CHEATER_KEY = "IS_CHEATER_KEY"

class QuizViewModel (private val savedStateHandle: SavedStateHandle) : ViewModel() {
    /*init {
        Log.d(TAG, "ViewModel instance created")
    }
    */

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)
    private val questionBank = listOf(
        Question(R.string.question_cpu, true),
        Question(R.string.question_ram, false),
        Question(R.string.question_gpu, false),
        Question(R.string.question_depre, true),
        Question(R.string.question_doggo, true),
        Question(R.string.question_dulce, false),
        Question(R.string.question_xbox, true)
    )

    var isCheater: Boolean
        get() = savedStateHandle.get(IS_CHEATER_KEY) ?: false
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)


    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].respuesta
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev(){
        if(currentIndex != 0){
            currentIndex = (currentIndex - 1) % questionBank.size
        }else {
           //do nothing :D
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}
