package rodriguez.juan.gamequizz

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val respuesta: Boolean)

