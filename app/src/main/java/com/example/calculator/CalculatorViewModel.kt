package com.example.calculator

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class CalculatorViewModel : ViewModel() {
  private  val _equation = MutableLiveData("")
    val equationText : LiveData<String> = _equation

    private val _result = MutableLiveData("0")
    val resultText : LiveData<String> = _result

    fun onClickButton(btn : String){
        Log.i("Clicked Button",btn)

        _equation.value?.let {
            if (btn == "AC") {
                _equation.value = ""
                _result.value="0"
                return
            }
            if(btn == "C"){
                if(it.isNotEmpty()){
                    _equation.value = it.substring(0,it.length-1)
                    return
                }
            }
            if(btn == "="){
                _equation.value = _result.value
                return
            }
            _equation.value = it+btn
            try{
                _result.value = calculateResult(_equation.value.toString())
            }
            catch (_ : Exception){}
        }
    }


fun calculateResult(equation : String) : String {
    val context : Context = Context.enter()
    context.optimizationLevel = -1
    val scriptable : Scriptable = context.initStandardObjects()
    var finalResult = context.evaluateString(scriptable,equation,"Javascript",1,null).toString()
    if(finalResult.endsWith(".0")){
        finalResult = finalResult.replace(".0","")
    }
    return finalResult
  }
}