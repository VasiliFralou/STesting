package by.vfdev.stesting.UI

import by.vfdev.stesting.Common.CurrentQuestion

interface IAnswerSelect {
    fun selectedAnswer(): CurrentQuestion
    fun showCorrectAnswer()
    fun disableAnswer()
    fun resetQuestion()
}