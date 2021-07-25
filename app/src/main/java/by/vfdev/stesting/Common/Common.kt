package by.vfdev.stesting.Common

import by.vfdev.stesting.RemoteModel.Question
import by.vfdev.stesting.UI.QuestionFragment

object Common {

    var TOTAL_TIME = 20*60*1000

    var answerSheetList: MutableList<CurrentQuestion> = ArrayList()
    var fragmentList: MutableList<QuestionFragment> = ArrayList()
    var selectedValues: MutableList<String> = ArrayList()

    enum class ANSWER_TYPE{
        NO_ANSWER,
        RIGHT_ANSWER,
        WRONG_ANSWER
    }
}